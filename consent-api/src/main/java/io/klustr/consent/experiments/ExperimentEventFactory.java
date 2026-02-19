package io.klustr.consent.experiments;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.utils.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExperimentEventFactory {

    private static final Logger log = LoggerFactory.getLogger(ExperimentEventFactory.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public ExperimentEventFactory(KafkaMetrics metrics) {
        this.metrics = metrics;
    }

    @Bean("KafkaTemplate<ExperimentEvent>")
    public KafkaTemplate<ExperimentEvent> build() {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<ExperimentEvent>(new KafkaTopic("klustr", "experiment_event"), opts, metrics) {
            @Override
            protected String serialize(ExperimentEvent value) {
                return U.toJson(value);
            }

            @Override
            protected ExperimentEvent deserialize(String value) {
                return U.fromJson(value, ExperimentEvent.class);
            }
        };
    }
}
