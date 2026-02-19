package io.klustr.integrations.haproxy;

import io.klustr.integrations.haproxy.acls.*;
import io.klustr.schemas.integrations.compute.LoadBalancerAclResource;
import io.klustr.schemas.integrations.compute.LoadBalancerBackendSwitchingRule;

public class HaproxyAclConverter {
    public static LoadBalancerAclResource convert(String aclName, LoadBalancerAcl acl) {
        if (acl instanceof HostnameAcl) {
            return new LoadBalancerAclResource()
                    .withCriterion("hdr_sub(host)")
                    .withAclName(aclName)
                    .withValue("-m beg " + ((HostnameAcl) acl).hostname);
        }
        if (acl instanceof SSL_FC_ServerNameIndicationAcl) {
            // use_backend mongo_foo if { req.ssl_sni -i foo.compute.dev.klustr.io }
            return new LoadBalancerAclResource()
                    .withCriterion("ssl_fc_sni")
                    .withAclName(aclName)
                    .withValue("-m sub " + ((SSL_FC_ServerNameIndicationAcl) acl).hostname);
        }
        if (acl instanceof SSL_REQ_ServerNameIndicationAcl) {
            return new LoadBalancerAclResource()
                    .withCriterion("req_ssl_sni")
                    .withAclName(aclName)
                    .withValue("-m sub " + ((SSL_REQ_ServerNameIndicationAcl) acl).hostname);
        }
        throw new RuntimeException("Unable to convert ACL '" + acl.getClass().getName() + "'");
    }

    public static LoadBalancerBackendSwitchingRule convert(LoadBalancerCondition condition) {
        if (condition instanceof LoadBalancerMatchesAclCondition) {
            return new LoadBalancerBackendSwitchingRule()
                    .withCond("if")
                    .withCondTest(((LoadBalancerMatchesAclCondition) condition).aclName)
                    .withName(((LoadBalancerMatchesAclCondition) condition).backend);
        }
        throw new RuntimeException("Unable to convert switching rule '" + condition.getClass().getName() + "'");
    }
}