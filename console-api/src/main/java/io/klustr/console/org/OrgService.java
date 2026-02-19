package io.klustr.console.org;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.permissions.*;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgOwner;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enables the management of billing, contact, and other related contracting information
 * for an account that consumes or participates. Required in order to create projects.
 */
@RestController
@Component
@RequestMapping("/directory/orgs")
@Tag(name = "Org Management", description = "Organization management functions.")
public class OrgService {
    private static final Logger log = LoggerFactory.getLogger(OrgService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    private final OrganizationSecurityPolicy security;

    private final OrganizationDefaultRoles defaultRoles;

    public OrgService(Storage storage,
                      PermissionProvider permissions,
                      OrganizationDefaultRoles defaultRoles,
                      OrganizationSecurityPolicy security) {
        this.storage = storage;
        this.permissions = permissions;
        this.security = security;
        this.defaultRoles = defaultRoles;
    }


    /**
     * Will list the organizations available that are granted for the current user.
     *
     * @param organizationId The organization ID
     * @param oauth          The principle making this request
     * @return The organization that matches.
     */
    @GetMapping("/{organizationId}")
    @Operation(
operationId = "getOrganizationDetails",
            summary = "Retrieve details of a specific organization.",
            description = """
Fetches the organization information associated with the provided organization ID. This endpoint is intended for users with access to their organizations, ensuring that only authorized users can view sensitive organization data.
""",
            parameters = {
                    @Parameter(name = "organizationId", required = true, description = "The organization ID to retrieve.")
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.get')")
    public Org getOrganization(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return org;
    }

    @GetMapping("/{organizationId}/roles")
    @Operation(
operationId = "listOrganizationRoles",
            summary = "Retrieve all roles defined in the organization",
            description = """
This endpoint returns a list of all roles currently defined within the specified organization. It is essential for understanding role assignments and permissions associated with the organization. Use this information to manage access and responsibilities effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.list')")
    public List<Role> getRoles(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Org entity = this.storage.organizations().getObject(organizationId);
        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles().withId(organizationId);
        }

        return orgRoles.getRoles();
    }

    /**
     * Returns all the permissions currently configured for the organization
     *
     * @param organizationId The organization to list permissions for.
     * @return Returns all unique permissions.
     */
    @GetMapping("/{organizationId}/permissions")
    @Operation(
operationId = "listOrganizationPermissions",
            summary = "Retrieve all permissions for the organization",
            description = """
This endpoint returns a comprehensive list of permissions associated with the specified organization. It aggregates permissions across all roles and groups, providing a clear overview of access rights. This information is essential for understanding the permission landscape within the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<Permission> getAllPermissions(@PathVariable("organizationId") String organizationId,
                                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found"
            );
        }
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles().withId(organizationId);
        }
        if (orgRoles.getRoles() == null || orgRoles.getRoles().isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, Permission> permissions = Maps.newConcurrentMap();
        orgRoles.getRoles().forEach(x -> {
            x.getPermissions().forEach(p -> {
                permissions.put(p.getScope(), new Permission(p.getScope()));
            });
        });
        return permissions.values().stream().toList();
    }

    @GetMapping("/domains")
    @Operation(
operationId = "checkDomainAvailability",
            summary = "Verify if a domain is available for new organization.",
            description = """
This endpoint checks if the specified domain is claimed or available for assignment to a new organization. Ensuring unique domain names is crucial for organizational identity. Use this to prevent conflicts and maintain a clear domain structure.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public DomainCheckResponse checkDomain(
            @RequestParam(value = "domain", required = false) String domain,
            @RequestParam(value = "subdomain", required = false) String subdomain,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        DomainCheckResponse res = new DomainCheckResponse();
        res.available = true;

        if (StringUtils.isNotBlank(domain)) {
            DbQuery fq = Db.query("domain").eq(domain.trim().toLowerCase());
            if (this.storage.organizations().findFirst(fq).isPresent()) {
                res.available = false;
                return res;
            }
        }

        if (StringUtils.isNotBlank(subdomain)) {
            DbQuery fq = Db.query("subdomain").eq(subdomain.trim().toLowerCase());
            if (this.storage.organizations().findFirst(fq).isPresent()) {
                res.available = false;
                return res;
            }
        }

        return res;
    }

    @PostMapping
    @Operation(
operationId = "createOrg",
            summary = "Create a new organization with default owner.",
            description = """
This endpoint allows users to create a new organization. The organization will be assigned a default owner upon creation. Ensure that you provide all necessary details in the request body. This operation is essential for users managing organizational accounts.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.create')")
    public Org create(
            @RequestBody Org org,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        DbQuery fq = Db.query("domain").eq(org.getDomain());
        Optional<Org> match = this.storage.organizations().findFirst(fq);
        if (match.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization already exists and can not be created."
            );
        }

        if (StringUtils.isBlank(org.getId())) {
            String id = org.getDomain().toLowerCase().replaceAll(" ", "-");
            org.setId(id);
        }

        Org existsAlready = this.storage.organizations().getObject(org.getId());
        if (existsAlready != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization ID conflict"
            );
        }

        org.setCreationDate(DateTime.now());
        org.setModifiedDate(DateTime.now());
        org.setOwner(
                new OrgOwner()
                        .withId(PrincipleUtils.tryGetEmailForUser(user))
        );

        this.storage.organizations().insertObject(org.getId(), org);

        OrgRoles orgRoles = this.storage.organization_roles().getObject(org.getId());
        if (orgRoles == null) {
            orgRoles = new OrgRoles().withId(org.getId());
        }

        defaultRoles.generateAndSave(orgRoles);

        this.permissions.grant(SubjectKey.user(user), Scope.admin("orgs"), new Target("orgs", org.getId()));
        this.permissions.grant(SubjectKey.user(user), Scope.namespace("members"), new Target("orgs", org.getId()));

        return org;
    }

    @PutMapping("/{organizationId}")
    @Operation(
operationId = "updateOrg",
            summary = "Update an existing organization with default owner.",
            description = """
This endpoint allows users to update an existing organization. It ensures that the organization retains its default ownership structure. This operation is crucial for maintaining accurate organizational data and is accessible to authorized users.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.update')")
    public Org update(
            @PathVariable("organizationId") String organizationId,
            @RequestBody Org org,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        if (org.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Organization does not exist."
            );
        }

        org.setModifiedDate(DateTime.now());
        this.storage.organizations().updateObject(org.getId(), org);
        return org;
    }

    @DeleteMapping("/{organizationId}")
    @Operation(
operationId = "deleteOrganization",
            summary = "Delete an organization from the directory.",
            description = """
This endpoint allows for the deletion of an organization identified by its ID. It ensures that only authorized users can perform this action, maintaining the integrity of the organization management system. Use this operation to remove organizations that are no longer needed.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.delete')")
    public void delete(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Organization does not exist."
            );
        }

        // TODO clear permissions are handle async/cleanup

        this.storage.organizations().deleteObject(organizationId);
    }

