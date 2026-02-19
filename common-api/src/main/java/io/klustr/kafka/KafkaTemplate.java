package io.klustr.kafka;

import com.google.common.collect.Lists;
import io.klustr.kafka.events.KafkaCloseEvent;
import io.klustr.kafka.events.KafkaErrorEvent;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.utils.U;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;

import java.time.Duration;
import java.util.List;

/**
 * Enables a person to quickly connect and write and read from Kafka.
 *
 * @param <V> The type of object being sent.
 */
public abstract class KafkaTemplate<V> {

    private static final Logger log = LoggerFactory.getLogger(KafkaTemplate.class);

    private final KafkaClient kafka;

    private final KafkaTopic topic;

    private final KafkaMetrics metrics;

    private final DeferredObject<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, V>> dfd = new DeferredObject<>();

    public KafkaTemplate(KafkaTopic topic, KafkaClientOptions opts, KafkaMetrics metrics) {
        this.topic = topic;
        this.metrics = metrics;
        this.kafka = new KafkaClientImpl(opts, metrics);
        log.info("Susbcribed to topic {} with kafka servers {}", topic.canonicalId(), opts.getServers());
    }

    public void send(String key, V value) {
        log.info("Sending {} with key {} and value {}", topic.canonicalId(), key, U.toJson(value));
        this.kafka.publish(topic, key, serialize(value));
    }

    public List<ConsumerRecord<String, V>> poll(String groupId, Duration duration) {
        List<ConsumerRecord<String, V>> results = Lists.newArrayList();
        this.kafka.poll(groupId, topic, duration).forEach(record -> {
            log.info("Received on topic {} a message with key {}", record.topic(), record.key());
            metrics.reads(this.topic).increment();
            ConsumerRecord<String, V> typed = new ConsumerRecord<>(record.topic(), record.partition(), record.offset(), record.key(), deserialize(record.value()));
            results.add(typed);
        });
        return results;
    }

    public Promise<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, V>> listen(String groupId) {
        this.kafka.subscribe(groupId, topic).progress(record -> {
            log.info("Received on topic {} a message with key {}", record.topic(), record.key());
            metrics.reads(this.topic).increment();
            ConsumerRecord<String, V> typed = new ConsumerRecord<>(record.topic(), record.partition(), record.offset(), record.key(), deserialize(record.value()));
            dfd.notify(typed);
        });
        return dfd.promise();
    }

    protected abstract String serialize(V value);

    protected abstract V deserialize(String value);

    public Health health() {
        return this.kafka.health();
    }
}
