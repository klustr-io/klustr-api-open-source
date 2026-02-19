package io.klustr.events;

import com.google.common.collect.Sets;
import io.klustr.kafka.KafkaClientImpl;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTopic;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jdeferred.ProgressCallback;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

public class KafkaTest {

    KafkaClientOptions config = new KafkaClientOptions()
            .withBootstrapServers("kafka.dev.klustr.io:9092");

    KafkaClientImpl driver = new KafkaClientImpl(config, new KafkaMetrics(new CompositeMeterRegistry()));

    @Test
    public void get_messages() throws Exception {
        driver.subscribe("unit_test", new KafkaTopic("test","user_locations"))
                .progress(new ProgressCallback<ConsumerRecord<String, String>>() {
                    @Override
                    public void onProgress(ConsumerRecord<String, String> record) {
                        System.out.println(record.topic() + "->" + record.value());
                    }
                });

        Thread.sleep(Duration.ofSeconds(30).getSeconds());
    }

    @Test
    public void send() throws Exception {
        Set<String> sent_uuids = Sets.newHashSet();

        Set<String> recieved_uuids = Sets.newHashSet();

        driver.subscribe("unit_test", new KafkaTopic("test","app_events")).progress(record -> {
            System.out.println("Got message -> " + record.key());
            if (sent_uuids.contains(record.key())) {
                recieved_uuids.add(record.key());
            }
        });

        for (int i = 0; i <= 10; i++) {
            String v = UUID.randomUUID().toString();
            sent_uuids.add(v);
            driver.publish( new KafkaTopic("test","app_events"), v, "{}");
        }

        while (sent_uuids.size() != recieved_uuids.size()) {
            System.out.println(".... waiting");
            Thread.sleep(Duration.ofSeconds(10).getSeconds());
        }
    }
}
