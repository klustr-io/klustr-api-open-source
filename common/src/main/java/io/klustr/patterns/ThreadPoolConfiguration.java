package io.klustr.patterns;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A generic thread pool configuration object used by our spring
 * configuration to tune and tweak various aspects of threading
 * to get the performance needed for our POS message rates as well
 * as other key areas where we want special parallelization.
 *
 * @author Terrance A. Snyder
 */
public class ThreadPoolConfiguration {

    public int coreSize = Runtime.getRuntime().availableProcessors() * 4;
    public int poolSize = 1000;
    public int timeout = 5;
    public TimeUnit timeunit = TimeUnit.SECONDS;
    public ThreadPoolType type = ThreadPoolType.CacheThreadPool;

    private BlockingQueue<Runnable> taskQ;

    public ThreadPoolConfiguration() {
    }

    private ThreadPoolConfiguration(Builder builder) {
        this(builder.coreSize, builder.poolSize, builder.timeout, builder.timeunit, builder.type, builder.taskQ);
    }

    public ThreadPoolConfiguration(int coreSize, int poolSize, int timeout, TimeUnit timeunit) {
        this(coreSize, poolSize, timeout, timeunit, ThreadPoolType.CacheThreadPool);
    }

    public ThreadPoolConfiguration(int coreSize, int poolSize, int timeout, TimeUnit timeunit, ThreadPoolType type) {
        this(coreSize, poolSize, timeout, timeunit, type, null);
    }

    public ThreadPoolConfiguration(int coreSize, int poolSize, int timeout, TimeUnit timeunit, ThreadPoolType type, BlockingQueue<Runnable> taskQ) {
        this.coreSize = coreSize;
        this.poolSize = poolSize;
        this.timeout = timeout;
        this.timeunit = timeunit;
        this.type = type;
        this.taskQ = taskQ;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public ExecutorService create(final String name) {
        return this.create(name, new ThreadPoolExecutor.DiscardPolicy(), this::handle);
    }

    public ExecutorService create() {
        return this.create(UUID.randomUUID().toString(), new ThreadPoolExecutor.DiscardPolicy(), this::handle);
    }

    public ExecutorService create(final String name, final RejectedExecutionHandler handler) {
        return this.create(name, handler, this::handle);
    }

    private void handle(Thread t, Throwable e) {
        // ignore
    }

    public ExecutorService create(final String name, final RejectedExecutionHandler handler, final UncaughtExceptionHandler exHandler) {

        ThreadFactory tf = new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                String id = name + "-" + count.incrementAndGet();
                Thread t = new Thread(r, id);
                t.setUncaughtExceptionHandler(exHandler);
                t.setDaemon(true);
                return t;
            }
        };


        BlockingQueue<Runnable> taskQ = this.taskQ;
        if (type == ThreadPoolType.ArrayBlockingQueue && taskQ == null) {
            taskQ = new ArrayBlockingQueue<>(this.coreSize);    // TODO tune me
        } else if (type == ThreadPoolType.LinkedBlockingQueue && taskQ == null) {
            taskQ = new LinkedBlockingQueue<>();
        } else if (type == ThreadPoolType.SynchronousQueue && taskQ == null) {
            taskQ = new SynchronousQueue<>();
        } else if (type == ThreadPoolType.CacheThreadPool) {
            return Executors.newCachedThreadPool(tf);
        } else if (type == ThreadPoolType.FixedThreadPool) {
            return Executors.newFixedThreadPool(this.coreSize, tf);
        }

        ThreadPoolExecutor pool = new ThreadPoolExecutor(this.coreSize,
                this.poolSize,
                this.timeout,
                this.timeunit,
                taskQ,
                tf);

        pool.setRejectedExecutionHandler(handler);
        pool.setThreadFactory(tf);

        return pool;
    }

    public static class Builder {
        private int coreSize = 1;
        private int poolSize = Runtime.getRuntime().availableProcessors();
        private int timeout = 5;
        private TimeUnit timeunit = TimeUnit.SECONDS;
        private ThreadPoolType type = ThreadPoolType.CacheThreadPool;
        private BlockingQueue<Runnable> taskQ;

        private Builder() {

        }

        public Builder withTimeout(int value) {
            this.timeout = value;
            return this;
        }

        public Builder withTimeUnit(TimeUnit unit) {
            this.timeunit = unit;
            return this;
        }

        public Builder withCoreSize(int size) {
            this.coreSize = size;
            return this;
        }

        public Builder withPoolSize(int size) {
            this.poolSize = size;
            return this;
        }

        public Builder withBlockingQueue(BlockingQueue<Runnable> taskQ) {
            this.taskQ = taskQ;
            return this;
        }

        public ThreadPoolConfiguration build() {
            return new ThreadPoolConfiguration(this);
        }

        public Builder withType(ThreadPoolType type) {
            this.type = type;
            return this;
        }
    }
}
