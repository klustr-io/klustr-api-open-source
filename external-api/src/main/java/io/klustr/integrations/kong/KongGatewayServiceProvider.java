package io.klustr.integrations.kong;

import io.klustr.integrations.kong.interfaces.GatewayServiceProvider;
import io.klustr.integrations.kong.interfaces.ServiceRoute;
import io.klustr.integrations.kong.models.KongGenericPagedResult;
import io.klustr.integrations.kong.models.plugins.AclPlugin;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.integrations.kong.KongService;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "kong.admin.url")
public class KongGatewayServiceProvider implements GatewayServiceProvider {
    private static final Logger log = LoggerFactory.getLogger(KongGatewayServiceProvider.class);
    private static MediaType mediaType = MediaType.parse("application/json");
    private final String adminUrl;
    private final OkHttpClient http;

    public KongGatewayServiceProvider(@Value("${kong.admin.url:https://kong-admin.dev.klustr.io/admin}") String adminUrl) {
        log.info("Kong Admin Url: " + adminUrl);
        http = new OkHttpClient();
        this.adminUrl = adminUrl;
    }

    @Override
    public void health() {
        Request request = new Request.Builder()
                .url(adminUrl)
                .get()
                .build();
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public EntityReference create(String serviceId, String url) {

        FormBody.Builder form = new FormBody.Builder()
                .add("name", serviceId)
                .add("retries", "3")
                .add("connect_timeout", "60000")
                .add("write_timeout", "60000")
                .add("read_timeout", "60000")
                .add("enabled", "true")
                .add("url", url);

        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId)
                .put(form.build())
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Created service");
                String body = res.body().string();
                KongService kongService = U.fromJson(body, KongService.class);
                return new EntityReference().withId(kongService.getId()).withName(kongService.getName());
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void serviceAddRoutes(String serviceId, List<String> hosts, List<String> routePaths) {

        FormBody.Builder form = new FormBody.Builder();

        for (String host :
                hosts) {
            form = form.add("hosts[]", host);
        }

        for (String route :
                routePaths) {
            form = form.add("paths[]", route);
        }

        this.serviceRoutes(serviceId).forEach(x -> {
            Request request = new Request.Builder()
                    .url(adminUrl + "/services/" + serviceId + "/routes/" + x.id)
                    .delete()
                    .build();
            try (Response res = http.newCall(request).execute()) {
                // we deleted it
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId + "/routes")
                .post(form.build())
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Created route on service");
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class KongRouteResponse extends KongGenericPagedResult<ServiceRoute> { }

    public List<ServiceRoute> serviceRoutes(String id) {
        List<ServiceRoute> results = new ArrayList<>();
        int size = 100;
        String offset = null;
        boolean hasNext = true;

        while (hasNext) {
            Request request = new Request.Builder()
                    .url(adminUrl + (offset != null ? offset : "/services/" + id + "/routes?size=" + size))
                    .get()
                    .build();

            try (Response res = http.newCall(request).execute()) {
                String json = res.body().string();
                if (res.isSuccessful()) {
                    KongRouteResponse r = U.fromJson(json, KongRouteResponse.class);
                    results.addAll(r.data);
                    if (StringUtils.isNotBlank(r.next)) {
                        offset = r.next;
                    } else {
                        hasNext = false;
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return results;
    }


    public void deleteServiceRoutes(String serviceId, String routeId) {
        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId + "/routes/" + routeId)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        throw new RuntimeException("Could not remove routes");
    }

    public void deleteService(String serviceId) {

        serviceRoutes(serviceId).forEach(x -> {
            deleteServiceRoutes(serviceId, x.id);
        });

        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Deleted service");
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException("Failed to delete service." + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<EntityReference> checkServiceExists(String serviceId) {
        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                EntityReference e = U.fromJson(json, EntityReference.class);
                return Optional.of(e);
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void requireRoleForService(String serviceId, String role) {

        AclPlugin acl = new AclPlugin(serviceId, role);
        acl.instance_name = role;

        Request request = new Request.Builder()
                .url(adminUrl + "/services/" + serviceId + "/plugins/" + role)
                .put(RequestBody.create(acl.toJson(), MediaType.get("application/json")))
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String body = res.body().string();
                log.info("Added ACL to service: " + body);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException("Failed to add ACL service." + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
