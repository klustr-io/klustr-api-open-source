package io.klustr.console.health;

import io.klustr.compute.ComputeUsageProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("compute.loadbalancer")
public class LoadbalancerHealthIndicator implements HealthIndicator {

    private ComputeLoadBalancerResolver loadbalancer;

    public LoadbalancerHealthIndicator(ComputeLoadBalancerResolver loadbalancer) {
        this.loadbalancer = loadbalancer;
    }

    @Override
    public Health health() {
        return this.loadbalancer.health();
    }
}
