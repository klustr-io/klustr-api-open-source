package io.klustr.integrations.haproxy.acls;

public class LoadBalancerMatchesAclCondition implements LoadBalancerCondition {

    public String aclName;
    public String backend;

    public LoadBalancerMatchesAclCondition() { }

    public LoadBalancerMatchesAclCondition(String aclName, String backend) {
        this.aclName = aclName;
        this.backend = backend;
    }

    public LoadBalancerMatchesAclCondition withAcl(String acl) {
        this.aclName = acl;
        return this;
    }

    public LoadBalancerMatchesAclCondition withBackend(String backend) {
        this.backend = backend;
        return this;
    }
}
