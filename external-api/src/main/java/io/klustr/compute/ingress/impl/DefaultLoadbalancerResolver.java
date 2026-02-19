package io.klustr.compute.ingress.impl;

import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.integrations.haproxy.HaproxyComputeLoadbalancerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * TODO implement with lookup to load balancer for a specific organization.
 */
@Component
public class DefaultLoadbalancerResolver implements ComputeLoadBalancerResolver {

    private ComputeLoadBalancerProvider lb;

    public DefaultLoadbalancerResolver(@Value("${compute.loadbalancer.haproxy.dataplane.url:http://loadbalancer.dev.klustr.io:5555}") String url,
                                       @Value("${compute.loadbalancer.haproxy.dataplane.usernae:admin}") String username,
                                       @Value("${compute.loadbalancer.haproxy.dataplane.password:ElUO7Pei}") String password) {
        this.lb = new HaproxyComputeLoadbalancerProvider(url, username, password);
    }

    @Override
    public Health health() {
        return this.lb.health();
    }

    @Override
    public ComputeLoadBalancerProvider resolve(String projectId, String orgId) {
        return this.lb;
    }
}
