package io.klustr.console.health;

import io.klustr.compute.dns.ComputeDnsProvider;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("compute.dns")
public class DnsHealthIndicator implements HealthIndicator {

    private final ComputeDnsProvider dns;

    public DnsHealthIndicator(ComputeDnsProvider dns) {
        this.dns = dns;
    }

    @Override
    public Health health() {
        return dns.health();
    }
}
