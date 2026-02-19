package io.klustr.compute.ingress;

import io.klustr.integrations.haproxy.acls.LoadBalancerAcl;
import io.klustr.integrations.haproxy.acls.LoadBalancerCondition;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.compute.*;
import org.springframework.boot.actuate.health.Health;

import java.util.List;
import java.util.Set;

public interface ComputeLoadBalancerProvider {

    Health health();

    /**
     * Will guess the front end to use based on the port and protocol defined by the user.
     * @param port THe port defined
     * @return The port, limited, and will always default to HTTPS if unknown.
     */
    FrontEnd guessFrontEndFromPort(int port);

    /**
     * Gets the current compute ingress information for the specified project
     * @param projectId The project ID to get the status against
     * @return The compute ingress status (always not null).
     */
    ComputeIngress getStatus(String projectId);

    /**
     * Returns the public IPs for the specified load balancer for routing/
     * @return The public IPs for this load balancer.
     */
    Set<String> getPublicIPV4Addresses();

    List<LoadBalancerBackendResource> getBackends();

    void createBackendIfNotExists(LoadBalancerBackendResource resource);

    List<LoadBalancerBackendServer> getBackendServers(String backend);

    void patchBackendServers(String backend, List<LoadBalancerBackendServer> servers);

    void removeServerFromBackend(String backend, String server);

    void addServerToBackend(String backend, LoadBalancerBackendServer server);

    List<LoadBalancerAclResource> getAcls(FrontEnd frontEnd);

    void deleteBackend(FrontEnd frontEnd, String name);

    List<LoadBalancerBackendSwitchingRule> getBackendSwitchingRules(FrontEnd frontEnd);

    void addBackendSwitchingRule(FrontEnd frontEnd, LoadBalancerCondition rule);

    void deleteBackendSwitchingRule(FrontEnd frontEnd, LoadBalancerBackendSwitchingRule rule);

    void addAclIfNotExists(FrontEnd frontEnd,  String aclName, LoadBalancerAcl acl);

    void deleteExistingAcl(FrontEnd frontEnd,  String aclName);

}
