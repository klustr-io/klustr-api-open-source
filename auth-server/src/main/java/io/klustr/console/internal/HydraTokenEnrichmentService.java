package io.klustr.console.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.consent.UserConsentProvider;
import io.klustr.consent.agreements.UserAgreementProvider;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.permissions.*;
import io.klustr.permissions.DefaultUserPermissions;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.oidc.OIDCCredential;
import io.klustr.schemas.console.oidc.UserPairwiseIds;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.orgs.RolePermission;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.roles.RoleAssignment;
import io.klustr.schemas.integrations.ory.HydraTokenEnrichmentPayload;
import io.klustr.schemas.integrations.ory.Metadata;
import io.klustr.schemas.persons.hydra.*;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Will handle requests from Hydra that will populate and update
 * the access and identity token.
 *
 * <a href="https://www.ory.sh/docs/hydra/guides/claims-at-refresh">https://www.ory.sh/docs/hydra/guides/claims-at-refresh</a>
 */
@RestController
@Component
@RequestMapping("/ory/hydra")
@ResourceGroup("Ory Integration")
@Tag(name = "Ory Integration")
public class HydraTokenEnrichmentService {

    private static final Logger log = LoggerFactory.getLogger(HydraTokenEnrichmentService.class);

    private final PermissionProvider permissions;

    private final Storage storage;

    private final UserConsentProvider consent;

    private final UserAgreementProvider agreements;

    private final InstrumentationFactory metrics;

    private final HydraApi hydra;

    private final DefaultUserPermissions userPermissions;

    public HydraTokenEnrichmentService(Storage storage,
                                       UserConsentProvider consent,
                                       UserAgreementProvider agreements,
                                       PermissionProvider permissions,
                                       HydraApi hydra,
                                       MeterRegistry metrics) {
        this.storage = storage;
        this.consent = consent;
        this.permissions = permissions;
        this.agreements = agreements;
        this.hydra = hydra;
        this.metrics = new InstrumentationFactory(metrics);
        this.userPermissions = new DefaultUserPermissions(permissions, storage);
    }

    private Set<String> getClientPermissions(String clientId) {
        Metadata metadata = this.hydra.getClient(clientId).getMetadata();
        if (metadata == null) return Sets.newHashSet();
        if (metadata.getPermissions() == null) Sets.newHashSet();
        return Sets.newHashSet(metadata.getPermissions());
    }

    private Set<String> getClientRoles(String clientId) {
        Metadata metadata = this.hydra.getClient(clientId).getMetadata();
        if (metadata == null) return Sets.newHashSet();
        if (metadata.getRoles() == null) Sets.newHashSet();
        return Sets.newHashSet(metadata.getRoles());
    }

    public static String extractSsoProvider(String jsonPayload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonPayload);

            // Navigate to request.url
            String url = root.path("request").path("url").asText();
            if (url == null || url.isEmpty()) {
                return null;
            }

            // Extract path from URL
            URI uri = new URI(url);
            String[] segments = uri.getPath().split("/");

