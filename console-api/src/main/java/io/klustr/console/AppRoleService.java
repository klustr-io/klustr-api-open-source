package io.klustr.console;

import io.klustr.permissions.*;
import io.klustr.schemas.console.projects.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Component
@RequestMapping("/console/projects/{projectId}/app/{appId}")
@Tag(name = "Project Role APIs", description = "Creating and managing the roles your users have in your app.")
public class AppRoleService {

    private static final Logger log = LoggerFactory.getLogger(AppRoleService.class);

    private final ProjectSecurityPolicy policy;

    private final PermissionProvider permissions;

    private final Storage storage;

    public AppRoleService(Storage storage,
                          PermissionProvider permissions,
                          ProjectSecurityPolicy policy) {
        this.storage = storage;
        this.permissions = permissions;
        this.policy = policy;
    }



    @GetMapping("/users/{subjectId}/roles")
    @Operation(
description = """
This endpoint fetches the roles associated with a specific user within a given application. It provides visibility into user permissions and roles, enabling effective management of access rights. This is essential for applications that implement role-based access control.
""",
            operationId = "getProjectAppUserRoles",
            summary = "Retrieve roles assigned to a user for an app",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Set<Relation> getRoles(@PathVariable("projectId") String projectId,
                                  @PathVariable("appId") String appId,
                                  @PathVariable("subjectId") String subjectId,
                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        return this.permissions.listRelations(SubjectKey.userId(subjectId), "roles", Target.of("app", appId));
    }


    @PostMapping("/users/{subjectId}/roles/{roleId}")
    @Operation(
description = """
This endpoint assigns a specific role to a user in a project. It is essential for managing user permissions and ensuring proper access levels. Verify that both the user and role exist before making this request to maintain security and functionality.
""",
            operationId = "assignRoleToUserInProject",
            summary = "Assign a user role within a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void addRole(@PathVariable("projectId") String projectId,
                                 @PathVariable("appId") String appId,
                                 @PathVariable("subjectId") String subjectId,
                                 @PathVariable("roleId") String roleId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        this.permissions.grant(SubjectKey.userId(subjectId), Scope.attribute("roles", roleId), Target.of("apps", appId));
    }

    @GetMapping("/users/{subjectId}/groups")
    @Operation(
description = """
This endpoint retrieves the groups associated with a specific user in a given application within a project. It ensures that the returned data is relevant to the user's context, aiding in effective role management. Utilize this to gain insights into user group memberships and their corresponding permissions.
""",
            operationId = "getAppUserGroups",
            summary = "Retrieve user groups for a specific application",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Set<Relation> getGroups(@PathVariable("projectId") String projectId,
                              @PathVariable("appId") String appId,
                              @PathVariable("subjectId") String subjectId,
                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        return this.permissions.listRelations(SubjectKey.userId(subjectId), "groups", Target.of("apps", appId));
    }

    @PostMapping("/users/{subjectId}/groups/{groupId}")
    @Operation(
description = """
This endpoint allows the addition of a specified group to a user within a project's context. It facilitates better organization of users into groups for effective role management. This operation is crucial for maintaining user permissions and access levels within the application.
""",
            operationId = "addUserGroupToProjectRole",
            summary = "Add a group to a user's project role",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void addGroup(@PathVariable("projectId") String projectId,
                        @PathVariable("appId") String appId,
                        @PathVariable("subjectId") String subjectId,
                        @PathVariable("groupId") String groupId,
                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        this.permissions.grant(SubjectKey.userId(subjectId), Scope.attribute("groups", groupId), Target.of("apps", appId));
    }


    @DeleteMapping("/users/{subjectId}/roles/{roleId}")
    @Operation(
description = """
This endpoint allows the removal of a specific role assigned to a user within a project application. It ensures that the user's permissions are updated securely, maintaining the integrity of access control. This operation is vital for effective user role management and enhancing application security.
""",
            operationId = "removeUserRoleFromApp",
            summary = "Remove a role from a specific user in an app.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void removeRole(@PathVariable("projectId") String projectId,
                                 @PathVariable("appId") String appId,
                                 @PathVariable("subjectId") String subjectId,
                                 @PathVariable("roleId") String roleId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        this.permissions.revoke(SubjectKey.userId(subjectId), Scope.attribute("roles", roleId), Target.of("apps", appId));
    }

    @DeleteMapping("/users/{subjectId}/groups/{groupId}")
    @Operation(
description = """
This endpoint facilitates the removal of a group associated with a user in a specified project and application context. It is essential for maintaining accurate user roles and permissions. Proper management of group memberships ensures that users have the appropriate access within the application.
""",
            operationId = "removeUserGroup",
            summary = "Remove a user from a specific group.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void removeGroup(@PathVariable("projectId") String projectId,
                           @PathVariable("appId") String appId,
                           @PathVariable("subjectId") String subjectId,
                           @PathVariable("groupId") String groupId,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.policy.checkPermissionAndGetProject(projectId, appId, oauth);
        this.permissions.revoke(SubjectKey.userId(subjectId), Scope.attribute("groups", groupId), Target.of("apps", appId));
    }

}
