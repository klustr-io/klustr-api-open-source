package io.klustr.consent.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaUserApplicationConsentFactory {

    private static final Logger log = LoggerFactory.getLogger(KafkaUserApplicationConsentFactory.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaUserApplicationConsentFactory(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<UserApplicationConsent>")
    public KafkaTemplate<UserApplicationConsent> build() {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<UserApplicationConsent>(new KafkaTopic("klustr", "database.consent.user_app_consent"), opts, metrics) {
            @Override
            protected String serialize(UserApplicationConsent value) {
                return U.toJson(value);
            }

            @Override
            protected UserApplicationConsent deserialize(String value) {
                return U.fromJson(value, UserApplicationConsent.class);
            }
        };
    }
}