    @GetMapping("/suggest")
    @Operation(
operationId = "suggestOrganizationsToJoin",
            summary = "Suggest organizations for user to join based on context.",
            description = """
This endpoint provides suggestions for organizations that a user can join. It takes into account the user's email domain and other trusted factors to enhance the relevance of the suggestions. This is particularly useful for users looking to expand their professional network within the platform.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public List<Org> suggestOrganizationsToJoin(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {
        String email = PrincipleUtils.tryGetEmailForUser(principle);
        if (StringUtils.isBlank(email)) {
            return Lists.newArrayList();
        }

        String domain = email.split("@")[1].replaceAll("@", "").toLowerCase();

        DbQuery fq = Db.query("domain").eq(domain);

        DocumentResult<Org> results = this.storage.organizations().insecureQuery(fq, new Pagination().withStart(0).withLimit(10));
        return results.docs;
    }

    @GetMapping("/{organizationId}/members")
    @Operation(
operationId = "listOrganizationMembers",
            summary = "List all members of the specified organization.",
            description = """
This endpoint retrieves all members associated with the given organization ID. It ensures that the current user has the necessary permissions to view this information. This is essential for understanding the organizational structure and collaboration opportunities.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public Set<String> listOrganizationMembers(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        if (!this.security.listOrgsForUser(user).contains(organizationId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User has not relationship with specified organization."
            );
        }

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization does not exist."
            );
        }

