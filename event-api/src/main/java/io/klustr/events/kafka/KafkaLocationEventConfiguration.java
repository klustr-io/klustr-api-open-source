package io.klustr.events.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.events.LocationEvent;
import io.klustr.utils.Json;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaLocationEventConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaLocationEventConfiguration.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private KafkaMetrics metrics;

    public KafkaLocationEventConfiguration(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<LocationEvent>")
    public KafkaTemplate<LocationEvent> LocationEvent(MeterRegistry registry) {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<LocationEvent>(new KafkaTopic("klustr", "database.events.user_locations_events"), opts, metrics) {
            @Override
            protected String serialize(LocationEvent value) {
                return Json.toJson(value);
            }

            @Override
            protected LocationEvent deserialize(String value) {
                return Json.parse(value, LocationEvent.class);
            }
        };
    }
}
