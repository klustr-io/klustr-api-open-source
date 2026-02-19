package io.klustr.integrations.haproxy.acls;

public class SSL_FC_ServerNameIndicationAcl implements  LoadBalancerAcl {
    public String hostname;

    public SSL_FC_ServerNameIndicationAcl(String hostname) {
        this.hostname =hostname;
    }
}
