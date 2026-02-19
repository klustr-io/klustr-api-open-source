package io.klustr.console;

import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.projects.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Component
@RequestMapping("/console/projects/{projectId}/apps/{appId}")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class AppService {

    private static final Logger log = LoggerFactory.getLogger(AppService.class);

    private final ProjectSecurityPolicy policy;

    private final PermissionProvider permissions;

    private final Storage storage;

    public AppService(Storage storage,
                             PermissionProvider permissions,
                             ProjectSecurityPolicy policy) {
        this.storage = storage;
        this.permissions = permissions;
        this.policy = policy;
    }

    @GetMapping
    @Operation(
description = """
This endpoint allows users with the necessary permissions to access detailed information about an application associated with a specified project. Ensure that both projectId and appId are valid to obtain accurate application data. This is crucial for managing application configurations effectively.
""",
            operationId = "getAppDetailsForProject",
            summary = "Retrieve application details for a specific project.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public App getApp(@PathVariable("projectId") String projectId,
                      @PathVariable("appId") String appId,
                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, appId, oauth);
        return project.getApp();
    }

    private Project checkPermissionAndGetProject(String projectId, String appId, OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found."
            );
        }

        if (!this.policy.hasUpdateAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Does not have update access for application."
            );
        }

        if (project.getApp() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project does not contain the app."
            );
        }

        if (project.getApp().getId().equalsIgnoreCase(appId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project does not contain this application."
            );
        }
        return project;
    }
}
