package io.klustr.notifications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.notifications.firebase.ContextAwareFirebaseNotificationProvider;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Component
@RequestMapping("/notifications/admin/channels")
@Tag(name = "Notifications APIs", description = "APIs for notifications")
public class UserChannelsAdminService {

    private final NotificationStorage storage;


    public UserChannelsAdminService(NotificationStorage storage, ContextAwareFirebaseNotificationProvider noti) {
        this.storage = storage;
    }

    @GetMapping
    @Operation(
operationId = "adminListUserNotificationChannels", summary = "List all user notification channels for admin access",
            description = """
This endpoint retrieves all user notification channels linked to the project. It is designed for administrative users to view and manage user subscriptions across various notification methods. This functionality is essential for monitoring and optimizing communication strategies effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('project.channels.list')")
    public List<SubscriptionChannels> listChannels(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        List<SubscriptionChannels> list = this.storage.subs().insecureQuery(Pagination.all()).docs;
        String project_id = PrincipleUtils.tryGetProjectId(oauth);
        if (StringUtils.isNotBlank(project_id)) {
            return list.stream().filter(x -> x.project_id.equalsIgnoreCase(project_id)).toList();
        }
        return list;
    }

    @GetMapping("/{userId}")
    @Operation(
operationId = "adminGetNotificationChannelsForUser", description = """
This API provides visibility into all notification channels linked to the specified user. It is designed for administrative purposes, enabling admins to effectively manage and monitor user notification settings across various projects.
""",
            summary = "Retrieve notification channels for a specific user as admin",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('user.channels.list')")
    public List<SubscriptionChannels> getChannelsForUser(@PathVariable("userId") String userId,
                                                         @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        List<SubscriptionChannels> list = this.storage.subs().insecureQuery(Pagination.all()).docs;
        return list.stream().filter(x -> x.user_id.equalsIgnoreCase(userId)).toList();
    }

}
