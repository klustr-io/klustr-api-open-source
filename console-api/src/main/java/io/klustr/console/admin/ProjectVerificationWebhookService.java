package io.klustr.console.admin;

import io.klustr.schemas.console.apps.AppVerification;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.projects.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Gets the approval or rejection information for projects to be
 * made verified.
 */
@RestController
@Component
@RequestMapping("/console/admin/projects")
@Tag(name = "Project Verification APIs", description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectVerificationWebhookService {

    private final Storage storage;

    private final ProjectSecurityPolicy securityPolicy;

    private final RethinkDbPersonRepository persons;

    private final HydraApi api;

    public ProjectVerificationWebhookService(Storage storage,
                                             RethinkDbPersonRepository persons,
                                             ProjectSecurityPolicy securityPolicy,
                                             HydraApi api) {
        this.storage = storage;
        this.securityPolicy = securityPolicy;
        this.persons = persons;
        this.api = api;
    }

    @DeleteMapping("/{projectId}/verification")
    @Operation(
operationId = "adminResetProjectVerificationStatus",
            summary = "Remove verification status from a project.",
            description = """
This endpoint allows administrators to reset the verification status of a specified project. It is crucial for managing project approvals and ensuring accurate project representations. Use this operation to revoke verification when necessary, maintaining the integrity of project management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void reset(@PathVariable("projectId") String projectId) {
        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Project no longer exists."
            );
        }
        project.getApp().setVerification(new AppVerification()
                .withApproval(AppVerification.Approval.NONE)
                .withStatus(AppVerification.AppStatus.NONE)
                .withUpdateDate(DateTime.now())
        );

        if (project.getClients() != null && !project.getClients().isEmpty()) {
            for (OIDCClient c : project.getClients()) {
                this.api.update(project, c);
            }
        }

        this.storage.projects().updateObject(project.getId(), project);
    }

    @PostMapping("/{projectId}/camunda/trusted")
    @Operation(
operationId = "adminTrustWebhookUpdate",
            summary = "Admin updates project trust status via webhook.",
            description = """
This endpoint allows administrators to update the trust status of a project through a webhook. It is essential for maintaining project integrity and ensuring that trust levels are accurately reflected. This functionality is crucial for managing project verification workflows effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void trustWebhook(@RequestBody String json) {
        CamundaBpmVerificationProvider.CamundaVerificationResponse payload = U.fromJson(json, CamundaBpmVerificationProvider.CamundaVerificationResponse.class);

        Project project = this.storage.projects().getObject(payload.project.getId());
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Project no longer exists."
            );
        }

        if (payload.status.equalsIgnoreCase("Approved")) {
            project.getApp().setTrust(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.VERIFIED)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("Denied")) {
            project.getApp().setTrust(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.REJECTED)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("Pending")) {
            project.getApp().setTrust(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.PENDING)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("None")) {
            project.getApp().setTrust(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.NONE)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        }

        if (project.getClients() != null && !project.getClients().isEmpty()) {
            for (OIDCClient c : project.getClients()) {
                this.api.update(project, c);
            }
        }

        this.storage.projects().updateObject(project.getId(), project);
    }

    @PostMapping("/{projectId}/camunda/verified")
    @Operation(
operationId = "adminUpdateProjectVerificationStatus",
            summary = "Update project verification status via webhook.",
            description = """
This endpoint is invoked by the verification workflow to update the verified status of a project. It is intended for administrative use, ensuring that only authorized services can modify project verification statuses. Proper security measures are in place to protect sensitive operations.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void verificationWebhook(@RequestBody String json) {
        CamundaBpmVerificationProvider.CamundaVerificationResponse payload = U.fromJson(json, CamundaBpmVerificationProvider.CamundaVerificationResponse.class);

        Project project = this.storage.projects().getObject(payload.project.getId());
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Project no longer exists."
            );
        }

        if (payload.status.equalsIgnoreCase("Approved")) {
            project.getApp().setVerification(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.VERIFIED)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("Denied")) {
            project.getApp().setVerification(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.REJECTED)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("Pending")) {
            project.getApp().setVerification(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.PENDING)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        } else if (payload.status.equalsIgnoreCase("None")) {
            project.getApp().setVerification(
                    new AppVerification()
                            .withApproval(AppVerification.Approval.NONE)
                            .withAdditionalDetails(payload.rejection_reasons)
                            .withUpdateDate(DateTime.now())
            );
        }

        if (project.getClients() != null && !project.getClients().isEmpty()) {
            for (OIDCClient c : project.getClients()) {
                this.api.update(project, c);
            }
        }

        this.storage.projects().updateObject(project.getId(), project);
    }


    @PutMapping("/{projectId}/verification")
    @Operation(
operationId = "adminUpdateVerificationStatus",
            summary = "Admin updates project verification status.",
            description = """
This endpoint allows administrators to update the verification status of projects. It enhances user trust by ensuring that verified applications provide a more reliable user experience. Use this to manage project verifications effectively.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The verification status was applied.")
            },
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void updateVerificationStatus(@PathVariable("projectId") String projectId,
                                         @RequestBody AppVerification verification,
                                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project p = this.storage.projects().getObject(projectId);
        try {
            AppVerification existing = p.getApp().getVerification();

            if (existing != null) {
                if (verification.getApproval() != null) {
                    existing.setApproval(verification.getApproval());
                }
                if (verification.getStatus() != null) {
                    existing.setStatus(verification.getStatus());
                }
                existing.setUpdateDate(DateTime.now());
            } else {
                p.getApp().setVerification(verification);
            }

            this.storage.projects().updateObject(projectId, p);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
