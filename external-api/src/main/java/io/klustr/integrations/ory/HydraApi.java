package io.klustr.integrations.ory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import io.klustr.schemas.console.AccessTokenStrategy;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.apps.AppVerification;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.OIDCCredential;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.ory.HydraClientRequest;
import io.klustr.schemas.integrations.ory.HydraCredentialsRequest;
import io.klustr.schemas.integrations.ory.Metadata;
import io.klustr.schemas.integrations.ory.OryClient;
import io.klustr.spring.TokenIntrospectionEnrichmentProvider;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@ConditionalOnProperty(name = "hydra.admin.url")
public class HydraApi implements TokenIntrospectionEnrichmentProvider {

    private static final Logger log = LoggerFactory.getLogger(HydraApi.class);

    private final OkHttpClient http = new OkHttpClient();
    private final String adminUrl;
    private final String url;
    ArrayList<String> grantTypes = Lists.newArrayList(
            "authorization_code",
            "client_credentials",
            "authorization_code",
            "implicit"
    );
    ArrayList<Object> responseTypes = Lists.newArrayList(
            "token", "code", "id_token"
    );
    MediaType mediaType = MediaType.parse("application/json");

    public HydraApi(@Value("${hydra.admin.url:https://hydra-admin.dev.klustr.io}") String adminUrl,
                    @Value("${hydra.url:https://hydra.dev.klustr.io}") String url
    ) {
        this.adminUrl = adminUrl;
        this.url = url;
    }

