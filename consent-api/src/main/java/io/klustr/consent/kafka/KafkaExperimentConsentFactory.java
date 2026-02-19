package io.klustr.consent.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaExperimentConsentFactory {

    private static final Logger log = LoggerFactory.getLogger(KafkaExperimentConsentFactory.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaExperimentConsentFactory(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<UserExperimentConsent>")
    public KafkaTemplate<UserExperimentConsent> build() {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<UserExperimentConsent>(new KafkaTopic("klustr", "database.user_experiment_consent"), opts, metrics) {
            @Override
            protected String serialize(UserExperimentConsent value) {
                return U.toJson(value);
            }

            @Override
            protected UserExperimentConsent deserialize(String value) {
                return U.fromJson(value, UserExperimentConsent.class);
            }
        };
    }
}
