package io.klustr.console.health;

import io.klustr.compute.ComputeUsageProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("compute.portainer")
public class PortainerHealthIndicator implements HealthIndicator {
    private PortainerComputeResourceProvider portainer;

    public PortainerHealthIndicator(PortainerComputeResourceProvider portainer) {
        this.portainer = portainer;
    }

    @Override
    public Health health() {
        return this.portainer.health();
    }
}
