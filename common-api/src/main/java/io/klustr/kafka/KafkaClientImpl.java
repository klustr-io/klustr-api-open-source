package io.klustr.kafka;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.kafka.events.KafkaCloseEvent;
import io.klustr.kafka.events.KafkaErrorEvent;
import io.klustr.kafka.metrics.KafkaMetrics;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jdeferred.Promise;
import org.springframework.boot.actuate.health.Health;

import java.time.Duration;
import java.util.*;

/**
 * A driver that wraps the implementation details kafka versions and allows a consumer to be
 * agnostic to the details of the Kafka version being used.
 *
 * @author Terrance A. Snyder
 */
public class KafkaClientImpl implements KafkaClient {

    private final KafkaClientOptions config;

    private final Map<KafkaSubscription, KafkaTopicSubscription> subscriptions = Maps.newConcurrentMap();
    private final Map<KafkaTopic, KafkaTopicProducer> producers = Maps.newConcurrentMap();

    private final KafkaMetrics metrics;

    public KafkaClientImpl(KafkaClientOptions config, KafkaMetrics metrics) {
        this.config = config;
        this.metrics = metrics;
    }

    private void ensureTopicExists(KafkaTopic topic, KafkaClientOptions config) {
        try {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getServers());
            try (Admin admin = Admin.create(properties)) {
                Set<String> topics = admin.listTopics().names().get();
                if (!topics.contains(topic.canonicalId())) {
                    List<NewTopic> newTopics = Lists.newArrayList();
                    newTopics.add(new NewTopic(topic.canonicalId(), Optional.empty(), Optional.empty()));
                    admin.createTopics(newTopics);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error when trying to create topic " + topic,ex);
        }
    }

    /**
     * Publishes the specified message for the topic given.
     *
     * @param topic   The topic to publish the message to.
     * @param key     The key to publish the message with.
     * @param message The string of the message to publish.
     */
    public void publish(KafkaTopic topic, String key, String message) {
        if (!producers.containsKey(topic)) {
            synchronized (producers) {
                if (!producers.containsKey(topic)) {
                    ensureTopicExists(topic, config);
                    producers.put(topic, new KafkaTopicProducer(topic, config));
                }
            }
        }
        try {
            producers.get(topic).send(key, message);
        } catch (Exception ex) {
            this.metrics.errors(topic).increment();
            throw ex;
        } finally {
            this.metrics.writes(topic).increment();
        }
    }

    /**
     * Async notifications of events being published through kafka using promise.
     *
     * @param topic The topic to subscribe to.
     * @return A JDeferred {@link Promise} object that can be used to get events by listening to
     * 'progress'.
     */
    public Promise<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, String>> subscribe(String groupId, KafkaTopic topic) {
        KafkaSubscription subscription = new KafkaSubscription(groupId, topic);
        if (!subscriptions.containsKey(subscription)) {
            synchronized (subscriptions) {
                if (!subscriptions.containsKey(subscription)) {
                    ensureTopicExists(topic, config);
                    subscriptions.put(subscription, new KafkaTopicSubscription(config, groupId, topic));
                }
            }
        }
        return subscriptions.get(subscription).listen();
    }

    /**
     * Sync polling of records from the kafka queue.
     *
     * @param groupId  The group ID to use when polling for messages.
     * @param duration The total duration to poll for before returning.
     * @param topic    The topic to subscribe to.
     * @return A JDeferred {@link Promise} object that can be used to get events by listening to
     * 'progress'.
     */
    public ConsumerRecords<String, String> poll(String groupId, KafkaTopic topic, Duration duration) {
        KafkaSubscription subscription = new KafkaSubscription(groupId, topic);
        if (!subscriptions.containsKey(subscription)) {
            synchronized (subscriptions) {
                if (!subscriptions.containsKey(subscription)) {
                    subscriptions.put(subscription, new KafkaTopicSubscription(config, groupId, topic));
                }
            }
        }
        return subscriptions.get(subscription).poll(duration);
    }

    /**
     * Closes the subscriptions and producers.
     *
     */
    @Override
    public void close() {
        subscriptions.values().forEach(IOUtils::closeQuietly);
        producers.values().forEach(IOUtils::closeQuietly);
    }

    @Override
    public Health health() {
        return Health.up().withDetail("subscriptions", this.subscriptions.size())
                .withDetail("producers", this.producers.size())
                .withDetail("servers", this.config.getServers())
                .build();
    }
}
