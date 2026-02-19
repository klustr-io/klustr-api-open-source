package io.klustr.console;

import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.permissions.*;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.ory.HydraClientRequest;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.RandomNameGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * Provides the ability to create and manage clients for projects.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Credentials APIs",
        description = "For management of project credentials and app credentials.")
public class ProjectClientService {

    private final Storage storage;
    private final HydraApi hydra;

    private final PermissionProvider permissions;

    public ProjectClientService(Storage storage, PermissionProvider permissions, HydraApi hydra) {
        this.storage = storage;
        this.permissions = permissions;
        this.hydra = hydra;
    }

    public List<String> getProjectReadiness(@PathVariable("projectId") String projectId) {
        Project p = this.storage.projects().getObject(projectId);
        List<String> errors = new ArrayList<>();
        if (p.getApp() == null) {
            // missing app
            errors.add("Missing required app setup.");
            return errors;
        }
        if (p.getApp().getBrand() == null) {
            // missing brand
            errors.add("Missing required brand setup.");
            return errors;
        }
        if (p.getApp().getConsent() == null) {
            // missing consents
            errors.add("Missing required consent setup.");
            return errors;
        }
        return errors;
    }

    @GetMapping("/{projectId}/clients")
    @Operation(
operationId = "listProjectClients",
            summary = "List OIDC clients for a specific project",
            description = """
Retrieve all OIDC clients associated with the specified project ID. If the project is not ready due to dependencies, an error will be returned. This endpoint allows users to effectively manage client configurations within their projects.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The oidc clients registered for this project", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OIDCClient.class)))
                    }),

            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<OIDCClient> getClients(@PathVariable("projectId") String projectId,
                                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project p = this.storage.projects().getObject(projectId);

        if (p == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project Not Found"
            );
        }

        List<OIDCClient> clients = p.getClients();
        return clients;
    }

    @GetMapping("/{projectId}/clients/{clientId}/permissions")
    @Operation(
operationId = "getClientPermissions",
            summary = "Retrieve permissions for a specific project client.",
            description = """
This endpoint provides a detailed list of permissions assigned to the specified client within a project. It helps in understanding the actions the client can perform, ensuring effective access control and management. Utilize this information to enhance security and client capability awareness.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public LinkedHashSet<String> getPermissions(@PathVariable("projectId") String projectId,
                                                @PathVariable("clientId") String clientId,
                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project p = this.storage.projects().getObject(projectId);
        List<OIDCClient> clients = p.getClients();
        // secret key is obfuscated on the way out
        Optional<OIDCClient> match = clients.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client Not Found"
            );
        }
        Set<Relation> relations = this.permissions.listRelations(SubjectKey.client(clientId), "permissions", Target.of("projects", projectId));
        return Sets.newLinkedHashSet(relations.stream().map(x -> {
            return x.scope().relation();
        }).toList());
    }

    @PostMapping("/{projectId}/clients/{clientId}/permissions")
    @Operation(
operationId = "updateProjectClientPermissions",
            summary = "Update permissions for a specific project client.",
            description = """
This endpoint allows you to create or modify the permissions for a client linked to a specific project. It ensures that access levels are appropriately set, enhancing project management and collaboration. Use this to effectively manage client capabilities and maintain project security.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public LinkedHashSet<String> updatePermissions(@PathVariable("projectId") String projectId,
                                                   @PathVariable("clientId") String clientId,
                                                   @RequestBody Permissions payload,
                                                   @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project p = this.storage.projects().getObject(projectId);
        List<OIDCClient> clients = p.getClients();
        // secret key is obfuscated on the way out
        Optional<OIDCClient> match = clients.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client Not Found"
            );
        }

        if (payload.permissions != null) {
            payload.permissions.forEach(permission -> {
                this.permissions.grant(SubjectKey.client(clientId), Scope.attribute("permissions", permission), Target.of("clients", clientId));
            });
        }

        return payload.permissions;
    }

    @GetMapping("/{projectId}/clients/{clientId}/secret")
    @Operation(
operationId = "getClientSecretKey",
            summary = "Retrieve secret key for a specific project client.",
            description = """
This endpoint retrieves the secret key associated with a specific client within a project. It is crucial for client authentication and secure interactions. Ensure that appropriate security measures are in place when accessing sensitive credentials.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCClient.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCClient getSecret(@PathVariable("projectId") String projectId,
                                @PathVariable("clientId") String clientId,
                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project p = this.storage.projects().getObject(projectId);
        List<OIDCClient> clients = p.getClients();
        return clients.stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst().get();
    }

    @PostMapping("/{projectId}/clients")
    @Operation(
operationId = "registerNewOauth2Client",
            summary = "Create a new OAuth 2.0 client for a project",
            description = """
This endpoint facilitates the registration of a new OAuth 2.0 client for project use. It enables secure access to project functionalities while managing user consent and interactions. Ideal for integrating clients that require user data and permissions.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The permissions granted to the specified client.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCClient.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCClient registerNewClient(@PathVariable("projectId") String projectId,
                                        @RequestBody HydraClientRequest registration,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project p = this.storage.projects().getObject(projectId);
        try {
            HydraApi.OryRegistration client = this.hydra.registerClient(p, registration);

            OIDCClient result = new OIDCClient().withClientId(client.client_id)
                    .withSecretKey(StringUtils.isNoneBlank(client.client_secret) ? client.client_secret : RandomNameGenerator.randomHexString(18))
                    .withStatus(OidcStatus.ENABLED)
                    .withTokenEndpointAuthMethod(registration.getTokenEndpointAuthMethod())
                    .withAccessTokenStrategy(registration.getAccessTokenStrategy())
                    .withAuthorizedOrigins(registration.getAuthorizedOrigins())
                    .withAuthorizedRedirectUris(registration.getAuthorizedRedirectUrls())
                    .withName(registration.getName())
                    .withCreationDate(DateTime.now());
            p.getClients().add(
                    result
            );
            this.storage.projects().updateObject(projectId, p);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/{projectId}/clients/{clientId}")
    @Operation(
operationId = "updateExistingOauth2Client",
            summary = "Update an existing OAuth 2.0 client configuration.",
            description = """
This endpoint updates the specified OAuth 2.0 client configuration. You can modify redirect URLs and other parameters associated with the client. This is crucial for ensuring the correct setup of clients used in project management.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The updated result.", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OIDCClient.class)
                            )}
                    )
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCClient updateExistingClient(@PathVariable("projectId") String projectId,
                                           @PathVariable("clientId") String clientId,
                                           @RequestBody HydraClientRequest registration) {
        Project p = this.storage.projects().getObject(projectId);

        try {
            Optional<OIDCClient> oidc = p.getClients().stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
            if (oidc.isEmpty()) {
                throw new RuntimeException("OIDC client not found or does not match project");
            }
            OIDCClient client = oidc.get();

            if (StringUtils.isBlank(client.getSecretKey())) {
                client.setSecretKey(RandomNameGenerator.randomHexString(24));
            }

            OIDCClient update = client.withAuthorizedOrigins(registration.getAuthorizedOrigins())
                    .withAuthorizedRedirectUris(registration.getAuthorizedRedirectUrls())
                    .withAccessTokenStrategy(registration.getAccessTokenStrategy())
                    .withTokenEndpointAuthMethod(registration.getTokenEndpointAuthMethod())
                    .withSecretKey(client.getSecretKey())
                    .withSubjectType(registration.getSubjectType() != null ? registration.getSubjectType() : SubjectType.PUBLIC)
                    .withName(registration.getName());

            // hydra update to sync changes
            this.hydra.update(p, update);

            // update project with latest
            this.storage.projects().updateObject(projectId, p);

            return client;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @DeleteMapping("/{projectId}/clients/{clientId}")
    @Operation(
operationId = "deleteProjectClient",
            summary = "Delete a specific OAuth 2.0 client.",
            description = """
This endpoint allows for the deletion of a specified OAuth 2.0 client. It will also disable the client and any associated logins. Ensure you have the necessary permissions to perform this action to maintain security and integrity.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The delete was applied.")
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public OIDCClient deleteClient(@PathVariable("projectId") String projectId,
                                   @PathVariable("clientId") String clientId,
                                   @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) throws Exception {
        Project p = this.storage.projects().getObject(projectId);
        Optional<OIDCClient> match = p.getClients().stream().filter(x -> x.getClientId().equalsIgnoreCase(clientId)).findFirst();
        if (match.isEmpty()) {
            throw new RuntimeException("Client ID not found");
        }

        // remove from OIDC hydra
        OIDCClient client = match.get();
        this.hydra.delete(client.getClientId());

        // update the status to deleted
        client.setStatus(OidcStatus.DELETED);
        this.storage.projects().updateObject(projectId, p);

        return client;
    }

    public static class Permissions {
        public LinkedHashSet<String> permissions = Sets.newLinkedHashSet();
    }

    public static class Roles {
        public LinkedHashSet<String> roles = Sets.newLinkedHashSet();
    }
}
