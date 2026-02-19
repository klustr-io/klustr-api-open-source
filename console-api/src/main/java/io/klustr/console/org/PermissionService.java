package io.klustr.console.org;

import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.SubjectKey;
import io.klustr.permissions.Target;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Takes a particular entity and determines what permissions they have
 * by breaking down their groups and roles and returning the roles and
 * groups they have along with what permissions they have.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/users/{userId}")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Permission Management", description = "Organization management functions.")
public class PermissionService {
    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    private final Storage storage;
    private final PermissionProvider permissions;

    public PermissionService(Storage storage,
                             PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    /**
     * Returns the ACLs that a user has been assigned.
     *
     * @param userId The user to get the permissions against.
     * @return The list of acls for the person.
     */
    @GetMapping("/permissions")
    @Operation(
operationId = "listUserPermissionsInOrganization",
            summary = "List permissions for a user in an organization",
            description = """
This endpoint retrieves all permissions associated with a specific user within a given organization. It provides insights into the user's roles and groups, helping to understand their access rights. Ideal for managing user permissions effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.permissions.list')")
    public Set<Relation> listPermissionsInOrganization(@PathVariable("userId") String userId,
                                                       @PathVariable("organizationId") String organizationId,
                                                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return permissions.listRelations(SubjectKey.userId(userId), "orgs", Target.of(organizationId));
    }

    /**
     * Returns the ACLs that a user has been assigned.
     *
     * @param userId The user to get the permissions against.
     * @return The list of acls for the person.
     */
    @GetMapping("/permissions/{namespace}")
    @Operation(
operationId = "getUserNamespacePermissions",
            summary = "Retrieve user permissions for a specific namespace.",
            description = """
This endpoint returns the permissions associated with a user for a specified namespace within an organization. It helps in understanding the user's access rights and roles in the context of the organization. Use this information to manage user permissions effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.permissions.list')")
    public Set<Relation> listPermissionsInOrganizationNamespace(@PathVariable("userId") String userId,
                                                            @PathVariable("organizationId") String orgId,
                                                            @PathVariable("namespace") String namespace,
                                                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return permissions.listRelations(SubjectKey.userId(userId), namespace, Target.of("orgs", orgId));
    }

    /**
     * Returns the ACLs that a user has been assigned.
     *
     * @param userId The user to get the permissions against.
     * @return The list of acls for the person.
     */
    @GetMapping("/permissions/{namespace}/{object_id}")
    @Operation(
operationId = "listUserObjectPermissions",
            summary = "Retrieve permissions for a specific user and object",
            description = """
This endpoint returns the permissions associated with a specified user for a given namespace and object. It provides insights into the user's roles and groups, helping to understand their access rights. This information is crucial for managing user permissions effectively within the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.permissions.list')")
    public Set<Relation> listPermissionsForObject(@PathVariable("userId") String userId,
                                              @PathVariable("namespace") String namespace,
                                              @PathVariable("organizationId") String org_id,
                                              @PathVariable("object_id") String object_id,
                                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return permissions.listRelations(SubjectKey.userId(userId), namespace, Target.of(namespace, object_id));
    }
}