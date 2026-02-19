package io.klustr.events.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.utils.Json;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaApplicationEventConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaApplicationEventConfiguration.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaApplicationEventConfiguration(KafkaMetrics metrics) {
        this.metrics = metrics;
    }


    @Bean("KafkaTemplate<ApplicationEvent>")
    public KafkaTemplate<ApplicationEvent> ApplicationEvent(MeterRegistry registry) {

        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<ApplicationEvent>(new KafkaTopic("klustr", "database.app_events"), opts, metrics) {
            @Override
            protected String serialize(ApplicationEvent value) {
                return Json.toJson(value);
            }

            @Override
            protected ApplicationEvent deserialize(String value) {
                return Json.parse(value, ApplicationEvent.class);
            }
        };
    }
}
