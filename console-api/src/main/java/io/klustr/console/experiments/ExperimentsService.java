package io.klustr.console.experiments;

import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.query.DbQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.security.ExperimentsSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.utils.RandomNameGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Provides the ability to define and run experiments.
 */
@RestController
@Component
@RequestMapping("/console/projects/{projectId}/experiments")
@Tag(name = "Experiment APIs", description = "APIs for tracking and analytics of experiments")
public class ExperimentsService {

    private final Storage storage;

    public ExperimentsService(Storage storage,
                              ExperimentsSecurityPolicy policy) {
        this.storage = storage;
    }

    @GetMapping
    @Operation(
operationId = "listExperiments", summary = "List all experiments for a project.",
            description = """
Retrieves a paginated list of experiments associated with the specified project ID. This endpoint is accessible to authenticated users and is intended for tracking and analytics purposes. Use the 'start' and 'limit' parameters to control pagination.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<Experiment> list(
            @PathVariable("projectId") String projectId,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "limit", defaultValue = "1024") Integer limit,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        DbQuery fq = Db.query("project_id").eq(projectId);

        return this.storage.experiments().insecureQuery(fq, new Pagination().withLimit(limit).withStart(start)).docs;
    }

    @GetMapping("/{experimentId}")
    @Operation(
operationId = "getExperiment", summary = "Retrieve a specific experiment by ID",
            description = """
This endpoint returns the details of a specified experiment within a project. It requires the project ID and experiment ID as path parameters. Ensure you have the necessary permissions to access this experiment.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Experiment get(@PathVariable("projectId") String projectId,
                          @PathVariable("experimentId") String experiment_id,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return this.storage.experiments().getObject(experiment_id);
    }

    @PostMapping
    @Operation(
operationId = "createExperiment", summary = "Create a new experiment for tracking.",
            description = """
This endpoint allows users to create an experiment within a specified project. The created experiment can be utilized for tracking and analytics purposes. Ensure that the provided experiment data adheres to the required format for successful creation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Experiment create(@PathVariable("projectId") String projectId,
                             @RequestBody Experiment experiment,
                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO double check this is random enough
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        experiment.setId(RandomNameGenerator.randomHexString(12));
        experiment.setStatus(experiment.getStatus() != null ? experiment.getStatus() : Experiment.Status.DRAFT);
        experiment.setOwner(email);

        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The requested project ID doesnt exist."
            );
        }

        experiment.setProjectId(projectId);
        experiment.setOrgId(project.getOrgId());

        this.storage.experiments().insertObject(experiment.getId(), experiment);

        return experiment;
    }

    @PutMapping("/{experimentId}")
    @Operation(
operationId = "updateExperiment", summary = "Update an existing experiment in the project.",
            description = """
This endpoint allows you to update the details of a specified experiment within a given project. Ensure that the experiment ID and project ID are correctly provided. This operation requires appropriate authentication and authorization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Experiment update(@PathVariable("projectId") String projectId,
                             @PathVariable("experimentId") String experiment_id,
                             @RequestBody Experiment experiment,
                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The requested project ID doesnt exist."
            );
        }
        experiment.setProjectId(projectId);

        experiment.setStatus(experiment.getStatus() != null ? experiment.getStatus() : Experiment.Status.DRAFT);
        this.storage.experiments().updateObject(experiment.getId(), experiment);
        return experiment;
    }

    @DeleteMapping("/{experimentId}")
    @Operation(
operationId = "deleteExperiment", summary = "Delete a specified experiment from the project.",
            description = """
This endpoint allows users to delete an experiment identified by its ID within a specific project. It ensures that only authorized users can perform this action, maintaining the integrity of the experiment tracking system. Use this operation to manage experiments effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void delete(@PathVariable("projectId") String projectId,
                       @PathVariable("experimentId") String experiment_id,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Experiment object = this.storage.experiments().getObject(experiment_id);
        if (object != null) {
            object.setStatus(Experiment.Status.DELETED);
            this.storage.experiments().updateObject(object.getId(), object);
        }
    }
}
