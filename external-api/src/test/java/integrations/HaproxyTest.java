package integrations;

import com.google.common.collect.Lists;
import io.klustr.compute.ingress.FrontEnd;
import io.klustr.integrations.haproxy.HaproxyComputeLoadbalancerProvider;
import io.klustr.integrations.haproxy.acls.HostnameAcl;
import io.klustr.integrations.haproxy.acls.LoadBalancerMatchesAclCondition;
import io.klustr.integrations.haproxy.acls.SSL_FC_ServerNameIndicationAcl;
import io.klustr.schemas.integrations.compute.*;
import io.klustr.utils.Json;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HaproxyTest {

    HaproxyComputeLoadbalancerProvider p = new HaproxyComputeLoadbalancerProvider("http://loadbalancer.dev.klustr.io:5555", "admin", "ElUO7Pei");

    @Test
    public void we_can_get_ports() throws Exception {
        List<LoadBalancerBackendServer> servers = this.p.getBackendServers("developed-impala-6aee31");
        assertThat(servers.isEmpty()).isFalse();
        System.out.println(Json.toJsonPrettyFormat(servers));
    }

    @Test
    public void we_can_read_frontend_mongo_sni() throws Exception {

        /**
         * [ {
         *   "name" : "kafka_client",
         *   "mode" : "tcp"
         * }, {
         *   "name" : "mongo_in",
         *   "mode" : "tcp"
         * }, {
         *   "name" : "prometheus",
         *   "mode" : "http"
         * }, {
         *   "name" : "redis_client",
         *   "mode" : "tcp"
         * }, {
         *   "name" : "rethinkdb_client",
         *   "mode" : "tcp"
         * }, {
         *   "name" : "https_in",
         *   "mode" : "http"
         * } ]
         */
        List<LoadBalancerFrontendResource> frontEnds = this.p.getFrontEnds();
        System.out.println(Json.toJsonPrettyFormat(frontEnds));

        /**
         * [ {
         *   "crt_list" : "/usr/local/etc/haproxy/crt.txt",
         *   "ssl" : true,
         *   "name" : "*:27017",
         *   "address" : "*"
         * } ]
         */
        List<LoadBalancerFrontendBindResource> binds = this.p.getFrontEndsBinds(FrontEnd.mongo_in);
        System.out.println(Json.toJsonPrettyFormat(binds));

        /**
         * [ {
         *   "acl_name" : "visiting_acl",
         *   "criterion" : "ssl_fc_sni",
         *   "value" : "-m sub visiting"
         * }, {
         *   "acl_name" : "pinniped_acl",
         *   "criterion" : "ssl_fc_sni",
         *   "value" : "-m sub architectural"
         * } ]
         */
        List<LoadBalancerAclResource> acls = this.p.getAcls(FrontEnd.mongo_in);
        System.out.println(Json.toJsonPrettyFormat(acls));

        /**
         * [ {
         *   "cond" : "if",
         *   "cond_test" : "visiting_acl",
         *   "name" : "visiting-slug-330010_backend"
         * }, {
         *   "cond" : "if",
         *   "cond_test" : "pinniped_acl",
         *   "name" : "architectural-pinniped-0055fb_backend"
         * } ]
         */
        List<LoadBalancerBackendSwitchingRule> rules = this.p.getBackendSwitchingRules(FrontEnd.mongo_in);
        System.out.println(Json.toJsonPrettyFormat(rules));
    }

    @Test
    public void we_can_add_sni() throws Exception {

/*
# --- FRONTEND FOR MONGODB VIA SNI ---
frontend mongo_in from unnamed_defaults_1
  mode tcp
  bind *:27017 ssl crt-list /usr/local/etc/haproxy/crt.txt
  acl visiting_acl ssl_fc_sni -m sub visiting
  acl pinniped_acl ssl_fc_sni -m sub architectural
  log-format "%ci:%cp [%tr] %ft %b/%s %TR/%Tw/%Tc/%Tr/%Ta %ST %B %CC %CS %tsc %ac/%fc/%bc/%sc/%rc %sq/%bq {sni=%[ssl_fc_sni]}"
  # single atomic rule: accept + capture
  # tcp-request inspect-delay 5s
  # tcp-request content accept if { req.ssl_hello_type 1 } { capture.req.ssl_sni(64) }
  use_backend visiting-slug-330010_backend if visiting_acl
  use_backend architectural-pinniped-0055fb_backend if pinniped_acl
  default_backend visiting-slug-330010_backend


# --- BACKEND DEFINITIONS ---

backend mongo_projectA
  mode tcp
  server projectA_mongo projectA-mongo:27017 resolvers dockerdns init-addr none

backend mongo_projectB
  mode tcp
  server projectB_mongo projectB-mongo:27017 resolvers dockerdns init-addr none
 */

        String backend_name = "visiting-slug-330010_backend";
        String backend_server = "visiting-slug-330010";
        String acl = "visiting-slug-330010_backend_acl";
        int port = 27017;

        // create backend again
        this.p.createBackendIfNotExists(new LoadBalancerBackendResource()
                .withName(backend_name)
                .withMode(LoadBalancerBackendResource.Mode.TCP)
        );

        // update backend servers
        this.p.patchBackendServers(backend_name,
                Lists.newArrayList(
                        new LoadBalancerBackendServer()
                                .withName(backend_server)
                                .withPort(port)
                                .withAddress(backend_server)
                ));

        this.p.addAclIfNotExists(FrontEnd.mongo_in, acl, new SSL_FC_ServerNameIndicationAcl(backend_server));

        this.p.addBackendSwitchingRule(FrontEnd.mongo_in, new LoadBalancerMatchesAclCondition().withBackend(backend_name).withAcl(acl));
    }

    @Test
    public void we_can_one_shot_it() throws Exception {
        // DNS register the domain name (my_project)

        String backend_name = "my_project_backend";
        String backend_server = "my_project";
        int port = 3000;

        // create backend again
        this.p.createBackendIfNotExists(new LoadBalancerBackendResource()
                .withName(backend_name)
                .withMode(LoadBalancerBackendResource.Mode.HTTP)
        );

        // update backend servers
        this.p.patchBackendServers(backend_name,
                Lists.newArrayList(
                        new LoadBalancerBackendServer()
                                .withName(backend_server)
                                .withPort(port)
                                .withAddress(backend_server)
        ));


        String aclName = "is_my_project_hostname";
        this.p.addAclIfNotExists(FrontEnd.https_in, aclName, new HostnameAcl("my_project"));

        this.p.addBackendSwitchingRule(FrontEnd.https_in, new LoadBalancerMatchesAclCondition(aclName, "my_project_backend"));
    }

    @Test
    public void we_can_list_backends() throws Exception {

        List<LoadBalancerBackendResource> backends = p.getBackends();
        assertThat(backends).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(backends));

        // http://loadbalancer.dev.klustr.io:5555/v3/services/haproxy/configuration/frontends/https_in/acls
    }

    @Test
    public void we_can_get_acls() throws Exception {
        List<LoadBalancerAclResource> acls = p.getAcls(FrontEnd.https_in);
        assertThat(acls.size()).isGreaterThan(0);
        System.out.println(Json.toJsonPrettyFormat(acls));
    }

    @Test
    public void we_can_get_backend_rules() throws Exception {
        List<LoadBalancerBackendSwitchingRule> rules = p.getBackendSwitchingRules(FrontEnd.https_in);
        assertThat(rules.size()).isGreaterThan(0);
        System.out.println(Json.toJsonPrettyFormat(rules));
    }
}