            // Look for `oidc/callback/{provider}`
            for (int i = 0; i < segments.length - 1; i++) {
                if ("callback".equals(segments[i]) && "oidc".equals(segments[i - 1])) {
                    return segments[i + 1]; // The provider
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Token enrichment only for users, not meant for service accounts or accounts
     * with no subject identifier. If using those inject attributes into the metadata
     *
     * @param json The json payload as sent from hydra for enrichment
     *
     * @return The enrichment which should include groups, roles, and permissions.
     */
    @PostMapping("/token_hook")
    @Operation(
description = """
This endpoint enriches the access and identity token by adding roles and groups. It is designed for internal use to ensure that tokens contain the necessary claims for authorization. Properly populating these claims enhances security and user experience.
""",
            operationId = "enrichTokenRolesAndGroups",
            summary = "Enrich token with roles and groups."
)
    @Timed("hydra.token.enrichment.timer")
    public HydraEnrichmentResponse token(@RequestBody String json) {
        log.info(json);

        HydraTokenEnrichmentPayload payload = U.fromJson(json, HydraTokenEnrichmentPayload.class);

        // we can get mapping of ppid to real
        String public_or_pairwise_subject_id = payload.getSession().getIdToken().getIdTokenClaims().getSub();
        String subject_id = payload.getSession().getIdToken().getSubject();
        String client_id = payload.getSession().getClientId();

        this.metrics.client(client_id).tokenErichment().increment();

        DbQuery fq = Db.query("clients").contains("client_id").eq(client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);

        // could be client but also is possible it is just a raw credentials
        if (project == null || project.isEmpty()) {
            // TODO performance optimize this with caching? or maybe we already cache
            log.info("Could not find client {} will search in credentials", client_id);
            fq = Db.query("credentials").contains("client_id").eq(client_id);
            project = this.storage.projects().findFirst(fq);
            subject_id = client_id; // switch up subject_id
            public_or_pairwise_subject_id = client_id;  // force to subject id
        }

        Set<String> extra_scopes = Sets.newHashSet();
        Set<String> roles = Sets.newHashSet();
        Set<String> groups = Sets.newHashSet();
        Set<String> all_permissions = getPermissionsForClient(client_id);
        Set<String> audiences = Sets.newHashSet();
        Set<String> experiments = Sets.newHashSet();

        log.info("client_id: " + client_id + " ppid/sub: " + public_or_pairwise_subject_id + " sub:" + subject_id);

        if (project.isPresent() && StringUtils.isNotBlank(public_or_pairwise_subject_id)) {
            log.info("✔\uFE0F client_id: " + client_id + " maps to project " + project.get().getId());

            if (!subject_id.equalsIgnoreCase(public_or_pairwise_subject_id)) {
                mapPairwiseIdentifier(subject_id, public_or_pairwise_subject_id, client_id, project.get());
            }

            this.metrics.client(project.get().getId(), client_id).tokenErichment().increment();

            roles.addAll(getRolesForUser(public_or_pairwise_subject_id, "clients", client_id));
            roles.addAll(getRolesForUser(public_or_pairwise_subject_id, "projects", project.get().getId()));

            groups.addAll(getGroupsForUser(public_or_pairwise_subject_id, "clients", client_id));
            groups.addAll(getGroupsForUser(public_or_pairwise_subject_id, "projects", project.get().getId()));

            audiences.add(project.get().getId());
            audiences.add(project.get().getOrgId());

            // application roles
            if (project.get().getApp() != null && project.get().getApp().getId() != null) {
                roles.addAll(getRolesForUser(public_or_pairwise_subject_id, "apps", project.get().getApp().getId()));
                groups.addAll(getGroupsForUser(public_or_pairwise_subject_id, "apps", project.get().getApp().getId()));
            }

            // inject all roles associated with the specified role
            // given the organization the client is under.
            if (StringUtils.isNotBlank(project.get().getOrgId())) {
                log.info("Getting org {} permissions, roles, and groups for {}", project.get().getOrgId(), subject_id);
                Org org = storage.organizations().getObject(project.get().getOrgId());
                OrgRoles orgRoles = storage.organization_roles().getObject(project.get().getOrgId());
                if (orgRoles != null) {
                    log.info("Org has roles {}", project.get().getOrgId());
                    roles.addAll(getRolesForUser(public_or_pairwise_subject_id, "orgs", project.get().getOrgId()));
                    groups.addAll(getGroupsForUser(public_or_pairwise_subject_id, "orgs", project.get().getOrgId()));
                    Set<String> orgPerms = getOrgPermissions(public_or_pairwise_subject_id, orgRoles);
                    all_permissions.addAll(orgPerms);

                    // grab roles from token
                    Set<String> clientRoles = getClientRoles(client_id);
                    Set<String> clientPerms = getOrgPermissions(clientRoles, orgRoles);
                    all_permissions.addAll(clientPerms);

                    log.info("\uD83D\uDD12 Result org {} roles for {} -> {}", project.get().getOrgId(), subject_id, Json.toJsonPrettyFormat(roles));
                    log.info("\uD83D\uDD12 Result org {} groups for {} -> {}", project.get().getOrgId(), subject_id, Json.toJsonPrettyFormat(groups));
                    log.info("\uD83D\uDD12 Result org {} permissions for {} -> {}", project.get().getOrgId(), subject_id, Json.toJsonPrettyFormat(orgPerms));
                } else {
                    log.warn("!! Org {} has NO rules defined", project.get().getOrgId());
                }
            }

            // add any scopes associated to the agreements that are on file
            List<UserAgreementConsent> agreements = this.agreements.getUserAgreements(public_or_pairwise_subject_id, project.get().getOrgId());
            agreements.forEach(agg -> {
                if (agg.getScopes() != null && !agg.getScopes().isEmpty()) {
                    if (agg.getStatus() == UserAgreementConsent.Status.ACTIVE) {
                        Agreement agreement = this.storage.agreements().getObject(agg.getAgreementId());
                        if (agreement == null) {
                            agreement = this.storage.agreements().getObject(agg.getAgreementId());
                        }
                        if (agreement != null) {
                            if (agreement.getStatus() == Agreement.Status.PUBLISHED) {
                                extra_scopes.addAll(agg.getScopes().stream().map(UserConsentScope::getId).toList());
                            }
                        }
                    }
                }
            });

            // find additional scopes given experiment that they have joined
            List<UserExperimentConsent> user_experiments_joined = this.consent.getExperimentConsentByProject(public_or_pairwise_subject_id, project.get().getId());

            // are the experiments still active if so add any scopes
            user_experiments_joined.forEach(x -> {
                Experiment exp = this.storage.experiments().getObject(x.getExperimentId());
                if (exp.getStatus() == Experiment.Status.RUNNING || exp.getStatus() == Experiment.Status.PUBLISHED) {
                    if (exp.getStartDate() != null && exp.getStartDate().isBeforeNow()) {
                        if (exp.getStopDate() != null && exp.getStopDate().isAfterNow()) {
                            experiments.add(exp.getId());
                            exp.getScopes().forEach(scope -> {
                                extra_scopes.add(scope.getId());
                            });
                        }
                    }
                }
            });

        } else {
            log.warn("⚠\uFE0F client_id: " + client_id + " could not be mapped to a project!");
        }


        HydraEnrichmentResponse r = new HydraEnrichmentResponse();

        extra_scopes.addAll(payload.getRequest().getGrantedScopes());

        // ensure traits are updated

        // TODO move this out to grafana specific enrichment
        if (all_permissions.contains("orgs:klustr.io:admin")) {
            roles.add("Admin");
        } else {
            roles.add("Viewer");    // add for grafana hack/temp work
        }

        DefaultUserPermissions.LinkedObjects resolve = userPermissions.resolve(subject_id);
        Set<String> projects = resolve.projectIds;
        Set<String> orgs = resolve.orgIds;

        r.setSession(new HydraSessionEnrichment()
                .withIdToken(new HydraIdTokenEnrichment()
                        .withAud(audiences.stream().toList())
                        .withSsoProvider(extractSsoProvider(json))
                        .withGroups(groups)
                        .withRoles(roles)
                        .withExperiments(experiments.stream().toList())
                        .withOrgs(Sets.newHashSet(orgs))
                        .withProjects(projects)
                        .withSource(new HydraSessionSource()
                                .withOrgId(project.map(Project::getOrgId).orElse(null))
                                .withProjectId(project.map(Project::getId).orElse(null))
                                .withAppId(project.map(value -> value.getApp().getId()).orElse(null))
                                .withClientId(client_id)
                        )
                )
                .withAccessToken(new HydraAccessTokenEnrichment()
                        .withClientId(client_id)
                        .withOrgId(project.map(Project::getOrgId).orElse(null))
                        .withProjectId(project.map(Project::getId).orElse(null))
                        .withPermissions(all_permissions)
                        .withGroups(groups)
                        .withRoles(roles)
                        .withOrgs(orgs)
                        .withProjects(projects)
                        .withScope(Joiner.on(" ").join(extra_scopes))
                )
        );

        log.info("[User] P: {}, SUB: {}, JSON: {}", public_or_pairwise_subject_id, subject_id, Json.toJsonPrettyFormat(r));

        return r;
    }

    private void mapPairwiseIdentifier(String subject_id, String public_or_pairwise_subject_id, String client_id, Project project) {
        String key = client_id + ":" + subject_id;
        Optional<UserPairwiseIds> idmap = this.storage.user_pairwise_ids().tryGetObject(key);
        if (idmap.isEmpty()) {
            idmap = Optional.of( new UserPairwiseIds()
                    .withId(key)
                    .withSubjectId(subject_id)
                    .withClientId(client_id)
                    .withProjectId(project.getId())
                    .withPairwiseId(public_or_pairwise_subject_id)
                    .withTimestamp(new DateTime())
                    .withOrgId(project.getOrgId()));
            this.storage.user_pairwise_ids().insertObject(idmap.get().getId(), idmap.get());
        }
    }

    private Set<String> getOrgPermissions(String subject_id, OrgRoles org) {
        Set<String> permissions = Sets.newHashSet();
        Set<Relation> acls = this.permissions.listRelations(SubjectKey.userId(subject_id), "roles", Target.of("orgs", org.getId()));

        // for all matched roles get the permissions
        acls.forEach(roleObj -> {
            // get all roles match '*' for all roles
            Optional<Role> matchRole = org.getRoles().stream().filter(r ->
                    roleObj.scope().relation().equalsIgnoreCase("*") ||
                            r.getId().equalsIgnoreCase(roleObj.scope().relation())).findFirst();
            if (matchRole.isPresent()) {
                List<String> rolePermissions = matchRole.get().getPermissions().stream().map(RolePermission::getScope).toList();
                permissions.addAll(rolePermissions);
            }
        });

        return permissions;
    }

    private Set<String> getOrgPermissions(Set<String> roles, OrgRoles org) {
        Set<String> permissions = Sets.newHashSet();
        // for all matched roles get the permissions
        roles.forEach(roleId -> {
            // get all roles match '*' for all roles
            Optional<Role> matchRole = org.getRoles().stream().filter(r ->
                    roleId.equalsIgnoreCase("*") ||
                            r.getId().equalsIgnoreCase(roleId)).findFirst();
            if (matchRole.isPresent()) {
                List<String> rolePermissions = matchRole.get().getPermissions().stream().map(RolePermission::getScope).toList();
                permissions.addAll(rolePermissions);
            }
        });

        return permissions;
    }

    private Set<String> getPermissionsForClient(String client_id) {

        Set<Relation> items = this.permissions.listRelations(SubjectKey.client(client_id),
                "permissions",
                Target.of("clients", client_id));

        LinkedHashSet<String> result = Sets.newLinkedHashSet(items.stream().map(x -> {
            return x.object().value();
        }).toList());
        Set<String> perms = this.getClientPermissions(client_id);
        result.addAll(perms);
        return perms;
    }

    private Set<String> getRolesForUser(String subject_id, String noun, String object_id) {

        Set<Relation> relations = this.permissions.listRelations(SubjectKey.userId(subject_id), "roles", Target.of(noun, object_id));
        List<Relation> acls = relations.stream()
                .filter(x -> x.scope().namespace().equalsIgnoreCase("roles")).map(x -> {
                    return x;
                }).toList();

        return acls.stream().map(x -> {
            return noun + ":" + object_id + ":" + x.scope().relation();
        }).collect(Collectors.toSet());
    }

    private Set<String> getGroupsForUser(String subject_id, String noun, String object_id) {
        Set<Relation> acls = this.permissions.listRelations(SubjectKey.userId(subject_id), "groups", Target.of(noun, object_id));
        return acls.stream().map(x -> {
            return noun + ":" + object_id + ":" + x.scope().relation();
        }).collect(Collectors.toSet());
    }
}
