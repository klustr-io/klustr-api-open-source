package io.klustr.console;

import com.google.common.collect.Lists;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.SubjectKey;
import io.klustr.permissions.Target;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.events.EventInviteUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.EntityReference;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Handles mapping a user to the organization.
 */
@RestController
@Component
@RequestMapping("/console/orgs")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectOrgService {

    private Storage storage;

    private ProjectSecurityPolicy projectPolicy;

    private OrganizationSecurityPolicy organizationPolicy;

    private final PermissionProvider permissions;

    public ProjectOrgService(Storage storage,
                             RethinkDbConnectionPool pool,
                             KafkaTemplate<EventInviteUserRequest> invites,
                             OrganizationSecurityPolicy organizationPolicy,
                             PermissionProvider permissions,
                             ProjectSecurityPolicy projectPolicy) {
        this.storage = storage;
        this.projectPolicy = projectPolicy;
        this.organizationPolicy = organizationPolicy;
        this.permissions = permissions;
    }


    @GetMapping
    @Operation(
operationId = "getAccessibleOrganizations", summary = "Retrieve organizations accessible to the authenticated user",
            description = """
This endpoint provides a list of organizations that the authenticated user can view. It ensures that users only access organizations relevant to their permissions, thereby enhancing security and usability. This functionality is essential for managing user access within the console API.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public  ResponseEntity<List<EntityReference>> getOrganizations(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        List<EntityReference> result = Lists.newArrayList();
        this.organizationPolicy.listOrgsForUser(oauth).forEach(orgId -> {
            Org org = this.storage.organizations().getObject(orgId);
            if (org == null) return;
            result.add(new EntityReference()
                    .withName(org.getName())
                    .withId(org.getId()));
        });

        Set<Relation> orgMemberships = this.permissions.listRelations(SubjectKey.user(oauth), "members", Target.inAny("orgs"));
        Optional<Relation> anyActiveOrgs = orgMemberships.stream().filter(x -> this.storage.organizations().getObject(x.object().value()) != null).findFirst();
        Set<String> orgIds = orgMemberships.stream().map(x -> x.object().value()).collect(Collectors.toSet());
        orgIds.forEach(org -> {
            Org match = this.storage.organizations().getObject(org);
            if (match == null) return;;
            if (!result.stream().anyMatch(x -> x.getId().equalsIgnoreCase(match.getId()))) {
                result.add(new EntityReference()
                        .withName(match.getName())
                        .withId(match.getId())
                );
            }
        });

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(2, TimeUnit.SECONDS)).body(result);
    }


    @PutMapping("/{organizationId}/projects/{projectId}")
    @Operation(
operationId = "moveProjectToOrganization", summary = "Move project to a specified organization",
            description = """
This endpoint allows users to transfer a project to a designated organization. It ensures proper assignment for enhanced organizational management and oversight. This operation is vital for aligning projects with the strategic goals of the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void moveProject(@PathVariable("projectId") String projectId,
                            @PathVariable("organizationId") String organizationId,
                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);
        if (!this.projectPolicy.hasDeleteAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Project is not movable by user, the project being moved needs to have delete rights."
            );
        }

        project.setOrgId(organizationId);
        this.storage.projects().updateObject(project.getId(), project);
    }

    @GetMapping("/{organizationId}/projects")
    @Operation(
operationId = "listOrganizationProjects", summary = "List projects for the specified organization",
            description = """
Retrieve a list of projects accessible to the authenticated user within the specified organization. This endpoint ensures that users can only view projects they have permission to access, thereby enhancing the management of organizational resources.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('projects.list')")
    public ResponseEntity<List<Project>> orgProjects(@PathVariable("organizationId") String organizationId,
                                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {

        DbQuery fq = Db.query("org_id").eq(organizationId);
        DocumentResult<Project> projects = this.storage.projects().insecureQuery(fq, Pagination.all());
        List<Project> result = projects.docs.stream().toList();

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(2, TimeUnit.SECONDS)).body(result);
    }
}
