package io.klustr.console.setup;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.documents.CollectionService;
import io.klustr.setup.*;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.*;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgOwner;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectOwner;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/setup")
@Tag(name = "Installation APIs", description = "Enables you to claim ownership and perform admin functions for entire platform.")
public class SetupService {
    private static final Logger log = LoggerFactory.getLogger(SetupService.class);

    public static final String ADMIN_SETUP = "admin::setup";
    private final Storage storage;
    private final PermissionProvider permissions;

    private final SetupRunner setupRunner;

    private final SetupInitializer setupInit;

    private final SetupStateStorage state;

    public SetupService(Storage storage, PermissionProvider permissions, SetupStateStorage state, SetupRunner setupRunner, SetupInitializer setupInit) {
        this.storage = storage;
        this.permissions = permissions;
        this.setupRunner = setupRunner;
        this.setupInit = setupInit;
        this.state = state;
    }

    @GetMapping("/status")
    @Operation(
summary = "Admin check for service setup status",
            operationId = "adminCheckServiceSetup",
            hidden = true,
            description = """
This endpoint verifies that all necessary configurations are in place for the service. It is intended for administrative users to ensure the platform is ready for operation. Proper setup is crucial for maintaining service reliability and performance.
"""
)
    public ResponseEntity<JsonNode> index() {
        if (this.state.history().tryGetObject(ADMIN_SETUP).isEmpty()) {
            JsonNode node = U.fromJson("{ \"status\": \"pending\" }", JsonNode.class);
            return ResponseEntity.ok(node);
        }
        JsonNode node = U.fromJson("{ \"status\": \"complete\" }", JsonNode.class);
        return ResponseEntity.ok(node);
    }

    @PostMapping("/claim")
    @Operation(
summary = "Claim ownership of the default organization as admin.",
            operationId = "adminClaimOwnershipOfDefaultOrganization",
            description = """
This endpoint allows an admin user to claim ownership of the default organization within the platform. Proper authentication and necessary permissions are required to access this functionality. This action is intended for administrative purposes only.
""",
            hidden = true,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void claimOrg(@RequestParam(value = "scope", required = false) String scope,
                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        if (this.state.history().tryGetObject(ADMIN_SETUP).isPresent()) {
            log.info("Ownership already claimed and run on all available projects.");
            return;
        }
        scope = StringUtils.isBlank(scope) ? "*" : scope;
        if (scope.equalsIgnoreCase("*")) {
            this.storage.organizations().listObjectIds(Db.all(), Pagination.all()).docs.forEach(id -> {
                claimOrgForUser(id, user);
            });
        } else {
            claimOrgForUser(scope, user);
        }
        log.info("Ownership claimed on all projects for administrative setup.");
        this.state.history().insertObject(ADMIN_SETUP, Json.toJsonNode( U.toJson(new ClaimState(ADMIN_SETUP, user))));
    }

    public static class ClaimState {
        public String id;
        public String user;
        public DateTime timestamp = DateTime.now();
        public ClaimState(String id, OAuth2AuthenticatedPrincipal principal) {
            this.id  = id;
            this.user = principal.getName();
        }
        public ClaimState() { }
    }

    private void claimOrgForUser(String orgId, OAuth2AuthenticatedPrincipal user) {
        Org org = this.storage.organizations().getObject(orgId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "organization could not be found."
            );
        }
        String orgSetupKey = "org::claims::" + orgId;
        if (this.state.history().tryGetObject(orgSetupKey).isEmpty()) {
            // set the owner
            String email = PrincipleUtils.tryGetEmailForUser(user);
            org.setOwner(new OrgOwner().withId(email));
            this.storage.organizations().updateObject(org.getId(), org);
            // grant user admin and member roles to org
            this.permissions.grant(SubjectKey.user(user), Scope.admin("orgs"), Target.of("orgs", org.getId()));
            this.permissions.grant(SubjectKey.user(user), Scope.namespace("members"), Target.of("orgs", org.getId()));
            this.state.history().insertObject(Json.toJsonNode(U.toJson(new ClaimState(orgSetupKey, user))) );
            log.info("Ownership claimed on org {} for user {}.", orgId, user.getName());
        }
        claimProjectsInOrganization(user, org);

    }

    private void claimProjectsInOrganization(OAuth2AuthenticatedPrincipal user, Org org) {
        // crawl all the projects and make owner
        DbQuery byOrg = Db.query("org_id").eq(org.getId());
        DocumentResult<Project> projects =
                this.storage.projects().insecureQuery(byOrg, Pagination.all());
        projects.docs.forEach(x -> {
            String projectClaimKey = "project::claims::" + org.getId() + "::" + x.getId();
            if (this.state.history().tryGetObject(projectClaimKey).isPresent()) {
                log.info("Ownership already claimed for project {} in org {}", x.getId(), x.getOrgId());
                return;
            }
            // grant user membership permissions
            this.permissions.grant(SubjectKey.user(user),
                    Scope.namespace("members"),
                    Target.of("projects", x.getId()));

            // grant as owner as well
            this.permissions.grant(SubjectKey.user(user),
                    Scope.namespace("owners"),
                    Target.of("projects", x.getId()));
            this.storage.projects().updateObject(x.getId(), x);

            this.state.history().insertObject(projectClaimKey, Json.toJsonNode(U.toJson(new ClaimState(projectClaimKey, user))));
            log.info("Ownership claimed for project {} in org {} for user {}", x.getId(), x.getOrgId(), user.getName());
        });
    }
}
