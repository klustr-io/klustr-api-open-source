package io.klustr.console.health;

import io.klustr.integrations.kong.KongServiceRegistry;
import io.klustr.integrations.ory.HydraApi;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("hydra")
public class HydraHealthIndicator implements HealthIndicator {
    private final HydraApi provider;

    public HydraHealthIndicator(HydraApi provider) {
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
