package io.klustr.console.jobs.hydra;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.orgs.OrgOwner;
import io.klustr.setup.SetupTask;
import io.klustr.setup.SetupTaskRunnable;
import io.klustr.console.org.OrganizationDefaultRoles;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLogo;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.apps.ConsentGroup;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.OIDCCredential;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.ory.OryClient;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.RandomNameGenerator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@SetupTask
public class SetupImportHydraClientsIntoProjectsTask implements SetupTaskRunnable {

    private static final Logger log = LoggerFactory.getLogger(SetupImportHydraClientsIntoProjectsTask.class);

    private final HydraApi hydra;
    private final Storage storage;
    private final OrganizationDefaultRoles roles;
    private final PermissionProvider permissions;

    public SetupImportHydraClientsIntoProjectsTask(Storage storage,
                                                   HydraApi hydra,
                                                   PermissionProvider permissions,
                                                   OrganizationDefaultRoles roles) {
        this.hydra = hydra;
        this.storage = storage;
        this.roles = roles;
        this.permissions = permissions;
    }

    public void setup() {
        createOrg("klustr.io", "klustr.io", "klustr.io");
        setupClients();
    }

    private void createOrg(String id, String name, String domain) {
        Org org;
        if (!this.storage.organizations().exists(id)) {
            org = new Org()
                    .withId(id)
                    .withName(name)
                    .withCreationDate(DateTime.now())
                    .withModifiedDate(DateTime.now())
                    .withDomain(domain)
                    .withOwner(new OrgOwner().withId("terrance.a.snyder@gmail.com"))
                    .withStatus(Org.Status.ACTIVE)
                    .withSubdomain("default");
            this.storage.organizations().insertObject(org.getId(), org);
            log.info("Created organization {} called {} with domain {}", id, name, domain);
        } else {
            org = this.storage.organizations().getObject(id);
            log.info("Found organization {} called {} with domain {}", id, name, domain);
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(org.getId());
        if (orgRoles == null || orgRoles.getRoles() == null) {
            orgRoles = new OrgRoles().withId(org.getId());
        }
        if (orgRoles.getRoles() == null || orgRoles.getRoles().isEmpty()) {
            this.roles.generateAndSave(orgRoles);
            log.info("Generated roles for organization {} called {} with domain {}", id, name, domain);
        }
    }

    private void setupClients() {
        List<OryClient> clients = this.hydra.listClients();
        clients.forEach(client -> {
            if (client.getMetadata() == null) {
                log.error("Client ID {} does not have metadata!", client.getClientId());
                return;
            }
            if (StringUtils.isBlank(client.getMetadata().getOrgId())) {
                log.error("Client ID {} does not have an 'org_id' metadata!", client.getClientId());
                return;
            }
            if (StringUtils.isBlank(client.getMetadata().getProjectId())) {
                log.error("Client ID {} does not have an 'project_id' metadata!", client.getClientId());
                return;
            }
            if (StringUtils.isBlank(client.getMetadata().getAppId())) {
                log.error("Client ID {} does not have an 'app_id' metadata!", client.getClientId());
                return;
            }
            if (StringUtils.isBlank(client.getMetadata().getDomain())) {
                log.warn("Client ID {} does not have an 'domain' metadata!", client.getClientId());
            }
            Org org = ensureOrgExists(client);
            DbQuery fq = Db.query("clients").contains("client_id").eq(client.getClientId());
            Optional<Project> project = this.storage.projects().findFirst(fq);
            if (project.isEmpty()) {
                try {
                    setupProject(client,  org.getId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private Org ensureOrgExists(OryClient client) {
        String orgId = client.getMetadata().getOrgId();

        // adding a domain will enable the person with that @<domain> sign in to claim this.
        String domain = client.getMetadata().getDomain() != null ? client.getMetadata().getDomain() : orgId;
        this.createOrg(orgId, domain, domain);
        return this.storage.organizations().getObject(orgId);
    }

    private Set<String> getAllowedDomain(OryClient client) {
        Set<String> domain = Sets.newConcurrentHashSet();
        String capture = "(http[s]{0,1}:\\/\\/[^\\/]+)";
        Pattern p = Pattern.compile(capture);
        if (client.getRequestUris() != null) {
            client.getRequestUris().forEach(uri -> {
                Matcher matcher = p.matcher(uri);
                if (matcher.find()) {
                    domain.add(matcher.group());
                }
            });
        }
        if (client.getRedirectUris() != null) {
            client.getRedirectUris().forEach(uri -> {
                Matcher matcher = p.matcher(uri);
                if (matcher.find()) {
                    domain.add(matcher.group());
                }
            });
        }
        return domain;
    }

    private void setupProject(OryClient client,  String orgId) {

        String projectId = client.getMetadata().getProjectId();
        if (StringUtils.isBlank(projectId)) {
            projectId = new RandomNameGenerator().randomAnimalsAndAdjectives(1).get(0).toLowerCase();
        }
        String appId = client.getMetadata().getAppId();
        if (StringUtils.isBlank(appId)) {
            appId = RandomNameGenerator.generateAlphaNumericCode(12).toLowerCase();
        }

        if (client.getMetadata() != null) {
            if (StringUtils.isNotBlank(client.getMetadata().getProjectId())) {
                projectId = client.getMetadata().getProjectId();
            }
            if (StringUtils.isNotBlank(client.getMetadata().getAppId())) {
                appId = client.getMetadata().getAppId();
            }
        }

        HashMap<String, String> metadata = Maps.newHashMap();
        metadata.put("org_id", orgId);
        metadata.put("project_id", projectId);
        metadata.put("app_id", appId);

        App app = new App()
                .withId(appId)
                .withBrand(new AppBrand()
                        .withName(client.getClientName())
                        .withLogo(new AppLogo()
                                .withUrl(client.getLogoUri())
                        )
                )
                .withCreationDate(DateTime.now())
                .withDomains(
                        Lists.newArrayList(
                                getAllowedDomain(client)
                        )
                )
                .withRequestUris(
                        client.getRequestUris()
                );

        Set<String> scopes = Sets.newHashSet();

        List<OIDCClient> clients = Lists.newArrayList();
        List<OIDCCredential> oidcCredentials = Lists.newArrayList();

        // client_credentials, authorization_code is grant type
        // authorization_code, refresh_token
        if (client.getGrantTypes().contains("client_credentials") && client.getRedirectUris().isEmpty()) {

            oidcCredentials.add(new OIDCCredential()
                            .withClientId(client.getClientId())
                    .withCreationDate(DateTime.now())
                    .withName(client.getClientName())
                    .withStatus(OIDCCredential.OidcStatus.ENABLED)
                    .withAccessTokenStrategy(client.getAccessTokenStrategy())
                    .withTokenEndpointAuthMethod(client.getTokenEndpointAuthMethod()));

        } else {

            // could be credential or client.. use type of object
            clients.add(new OIDCClient().withClientId(client.getClientId())
                    .withCreationDate(DateTime.now())
                    .withAuthorizedOrigins(client.getAllowedCorsOrigins())
                    .withAccessTokenStrategy(client.getAccessTokenStrategy())
                    .withSubjectType(client.getSubjectType())
                    .withAuthorizedRedirectUris(client.getRedirectUris())
                    .withRefreshTokenLifespan(client.getRefreshTokenGrantRefreshTokenLifespan())
                    .withAccessTokenLifespan(client.getClientCredentialsGrantAccessTokenLifespan())
                    .withTokenEndpointAuthMethod(client.getTokenEndpointAuthMethod())
                    .withName(client.getClientName())
                    .withScopes(Arrays.stream(client.getScope().split(" ")).toList())
                    .withSkipConsent(false)
                    .withStatus(OidcStatus.ENABLED));

            scopes.addAll(Arrays.stream(client.getScope().split(" ")).toList());
        }


        ConsentGroup appConsent = new ConsentGroup().withScopes(scopes.stream().map(x -> {
            return new ConsentScope().withId(x);
        }).toList()).withId(projectId);

        Project p = new Project()
                .withId(projectId)
                .withApp(app.withConsent(appConsent))
                .withClients(clients)
                .withCredentials(oidcCredentials)
                .withCreationDate(DateTime.now())
                .withDescription("A project that was generated from the configuration found in Hydra")
                .withMetadata(metadata)
                .withOrgId(orgId)
                .withStatus(Project.Status.ACTIVE)
                .withName(client.getClientName());

        this.storage.projects().insertObject(p.getId(), p);
        log.info("Creating project {} in organization {}", projectId, orgId);
    }

    @Override
    public boolean repeat() {
        return true;
    }
}
