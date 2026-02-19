package io.klustr.console.org;

import com.google.common.collect.Lists;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.permissions.*;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.roles.RoleAssignment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Enables role management that defines roles and their permissions.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/users/{userId}/roles")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Role Management", description = "Organization management functions.")
public class UserRoleAssignmentService {
    private static final Logger log = LoggerFactory.getLogger(UserRoleAssignmentService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public UserRoleAssignmentService(Storage storage,
                                     PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    private static String docID(RoleAssignment assn) {
        if (StringUtils.isBlank(assn.getRoleId())) {
            throw new IllegalArgumentException("No role ID defined");
        }
        if (StringUtils.isBlank(assn.getAssignedTo())) {
            throw new IllegalArgumentException("No role assignment defined");
        }
        if (StringUtils.isBlank(assn.getOrgId())) {
            throw new IllegalArgumentException("No org ID defined");
        }
        if (assn.getAssigneeType() == null) {
            throw new IllegalArgumentException("No assignee type defined");
        }
        return U.md5((assn.getOrgId() + "/" + assn.getAssigneeType() + "/" + assn.getAssignedTo() + "/" + assn.getRoleId()).toLowerCase());
    }

    @GetMapping
    @Operation(
operationId = "getUserRoles",
            summary = "Retrieve roles for a specific user in the organization.",
            description = """
This endpoint returns all roles associated with the specified user within the given organization. It provides insights into the user's permissions and access levels. This information is crucial for managing user entitlements effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.permissions.list')")
    public UserEntitlements getEntitlements(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        // TODO debug me
        Set<Relation> acls = this.permissions.listRelations(SubjectKey.user(user), "orgs", new Target(organizationId));
        if (acls.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User is not a member of this organization."
            );
        }

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found"
            );
        }
        UserEntitlements e = new UserEntitlements();
        e.user_id = userId;
        e.org_id = organizationId;

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            return e;
        }

        // find matches
        acls.forEach(x -> {
            Optional<Role> match = orgRoles.getRoles().stream().filter(r -> {
                return r.getId().equalsIgnoreCase(x.scope().relation());
            }).findAny();
            if (!match.isEmpty()) {
                e.roles.add(match.get());
            }
        });

        return e;
    }

    /**
     * Create roles within the organization
     *
     * @param organizationId The organization ID that owns the role.
     * @param userId         The user to assign to the role
     * @param oauth          The OAuth credential making this request
     * @return Returns the {@link RoleAssignment} for this user or an error.
     */
    @PostMapping
    @Operation(
operationId = "updateUserRoles",
            summary = "Assign roles to a user in the organization",
            description = """
This endpoint allows the addition of specified roles to a user within a particular organization. It ensures that role management is handled securely and effectively. Use this operation to enhance user permissions and access based on organizational needs.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.roles.update')")
    public List<RoleAssignment> create(@PathVariable("organizationId") String organizationId,
                                 @RequestBody List<String> roles,
                                 @PathVariable("userId") String userId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Set<Relation> existing = this.permissions.listRelationsForObject(Target.of("orgs", organizationId));
        List<Relation> matches = Lists.newArrayList();

        List<RoleAssignment> result = Lists.newArrayList();
        for (String role :
                roles) {
            Optional<Relation> match = existing.stream().filter(x -> x.scope().relation().equalsIgnoreCase(role)).findFirst();
            if (match.isEmpty()) {
                RoleAssignment roleAssignment = this.create(organizationId, role, userId, oauth);
                result.add(roleAssignment);
            } else {
                result.add(new RoleAssignment()
                        .withAssigneeType(RoleAssignment.AssigneeType.USER)
                        .withOrgId(organizationId)
                        .withRoleId(role)
                        .withId(UUID.randomUUID().toString())
                        .withAssignedTo(userId)
                );
                this.permissions.grant(SubjectKey.userId(userId), Scope.attribute("roles", role), Target.of("orgs", organizationId));
            }
        }

        // find where existing doesn't exist anymore
        List<Relation> removed = existing.stream().filter(e -> {
            return !roles.contains(e.scope().relation());
        }).toList();
        removed.forEach(r -> {
            this.permissions.revoke(r.subject(), r.scope(), Target.of("orgs", organizationId));
        });

        return result;
    }

    /**
     * Link the user to the specified role.
     *
     * @param organizationId The organization ID that owns the role.
     * @param roleId         The role to assign the user
     * @param userId         The user to assign to the role
     * @param oauth          The OAuth credential making this request
     * @return Returns the {@link RoleAssignment} for this user or an error.
     */
    @PostMapping("/{roleId}")
    @Operation(
operationId = "addUserToRole",
            summary = "Assign a role to a user in the organization",
            description = """
This endpoint allows you to add a specified role to a user within the organization. It is essential for managing user permissions effectively. Ensure that you have the necessary authorization to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    // @PreAuthorize("hasAuthority('org.user.roles.get')")
    public RoleAssignment create(@PathVariable("organizationId") String organizationId,
                                 @PathVariable("roleId") String roleId,
                                 @PathVariable("userId") String userId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        RoleAssignment role = new RoleAssignment()
                .withAssignedTo(userId)
                .withAssigneeType(RoleAssignment.AssigneeType.USER)
                .withOrgId(organizationId)
                .withRoleId(roleId);

        if (StringUtils.isBlank(role.getRoleId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide a role ID."
            );
        }
        ensureRoleExists(organizationId, role.getRoleId());


        this.permissions.grant(SubjectKey.userId(userId), Scope.attribute("roles", roleId), Target.of("orgs", organizationId));

        return role;
    }

    @GetMapping("/{roleId}/delete")
    @Operation(
operationId = "removeUserRole",
            summary = "Remove a role from a user in the organization",
            description = """
This endpoint allows the removal of a specified role from a user within the organization. It ensures that the role is only removed if it currently exists for the user. This operation is crucial for maintaining accurate role assignments and permissions.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    // @PreAuthorize("hasAuthority('org.user.roles.update')")
    public void delete(@PathVariable("organizationId") String organizationId,
                       @PathVariable("roleId") String roleId,
                       @PathVariable("userId") String userId,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        RoleAssignment r = new RoleAssignment()
                .withAssignedTo(userId)
                .withAssigneeType(RoleAssignment.AssigneeType.USER)
                .withOrgId(organizationId)
                .withRoleId(roleId);
        String docSignature = docID(r);

        this.permissions.revoke(SubjectKey.userId(userId), Scope.attribute("roles", roleId), Target.of("orgs", organizationId));
    }

    private void ensureRoleExists(String orgId, String roleId) {
        if (StringUtils.isBlank(orgId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "OrgID missing"
            );
        }
        if (StringUtils.isBlank(roleId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing role ID"
            );
        }

        Org org = this.storage.organizations().getObject(orgId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer does not exist."
            );
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(orgId);

        if (orgRoles == null || orgRoles.getRoles() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No roles defined for org."
            );
        }
        if (orgRoles.getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(roleId)).findAny().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role " + roleId + " does not exist");
        }
    }

    public static class UserEntitlements {
        public String user_id;
        public String org_id;
        public List<Role> roles = Lists.newArrayList();
    }
}