package io.klustr.integrations.haproxy.acls;

public class HostnameAcl implements LoadBalancerAcl {
    public String hostname;

    public HostnameAcl(String hostname) {
        this.hostname = hostname;
    }

}
