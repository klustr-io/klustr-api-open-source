package io.klustr.integrations.haproxy.acls;

public class SSL_REQ_ServerNameIndicationAcl implements  LoadBalancerAcl {
    public String hostname;

    public SSL_REQ_ServerNameIndicationAcl(String hostname) {
        this.hostname =hostname;
    }
}
