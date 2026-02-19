package io.klustr.console.health;

import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("lago")
public class LagoHealthIndicator implements HealthIndicator {
    private final LagoBillingAdapter provider;

    public LagoHealthIndicator(LagoBillingAdapter provider) {
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
