package io.klustr.integrations.kong;

import com.google.common.collect.Lists;
import io.klustr.integrations.kong.interfaces.GatewayPlugin;
import io.klustr.integrations.kong.interfaces.GatewayPluginProvider;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "kong.admin.url")
public class KongGatewayPluginProvider implements GatewayPluginProvider {
    private static final Logger log = LoggerFactory.getLogger(KongGatewayServiceProvider.class);
    private static MediaType mediaType = MediaType.parse("application/json");
    private final String adminUrl;
    private final OkHttpClient http;

    public KongGatewayPluginProvider(@Value("${kong.admin.url:https://kong-admin.dev.klustr.io/admin}") String adminUrl) {
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

    public void upsertPlugin(GatewayPlugin plugin) {
        Optional<GatewayPlugin> match = getPlugins().stream().filter(x -> {
            return x.name.equalsIgnoreCase(plugin.name);
        }).findFirst();

        String id = match.isPresent() ? match.get().id : UUID.randomUUID().toString();

        String url = adminUrl + "/plugins/" + id;
        Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create(plugin.toJson(), mediaType))
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String body = res.body().string();
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to add plugin to url [" + url + "]: " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<GatewayPlugin> getPlugins() {
        Request request = new Request.Builder()
                .url(adminUrl + "/plugins")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String body = res.body().string();
            KongResponse r = U.fromJson(body, KongResponse.class);
            return r.data;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class KongResponse {
        public List<GatewayPlugin> data = Lists.newArrayList();
    }

    public boolean hasPlugin(String serviceId, String pluginName) {

        Optional<GatewayPlugin> exists = this.getPlugins().stream().filter(x -> {
            return x.name.equalsIgnoreCase(pluginName) &&
                    x.service != null &&
                    x.service.getId().equalsIgnoreCase(serviceId);
        }).findFirst();
        if (exists.isPresent()) {
            return true;
        }
        return false;
    }
}