        Set<Relation> relations = this.permissions.listRelationsForObject(Target.of("orgs", organizationId));
        return relations.stream().map(x -> {
            return x.subject().id();
        }).collect(Collectors.toSet());
    }

    @PutMapping("/{organizationId}/members/{subjectId}")
    @Operation(
operationId = "addOrganizationMember",
            summary = "Register a new member in the organization",
            description = """
This endpoint allows the addition of a member to a specified organization. It requires the organization ID and the subject ID of the member to be added. Ensure that the authenticated user has the necessary permissions to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.members.create')")
    public void addOrganizationMember(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("subjectId") String subjectId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        if (!this.security.listOrgsForUser(user).contains(organizationId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User has not relationship with specified organization."
            );
        }

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization does not exist."
            );
        }


        this.permissions.grant(SubjectKey.userId(subjectId), Scope.namespace("members"), Target.of("orgs", organizationId));
    }

    @DeleteMapping("/{organizationId}/members/{subjectId}")
    @Operation(
operationId = "removeOrganizationMember",
            summary = "Remove a member from the organization.",
            description = """
This endpoint allows the removal of a specified member from the organization. It ensures that only authorized users can perform this action, maintaining the integrity of the organization's membership. This operation is crucial for managing organizational roles and responsibilities effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.members.create')")
    public void removeOrganizationMember(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("subjectId") String subjectId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        if (!this.security.listOrgsForUser(user).contains(organizationId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User has not relationship with specified organization."
            );
        }

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization does not exist."
            );
        }


        this.permissions.revoke(SubjectKey.userId(subjectId), Scope.namespace("members"), Target.of("orgs", organizationId));
    }

    @PutMapping("/{organizationId}/members")
    @Operation(
operationId = "joinOrganizationWithVerifiedEmail",
            summary = "Join an organization with a verified email address",
            description = """
This endpoint allows users to join an organization by providing a confirmed email address that matches the organization's domain. Users may also join if they have received an invitation from an existing member. Basic membership permissions will be granted upon successful verification.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public void joinOrganization(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        String email = PrincipleUtils.tryGetEmailForUser(user);
        if (StringUtils.isBlank(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Subject has not email address."
            );
        }
        String domain = email.split("@")[1].replaceAll("@", "").toLowerCase();
        DbQuery fq = Db.query("domain").eq(domain);

        DocumentResult<Org> results = this.storage.organizations().insecureQuery(fq, new Pagination().withStart(0).withLimit(10));
        Optional<Org> match = results.docs.stream().filter(x -> {
            return x.getId().equalsIgnoreCase(organizationId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid organization to join."
            );
        }

        boolean existingMember = this.permissions.check(SubjectKey.user(user), Scope.namespace("members"), Target.of("orgs", organizationId));
        if (existingMember) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This user is already a member of the organization."
            );
        }

        this.permissions.grant(SubjectKey.user(user), Scope.namespace("members"), Target.of("orgs", organizationId));
    }

    @DeleteMapping("/{organizationId}/members")
    @Operation(
operationId = "leaveOrganization",
            summary = "User leaves the specified organization.",
            description = """
This operation allows the authenticated user to remove their access and permissions to the specified organization. It ensures that the user's association with the organization is terminated, reflecting the change in their organizational role. This is crucial for maintaining accurate access control and organizational membership.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public void leaveOrganization(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        boolean isMember = this.permissions.check(SubjectKey.user(user), Scope.namespace("members"), Target.of("orgs", organizationId));

        if (!isMember) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User is not a member of the organization."
            );
        }

        this.permissions.revokeAll(SubjectKey.user(user), Target.of("orgs", organizationId));
    }

    public static class Permission {
        public String scope;

        public Permission() {
        }

        public Permission(String scope) {
            this.scope = scope;
        }
    }

    public static class DomainCheckResponse {
        public boolean available = false;
    }

}