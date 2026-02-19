package io.klustr.integrations.growthbook.responses;

public abstract class GrowthBookPaginationResult {
    public int limit;
    public int offset;
    public int count;
    public int total;
    public boolean hasMore;
}
