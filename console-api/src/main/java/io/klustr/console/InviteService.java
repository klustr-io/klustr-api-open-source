package io.klustr.console;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.Lists;
import io.klustr.permissions.*;
import io.klustr.permissions.dsl.Permissions;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.events.EventInviteUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.EntityReference;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@RestController
@Component
@RequestMapping("/console/invites")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class InviteService {
    private final OrganizationSecurityPolicy organizationPolicy;

    private final Storage storage;

    private final PermissionProvider permissions;

    public InviteService(RethinkDbConnectionPool pool,
                         Storage storage,
                         PermissionProvider permissions,
                         OrganizationSecurityPolicy organizationPolicy) {
        this.organizationPolicy = organizationPolicy;
        this.storage = storage;
        this.permissions = permissions;
    }

    @GetMapping
    @Operation(
operationId = "getMyActiveInvitations",
            summary = "Retrieve active invitations for the authenticated user.",
            description = """
This endpoint provides a list of all active invitations linked to the current user. It allows users to efficiently manage their pending invites and take necessary actions. Use this to stay updated on any invitations that require your attention.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<EventInviteUserRequest> getActiveInvitations(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {
        return getInvitesForUser(principle).stream().map(x -> {
            if (x.getEntity() == null) return null;
            if (StringUtils.isBlank(x.getEntity().getId())) return null;
            return x;
        }).filter(Objects::nonNull).toList();
    }

    private List<EventInviteUserRequest> getInvitesForUser(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {
        String email = PrincipleUtils.tryGetEmailForUser(principle);
        if (StringUtils.isBlank(email)) {
            return Lists.newArrayList();
        }
        DbQuery fq = Db.query("email").eq(email.trim().toLowerCase());
        DocumentResult<EventInviteUserRequest> result = this.storage.invites().insecureQuery(fq, new Pagination().withStart(0).withLimit(10));
        return result.docs.stream().filter(x -> {
            if (x.getExpiresDate() != null && x.getExpiresDate().isBeforeNow()) {
                return false;
            }
            return x.getStatus() == EventInviteUserRequest.Status.PENDING;
        }).toList();
    }

    @PostMapping
    @Operation(
operationId = "acceptUserInvite",
            summary = "Accept an invitation to join an organization.",
            description = """
This endpoint allows users to accept invitations to join organizations. Proper authentication is required to ensure that only authorized users can confirm their participation. This action is crucial for onboarding new members securely.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void acceptInvite(
            @RequestBody AcceptInvitePayload payload,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {

        Optional<EventInviteUserRequest> match = getInvitesForUser(principle)
                .stream().filter(x -> x.getScope() == payload.scope && x.getEntity().getId().equalsIgnoreCase(payload.entity.getId())).findFirst();

        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No matching invite found for this organization and user."
            );
        }

        EventInviteUserRequest invite = match.get();

        String namespace;
        if (payload.scope == EventInviteUserRequest.Scope.PROJECT) {
            namespace = "projects";
        } else {
            namespace = "orgs";
        }

        invite.getRoles().forEach(x -> {
            this.permissions.grant(SubjectKey.user(principle), Scope.attribute(namespace, x), Target.of(namespace, payload.entity.getId()));
        });

        // ensure generic member role
        this.permissions.grant(SubjectKey.user(principle), Scope.namespace("members"), Target.of(namespace, payload.entity.getId()));

        invite.setStatus(EventInviteUserRequest.Status.CLAIMED);
        invite.setInviteId(invite.getId());
        this.storage.invites().updateObject(invite.getId(), invite);
    }

    @PutMapping("/projects/{projectId}")
    @Operation(
operationId = "inviteUserToProject",
            summary = "Invite a user to a specific project",
            description = """
This endpoint allows you to send an invitation to a user for participation in a specified project. It is essential for managing project collaborations within the console. Ensure that the user has the necessary permissions to receive the invitation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<EventInviteUserRequest> inviteUserToProjects(@PathVariable("projectId") String projectId,
                                                                 @RequestBody InviteRequest request,
                                                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {

        if (request.emails == null || request.emails.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required to invite a user to an organization."
            );
        }

        List<EventInviteUserRequest> results = Lists.newArrayList();

        String requesting_user_email = PrincipleUtils.tryGetEmailForUser(principal);

        Project project = this.storage.projects().getObject(projectId);

        this.permissions.iam().can()
                    .user(principal)
                    .write("invitations")
                    .in("projects")
                    .withId(projectId)
                    .throwIfUnauthorized();

        request.emails.forEach(email -> {
            String cleanEmail = email.trim().toLowerCase();
            DbQueryAndCondition fq = Db.and(
                    Db.query("entity").with("id").eq(project.getId()),
                    Db.query("scope").eq(EventInviteUserRequest.Scope.PROJECT.value()),
                    Db.query("email").eq(cleanEmail)
            );

            EventInviteUserRequest e = saveRequest(request,
                    EventInviteUserRequest.Scope.PROJECT,
                    new EntityReference().withId(projectId).withName(project.getName()),
                    principal, cleanEmail, requesting_user_email);

            results.add(e);
        });

        return results;
    }

    @PutMapping("/orgs/{organizationId}")
    @Operation(
operationId = "inviteUserToOrganization",
            summary = "Invite a user to join an organization",
            description = """
This endpoint allows authenticated users to send invitations for joining a specific organization. It requires the organization ID and details of the invite request. This operation is essential for managing user invitations effectively within the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<EventInviteUserRequest> inviteUserToOrganization(@PathVariable("organizationId") String organizationId,
                                                                        @RequestBody InviteRequest request,
                                                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        if (request.emails == null || request.emails.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required to invite a user to an organization."
            );
        }

        String requesting_user_email = PrincipleUtils.tryGetEmailForUser(user);

//        this.permissions.iam().can()
//                    .user(user)
//                    .write("invitations")
//                    .in("orgs")
//                    .withId(organizationId)
//                    .throwIfUnauthorized();

        List<EventInviteUserRequest> results = Lists.newArrayList();
        Org org = this.storage.organizations().getObject(organizationId);

        request.emails.forEach(email -> {
            String cleanEmail = email.trim().toLowerCase();
            DbQueryAndCondition fq = Db.and(
                    Db.query("entity").with("id").eq(organizationId),
                    Db.query("scope").eq(EventInviteUserRequest.Scope.ORGANIZATION.value()),
                    Db.query("email").eq(cleanEmail)
            );

            EventInviteUserRequest e = saveRequest(request,
                        EventInviteUserRequest.Scope.ORGANIZATION,
                        new EntityReference().withId(organizationId).withName(org.getName()),
                        user, cleanEmail, requesting_user_email);

            results.add(e);
        });
        return results;
    }

    @NotNull
    private EventInviteUserRequest saveRequest(InviteRequest request,
                                               EventInviteUserRequest.Scope scope,
                                               EntityReference entity,
                                               OAuth2AuthenticatedPrincipal user,
                                               String cleanEmail,
                                               String requesting_user_email) {
        EventInviteUserRequest e = new EventInviteUserRequest()
                .withId(UUID.randomUUID().toString())
                .withInviteId(UUID.randomUUID().toString())
                .withCreationDate(DateTime.now())
                .withEmail(cleanEmail)
                .withRequestingUserEmail(requesting_user_email)
                .withRequestingUserId(user.getName())
                .withScope(scope)
                .withEntity(entity)
                .withStatus(EventInviteUserRequest.Status.PENDING)
                .withRoles(request.roles);

        if (request.expireDays >= 1) {
            e.setExpiresDate(DateTime.now().withTimeAtStartOfDay().plusDays(request.expireDays));
        } else {
            e.setExpiresDate(DateTime.now().withTimeAtStartOfDay().plusDays(28));
        }

        // this.invitesQ.send(e.getId(), e);
        this.storage.invites().insertObject(e.getId(), e);
        return e;
    }

    public static class AcceptInvitePayload {
        public EventInviteUserRequest.Scope scope;
        public EntityReference entity;
    }

    public static class InviteRequest {

        @JsonPropertyDescription("The number of days the invite is valid for before expiring.")
        @Schema(description = "The number of days the invite is valid for before expiring.")
        public int expireDays = 1;

        @JsonPropertyDescription("If the invite requires users get unique codes to use when registering.")
        @Schema(description = "If the invite requires users get unique codes to use when registering.")
        public boolean secure = false;

        @JsonPropertyDescription("The array of roles to assign this user when they join the organization.")
        @Schema(description = "The array of roles to assign this user when they join the organization.")
        public List<String> roles = Lists.newArrayList();

        @JsonPropertyDescription("The emails of the users to invite.")
        @Schema(description = "The emails of the users to invite.")
        public List<String> emails = Lists.newArrayList();
    }


    @DeleteMapping()
    @Operation(
operationId = "deleteUserInformation",
            summary = "Delete all user information related to projects.",
            hidden = true,
            description = """
This operation permanently removes all user-related data associated with projects. It is crucial to ensure that this action is intended, as it cannot be undone. Use this endpoint with caution, as it affects all linked user information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteMe(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {
        // TODO this should really only delete within a singular organization NOT all.
        throw new RuntimeException("Not implemented");
//        this.permissions.deleteNamespaceForSubject("orgs", SubjectKey.userId(principle.getName()));
//        this.permissions.deleteNamespaceForSubject("projects", SubjectKey.userId(principle.getName()));
//        this.permissions.deleteNamespaceForSubject("roles", SubjectKey.userId(principle.getName()));
//        this.permissions.deleteNamespaceForSubject("groups", SubjectKey.userId(principle.getName()));
    }
}
