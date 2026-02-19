package io.klustr.console.health;

import io.klustr.compute.ComputeUsageProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("compute.usage")
public class ComputeUsageHealthIndicator implements HealthIndicator {

    private ComputeUsageProvider usage;

    public ComputeUsageHealthIndicator(
                                  ComputeUsageProvider usage) {
        this.usage = usage;
    }

    @Override
    public Health health() {
        return this.usage.health();
    }
}
