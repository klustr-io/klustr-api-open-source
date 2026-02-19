package io.klustr.consent.junk;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This should be replaced with a real service.
 */
public class InMemoryRateUserImpressionTrackingProvider {
    private Cache<String, Map<String, AtomicInteger>> _impressions;

    public InMemoryRateUserImpressionTrackingProvider(Duration expireAfterWrite) {
        this._impressions = CacheBuilder.newBuilder()
                .expireAfterWrite(expireAfterWrite)
                .recordStats()
                .build();
    }

    public int incrementAndGet(String userId, String namespace, String objectId) {
        Map<String, AtomicInteger> userImpressions = _impressions.getIfPresent(userId);
        if (userImpressions == null) {
            userImpressions = Maps.newConcurrentMap();
            _impressions.put(userId, userImpressions);
        }
        String key = namespace + ":" + objectId;
        AtomicInteger counter = userImpressions.get(key);
        if (counter == null) {
            counter = new AtomicInteger();
            userImpressions.put(key, counter);
        }
        return counter.incrementAndGet();
    }
}
