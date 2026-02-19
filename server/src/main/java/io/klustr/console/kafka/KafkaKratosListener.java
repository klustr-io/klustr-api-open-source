package io.klustr.console.kafka;

import io.klustr.integrations.svix.WebhookProvider;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.integrations.ory.KratosWebhookLoginPayload;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaKratosListener {

    private WebhookProvider webhooks;
    private static final Logger log = LoggerFactory.getLogger(KafkaKratosListener.class);

    public KafkaKratosListener(
            @Value(value = "${kratos.login.listener.kafka.brokers}") String bootstrapAddress,
            @Value(value = "${kratos.login.listener.kafka.consumer}") String groupId,
            WebhookProvider webhooks,
            KafkaMetrics metrics) {
        log.info("Starting kafka kratos listener");

        this.webhooks = webhooks;
        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(bootstrapAddress);
        KafkaTemplate<KratosWebhookLoginPayload> k = new KafkaTemplate<KratosWebhookLoginPayload>(new KafkaTopic("klustr", "user_login"), opts, metrics) {
            @Override
            protected String serialize(KratosWebhookLoginPayload value) {
                return U.toJson(value);
            }

            @Override
            protected KratosWebhookLoginPayload deserialize(String value) {
                return U.fromJson(value, KratosWebhookLoginPayload.class);
            }
        };
        k.listen(groupId).progress(this::handle);
    }

    public void handle(ConsumerRecord<String, KratosWebhookLoginPayload> record) {
        // TODO handle sign in
    }
}