    public void health() {
        // https://hydra-admin.dev.klustr.io/health/ready
        Request request = new Request.Builder()
                .url(adminUrl + "/health/ready")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        request = new Request.Builder()
                .url(url + "/health/ready")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This endpoint revokes a subject's granted consent sessions and invalidates
     * all associated OAuth 2.0 Access Tokens.
     * <p>
     * You may also only revoke sessions for a specific OAuth 2.0 Client ID.
     * <p>
     * Link --> ..../admin/oauth2/auth/sessions/consent
     *
     * @param client_id  If set, deletes only those consent sessions that have been granted to the specified OAuth 2.0 Client ID.
     * @param subject_id The subject whose consent sessions should be deleted.
     * @param all        If set to true deletes all consent sessions by the Subject that have been granted.
     * @apiNote https://www.ory.sh/docs/oauth2-oidc/revoke-consent
     * @apiNote https://github.com/ory/hydra/blob/c9f4b5f3fbd7ccdac15923335a09ff63f94cf509/internal/httpclient/api/openapi.yaml#L1023
     * @apiNote https://www.ory.sh/docs/hydra/reference/api#tag/oAuth2/operation/revokeOAuth2ConsentSessions
     * <p>
     * /admin/oauth2/auth/sessions/consent
     */
    public void revokeConsent(String client_id, String subject_id, Boolean all) {
        log.info("Revoking Consent for " + client_id + " and subject " + subject_id);
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/oauth2/auth/sessions/consent" +
                        "?subject=" + U.encodeURIComponent(subject_id) +
                        "&client=" + U.encodeURIComponent(client_id) +
                        "&all=true")
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Revoked client token for client: " + client_id + " and subject: " + subject_id);
            } else {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(String client_id) {
        log.info("Deleting client id " + client_id);
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/clients/" + client_id)
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.info("Done deleting client id " + client_id);
                return;
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public TokenIntrospectionResponse introspect(String token) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();

        Request request = new Request.Builder()
                .url(adminUrl + "/oauth2/introspect")
                .post(formBody)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to skip consent for client. " + res.body().string());
            }
            String json = res.body().string();
            return U.fromJson(json, TokenIntrospectionResponse.class);
        }
    }

    public OryClient newClientFromCredential(Project project,
                                             String client_id,
                                             HydraCredentialsRequest credential) {


        return new OryClient()
                .withClientId(client_id)
                .withClientName(credential.getName())
                .withCreatedAt(DateTime.now())
                .withAudience(credential.getAudience())
                .withClientSecret(credential.getClientSecret())
                .withOwner(project.getOwner() != null ? project.getOwner().getId() : null)
                .withResponseTypes(responseTypes)
                .withGrantTypes(grantTypes)
                .withMetadata(new Metadata()
                        .withProjectId(project.getId())
                        .withAppId("service-credential")
                        .withRef(credential.getName())
                        .withStatus(credential.getStatus() != null ? credential.getStatus().value() : OidcStatus.ENABLED.value())
                )
                .withAccessTokenStrategy(credential.getAccessTokenStrategy())
                .withSubjectType(SubjectType.PUBLIC) // enable pairwise
                .withUserinfoSignedResponseAlg(OryClient.UserInfoResponseAlgo.NONE)
                .withTokenEndpointAuthMethod(credential.getTokenEndpointAuthMethod());
    }

    public OryTokenResponse auth(String client_id, String secret, List<String> scopes) {
        // grant_type=client_credentials&scope=api

        MediaType mediaType = MediaType.parse("application/json");
        FormBody.Builder formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials");

        if (scopes != null && scopes.isEmpty() == false) {
            formBody.add("scope", Joiner.on(" ").join(scopes));
        }

        String credential = Credentials.basic(client_id, secret);

        Request request = new Request.Builder()
                .url(url + "/oauth2/token")
                .header("Authorization", credential)
                .post(formBody.build())
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return U.fromJson(json, OryTokenResponse.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    OryClient newWebClient(Project project,
                           HydraClientRequest request) {

        AppVerification verification = project.getApp() != null ? project.getApp().getVerification() : null;

        String sectorUrl = null;
        if (request.getAccessTokenStrategy() == AccessTokenStrategy.OPAQUE) {
            if (request.getSubjectType() == SubjectType.PAIRWISE) {
                sectorUrl = "https://api.dev.klustr.io/directory/public/" + project.getOrgId() + "/sectors";
            }
        }

        return new OryClient()
                .withClientId(request.getClientId())
                .withClientName(project.getApp().getBrand().getName())
                .withAudience(request.getAudience())

                .withClientUri(project.getApp().getLinks() != null ? project.getApp().getLinks().getHome() : null)
                .withPolicyUri(project.getApp().getLinks() != null ? project.getApp().getLinks().getPrivacy() : null)
                .withTosUri(project.getApp().getLinks() != null ? project.getApp().getLinks().getTos() : null)

                .withCreatedAt(DateTime.now())
                .withOwner(project.getOwner() != null ? project.getOwner().getId() : null)
                .withResponseTypes(responseTypes)
                .withGrantTypes(grantTypes)
                .withMetadata(new Metadata()
                        .withProjectId(project.getId())
                        .withAppId(project.getApp().getId())
                        .withRef(request.getName())
                        .withOrgId(project.getOrgId())
                        .withVerified(verification != null && verification.getApproval() != null ? verification.getApproval().toString() : "false")
                )
                .withLogoUri(project.getApp().getBrand().getLogo().getUrl())
                .withScope(
                        Joiner.on(" ").join(project.getApp().getConsent().getScopes().stream().map(ConsentScope::getId).toList())
                )

                .withSkipConsent(false)

                .withRequestUris(project.getApp().getRequestUris())
                .withRedirectUris(request.getAuthorizedRedirectUrls())
                .withAllowedCorsOrigins(request.getAuthorizedOrigins())
                .withAccessTokenStrategy(request.getAccessTokenStrategy())
                .withRefreshTokenGrantAccessTokenLifespan(request.getAccessTokenLifespan())
                .withRefreshTokenGrantRefreshTokenLifespan(request.getRefreshTokenLifespan())
                .withSubjectType(request.getSubjectType())  // enable pairwise
                .withUserinfoSignedResponseAlg(OryClient.UserInfoResponseAlgo.NONE)
                .withSectorIdentifierUri(sectorUrl)
                .withTokenEndpointAuthMethod(request.getTokenEndpointAuthMethod());
    }

    private final String toOryInMinutes(Duration d) {
        return d.toMinutes() + "m";
    }

    public void skipConsent(Project project, String client_id, boolean skip) throws Exception {
        OryClient client = this.getClient(client_id);
        client = client.withSkipConsent(skip);

        String json = U.toJsonPrettyFormat(client);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/clients/" + client.getClientId())
                .put(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to skip consent for client. " + res.body().string());
            }
        }
    }

    public void update(Project project, OIDCCredential client) {
        OryClient oryClient = getClient(client.getClientId());

        boolean exists = oryClient != null;
        if (!exists) {
            // post a new one
            HydraClientRequest request = new HydraClientRequest()
                    .withName(client.getName())
                    .withTokenEndpointAuthMethod(client.getTokenEndpointAuthMethod())
                    .withClientId(client.getClientId())
                    .withAudience(client.getAudience())
                    .withAccessTokenStrategy(client.getAccessTokenStrategy());

            oryClient = newWebClient(project, request);
            if (StringUtils.isNotBlank(client.getSecretKey())) {
                oryClient.withClientSecret(client.getSecretKey());
            }
        } else {
            oryClient
                    .withClientSecret(client.getSecretKey())
                    .withClientName(client.getName())
                    .withResponseTypes(responseTypes)
                    .withAudience(client.getAudience())
                    .withGrantTypes(grantTypes);
        }

        String json = U.toJsonPrettyFormat(oryClient);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = exists ? new Request.Builder()
                .url(adminUrl + "/admin/clients/" + client.getClientId())
                .put(body)
                .build() : new Request.Builder()
                .url(adminUrl + "/admin/clients")
                .post(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(Project project, OIDCClient client) {

        OryClient oryClient = getClient(client.getClientId());
        boolean exists = oryClient != null;

        // need to set sectory identifier to org id
        String sector = null;
        if (client.getAccessTokenStrategy() == AccessTokenStrategy.OPAQUE) {
            if (client.getSubjectType() == SubjectType.PAIRWISE) {
                sector = "https://api.dev.klustr.io/directory/public/" + project.getOrgId() + "/sectors";
            }
        }

        oryClient = newWebClient(project, new HydraClientRequest()
                .withAuthorizedRedirectUrls(client.getAuthorizedRedirectUris())
                .withName(client.getName())
                .withAuthorizedOrigins(client.getAuthorizedOrigins())
                .withTokenEndpointAuthMethod(client.getTokenEndpointAuthMethod())
                .withClientId(client.getClientId())
                .withAccessTokenLifespan(client.getAccessTokenLifespan())
                .withAccessTokenStrategy(client.getAccessTokenStrategy())
                .withRefreshTokenLifespan(client.getRefreshTokenLifespan()))
                .withAudience(client.getAudience())
                .withResponseTypes(responseTypes)
                .withUserinfoSignedResponseAlg(OryClient.UserInfoResponseAlgo.NONE)
                .withGrantTypes(grantTypes)
                .withSectorIdentifierUri(sector)
                .withSubjectType(client.getSubjectType() != null ? client.getSubjectType() : SubjectType.PUBLIC);
        if (StringUtils.isNotBlank(client.getSecretKey())) {
            oryClient.withClientSecret(client.getSecretKey());
        }

        String json = U.toJsonPrettyFormat(oryClient);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = exists ? new Request.Builder()
                .url(adminUrl + "/admin/clients/" + client.getClientId())
                .put(body)
                .build() : new Request.Builder()
                .url(adminUrl + "/admin/clients")
                .post(body)
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<OryClient> listClients() {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/clients")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            return U.fromJson(res.body().string(), OryClientList.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public OryClient getClient(String id) {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/clients/" + id)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.code() == 200) {
                return U.fromJson(res.body().string(), OryClient.class);
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Map<String, String> getEnrichment(String client_id) {
        OryClient client = this.getClient(client_id);
        Metadata metadata = client.getMetadata();
        Map<String, String> r = new HashMap();
        r.put("project_id", metadata.getProjectId());
        r.put("app_id", metadata.getAppId());
        r.put("org_id", metadata.getOrgId());
        r.put("status", metadata.getStatus());
        return r;
    }

    public OryRegistration createOrUpdateCredential(Project project,
                                                    HydraCredentialsRequest cred) {

        OryClient oryObject = null;
        boolean exists = false;
        if (StringUtils.isNotBlank(cred.getClientId())) {
            oryObject = this.getClient(cred.getClientId());
            exists = oryObject != null;
        }

        if (!exists) {
            if (StringUtils.isBlank(cred.getClientId())) {
                cred.setClientId(UUID.randomUUID().toString());
            }
            oryObject = newClientFromCredential(project, cred.getClientId(), cred);
        }

        String json = U.toJsonPrettyFormat(oryObject);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = exists ?
                new Request.Builder()
                        .url(adminUrl + "/admin/clients/" + cred.getClientId())
                        .put(body)
                        .build() :
                new Request.Builder()
                        .url(adminUrl + "/admin/clients")
                        .post(body)
                        .build();

        try (Response res = http.newCall(request).execute()) {
            String res_json = res.body().string();
            log.debug(res_json);
            OryClient obj = U.fromJson(res_json, OryClient.class);
            OryRegistration reg = new OryRegistration();
            reg.client_id = obj.getClientId();
            reg.client_secret = obj.getClientSecret();
            return reg;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public OryRegistration registerClient(Project project,
                                          HydraClientRequest request) {

        OryClient oryObject = newWebClient(project, request);
        String json = U.toJsonPrettyFormat(oryObject);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request post = new Request.Builder().url(adminUrl + "/admin/clients").post(body).build();

        try (Response res = http.newCall(post).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.code() + " -> " + res.body().string());
            }
            String json1 = res.body().string();
            OryClient obj = U.fromJson(json1, OryClient.class);
            OryRegistration reg = new OryRegistration();
            reg.client_id = obj.getClientId();
            reg.client_secret = obj.getClientSecret();
            return reg;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getProjectIdFromClient(String clientId) {
        Request request = new Request.Builder()
                .url(adminUrl + "/admin/clients/" + clientId)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to skip consent for client. " + res.body().string());
            }
            String json = res.body().string();
            OryClient c = U.fromJson(json, OryClient.class);
            if (c == null) return null;
            Metadata metadata = c.getMetadata();
            if (metadata == null) return null;
            return metadata.getProjectId();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class TokenIntrospectionResponse {
        public String sub;
        public String scope;
        public String client_id;
        public Boolean active;
        public List<String> aud;
        public String iss;
        public String token_type;
        public String token_use;
    }

    public static class OryTokenResponse {
        public String access_token;
        public int expires_in;
        public String scope;
        public String token_type;
    }

    public static class OryClientTokenExpirations {
        public Duration accessTokenExpiry = Duration.ofMinutes(30);
        public Duration refreshTokenExpiry = Duration.ofDays(7);
    }

    public static class OryClientList extends ArrayList<OryClient> {
    }

    public static class OryRegistration {
        public String client_id;
        public String client_secret;
    }
}
