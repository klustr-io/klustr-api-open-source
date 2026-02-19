package io.klustr.console.health;

import io.klustr.integrations.svix.SvixWebhookProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("svix")
public class SvixHealthIndicator implements HealthIndicator {
    private final SvixWebhookProvider provider;

    public SvixHealthIndicator(SvixWebhookProvider provider) {
        this.provider = provider;
    }

    @Override
    public Health health() {
        try {
            provider.health();
            return Health.up().build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}
