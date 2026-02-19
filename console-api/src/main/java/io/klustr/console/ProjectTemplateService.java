package io.klustr.console;

import io.klustr.compute.MockProjectTemplateRepository;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Enables a storage and manifests that allow a user to immediately boostrap
 * their project with a boilerplate and template
 */
@RestController
@Component
@RequestMapping("/console/projects/templates")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectTemplateService {

    private static final Logger log = LoggerFactory.getLogger(ProjectTemplateService.class);

    private final MockProjectTemplateRepository templateRepo = new MockProjectTemplateRepository();

    @GetMapping()
    @Operation(
operationId = "listProjectTemplates", summary = "Retrieve available project bootstrap templates for users",
            description = """
This endpoint provides users with a list of templates that can be used to bootstrap their projects. Each template includes necessary parameters for activation. It is designed to facilitate quick project setup and enhance user experience.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<ProjectTemplate> listTemplates(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        return templateRepo.getTemplates();
    }

    @GetMapping("/{templateId}")
    @Operation(
operationId = "getProjectTemplateById", summary = "Retrieve a specific project template by its ID.",
            description = """
This endpoint returns the metadata of the specified project template. It is intended for users to evaluate and provision the template for their projects. Ensure you have the necessary permissions to access this information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ProjectTemplate getProjectTemplate(@PathVariable("templateId") String templateId,
                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<ProjectTemplate> template = templateRepo.getTemplates().stream().filter(x -> {
            return x.getId().equalsIgnoreCase(templateId);
        }).findFirst();

        if (template.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The template '" + templateId + "' was not found."
            );
        }

        return template.get();
    }
}
