package io.klustr.console.org;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.Target;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.orgs.RolePermission;
import io.klustr.schemas.console.roles.RoleAssignment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
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

import java.util.*;

/**
 * Enables role management that defines roles and their permissions.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/roles")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Role Management", description = "Organization management functions.")
public class OrganizationRoleService {
    private static final Logger log = LoggerFactory.getLogger(OrganizationRoleService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public OrganizationRoleService(Storage storage,
                                   PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    @GetMapping("/permissions")
    @Operation(
operationId = "listOrgPermissions",
            summary = "List all permissions for the organization",
            description = """
Retrieve a comprehensive list of permissions configured for the organization. This includes permissions associated with all roles and groups, providing a clear overview of access rights. Useful for understanding and managing organizational permissions effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<OrgService.Permission> getAllPermissions(@PathVariable("organizationId") String organizationId,
                                                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found"
            );
        }
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null || orgRoles.getRoles().isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, OrgService.Permission> permissions = Maps.newConcurrentMap();
        orgRoles.getRoles().forEach(x -> {
            x.getPermissions().forEach(p -> {
                permissions.put(p.getScope(), new OrgService.Permission(p.getScope()));
            });
        });
        return permissions.values().stream().toList();
    }

    @PostMapping("/generate")
    @Operation(
operationId = "generateOrgRoles",
            summary = "Generate roles and permissions for an organization",
            description = """
This endpoint generates a default set of roles and permissions for the specified organization. It ensures standard compliance in role management, facilitating easier maintenance and generation of roles based on the provided namespace and noun.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The generated roles and permissions.", content = {
                            @Content(mediaType = "application/json", examples = {
                                    @ExampleObject(name = "Generated Roles", description = "Example generated roles when using `user.consent` as input parameter.", value = """
                                            [
                                                {
                                                    "id": "user.consent.admin.role",
                                                    "name": "user.consent - Admin",
                                                    "namespace": "User Consent API",
                                                    "description": "Default admin role for User Consent",
                                                    "stage": "alpha",
                                                    "permissions": [
                                                        {
                                                            "scope": "user.consent.create"
                                                        },
                                                        {
                                                            "scope": "user.consent.delete"
                                                        },
                                                        {
                                                            "scope": "user.consent.update"
                                                        },
                                                        {
                                                            "scope": "user.consent.get"
                                                        },
                                                        {
                                                            "scope": "user.consent.list"
                                                        },
                                                        {
                                                            "scope": "user.consent.lookup"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "id": "user.consent.readonly.role",
                                                    "name": "user.consent - Read Only",
                                                    "namespace": "User Consent API",
                                                    "description": "Default reader role for User.consent",
                                                    "stage": "alpha",
                                                    "permissions": [
                                                        {
                                                            "scope": "user.consent.get"
                                                        },
                                                        {
                                                            "scope": "user.consent.list"
                                                        }
                                                    ]
                                                }
                                            ]
                                                                                        """)
                            })
                    })
            }
)
    @PreAuthorize("hasAuthority('org.roles.create')")
    public List<Role> generateRoles(@PathVariable("organizationId") String organizationId,
                                    @RequestBody GenerateRequest request,
                                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }

        List<Role> roles = new ArrayList<>();
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);

        for (Resource r :
                request.resources) {
            roles.addAll(DefaultPermissions.apply(orgRoles, r));
        }

        this.storage.organization_roles().updateObject(orgRoles.getId(), orgRoles);

        return roles;
    }

    @GetMapping("/{roleId}/assignments")
    @Operation(
operationId = "getOrgRoleAssignments",
            summary = "Retrieve role assignments for the specified organization role.",
            description = """
This endpoint returns all role assignments associated with a specific role in the organization. It provides visibility into the users assigned to the role, helping manage permissions effectively. Use this to ensure appropriate access levels within your organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.list')")
    public DocumentResult<RoleAssignment> getRoleAssignments(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("roleId") String roleId,
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // list all role assignments
        Set<Relation> relations = this.permissions.listRelationsForObject(Target.of("orgs", organizationId));
        List<RoleAssignment> roles = relations.stream()
                .filter(x -> x.scope().relation().equalsIgnoreCase(roleId)).map(x -> {
            return new RoleAssignment()
                    .withOrgId(organizationId)
                    .withRoleId(x.scope().relation())
                    .withAssignedTo(x.subject().id());
        }).toList();

        DocumentResult<RoleAssignment> r = new DocumentResult<>();
        r.docs = roles;
        r.count = (long) roles.size();
        r.limit = 1024;
        r.skip = 0;
        return r;
    }


    @GetMapping("/{roleId}")
    @Operation(
operationId = "getOrgRoleById",
            summary = "Retrieve a specific role within an organization",
            description = """
Fetches details of a role identified by its ID within the specified organization. This endpoint ensures that only existing roles are returned, providing clarity on role permissions and attributes. It is essential for managing user roles effectively within the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.get')")
    public Role getRoleById(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("roleId") String roleId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org entity = this.storage.organizations().getObject(organizationId);
        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles.getRoles() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }
        Optional<Role> match = orgRoles.getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(roleId)).findAny();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }
        Role role = match.get();
        role.getPermissions().sort(new Comparator<RolePermission>() {
            @Override
            public int compare(RolePermission o1, RolePermission o2) {
                return o1.getScope().compareTo(o2.getScope());
            }
        });
        return role;
    }

    @PostMapping
    @Operation(
operationId = "createOrgRole",
            summary = "Create a new role within the organization",
            description = """
This endpoint allows users to create a new role in the specified organization. It is intended for administrators managing roles and permissions. Ensure that the role definition aligns with organizational policies and requirements.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.create')")
    public Role createRole(@PathVariable("organizationId") String organizationId,
                           @RequestBody Role entity,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles();
            orgRoles.withId(organizationId);
        }

        if (orgRoles.getRoles() == null) {
            orgRoles.setRoles(Lists.newArrayList());
        }

        if (StringUtils.isBlank(entity.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID is not valid"
            );
        }

        orgRoles.getRoles().add(entity);
        this.storage.organization_roles().updateObject(orgRoles.getId(), orgRoles);
        return entity;
    }

    @PutMapping("/{roleId}")
    @Operation(
operationId = "updateOrgRole",
            summary = "Update an existing role within an organization",
            description = """
This endpoint allows for the modification of a specific role in the organization. It requires the organization ID and role ID to identify the role being updated. Proper authentication is necessary to ensure that only authorized users can make changes to roles.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.update')")
    public Role updateRole(@PathVariable("organizationId") String organizationId,
                           @PathVariable("roleId") String roleId,
                           @RequestBody Role entity,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }
        if (entity.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Entity does not exist."
            );
        }
        if (!entity.getId().equalsIgnoreCase(roleId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Entity does not match role ID."
            );
        }
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles();
            orgRoles.withId(organizationId);
        }

        if (orgRoles.getRoles() == null) {
            orgRoles.setRoles(Lists.newArrayList());
        }
        Optional<Role> match = orgRoles.getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(roleId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role does not exist to update, did you mean to post instead of PUT?"
            );
        }
        orgRoles.getRoles().remove(match.get());
        orgRoles.getRoles().add(entity);
        this.storage.organization_roles().updateObject(org.getId(), orgRoles);
        return match.get();
    }

    @DeleteMapping
    @Operation(
operationId = "deleteAllOrganizationRoles",
            summary = "Delete all roles for the specified organization",
            description = """
This endpoint allows the deletion of all roles associated with a specific organization. It is intended for use by authorized personnel to manage role permissions effectively. Ensure that you have the necessary permissions before executing this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.delete')")
    public void deleteAllRoles(@PathVariable("organizationId") String organizationId,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles();
            orgRoles.withId(organizationId);
        }

        if (orgRoles.getRoles() == null) {
            orgRoles.setRoles(Lists.newArrayList());
        }

        orgRoles.getRoles().clear();
        org.setModifiedDate(DateTime.now());
        this.storage.organization_roles().updateObject(orgRoles.getId(), orgRoles);
    }

    @DeleteMapping("/{roleId}")
    @Operation(
operationId = "deleteOrgRole",
            summary = "Delete a specific role in the organization",
            description = """
This endpoint allows for the removal of a designated role within a specified organization. It is crucial for maintaining accurate role management and permissions. Ensure that the role being deleted is no longer needed, as this action is irreversible.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.roles.delete')")
    public void deleteRole(@PathVariable("organizationId") String organizationId,
                           @PathVariable("roleId") String roleId,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity does not exist."
            );
        }

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            orgRoles = new OrgRoles();
            orgRoles.withId(organizationId);
        }

        Optional<Role> match = orgRoles.getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(roleId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Role does not exist to delete."
            );
        }
        orgRoles.getRoles().remove(match.get());
        org.setModifiedDate(DateTime.now());
        this.storage.organization_roles().updateObject(orgRoles.getId(), orgRoles);
    }

    public static class GenerateRequest {
        public List<Resource> resources = Lists.newArrayList();
    }

    public static class Resource {
        public String namespace;

        public Resource() {
        }

        public Resource(String namespace) {
            this.namespace = namespace;
        }
    }
}