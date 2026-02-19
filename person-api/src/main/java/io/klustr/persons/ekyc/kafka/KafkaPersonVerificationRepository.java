package io.klustr.persons.ekyc.kafka;

import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.persons.PersonVerification;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaPersonVerificationRepository {

    private static final Logger log = LoggerFactory.getLogger(KafkaPersonVerificationRepository.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaPersonVerificationRepository(KafkaMetrics metrics) {
        this.metrics = metrics;
    }


    @Bean("KafkaTemplate<PersonVerification>")
    public KafkaTemplate<PersonVerification> PersonVerification() {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<PersonVerification>(new KafkaTopic("klustr", "database.ekyc.verifications"), opts, metrics) {
            @Override
            protected String serialize(PersonVerification value) {
                return U.toJson(value);
            }

            @Override
            protected PersonVerification deserialize(String value) {
                return U.fromJson(value, PersonVerification.class);
            }
        };
    }
}
