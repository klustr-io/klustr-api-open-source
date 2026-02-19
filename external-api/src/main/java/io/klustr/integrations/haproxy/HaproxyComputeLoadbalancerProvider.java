package io.klustr.integrations.haproxy;

import com.google.common.collect.Sets;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.FrontEnd;
import io.klustr.integrations.haproxy.acls.LoadBalancerAcl;
import io.klustr.integrations.haproxy.acls.LoadBalancerCondition;
import io.klustr.schemas.integrations.compute.*;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class HaproxyComputeLoadbalancerProvider implements ComputeLoadBalancerProvider {

    private final String url;
    private final String username;
    private final String password;

    private final OkHttpClient http = new OkHttpClient();
    private static final MediaType mediaType = MediaType.parse("application/json");
    private Set<String> ips = Sets.newHashSet("100.124.171.82");    // tailscale IP

    public HaproxyComputeLoadbalancerProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Health health() {
        try {
            List<LoadBalancerBackendResource> backends = this.getBackends();
            return Health.up().withDetail("url", url).build();
        } catch (Exception ex) {
            return Health.down(ex).withDetail("url", url).build();
        }
    }

    public Set<String> getPublicIPV4Addresses() {
        return ips;
    }

    public ComputeIngress getStatus(String projectId) {

        // is DNS configured for ingress?
        String ipAddress = U.waitForDnsOrExit(projectId + ".dev.klustr.io", 100);

        // DNS routed?
        ComputeIngressDns dns = StringUtils.isNotBlank(ipAddress) ? new ComputeIngressDns()
                .withIp(new ComputeIngressDnsIpRecord().withAddress(ipAddress).withType(ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType.PUBLIC))
                .withZone("dev.klustr.io")
                .withActive(true)
                .withName(projectId) : new ComputeIngressDns().withActive(false);

        // grab all servers mounted
        List<LoadBalancerBackendServer> servers = this.getBackendServers(projectId);
        if (servers.isEmpty()) {
            return new ComputeIngress().withStatus(ComputeIngress.Status.DISABLED).withDns(dns);
        }

        // grab all the bindings for this route (servers and weight)
        List<ComputeIngressBinding> bindings = new ArrayList<>();
        servers.forEach(s -> {
            ComputeIngressBinding bind = new ComputeIngressBinding().withPort(s.getPort())
                    .withServer(s.getName())
                    .withWeight(1);
            bindings.add(bind);
        });

        // our final result
        ComputeIngress result = new ComputeIngress()
                .withDns(dns)
                .withBindings(bindings);

        if (bindings.isEmpty() || dns.getActive() == false) {
            result.setStatus(ComputeIngress.Status.DISABLED);
        } else {
            result.setStatus(ComputeIngress.Status.ACTIVE);
        }

        return result;
    }

    public List<LoadBalancerFrontendResource> getFrontEnds() {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            String json = res.body().string();
            if (!res.isSuccessful()) {
                throw new RuntimeException(json);
            }
            return Json.parseArray(json, LoadBalancerFrontendResource.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public FrontEnd guessFrontEndFromPort(int port) {
        if (port == 27017) return FrontEnd.mongo_in;
        if (port == 5432) return FrontEnd.postgres_in;
        if (port == 6379) return FrontEnd.redis_in;
        if (port == 28015) return FrontEnd.rethinkdb_in;
        if (port == 9092) return FrontEnd.kafka_in;
        if (port == 9000) return FrontEnd.vscode_in;
        return FrontEnd.https_in;
    }

    public List<LoadBalancerFrontendBindResource> getFrontEndsBinds(FrontEnd frontEnd) {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/binds")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            String json = res.body().string();
            if (!res.isSuccessful()) {
                throw new RuntimeException(json);
            }
            return Json.parseArray(json, LoadBalancerFrontendBindResource.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<LoadBalancerBackendResource> getBackends() {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Json.parseArray(res.body().string(), LoadBalancerBackendResource.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createBackendIfNotExists(LoadBalancerBackendResource resource) {

        // if backend already exists nothing to do
        if (this.getBackends().stream().anyMatch(x -> {
            return x.getName().equalsIgnoreCase(resource.getName());
        })) {
            return;
        }

        List<LoadBalancerBackendResource> backends = this.getBackends();
        Optional<LoadBalancerBackendResource> match = backends.stream().filter(x -> {
            return x.getName().equalsIgnoreCase(resource.getName());
        }).findFirst();
        if (match.isPresent()) {
            throw new RuntimeException("Can not add backend that already exists");
        }

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .post(RequestBody.create(Json.toJson(resource), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<LoadBalancerBackendServer> getBackendServers(String backend) {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends/" + backend + "/servers")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, LoadBalancerBackendServer.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void patchBackendServers(String backend, List<LoadBalancerBackendServer> servers) {
        List<LoadBalancerBackendServer> existing_servers = this.getBackendServers(backend);
        if (!existing_servers.isEmpty()) {
            existing_servers.forEach(remove -> {
                removeServerFromBackend(backend, remove.getName());
            });
        }

        servers.forEach(s -> {
            addServerToBackend(backend, s);
        });
    }

    public void removeServerFromBackend(String backend, String server) {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends/" + backend + "/servers/" + server + "?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addServerToBackend(String backend, LoadBalancerBackendServer server) {

        Optional<LoadBalancerBackendServer> exists = this.getBackendServers(backend).stream().filter(x -> {
            return x.getName().equalsIgnoreCase(server.getName()) &&
                    x.getAddress().equalsIgnoreCase(server.getAddress());
        }).findFirst();
        if (exists.isPresent()) return;

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends/" + backend + "/servers?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .post(RequestBody.create(Json.toJson(server), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<LoadBalancerAclResource> getAcls(FrontEnd frontEnd) {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/acls")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Json.parseArray(res.body().string(), LoadBalancerAclResource.class);
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public Integer getCurrentVersion() {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Integer.parseInt(res.headers().get("configuration-version"));
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public void deleteBackend(FrontEnd frontEnd, String name) {

        List<LoadBalancerBackendSwitchingRule> rules = this.getBackendSwitchingRules(frontEnd);
        rules.stream().filter(x -> {
            return x.getName().equalsIgnoreCase(name);
        }).forEach(rm -> {
            this.deleteBackendSwitchingRule(frontEnd, rm);
        });

        // problem is acls will still exist

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/backends/" + name + "?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<LoadBalancerBackendSwitchingRule> getBackendSwitchingRules(FrontEnd frontend) {
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontend + "/backend_switching_rules")
                .header("Authorization", Credentials.basic(username, password))
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Json.parseArray(res.body().string(), LoadBalancerBackendSwitchingRule.class);
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public void addBackendSwitchingRule(FrontEnd frontEnd, LoadBalancerCondition rule) {

        LoadBalancerBackendSwitchingRule conditon = HaproxyAclConverter.convert(rule);

        List<LoadBalancerBackendSwitchingRule> rules = this.getBackendSwitchingRules(frontEnd);
        Optional<LoadBalancerBackendSwitchingRule> match = rules.stream().filter(x -> {
            return x.getName().equalsIgnoreCase(conditon.getName());
        }).findFirst();

        // remove if exists
        if (match.isPresent()) {
            this.deleteBackendSwitchingRule(frontEnd, match.get());
            rules = this.getBackendSwitchingRules(frontEnd);    // refresh indexes
        }

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/backend_switching_rules/" + rules.size() + "?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .post(RequestBody.create(Json.toJson(conditon), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public void deleteBackendSwitchingRule(FrontEnd frontEnd, LoadBalancerBackendSwitchingRule rule) {
        List<LoadBalancerBackendSwitchingRule> rules = this.getBackendSwitchingRules(frontEnd);

        Optional<LoadBalancerBackendSwitchingRule> match = rules.stream().filter(x -> {
            return x.getName().equalsIgnoreCase(rule.getName());
        }).findFirst();
        if (match.isEmpty()) {
            throw new RuntimeException("Could not find backend switching rule");
        }
        int index = rules.indexOf(match.get());

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/backend_switching_rules/" + index + "?version=" + this.getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public void addAclIfNotExists(FrontEnd frontEnd, String aclName, LoadBalancerAcl acl) {

        LoadBalancerAclResource convert = HaproxyAclConverter.convert(aclName, acl);

        Optional<LoadBalancerAclResource> aclExists = this.getAcls(frontEnd).stream().filter(x -> {
            return x.getAclName().equalsIgnoreCase(aclName) &&
                    x.getCriterion().equalsIgnoreCase(convert.getCriterion()) &&
                    x.getValue().equalsIgnoreCase(convert.getValue());
        }).findFirst();
        if (aclExists.isPresent()) {
            return;
        }

        // acl exists but doesn't match new criteria
        List<LoadBalancerAclResource> acls = this.getAcls(frontEnd);
        Optional<LoadBalancerAclResource> match = acls.stream().filter(x -> {
            return x.getAclName().equalsIgnoreCase(aclName);
        }).findFirst();
        // delete existing version as its not the same
        if (match.isPresent()) {
            deleteExistingAcl(frontEnd, match.get().getAclName());
            acls = this.getAcls(frontEnd);  // refetch for new index
        }

        int newIndex = acls.size();   // 0 index array
        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/acls/" + newIndex + "?version=" + getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .post(RequestBody.create(Json.toJson(convert), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public void deleteExistingAcl(FrontEnd frontEnd, String aclName) {
        List<LoadBalancerAclResource> acls = this.getAcls(frontEnd);

        Optional<LoadBalancerAclResource> match = acls.stream().filter(x -> {
            return x.getAclName().equalsIgnoreCase(aclName);
        }).findFirst();
        if (match.isEmpty()) {
            throw new RuntimeException("Request to delete ACL but not found.");
        }
        int removeIndex = acls.indexOf(match.get());

        Request req = new Request.Builder()
                .url(this.url + "/v3/services/haproxy/configuration/frontends/" + frontEnd + "/acls/" + removeIndex + "?version=" + getCurrentVersion())
                .header("Authorization", Credentials.basic(username, password))
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }
}
