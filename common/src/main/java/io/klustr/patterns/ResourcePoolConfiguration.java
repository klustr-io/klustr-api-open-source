package io.klustr.patterns;

public class ResourcePoolConfiguration {
    private int maxConnections = 1000;
    private int minConnections = 1;
    private long idleTimeoutMillis =  30 * 1000;

    public ResourcePoolConfiguration setMaxConnections(int v) {
        this.maxConnections = v;
        return this;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public ResourcePoolConfiguration setMinConnections(int v) {
        this.minConnections = v;
        return this;
    }

    public int getMinConnections() {
        return this.minConnections;
    }

    public ResourcePoolConfiguration setIdleTimeoutMillis(long v) {
        this.idleTimeoutMillis = v;
        return this;
    }

    public long getIdleTimeoutMillis() {
        return this.idleTimeoutMillis;
    }
}
