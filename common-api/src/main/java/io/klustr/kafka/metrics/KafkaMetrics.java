package io.klustr.kafka.metrics;

import com.google.common.collect.Maps;
import io.klustr.kafka.KafkaTopic;
import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaMetrics {

    private final MeterRegistry registry;

    public KafkaMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    /**
     * Holds per instance of our topic counters which would include namespace and topic.
     *
     * @param writes
     * @param errors
     */
    private static record Instance (Counter writes, Counter errors, Counter reads) {

    }

    private static final Map<KafkaTopic, Instance> _cache = Maps.newConcurrentMap();

    private Instance singleton(KafkaTopic topic) {
        Instance r = _cache.get(topic);
        if (r != null) return r;
        synchronized (_cache) {
            r = _cache.get(topic);
            if (r != null) return r;

            Tags tags = Tags.of(Tag.of("topic", topic.topicName()), Tag.of("org_id", topic.orgId()));
            Counter writes = registry.counter("kafka.messsage.writes", tags);
            Counter errors = registry.counter("kafka.messsage.errors", tags);
            Counter reads = registry.counter("kafka.messsage.reads", tags);

            Instance value = new Instance(writes, errors, reads);
            _cache.put(topic, value);
            return value;
        }
    }

    public Counter reads(KafkaTopic topic) { return singleton(topic).reads; }

    public Counter writes(KafkaTopic topic) { return singleton(topic).writes; }

    public Counter errors(KafkaTopic topic) { return singleton(topic).errors; }

}
