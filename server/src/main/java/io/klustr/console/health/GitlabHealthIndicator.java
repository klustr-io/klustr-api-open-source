package io.klustr.console.health;

import io.klustr.compute.ComputeUsageProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("compute.gitlab")
public class GitlabHealthIndicator implements HealthIndicator {

    private GitProjectResourceProvider git;

    public GitlabHealthIndicator(GitProjectResourceProvider git) {
        this.git = git;
    }

    @Override
    public Health health() {
        return this.git.health();
    }
}
