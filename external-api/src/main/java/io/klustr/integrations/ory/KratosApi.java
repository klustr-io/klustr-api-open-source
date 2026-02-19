package io.klustr.integrations.ory;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.identity.IdentityCredentials;
import io.klustr.schemas.console.identity.IdentityPasswordCredential;
import io.klustr.schemas.console.identity.IdentityPasswordCredentialConfig;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.integrations.ory.IdentityProfile;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * API integration with Ory Kratos for getting and setting identities.
 *
 * @see "https://www.ory.sh/docs/kratos/reference/api"
 */
@Component
@ConditionalOnProperty(name = "kratos.admin.url")
public class KratosApi {

    private static final Logger log = LoggerFactory.getLogger(KratosApi.class);

    private final OkHttpClient http = new OkHttpClient();
    private final String url;
    private final String source;

    private final MediaType mediaType = MediaType.parse("application/json");

    public KratosApi(@Value("${kratos.admin.url}") String url,
                     @Value("${kratos.identity.source:kratos}") String source) {
        this.url = url;
        this.source = source;
    }

    public void health() {
        // https://kratos-admin.dev.klustr.io/admin/health/ready

        Request request = new Request.Builder()
                .url(url + "/health/ready")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Kratos URL not available. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the public identity of the user
     * without sensitive information and only
     * public information.
     *
     * @param id The unique ID of the entity.
     * @return The {@link Identity} if it was found.
     */
    public IdentityProfile getPublicIdentity(String id) {
        Request request = new Request.Builder()
                .url(url + "/admin/identities/" + id)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
                return U.fromJson(body, IdentityProfile.class);
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setCredentials(String id, String username, String password) {
        Identity identity = this.getIdentity(id);
        IdentityCredentials credentials = identity.getCredentials();
        if (credentials == null) {
            credentials = new IdentityCredentials();
        }
        if (credentials.getPassword() == null) {
            credentials.setPassword(new IdentityPasswordCredential()
                    .withConfig(new IdentityPasswordCredentialConfig().withPassword(password))
            );
        } else {
            credentials.getPassword().setConfig(new IdentityPasswordCredentialConfig()
                    .withPassword(password)
            );
        }
        identity.getTraits().withUsername(username);

        RequestBody body = RequestBody.create(U.toJson(identity), mediaType);
        Request request = new Request.Builder()
                .url(url + "/admin/identities/" + identity.getId())
                .put(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to register IdP: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the configured identity from the identity store.
     *
     * @param id The unique ID of the entity.
     * @return The {@link Identity} if it was found.
     */
    public Identity getIdentity(String id) {

        Request request = new Request.Builder()
                .url(url + "/admin/identities/" + id)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                Identity identity = U.fromJson(body, Identity.class);
                identity.withSource(this.source);
                return identity;
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Identity create(Identity identity) {
        String requestJson = U.toJson(identity);
        RequestBody body = RequestBody.create(requestJson, mediaType);
        Request request = new Request.Builder()
                .url(url + "/admin/identities")
                .post(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to register IdP: " + res.body().string() + " w/ json " + requestJson);
            }
            String json = res.body().string();
            return U.fromJson(json, Identity.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(Identity identity) {

        // dont update credentials that are passwords
        // as we can't do that, and must use credentials
        if (identity.getCredentials() != null) {
            if (identity.getCredentials().getPassword() != null) {
                identity.setCredentials(null);  // cant set this via this method
            }
        }

        RequestBody body = RequestBody.create(U.toJson(identity), mediaType);
        Request request = new Request.Builder()
                .url(url + "/admin/identities/" + identity.getId())
                .put(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to register IdP: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Identity> listIdentities() {
        Request request = new Request.Builder()
                .url(url + "/admin/identities")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
                return U.fromJson(body, ListResponse.class);
            } else {
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteIdentity(String id) {

        Request request = new Request.Builder()
                .url(url + "/admin/identities/" + id)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
            } else {
                if (res.code() == 204) {
                    return;
                }
                log.error(body);
                throw new RuntimeException("Failed to delete identity. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Identity getByEmail(String email) {
        Request request = new Request.Builder()
                .url(url + "/admin/identities?credentials_identifier=" + U.encodeURIComponent(email))
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String body = res.body() != null ? res.body().string() : null;
            if (res.isSuccessful()) {
                log.debug(body);
                ListResponse identities = U.fromJson(body, ListResponse.class);
                if (identities.isEmpty()) return null;
                return identities.get(0);
            } else {
                log.error(body);
                throw new RuntimeException("Failed to fetch identity by email client. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class ListResponse extends ArrayList<Identity> {
    }
}
