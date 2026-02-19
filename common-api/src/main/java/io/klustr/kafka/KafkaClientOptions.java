package io.klustr.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaClientOptions {
    private String servers;

    // linger.ms increase to 10–100 (default 0)
    private int linger_ms = 10;

    // batch.size increase to 100000–200000 (default 16384)
    private int batch_size = 100000;

    // compression.type=gzip
    private String compression_type = "gzip";

    // acks=1 (default all, since Apache Kafka version 3.0)
    private String acks = "1";

    public KafkaClientOptions() {
    }

    public KafkaClientOptions setServers(String servers) {
        this.servers = servers;
        return this;
    }

    public String getServers() {
        return this.servers;
    }

    public KafkaClientOptions withCompression(String compression_type) {
        this.compression_type = compression_type;
        return this;
    }

    public KafkaClientOptions withBootstrapServers(String servers) {
        this.servers = servers;
        return this;
    }

    public KafkaClientOptions withBatchSize(int batch_size) {
        this.batch_size = batch_size;
        return this;
    }

    public KafkaClientOptions withLingerMs(int linger_ms) {
        this.linger_ms = linger_ms;
        return this;
    }

    public KafkaConsumer<String, String> consumer(String groupId) {

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);

        // consumer
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<>(properties);
    }

    public KafkaProducer<String, String> producer() {
        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);

        // producer
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // tune
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, this.compression_type);
        properties.setProperty(ProducerConfig.ACKS_CONFIG, this.acks);
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(this.batch_size));
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, String.valueOf(this.linger_ms));

        return new KafkaProducer<>(properties);
    }
}
