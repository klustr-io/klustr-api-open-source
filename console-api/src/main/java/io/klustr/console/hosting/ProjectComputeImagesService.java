package io.klustr.console.hosting;

import com.google.common.collect.Lists;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.console.storage.Storage;
import io.klustr.exceptions.StandardResponseException;
import io.klustr.git.GitProjectDockerImageReferenceProvider;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.gitlab.GitLabProjectDockerImageReferenceProvider;
import io.klustr.integrations.haproxy.HaproxyComputeLoadbalancerProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.schemas.integrations.git.GitProjectDockerImageReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Deals with the mapping of projects to various images.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectComputeImagesService {

    private static final Logger log = LoggerFactory.getLogger(ProjectComputeImagesService.class);

    private final Storage storage;

    private final PortainerComputeResourceProvider computeProvider;
    private final ComputeLoadBalancerResolver loadbalancers;
    private final GitProjectDockerImageReferenceProvider images;
    private final GitProjectResourceProvider git;

    public ProjectComputeImagesService(Storage storage,
                                 GitLabProjectDockerImageReferenceProvider images,
                                 GitProjectResourceProvider git,
                                 PortainerComputeResourceProvider computeProvider,
                                       ComputeLoadBalancerResolver loadbalancers) {
        this.storage = storage;
        this.images = images;
        this.git = git;
        this.loadbalancers = loadbalancers;
        this.computeProvider = computeProvider;
    }

    @GetMapping("/{projectId}/images")
    @Operation(
            operationId = "getProjectDockerImages", summary = "Returns the available docker images for this project.",
            description = """
Will return any configured docker images for this project.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<GitProjectDockerImageReference> getProjectDockerImage(@PathVariable("projectId") String projectId,
                                                                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<GitProjectReference> ref = this.git.tryGetProject(projectId);
        if (ref.isEmpty()) {
            return Lists.newArrayList();
        }
        return this.images.getImages(ref.get());
    }

    @GetMapping("/{projectId}/images/latest")
    @Operation(
            operationId = "getProjectDockerLatestImage", summary = "Returns the latest docker project image",
            description = """
Will return the latest docker project image
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public GitProjectDockerImageReference getProjectDockerLatestImage(@PathVariable("projectId") String projectId,
                                                                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<GitProjectReference> ref = this.git.tryGetProject(projectId);
        if (ref.isEmpty()) {
            throw new StandardResponseException(HttpStatus.NOT_FOUND).withErrorId("Project not found.");
        }
        GitProjectDockerImageReference rs = this.images.getLastestImage(ref.get());
        if (rs == null) {
            return null;
        }
        rs.setImageId("sha256:" + rs.getRevision());    // set to match if != original
        return rs;
    }
}
