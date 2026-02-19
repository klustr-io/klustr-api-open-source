package io.klustr.kafka;

import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.io.IOException;

/**
 * Manages a connection to a specific topic in kafka and enables
 * publishing messages to this topic.
 */
public class KafkaTopicProducer implements Closeable {

    private final KafkaProducer<String, String> producer;
    private final KafkaTopic topic;

    public KafkaTopicProducer(KafkaTopic topic, KafkaClientOptions config) {
        this.topic = topic;
        this.producer = config.producer();
    }


    public void send(String key, String body) {
        this.producer.send(new ProducerRecord<>(topic.canonicalId(), key, body));
    }

    @Override
    public void close() throws IOException {
        // flush data - synchronous
        producer.flush();

        // close it out
        IOUtils.closeQuietly(this.producer);
    }
}
