package io.klustr.console.health;

import io.klustr.integrations.ory.HydraApi;
import io.klustr.integrations.ory.KetoAdminApi;
import io.klustr.integrations.ory.KetoReadApi;
import io.klustr.permissions.PermissionProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("keto")
public class KetoHealthIndicator implements HealthIndicator {
    private final PermissionProvider perms;

    public KetoHealthIndicator(PermissionProvider perms) {
        this.perms = perms;
    }

    @Override
    public Health health() {
        try {
            perms.health();
            return Health.up().build();
        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}
