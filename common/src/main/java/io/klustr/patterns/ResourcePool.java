package io.klustr.patterns;

import avro.shaded.com.google.common.collect.Maps;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Common pattern to issue and manage connections or other
 * resources which are constrained such as a database connection,
 * kafka, etc.
 * @param <T> The type of thing.
 */
public class ResourcePool<T> {
    private static final Logger log = LoggerFactory.getLogger(ResourcePool.class);
    private final ResourcePoolConfiguration config;
    private final Semaphore semaphore;
    private final Map<T, Long> activeResources;
    private final Supplier<T> resourceSupplier;
    private final ScheduledExecutorService executor;
    private final String name;

    public ResourcePool(String name, ResourcePoolConfiguration config, Supplier<T> resourceSupplier) {
        this.name = name;
        this.config = config;
        this.resourceSupplier = resourceSupplier;
        this.semaphore = new Semaphore(config.getMaxConnections());
        this.activeResources = Maps.newConcurrentMap();
        this.executor = Executors.newSingleThreadScheduledExecutor(// Custom thread factory to include thread ID in the name
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName(name + "-resource-pool-" + thread.getName());
                    return thread;
                });

        // Schedule idle resource cleanup
         this.executor.scheduleAtFixedRate(this::cleanupIdleResources, config.getIdleTimeoutMillis(), config.getIdleTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    public T getResource()  {
        try {
            if (this.getAvailableCount() <= 0) {
                String s = "Resource [" + this.name + "] has reached maximum available count. Threads will block.";
                log.warn(s);
            }
            semaphore.acquire(); // Block if maxConnections reached
            T resource = resourceSupplier.get();
            activeResources.put(resource, System.currentTimeMillis());
            return resource;
        } catch (InterruptedException ex) {
            throw new RuntimeException("Could not issue resource from resource pool " + this.name,ex);
        } finally {
            // do nothing
        }
    }

    public int getActiveCount() {
        return this.activeResources.size();
    }

    public int getAvailableCount() {
        return config.getMaxConnections() - this.activeResources.size();
    }

    public void releaseResource(T resource) {
        try {
            if (activeResources.size() > config.getMinConnections() && activeResources.remove(resource) != null) {
                // Optionally close the resource if necessary
                if (resource instanceof AutoCloseable) {
                    try {
                        ((AutoCloseable) resource).close();
                    } catch (Exception e) {
                        System.err.println("Error closing resource: " + e.getMessage());
                    }
                }
            } else {
                // If not removing, update the last-used time to keep it active
                activeResources.put(resource, System.currentTimeMillis());
            }
        } finally {
            // Always release the semaphore, regardless of whether the resource was closed
            semaphore.release();
        }
    }

    private void cleanupIdleResources() {
        long now = System.currentTimeMillis();
        activeResources.forEach((resource, lastUsed) -> {
            if (activeResources.size() > config.getMinConnections() && now - lastUsed > config.getIdleTimeoutMillis()) {
                if (activeResources.remove(resource) != null) {
                    semaphore.release();
                    if (resource instanceof AutoCloseable) {
                        try {
                            ((AutoCloseable) resource).close();
                        } catch (Exception e) {
                            System.err.println("Error closing idle resource: " + e.getMessage());
                        }
                    }
                }
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
        activeResources.clear();
    }
}
