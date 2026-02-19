package io.klustr.console.internal;

import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.integrations.ory.KratosWebhookLoginPayload;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.utils.U;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaLoginEventConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaLoginEventConfiguration.class);
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final KafkaMetrics metrics;

    public KafkaLoginEventConfiguration(KafkaMetrics metrics) { this.metrics = metrics;}


    @Bean("KafkaTemplate<KratosWebhookLoginPayload>")
    public KafkaTemplate<KratosWebhookLoginPayload> KratosWebhookLoginPayload(MeterRegistry registry) {
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(this.bootstrapAddress);
        return new KafkaTemplate<KratosWebhookLoginPayload>(new KafkaTopic("klustr","user_login"), opts, metrics) {
            @Override
            protected String serialize(KratosWebhookLoginPayload value) {
                return U.toJson(value);
            }

            @Override
            protected KratosWebhookLoginPayload deserialize(String value) {
                return U.fromJson(value, KratosWebhookLoginPayload.class);
            }
        };
    }
}
