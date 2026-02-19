package io.klustr.console;

import com.google.common.collect.Lists;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.Target;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.UserService;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.events.EventInviteUserRequest;
import io.klustr.schemas.persons.Person;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

/**
 * Handles mapping a user to the organization.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectUserService {
    private Storage storage;

    private ProjectSecurityPolicy projectPolicy;

    private OrganizationSecurityPolicy organizationPolicy;

    private KafkaTemplate<EventInviteUserRequest> invites;

    private PermissionProvider permissions;

    private PersonStorage persons;

    public ProjectUserService(Storage storage,
                              PersonStorage persons,
                              KafkaTemplate<EventInviteUserRequest> invites,
                              OrganizationSecurityPolicy organizationPolicy,
                              PermissionProvider permissions,
                              ProjectSecurityPolicy projectPolicy) {
        this.storage = storage;
        this.projectPolicy = projectPolicy;
        this.organizationPolicy = organizationPolicy;
        this.invites = invites;
        this.permissions = permissions;
        this.persons = persons;
    }


    @GetMapping("/{projectId}/users")
    @Operation(
operationId = "getProjectUsers", summary = "Retrieve users linked to a specific project",
            description = """
This endpoint provides a list of users associated with the specified project and their roles. It is intended for internal management of project user access. Ensure you have the appropriate permissions to access this sensitive information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<UserService.Profile> getProjectUsers(@PathVariable("projectId") String projectId,
                                                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        if (!this.projectPolicy.hasReadAccess(oauth, project)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Set<Relation> users = this.permissions.listRelationsForObject(Target.of("projects", projectId));
        List<UserService.Profile> members = Lists.newArrayList(
        users.stream().filter(x -> x.scope().relation().equalsIgnoreCase("members")).map(x -> {
            // this maybe break because subject value is 'user:xxxx'?
            Person person = this.persons.persons().getObject(x.subject().id());
            UserService.Profile p = new UserService.Profile();
            p.metadata_public = person.getMetadataPublic();
            p.traits = person.getTraits();
            return p;
        }).toList());


        return members;
    }
}
