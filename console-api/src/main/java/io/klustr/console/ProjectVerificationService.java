package io.klustr.console;

import io.klustr.persons.PersonStorage;
import io.klustr.schemas.console.apps.AppVerification;
import io.klustr.schemas.console.projects.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.persons.Person;
import io.klustr.console.admin.VerificationProvider;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.spring.OAuthCredentialType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Project Verification APIs",
        description = "Manages the verification of projects and apps for production.")
public class ProjectVerificationService {
    private static final Logger log = LoggerFactory.getLogger(ProjectVerificationService.class);


    private final VerificationProvider verifier;

    private final PersonStorage person_storage;

    private final Storage storage;

    private final ProjectSecurityPolicy policy;

    private final HydraApi hydraApi;

    public ProjectVerificationService(Storage storage,
                                      PersonStorage person_storage,
                                      ProjectSecurityPolicy policy,
                                      HydraApi hydraApi,
                                      VerificationProvider verifier) {
        this.verifier = verifier;
        this.policy = policy;
        this.person_storage = person_storage;
        this.storage = storage;
        this.hydraApi = hydraApi;
    }


    @PostMapping("/{projectId}/verification")
    @Operation(
summary = "Initiate verification workflow for a project.",
            operationId = "requestProjectVerification",
            description = """
This endpoint begins the verification process for the specified project. It triggers essential workflows to validate the application's readiness for production. Ensure you possess the necessary permissions to initiate this request.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void request(@PathVariable("projectId") String projectId,
                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);

        if (!this.policy.hasReadAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Entity is not owned by user."
            );
        }

        Optional<Person> person = this.person_storage.persons().tryGetObject(oauth.getName());
        if (person.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person associated with project is not registered."
            );
        }

        project.getApp().withVerification(
                new AppVerification()
                        .withUpdateDate(DateTime.now())
                        .withApproval(AppVerification.Approval.PENDING)
        );

        this.storage.projects().updateObject(project.getId(), project);

        this.verifier.verification(project, person.get().getTraits());
    }

    @PostMapping("/{projectId}/trust")
    @Operation(
summary = "Initiate trusted status workflow for a project.",
            operationId = "startTrustWorkflow",
            description = """
This endpoint initiates the workflows required to start the application trust process for the specified project. It is designed for authorized users to ensure that the project complies with necessary trust criteria. Proper authentication is mandatory to access this functionality.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void startTrust(@PathVariable("projectId") String projectId,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);

        if (!this.policy.hasReadAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Entity is not owned by user."
            );
        }

        Optional<Person> person = this.person_storage.persons().tryGetObject(oauth.getName());
        if (person.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person associated with project is not registered."
            );
        }

        project.getApp().withTrust(new AppVerification()
                .withApproval(AppVerification.Approval.PENDING)
                .withStatus(AppVerification.AppStatus.NONE)
                .withUpdateDate(DateTime.now())
        );

        this.storage.projects().updateObject(project.getId(), project);

        this.verifier.trust(project, person.get().getTraits());
    }
}
