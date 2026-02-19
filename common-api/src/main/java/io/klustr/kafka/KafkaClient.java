package io.klustr.kafka;

import io.klustr.kafka.events.KafkaCloseEvent;
import io.klustr.kafka.events.KafkaErrorEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jdeferred.Promise;
import org.springframework.boot.actuate.health.Health;

import java.io.Closeable;
import java.time.Duration;

/**
 * Interface required by all Kafka versions to abstract away implementation details and allow
 * clients to switch between kafka versions.
 *
 * @author Terrance A. Snyder
 */
public interface KafkaClient extends Closeable {

    /**
     * Publishes the message with the given topic
     *
     * @param topic   The topic to publish
     * @param key     The key to use for semantic partitioning of the data (for use with analytic time windows, etc)
     * @param message The byte[] array of the message you want to publish.
     */
    void publish(KafkaTopic topic, String key, String message);

    /**
     * Listens any kafka message delivered with the specific topic for the specified duration. Use
     * this method will be run inside the current process and be a sync based read.
     *
     * @param topic    The topic to subscribe to.
     * @param groupId  The group to use when subscribing
     * @param duration The duration to wait for subscription.
     * @return The promise object used to async notify of a message being delivered.
     */
    ConsumerRecords<String, String> poll(String groupId, KafkaTopic topic, Duration duration);

    /**
     * Listens any kafka message delivered with the specific topic. Use this method to setup an
     * async event loop as this will spawn an executor service. Messages are delivered on the
     * "progress" of the promise object.
     *
     * @param topic The topic to subscribe to.
     * @return The promise object used to async notify of a message being delivered.
     */
    Promise<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, String>> subscribe(String groupId, KafkaTopic topic);

    /**
     * The health of the current kafka client (connected, disconnected, etc).
     * @return
     */
    Health health();
}
