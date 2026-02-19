package io.klustr.console;

import com.lago.openapi.model.LagoCustomerUsageObject;
import io.klustr.billing.models.BillingKey;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.projects.Project;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.permissions.PermissionProvider;
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

import java.util.Optional;

@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Billing & Usage APIs", description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectBillingService {
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final LagoBillingAdapter lago;

    private final Storage storage;


    public ProjectBillingService(Storage storage,
                                 LagoBillingAdapter lago,
                                 HydraApi hydra,
                                 ProjectSecurityPolicy policy,
                                 PermissionProvider permissions) {

        this.storage = storage;
        this.lago = lago;
    }


    /**
     * Returns the current project billing information.
     *
     * @param projectId The current projects billing information
     * @return The billing for the current period.
     */
    @GetMapping("/{projectId}/billing")
    @Operation(
operationId = "getCurrentProjectBillingUsage", summary = "Retrieve current billing and usage for a project",
            description = """
This endpoint retrieves the current billing and usage details for the specified project. It allows users to effectively monitor financial metrics and resource consumption. Secure access is required, ensuring that only authorized users can view this sensitive information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('projects.billing.get')")
    public com.lago.openapi.model.LagoCustomerUsageObject getCurrentBillingUsage(@PathVariable("projectId") String projectId,
                                                                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        Optional<LagoCustomerUsageObject> usage =
                this.lago.getCurrentUsage(new BillingKey(project.getOrgId(), projectId));
        if (usage.isEmpty()) {
            return new LagoCustomerUsageObject();
        }
        return usage.get();
    }
}
