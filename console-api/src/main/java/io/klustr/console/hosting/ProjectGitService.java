package io.klustr.console.hosting;

import com.google.common.collect.Lists;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.git.*;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.Target;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.git.*;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that deals with managing the git linked to this project.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectGitService {

    private static final Logger log = LoggerFactory.getLogger(ProjectGitService.class);

    private final PermissionProvider permissions;

    private final Storage storage;

    private final ProjectSecurityPolicy policy;

    private final GitGroupMembershipResourceProvider gitMembers;
    private final GitGroupResourceProvider gitGroups;
    private final GitProjectResourceProvider gitProjects;
    private final GitUserResourceProvider gitUsers;
    private final GitProjectPipelineResourceProvider gitPipelines;
    private final KratosApi kratos;

    public ProjectGitService(Storage storage,
                             KratosApi kratos,
                             ProjectSecurityPolicy policy,
                             PermissionProvider permissions,
                             GitUserResourceProvider gitUsers,
                             GitGroupMembershipResourceProvider gitMembers,
                             GitGroupResourceProvider gitGroups,
                             GitProjectResourceProvider gitProjects,
                             GitProjectPipelineResourceProvider gitPipelines) {
        this.kratos = kratos;
        this.storage = storage;
        this.permissions = permissions;
        this.policy = policy;
        this.gitProjects = gitProjects;
        this.gitGroups = gitGroups;
        this.gitMembers = gitMembers;
        this.gitUsers = gitUsers;
        this.gitPipelines = gitPipelines;
    }

    @GetMapping("/{projectId}/git")
    @Operation(
            operationId = "getGitForProject", summary = "Returns the git information for the project if it is active.",
            description = """
                    This endpoint enables a user to lookup and get key information about the git project linked to their console project. If the user has activated git on their project this will return the latest information included the git url, and connection options as well as stats.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public GitProjectResponse getGitProject(@PathVariable("projectId") String projectId,
                                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        GitProjectResponse res = new GitProjectResponse();
        Project project = this.storage.projects().getObject(projectId);
        Optional<GitProjectReference> match = this.gitProjects.tryGetProject(project.getId());
        if (match.isPresent()) {
            res.enabled = true;
            res.git = match.get();
        }
        ;
        return res;
    }

    public static class GitProjectResponse {
        public GitProjectReference git;
        public boolean enabled = false;
    }

    @GetMapping("/{projectId}/git/pipelines/latest")
    @Operation(
            operationId = "getLatestPipeline", summary = "Get the latest build from the git project",
            description = """
                    This endpoint will examine and find the latest build and get the general pipeline status.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public GitPipelineReference getLatestPipeline(@PathVariable("projectId") String projectId,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<GitProjectReference> projectRef = this.gitProjects.tryGetProject(projectId);
        if (projectRef.isEmpty()) {
            return null;
        }
        try {
            Optional<GitPipelineReference> pipeline = this.gitPipelines.getLatestPipeline(projectRef.get());
            return pipeline.orElse(null);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @GetMapping("/{projectId}/git/pipelines")
    @Operation(
            operationId = "getProjectPipelines", summary = "List the pipelines for git",
            description = """
                    This endpoint will return the pipelines that have been run ordered by date descending.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<GitPipelineReference> getProjectPipelines(@PathVariable("projectId") String projectId,
                                                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<GitProjectReference> projectRef = this.gitProjects.tryGetProject(projectId);
        if (projectRef.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return this.gitPipelines.getPipelines(projectRef.get());
    }

    @GetMapping("/{projectId}/git/pipelines/latest/jobs")
    @Operation(
            operationId = "getProjectPipelineJobs", summary = "List the steps performed in a specific pipeline",
            description = """
                    Will return the steps performed in the given pipeline and include which jobs have succeeded or failed.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<GitPipelineStageReference> getProjectPipelineJobs(@PathVariable("projectId") String projectId,
                                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<GitProjectReference> projectRef = this.gitProjects.tryGetProject(projectId);
        if (projectRef.isEmpty()) {
            return Lists.newArrayList();
        }
        Optional<GitPipelineReference> pipeline = this.gitPipelines.getLatestPipeline(projectRef.get());
        List<GitPipelineStageReference> stages = this.gitPipelines.getPipelineDetails(pipeline.get());
        Collections.reverse(stages);
        return stages;
    }

    @PostMapping("/{projectId}/git")
    @Operation(
            operationId = "enableGitForProject", summary = "Enable Git for the project",
            description = """
                    This endpoint is made to enable git for the specific project so that the user can have a complete project for software development.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public GitProjectReference activate(@PathVariable("projectId") String projectId,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        Org org = this.storage.organizations().getObject(project.getOrgId());
        Optional<GitGroupReference> orgMatch = this.gitGroups.getGroupByPath(project.getOrgId());
        if (orgMatch.isEmpty()) {
            // create organization in gitlab
            GitGroupReference group = this.gitGroups.createGroup(new GitCreateGroupRequest()
                    .withName(org.getName())
                    .withPath(org.getId())
            );
        }
        orgMatch = this.gitGroups.getGroupByPath(project.getOrgId());

        try {
            Optional<GitProjectReference> match = this.gitProjects.tryGetProject(project.getId());
            if (match.isPresent()) {

                // update it
                this.gitProjects.updateProject(match.get().getId().toString(), new GitUpdateProjectRequest()
                        .withDescription("")
                        .withName(project.getId())
                        .withAutoDevopsEnabled(true)
                        .withAutoDevopsDeployStrategy("continuous")
                );

                return match.get();
            }

            GitProjectReference ref = this.gitProjects.createProject(new GitCreateProjectRequest()
                    .withName(project.getId())
                    .withPath(project.getId())
                    .withDescription(project.getName())
                    .withNamespaceId(orgMatch.get().getId())
                    .withInitializeWithReadme(false)
                    .withAutoDevopsEnabled(true)
                    .withAutoDevopsDeployStrategy(GitCreateProjectRequest.AutoDevopsDeployStrategy.CONTINUOUS)
            );

            // really make sure the devops is enabled
            this.gitProjects.updateProject(ref.getId().toString(), new GitUpdateProjectRequest()
                    .withName(project.getId())
                    .withAutoDevopsEnabled(true)
                    .withAutoDevopsDeployStrategy("continuous")
            );

            // poll for auto is enabled
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    // ignore
                }
                ref = this.gitProjects.getProjectById(ref.getId().toString());
                if (ref.getAutoDevopsEnabled() != null && ref.getAutoDevopsEnabled() == true) {
                    break;
                }
            }

            return ref;
        } finally {
            // ensure permissions for users at organization level
            syncOrgMembersToGit(org.getId());
        }
    }

    void syncOrgMembersToGit(String orgID) {
        Set<Relation> relations = this.permissions.listRelationsForObject(Target.of("orgs", orgID));
        Set<String> users = relations.stream().map(x -> {
            return x.subject().id();
        }).collect(Collectors.toSet());

        GitGroupReference groupRef = this.gitGroups.getGroupByPath(orgID).get();

        List<Identity> identities = users.stream().map(this.kratos::getIdentity).toList();

        identities.forEach(identity -> {

            String subjectId = identity.getId();
            Optional<GitUserReference> match = this.gitUsers.getByOpenIdProvider(subjectId);
            if (match.isPresent()) {
                this.gitMembers.addGroupMember(groupRef, match.get(), AccessLevel.DEVELOPER);
                return;
            }

            // create the user in gitlab
            IdentityTraits traits = identity.getTraits();
            String name = "";
            String username = "";
            if (traits.getName() != null) {
                name = traits.getName().getGivenName() + " " + traits.getName().getFamilyName();
            }
            username = traits.getName().getGivenName().toLowerCase() + "_" + traits.getName().getFamilyName().toLowerCase();
            username = U.toAscii(username);

            GitUserReference userRef = this.gitUsers.createUser(new GitCreateUserRequest()
                    .withOrganization(orgID)
                    .withUsername(username)
                    .withEmail(traits.getEmail())
                    .withExternUid(subjectId)
                    .withProvider("openid-connect")
                    .withName(name)
                    .withAvatar(identity.getMetadataPublic().getPicture())
                    .withForceRandomPassword(true)
            );

            this.gitMembers.addGroupMember(groupRef, userRef, AccessLevel.DEVELOPER);
        });
    }
}
