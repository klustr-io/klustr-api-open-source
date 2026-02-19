package io.klustr.storage.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

public class DbAdapterCacheDefaultImpl<T> implements DbAdapterCache<T> {

    private static final Logger log = LoggerFactory.getLogger(DbAdapterCacheDefaultImpl.class);

    private final Cache<String, Nullable<T>> cache;

    private final Cache<String, DocumentResult<T>> query_cache;

    private final Counter hits;
    private final Counter misses;
    private final Counter invalidations;

    private final Counter writes;

    private final Counter q_hits;
    private final Counter q_misses;

    private final Counter q_writes;

    private final String table;

    public DbAdapterCacheDefaultImpl(String table) {
        this.table = table;
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(5))
                .recordStats()
                .build();

        writes = Metrics.counter("db.cache.writes", "table", table);
        hits = Metrics.counter("db.cache.hits", "table", table);
        misses = Metrics.counter("db.cache.misses", "table", table);
        invalidations = Metrics.counter("db.cache.invalidation", "table", table);

        query_cache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(5))
                .recordStats()
                .build();

        q_writes = Metrics.counter("db.query.cache.writes", "table", table);
        q_hits = Metrics.counter("db.query.cache.hits", "table", table);
        q_misses = Metrics.counter("db.query.cache.misses", "table", table);
    }


    @Override
    public void invalidate(String id, T obj) {
        cache.invalidate(id);
        this.invalidations.increment();
        this.query_cache.invalidateAll();   // have to kill everything, bit overkill but safe.
        this.query_cache.cleanUp();
    }

    @Override
    public void invalidateAll() {
        this.invalidations.increment();
        this.query_cache.invalidateAll();
        this.query_cache.cleanUp();
        this.cache.invalidateAll();
    }

    @Override
    public Nullable<T> getIfPresent(String id) {
        Nullable<T> va = cache.getIfPresent(id);
        if (va == null) {
            if (this.misses.count() > 0 && this.misses.count() % 1000 == 0) {
                if (log.isInfoEnabled()) {
                    log.info("Exceeded #{} misses on cache for table {}: Key = '{}'", this.misses.count(), this.table, id);
                }
            }
            this.misses.increment();
            return null;
        }
        this.hits.increment();
        return va;
    }

    @Override
    public Optional<T> put(String id, Optional<T> obj) {
        this.writes.increment();
        this.cache.put(id, new Nullable<T>(obj));
        return obj;
    }

    @Override
    public DocumentResult<T> tryGetCachedQuery(String key) {
        DocumentResult<T> r = query_cache.getIfPresent(key);
        if (r != null) {
            q_hits.increment();
            return r;
        } else {
            q_misses.increment();
            return null;
        }
    }

    @Override
    public void updateCacheQuery(String key, DocumentResult<T> result) {
        query_cache.put(key, result);
        q_writes.increment();
    }
}
