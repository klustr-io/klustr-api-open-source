package io.klustr.storage;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Create a {@link DbAdapterListener} that for specific schemas and tables.
 */
public interface DbAdapterBroadcasterFactory {

    /**
     * Creates a new {@link DbAdapterListener} given the specified object, schema, and table.
     *
     * @param clazz The class that is being saved or persisted.
     * @param table The table that this is realted to
     * @param <T>   The Type of object being saved.
     * @return The object that was broadcast
     */
    <T> DbAdapterListener<T> create(Class<T> clazz, String database, String table);
}
