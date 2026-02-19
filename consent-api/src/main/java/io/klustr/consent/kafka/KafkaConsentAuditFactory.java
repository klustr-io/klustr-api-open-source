package io.klustr.consent.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.console.consent.ConsentAudit;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsentAuditFactory {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsentAuditFactory.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaConsentAuditFactory(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<ConsentAudit>")
    public KafkaTemplate<ConsentAudit> build() {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<ConsentAudit>(new KafkaTopic("klustr", "database.consent.consent_audit"), opts, metrics) {
            @Override
            protected String serialize(ConsentAudit value) {
                return U.toJson(value);
            }

            @Override
            protected ConsentAudit deserialize(String value) {
                return U.fromJson(value, ConsentAudit.class);
            }
        };
    }
}
