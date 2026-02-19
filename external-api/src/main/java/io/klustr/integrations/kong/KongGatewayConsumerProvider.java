package io.klustr.integrations.kong;

import io.klustr.integrations.kong.interfaces.EnabledServices;
import io.klustr.integrations.kong.interfaces.GatewayConsumerProvider;
import io.klustr.integrations.kong.models.KongConsumer;
import io.klustr.integrations.kong.models.KongConsumerRequest;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "kong.admin.url")
public class KongGatewayConsumerProvider implements GatewayConsumerProvider {
    private static final Logger log = LoggerFactory.getLogger(KongGatewayServiceProvider.class);
    private static MediaType mediaType = MediaType.parse("application/json");
    private final String adminUrl;

    private final OkHttpClient http;

    public KongGatewayConsumerProvider(@Value("${kong.admin.url:https://kong-admin.dev.klustr.io/admin}") String adminUrl) {
        log.info("Kong Admin Url: " + adminUrl);
        http = new OkHttpClient();
        this.adminUrl = adminUrl;
    }

    public void deleteConsumer(String username) {

        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + username)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Deleted client");
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<KongConsumer> getCustomer(String consumerId) {
        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + consumerId)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return Optional.of( U.fromJson(res.body().string(), KongConsumer.class));
            } else {
                if (res.code() >= 400 && res.code() < 500) {
                    return Optional.empty();
                } else {
                    throw new RuntimeException("Could not fetch customer: " + res.code() + " - " + res.body().string());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public KongConsumer createConsumer(KongConsumerRequest req) {

        FormBody.Builder form = new FormBody.Builder()
                .add("username", req.username)
                .add("custom_id", req.custom_id);

        for (String tag :
                req.tags) {
            form = form.add("tags", tag);
        }

        Request request = new Request.Builder()
                .url(adminUrl + "/consumers")
                .post(form.build())
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Registered new client");
                return U.fromJson(res.body().string(), KongConsumer.class);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException("Failed to create client." + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public KongConsumer getConsumer(String username) {

        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + username)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Registered new client");
                return U.fromJson(res.body().string(), KongConsumer.class);
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void enableServiceForConsumer(String consumerId, String serviceId) {


        FormBody.Builder form = new FormBody.Builder()
                .add("group", serviceId);

        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + consumerId + "/acls")
                .post(form.build())
                .build();


        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Added consumer to group");
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException("Failed to add ACL to consumer." + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public EnabledServices getEnabledServicesForConsumer(String consumerId) {

        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + consumerId + "/acls")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return U.fromJson(res.body().string(), EnabledServices.class);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void disableServiceForConsumer(String consumerId, String service) {
        Request request = new Request.Builder()
                .url(adminUrl + "/consumers/" + consumerId + "/acls/" + service)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Removed consumer from group");
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException("Failed to remove ACL for consumer." + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
