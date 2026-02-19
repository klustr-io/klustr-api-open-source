package io.klustr.console.health;

import io.klustr.console.kafka.KafkaKratosListener;
import io.klustr.console.kafka.KafkaUserApplicationConsentListener;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.kafka.KafkaClientOptions;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component("kafka")
public class KafkaHealthIndicator implements HealthIndicator {

    private final KafkaClientOptions config;

    public KafkaHealthIndicator(KafkaClientOptions config) {
        this.config = config;
    }

    @Override
    public Health health() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getServers());

        try (Admin admin = Admin.create(props)) {
            ListTopicsResult result = admin.listTopics();
            Set<String> topics = result.names().get(); // wait for the result
            return Health.up()
                    .withDetail("bootstrapServers", config.getServers())
                    .withDetail("topicCount", topics.size())
                    .build();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return Health.down(e).withDetail("bootstrapServers", config.getServers()).build();
        } catch (Exception e) {
            return Health.down(e).withDetail("bootstrapServers", config.getServers()).build();
        }
    }
}
