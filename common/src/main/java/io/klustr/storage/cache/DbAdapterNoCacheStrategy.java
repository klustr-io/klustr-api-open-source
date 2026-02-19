package io.klustr.storage.cache;

import io.klustr.storage.DocumentResult;
import io.klustr.storage.Nullable;

import java.util.Optional;

public class DbAdapterNoCacheStrategy<T> implements DbAdapterCache<T> {

    @Override
    public void invalidate(String id, T obj) {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public Nullable<T> getIfPresent(String id) {
        return new Nullable<>();
    }

    @Override
    public Optional<T> put(String id, Optional<T> obj) {
        // nothing
        return obj;
    }

    @Override
    public DocumentResult<T> tryGetCachedQuery(String key) {
        return null;
    }

    @Override
    public void updateCacheQuery(String key, DocumentResult<T> result) {
        // do nothing
    }
}
