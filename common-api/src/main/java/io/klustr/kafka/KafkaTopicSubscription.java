package io.klustr.kafka;

import com.google.common.collect.Lists;
import io.klustr.kafka.events.KafkaCloseEvent;
import io.klustr.kafka.events.KafkaErrorEvent;
import io.klustr.kafka.events.KafkaPollErrorEvent;
import io.klustr.kafka.events.KafkaRecordErrorEvent;
import io.klustr.patterns.ThreadPoolConfiguration;
import io.klustr.patterns.ThreadPoolType;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages the connection and subscription to a specific kafka topic.
 */
public class KafkaTopicSubscription implements Closeable {

    private final KafkaConsumer<String, String> consumer;

    private final KafkaTopic topic;

    private final DeferredObject<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, String>> dfd = new DeferredObject<>();

    private final ExecutorService executor;

    private final AtomicBoolean alive = new AtomicBoolean(true);

    public KafkaTopicSubscription(KafkaClientOptions config, String groupId, KafkaTopic topic) {
        this.topic = topic;
        this.consumer = config.consumer(groupId);
        this.executor = ThreadPoolConfiguration.newBuilder()
                .withPoolSize(1)
                .withCoreSize(1)
                .withType(ThreadPoolType.FixedThreadPool)
                .build()
                .create();
    }

    private ConsumerRecords<String, String> read(KafkaConsumer<String, String> consumer, Duration duration) throws IOException {
        ConsumerRecords<String, String> records = null;
        try {
            records = consumer.poll(duration);
        } catch (Exception ex) {
            this.close();
            throw new IOException(ex);
        }
        return records;
    }

    public ConsumerRecords<String, String> poll(Duration duration) {
        consumer.subscribe(Lists.newArrayList(topic.canonicalId()));
        return consumer.poll(duration);
    }

    public Promise<KafkaCloseEvent, KafkaErrorEvent, ConsumerRecord<String, String>> listen() {

        consumer.subscribe(Lists.newArrayList(topic.canonicalId()));

        executor.submit(new Runnable() {
            @Override
            public void run() {
                while (alive.get()) {
                    try {
                        ConsumerRecords<String, String> records = read(consumer, Duration.ofSeconds(5));
                        if (records != null) {
                            for (ConsumerRecord<String, String> record : records) {
                                try {
                                    dfd.notify(record);
                                } catch (Exception ex) {
                                    dfd.reject(new KafkaRecordErrorEvent(record, ex));
                                }
                            }
                        }
                    } catch (Exception ex) {
                        dfd.reject(new KafkaPollErrorEvent(ex));
                    }
                }
            }
        });

        return dfd.promise();
    }

    @Override
    public void close() throws IOException {
        alive.set(false);   // close this to exit the loop
        IOUtils.closeQuietly(this.consumer);
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            // ignore
            throw new IOException(ex);
        }
        executor.shutdown();
    }
}
