package io.klustr.console.hosting;

import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Handles the listing of supported machine node types.
 */
@RestController
@Component
@RequestMapping("/console/compute/.metadata")
@Tag(name = "Compute APIs",
        description = "Compute infrastructure commands and related topics.")
public class ProjectComputeMachineTypesService {

    private final ComputeInstanceTypeProvider metadataProvider;

    public ProjectComputeMachineTypesService(
            ComputeInstanceTypeProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
    }

    @GetMapping("/instanceTypes")
    @Operation(
            operationId = "getMachineTypes", summary = "Returns the available machine types.",
            description = """
This will return the available machine types, without any filters applied. This does not indicate
the user has the sufficient resourcing or pooling required to use any of these resources.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId",description = "The project ID")
            }
    )
    public List<ComputeInstanceType> getMachineTypes() {
        return this.metadataProvider.getInstanceTypes();
    }

    @GetMapping("/instanceTypes/{name}")
    @Operation(
            operationId = "getMachineTypeByName", summary = "Returns the available machine type by name.",
            description = """
This will return a specific machine type.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "name",description = "The name of the machine type.")
            }
    )
    public ComputeInstanceType getMachineTypeByName(@PathVariable("name") String name) {
        Optional<ComputeInstanceType> machineTypeByName = this.metadataProvider.getInstanceTypeByName(name);
        if (machineTypeByName.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The machine name could not be found"
            );
        }
        return machineTypeByName.get();
    }
}
