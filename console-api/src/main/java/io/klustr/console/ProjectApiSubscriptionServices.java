package io.klustr.console;

import io.klustr.integrations.kong.interfaces.GatewayConsumerProvider;
import io.klustr.integrations.kong.interfaces.GatewayServiceProvider;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectServiceReference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maintains registrations for projects to consume and use registered API endpoints
 * for the purposes of rate limiting, billing, and controlling access.
 */
@RestController
@Component
@RequestMapping("/console/projects/{projectId}/gateway")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectApiSubscriptionServices {

    private final Storage storage;
    private final GatewayConsumerProvider gateway;
    private final HydraApi hydra;

    public ProjectApiSubscriptionServices(Storage storage, GatewayConsumerProvider gateway, HydraApi hydra) {
        this.gateway = gateway;
        this.storage = storage;
        this.hydra = hydra;
    }

    /**
     * The services available for the project.
     *
     * @param projectId The ID of the project to get keys against.
     * @return The list of api keys.
     */
    @GetMapping
    @Operation(
summary = "List APIs enabled for the specified project",
            operationId = "listProjectApiServices",
            description = """
This operation retrieves all API services that are enabled for the given project. It is crucial for managing API access, ensuring proper usage, and monitoring subscriptions effectively within the project's scope.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<ProjectServiceReference> getServices(@PathVariable("projectId") String projectId) {
        Project p = this.storage.projects().getObject(projectId);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return p.getServices();
    }

    /**
     * Registers a new API key
     *
     * @param projectId The project ID to add keys to
     * @param serviceId The api request with optional parameters to create
     * @return The API key that was created.
     */
    @PostMapping("/{serviceId}")
    @Operation(
summary = "Enable API subscription for a project service.",
            operationId = "enableProjectApiSubscription",
            description = """
This endpoint enables a specific API subscription for a project. It updates the subscription key state, allowing access to associated operations. This is essential for managing project resources and ensuring compliance with rate limiting and billing requirements.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public boolean enableService(@PathVariable("projectId") String projectId, @PathVariable("serviceId") String serviceId) {
        Project p = this.storage.projects().getObject(projectId);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<ProjectServiceReference> match = p.getServices().stream().filter(x -> x.getId().equalsIgnoreCase(serviceId)).findFirst();
        if (match.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already enabled");
        }

        // add the service
        p.getServices().add(
                new ProjectServiceReference()
                        .withId(serviceId)
        );

        // ensure we add ACL to Kong consumer
        gateway.enableServiceForConsumer(p.getId(), serviceId);

        // add the audience to any existing clients
        Set<String> audiences = p.getServices().stream().map(ProjectServiceReference::getId).collect(Collectors.toSet());
            p.getCredentials().forEach(x -> {
                x.setAudience(audiences);
                hydra.update(p, x);
            });

        this.storage.projects().updateObject(p.getId(), p);

        return true;
    }

    @GetMapping("/{serviceId}")
    @Operation(
summary = "Retrieve activated service for the specified project",
            operationId = "getProjectActivatedService",
            description = """
This endpoint fetches the service ID linked to the specified project if it is currently activated. It plays a vital role in managing API subscriptions and ensuring appropriate access control for project resources.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ProjectServiceReference getService(@PathVariable("projectId") String projectId,
                                              @PathVariable("serviceId") String serviceId) {
        Project p = this.storage.projects().getObject(projectId);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<ProjectServiceReference> match = p.getServices().stream().filter(x -> x.getId().equalsIgnoreCase(serviceId)).findFirst();
        if (match.isEmpty()) {
            return null;
        }
        return match.get();
    }

    /**
     * Deletes the specified service
     *
     * @param projectId The project ID to delete the key from
     * @param serviceId The id of the service to delete.
     * @return API key that was deleted.
     */
    @DeleteMapping("/{serviceId}")
    @Operation(
summary = "Disable an API subscription for a specific project",
            operationId = "disableApiSubscriptionForProject",
            description = """
This endpoint allows you to disable a specific API subscription linked to a project. By doing so, the service will no longer be accessible, aiding in the management of resource usage and access control. This is crucial for maintaining the integrity and performance of your project's API consumption.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void disableService(@PathVariable("projectId") String projectId, @PathVariable("serviceId") String serviceId) {
        Project p = this.storage.projects().getObject(projectId);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<ProjectServiceReference> match = p.getServices().stream().filter(x -> x.getId().equalsIgnoreCase(serviceId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        this.gateway.disableServiceForConsumer(p.getId(), serviceId);

        p.getServices().remove(match.get());

        // update the audience to any existing clients
        Set<String> audiences = p.getServices().stream().map(ProjectServiceReference::getId).collect(Collectors.toSet());
        p.getCredentials().forEach(x -> {
            x.setAudience(audiences);
            hydra.update(p, x);
        });

        // save back to projects
        this.storage.projects().updateObject(projectId, p);
    }

    public static class ServiceRequest {
        public boolean enabled;
    }
}
