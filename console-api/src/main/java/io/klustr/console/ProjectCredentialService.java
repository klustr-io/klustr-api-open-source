package io.klustr.console;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.permissions.*;
import io.klustr.schemas.console.oidc.OIDCCredential;
import io.klustr.schemas.console.projects.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.integrations.ory.HydraCredentialsRequest;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.RandomNameGenerator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * Provides credential maangement for a project.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Credentials APIs",
        description = "For management of project credentials and app credentials.")
public class ProjectCredentialService {

    private final Storage storage;
    private final HydraApi hydra;

    private final PermissionProvider permissions;

    public ProjectCredentialService(Storage storage, PermissionProvider permissions, HydraApi hydra) {
        this.storage = storage;
        this.permissions = permissions;
        this.hydra = hydra;
    }

    @GetMapping("/{projectId}/credentials")
    @Operation(
operationId = "listProjectCredentials",
            summary = "List all project OIDC credentials for authorized users",
            description = """
This endpoint retrieves all OIDC credentials associated with the specified project ID. It is designed for users with the necessary permissions to manage project credentials. Ensure you are authenticated to access this sensitive information.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The oidc credentials registered for this project", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OIDCCredential.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<OIDCCredential> getCredentials(@PathVariable("projectId") String projectId,
                                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        List<OIDCCredential> clients = project.getCredentials();
        clients = clients.stream().filter(x -> x.getStatus() != OIDCCredential.OidcStatus.DELETED).toList();
        return clients;
    }

    @GetMapping("/{projectId}/credentials/{clientId}/secret")
    @Operation(
operationId = "getProjectCredentialsSecret",
            summary = "Retrieve project service credentials secret key.",
            description = """
This endpoint retrieves the secret key for the specified service credentials linked to a project. It is designed for authorized users to securely manage project credentials. Ensure you possess the required permissions to access this sensitive information.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCCredential.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCCredential getCredentialsSecret(@PathVariable("projectId") String projectId,
                                               @PathVariable("clientId") String clientId,
                                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        List<OIDCCredential> clients = project.getCredentials();
        return clients.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst().get();
    }

    @PostMapping("/{projectId}/credentials")
    @Operation(
operationId = "createServiceCredential",
            summary = "Create a service credential for project authentication",
            description = """
This endpoint enables the creation of a service credential for secure service-to-service authentication within a specified project. Proper permissions must be granted to the client for successful registration. This ensures secure communication between services and enhances project integrity.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCCredential.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCCredential registerNewCredential(@PathVariable("projectId") String projectId,
                                                @RequestBody HydraCredentialsRequest registration,
                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (StringUtils.isBlank(registration.getName())) {
            registration.withName("service-" + RandomNameGenerator.randomHexString(6));
        }
        if (StringUtils.isBlank(registration.getClientId())) {
            registration.withClientId(UUID.randomUUID().toString());
        }
        if (StringUtils.isBlank(registration.getClientSecret())) {
            registration.withClientSecret(RandomNameGenerator.randomHexString(16));
        }

        Project project = this.storage.projects().getObject(projectId);

        try {

            HydraApi.OryRegistration client = this.hydra.createOrUpdateCredential(project, registration);

            OIDCCredential result = new OIDCCredential().withClientId(client.client_id)
                    .withSecretKey(client.client_secret)
                    .withStatus(OIDCCredential.OidcStatus.ENABLED)
                    .withTokenEndpointAuthMethod(registration.getTokenEndpointAuthMethod())
                    .withAccessTokenStrategy(registration.getAccessTokenStrategy())
                    .withName(registration.getName())
                    .withCreationDate(DateTime.now());
            project.getCredentials().add(
                    result
            );
            this.storage.projects().updateObject(projectId, project);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/{projectId}/credentials/{clientId}")
    @Operation(
operationId = "updateProjectCredentials",
            summary = "Update service credentials for a specific project.",
            description = """
This endpoint allows you to update the service credentials associated with a specific project. It ensures that the credentials remain secure and up-to-date. Use this operation to effectively manage the credentials within your project's scope.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The updated result.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCCredential.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCCredential update(@PathVariable("projectId") String projectId,
                                 @PathVariable("clientId") String clientId,
                                 @RequestBody HydraCredentialsRequest registration) {
        Project project = this.storage.projects().getObject(projectId);
        try {
            Optional<OIDCCredential> oidc = project.getCredentials().stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
            if (oidc.isEmpty()) {
                throw new RuntimeException("OIDC client not found or does not match project");
            }
            OIDCCredential client = oidc.get();

            OIDCCredential update = client
                    .withAccessTokenStrategy(registration.getAccessTokenStrategy())
                    .withTokenEndpointAuthMethod(registration.getTokenEndpointAuthMethod())
                    .withName(registration.getName());

            // hydra update to sync changes
            this.hydra.update(project, update);

            // update project with latest
            this.storage.projects().updateObject(projectId, project);

            return client;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping("/{projectId}/credentials/{clientId}/permissions")
    @Operation(
operationId = "getProjectCredentialPermissions",
            summary = "Retrieve permissions for a project credential",
            description = """
This endpoint provides a detailed list of permissions associated with a specific project credential. It helps users understand the actions that can be performed with the credential, ensuring proper access management and authorization for project tasks.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified credential.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public LinkedHashSet<String> getCredentialPermissions(@PathVariable("projectId") String projectId,
                                                          @PathVariable("clientId") String clientId,
                                                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        List<OIDCCredential> credentials = project.getCredentials() != null ? project.getCredentials() : Lists.newArrayList();

        // secret key is obfuscated on the way out
        Optional<OIDCCredential> match = credentials.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Credential Not Found"
            );
        }
        Set<Relation> relations = this.permissions.listRelationsForObject(new Target("client", clientId));
        return Sets.newLinkedHashSet(relations.stream().map(x -> {
            return x.scope().relation();
        }).toList());
    }

    @PostMapping("/{projectId}/credentials/{clientId}/permissions")
    @Operation(
operationId = "updateProjectCredentialPermissions",
            summary = "Update project service credential permissions.",
            description = """
This endpoint updates the permissions for specific service credentials associated with a project. It ensures that the correct access levels are granted based on the provided parameters. Use this to effectively manage and control credential permissions within your project.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified credentials.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public LinkedHashSet<String> updateCredentialPermissions(@PathVariable("projectId") String projectId,
                                                             @PathVariable("clientId") String clientId,
                                                             @RequestBody ProjectClientService.Permissions payload,
                                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        List<OIDCCredential> credentials = project.getCredentials() != null ? project.getCredentials() : Lists.newArrayList();

        // secret key is obfuscated on the way out
        Optional<OIDCCredential> match = credentials.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Credential Not Found"
            );
        }

        // TODO should we really be deleting ALL?!
        this.permissions.revokeAll(SubjectKey.client(clientId), Target.of("permissions", clientId));

        if (payload.permissions != null) {
            payload.permissions.forEach(permission -> {
                this.permissions.grant(SubjectKey.client(clientId), Scope.attribute("permissions", permission), Target.of("clients", clientId));
            });
        }

        return payload.permissions;
    }

    @PostMapping("/{projectId}/credentials/{clientId}/roles")
    @Operation(
operationId = "updateProjectCredentialRoles",
            summary = "Update roles for project service credentials.",
            description = """
This endpoint updates the roles assigned to a specific service credential within a project. It ensures that the roles are accurately reflected, enhancing access control and permissions management for the credential in question.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The roles granted to the specified credentials.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public LinkedHashSet<String> updateRoles(@PathVariable("projectId") String projectId,
                                             @PathVariable("clientId") String clientId,
                                             @RequestBody ProjectClientService.Roles payload,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        List<OIDCCredential> credentials = project.getCredentials() != null ? project.getCredentials() : Lists.newArrayList();

        // secret key is obfuscated on the way out
        Optional<OIDCCredential> match = credentials.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Credential Not Found"
            );
        }

        this.permissions.revokeAll(SubjectKey.client(clientId), Target.of("roles", clientId));

        if (payload.roles != null) {
            payload.roles.forEach(role -> {
                this.permissions.grant(SubjectKey.client(clientId), Scope.attribute("roles", role), Target.of("clients", clientId));
            });
        }

        return payload.roles;
    }

    @DeleteMapping("/{projectId}/credentials/{clientId}")
    @Operation(
operationId = "deleteProjectCredential",
            summary = "Delete a specific project credential for management.",
            description = """
This endpoint allows users to delete a specific service credential linked to a project. Providing the correct projectId and clientId is essential for successful deletion. This operation is vital for maintaining security and managing access to project resources.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteCredential(@PathVariable("projectId") String projectId,
                                 @PathVariable("clientId") String clientId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);

        Optional<OIDCCredential> credential_match = project.getCredentials().stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (credential_match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        // clear from permissions
        this.permissions.revokeAll(SubjectKey.client(clientId));

        // remove from OIDC hydra
        this.hydra.delete(credential_match.get().getClientId());

        // update the status to deleted
        credential_match.get().setStatus(OIDCCredential.OidcStatus.DELETED);
        this.storage.projects().updateObject(projectId, project);
    }
}
