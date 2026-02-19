package io.klustr.console.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.events.EventInviteUserRequest;
import io.klustr.utils.U;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaEventInviteUserToOrganization {
    private static final Logger log = LoggerFactory.getLogger(KafkaEventInviteUserToOrganization.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaEventInviteUserToOrganization(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<EventInviteUserRequest>")
    public KafkaTemplate<EventInviteUserRequest> getQueue(MeterRegistry registry) {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<EventInviteUserRequest>(new KafkaTopic("klustr", "database.org.invites"), opts, metrics) {
            @Override
            protected String serialize(EventInviteUserRequest value) {
                return U.toJson(value);
            }

            @Override
            protected EventInviteUserRequest deserialize(String value) {
                return U.fromJson(value, EventInviteUserRequest.class);
            }
        };
    }
}
