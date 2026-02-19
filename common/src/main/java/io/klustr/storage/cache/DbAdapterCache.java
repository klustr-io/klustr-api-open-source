package io.klustr.storage.cache;

import io.klustr.storage.DocumentResult;
import io.klustr.storage.Nullable;

import java.util.Optional;

/**
 * Interface for all caches that enable a cache management
 * system for data sources.
 * @param <T>
 */
public interface DbAdapterCache<T> {

    /**
     * An update made to a specific object that should trigger invalidation of that
     * object and potentially cascade and impact any lists, searches, etc.
     * @param id The id of the object change
     * @param obj The object that was changed
     */
    void invalidate(String id, T obj);

    void invalidateAll();

    /**
     * Returns the object if it exists in cache, if not null.
     * @param id The object identifier
     * @return The object or null
     */
    Nullable<T> getIfPresent(String id);

    /**
     * Updates the cache for the specified object.
     * @param id The id of the object
     * @param obj The value, we can cache null so optional incates the object was not found.
     * @return The object that was inserted.
     */
    Optional<T> put(String id, Optional<T> obj);

    /**
     * Return a query result from the specified unique key
     * @param key The key of the query (up to application to generate)
     * @return Return the document result.
     */
    DocumentResult<T> tryGetCachedQuery(String key);

    /**
     * Updates the result of a query for the specified key
     * @param key The key of the query
     * @param result The result.
     */
    void updateCacheQuery(String key, DocumentResult<T> result);
}
