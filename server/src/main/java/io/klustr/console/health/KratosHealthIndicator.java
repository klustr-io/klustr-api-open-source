package io.klustr.console.health;

import io.klustr.integrations.ory.HydraApi;
import io.klustr.integrations.ory.KratosApi;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("kratos")
public class KratosHealthIndicator implements HealthIndicator {
    private final KratosApi provider;

    public KratosHealthIndicator(KratosApi provider) {
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
