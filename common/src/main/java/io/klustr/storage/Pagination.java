package io.klustr.storage;

public class Pagination {
    public int limit;
    public int start;
    public boolean cache = true;

    public Pagination() {
        this.limit = 10;
        this.start = 0;
    }

    public static Pagination all() {
        return new Pagination().withLimit(1024).withStart(0);
    }

    public static Pagination limit(int limit) {
        return new Pagination().withLimit(limit).withStart(0);
    }

    public Pagination withLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public Pagination withCache(boolean c) {
        this.cache = c;
        return this;
    }

    public Pagination withStart(int start) {
        this.start = start;
        return this;
    }
}
