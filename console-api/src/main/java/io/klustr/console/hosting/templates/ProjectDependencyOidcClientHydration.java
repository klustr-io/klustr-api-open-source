package io.klustr.console.hosting.templates;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.schemas.console.AccessTokenStrategy;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.TokenEndpointAuthMethod;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.OIDCCredential;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.console.projects.TemplateAppConfiguration;
import io.klustr.schemas.console.projects.TemplateOauthConfiguration;
import io.klustr.schemas.integrations.ory.HydraClientRequest;
import io.klustr.schemas.integrations.ory.HydraCredentialsRequest;
import io.klustr.utils.RandomNameGenerator;
import org.joda.time.DateTime;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(2)
public class ProjectDependencyOidcClientHydration implements ProjectDependencyHydration {

    private final HydraApi hydra;

    public ProjectDependencyOidcClientHydration(HydraApi hydra) {
        this.hydra = hydra;
    }

    @Override
    public void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env) {

        if (template.getRequiredFeatures() == null) return;

        TemplateOauthConfiguration c = template.getRequiredFeatures().getOauthClient();
        if (c == null) return;

        TemplateAppConfiguration app = template.getRequiredFeatures().getApp();
        if (app == null) return;

        // check if already hydrated?
        Project project = storage.projects().getObject(projectId);

        // we already created a credential for this template and prjoject
        String TEMPLATE_KEY = "_xtemplate";
        if (project.getClients() != null && !project.getClients().isEmpty()) {
            Optional<OIDCClient> match = project.getClients().stream().filter(x -> {
                return x.getMetadata() != null && x.getMetadata().get(TEMPLATE_KEY) != null
                        && x.getMetadata().get(TEMPLATE_KEY).equalsIgnoreCase(template.getId());
            }).findFirst();
            if (match.isPresent()) {
                env.add("OIDC_ISSUER", "https://secure.dev.klustr.io/hydra");
                env.add("OIDC_CLIENT", match.get().getClientId());
                env.add("OIDC_SECRET", match.get().getSecretKey());
                env.add("OIDC_SCOPES", Joiner.on(" ").join(app.getScopes()));
                return;
            }
        }

        HydraClientRequest hydraReq = new HydraClientRequest()
                .withClientId(UUID.randomUUID().toString())
                .withName(projectId + "-oidc")
                .withScopes(app.getScopes())
                .withStatus(OidcStatus.ENABLED)
                .withAccessTokenStrategy(AccessTokenStrategy.OPAQUE)
                .withTokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .withAuthorizedOrigins(project.getApp().getDomains().stream().map(x -> {
                    return x.replace("{projectId}", projectId);
                }).toList())
                .withAuthorizedRedirectUrls(c.getAuthorizedRedirectUris().stream().map(x -> {
                    return x.replace("{projectId}", projectId);
                }).toList())
                .withSubjectType(SubjectType.PUBLIC);


        try {
            HydraApi.OryRegistration oryRegistration = this.hydra.registerClient(project, hydraReq);

            // used to tag so we dont provision multiple times.
            Map<String, String> meta = Maps.newHashMap();
            meta.put(TEMPLATE_KEY, template.getId());

            env.add("OIDC_ISSUER", "https://secure.dev.klustr.io/hydra");
            env.add("OIDC_CLIENT", oryRegistration.client_id);
            env.add("OIDC_SECRET", oryRegistration.client_secret);
            env.add("OIDC_SCOPES", Joiner.on(" ").join(hydraReq.getScopes()));


            OIDCClient oidcClient = new OIDCClient()
                    .withName(hydraReq.getName())
                    .withCreationDate(DateTime.now())
                    .withStatus(OidcStatus.ENABLED)
                    .withAuthorizedOrigins(hydraReq.getAuthorizedOrigins())
                    .withAccessTokenStrategy(hydraReq.getAccessTokenStrategy())
                    .withTokenEndpointAuthMethod(hydraReq.getTokenEndpointAuthMethod())
                    .withSubjectType(hydraReq.getSubjectType())
                    .withScopes(hydraReq.getScopes())
                    .withAudience(hydraReq.getAudience())
                    .withAccessTokenLifespan(hydraReq.getAccessTokenLifespan())
                    .withRefreshTokenLifespan(hydraReq.getRefreshTokenLifespan())
                    .withSecretKey(oryRegistration.client_secret)
                    .withClientId(oryRegistration.client_id)
                    .withAuthorizedRedirectUris(hydraReq.getAuthorizedRedirectUrls())
                    .withMetadata(meta);

            project.getClients().add(
                    oidcClient
            );
            storage.projects().updateObject(projectId, project);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}