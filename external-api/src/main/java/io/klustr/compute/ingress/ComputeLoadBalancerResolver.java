package io.klustr.compute.ingress;

import org.springframework.boot.actuate.health.Health;

/**
 * When running multiple tenents we want to have multiple load balancers
 * configured that will route traffic per domain. Preferred is each
 * organization will have its own load balancer and all routes to *.{org}.dev.klustr.io will
 * route to the specified load balancer (IP).
 */
public interface ComputeLoadBalancerResolver {

    Health health();

    /**
     * Resolves the load balancer associated with this project and orgId.
     * @param projectId The project to lookup the load balancer for
     * @param orgId The organization owning this project
     * @return The load balancer associated with this project.
     */
    public ComputeLoadBalancerProvider resolve(String projectId, String orgId);
}
