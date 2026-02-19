package io.klustr.console.health;

import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("rethinkdb")
public class RethinkDbHealthIndicator implements HealthIndicator {
    private final RethinkDbConnectionPool provider;

    public RethinkDbHealthIndicator(RethinkDbConnectionPool provider) {
        this.provider = provider;
    }

    @Override
    public Health health() {
        try {
            provider.getInstance().ensureDatabase("_health");
            return Health.up().build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}
