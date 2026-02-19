package io.klustr.notifications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.notifications.firebase.ContextAwareFirebaseNotificationProvider;
import io.klustr.notifications.firebase.Notification;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.spring.OAuthCredentialType;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Enables sending of notifications to users should they have a registered device
 * and option. Requires that users register the various notification options
 * when the user enters the website.
 */
@RestController
@Component
@RequestMapping("/notifications/me/channels/firebase")
@Tag(name = "Notifications APIs", description = "APIs for notifications")
public class UserFirebaseChannelsService {

    private final NotificationStorage notificationStorage;

    private final ContextAwareFirebaseNotificationProvider noti;

    public UserFirebaseChannelsService(NotificationStorage notificationStorage, ContextAwareFirebaseNotificationProvider noti) {
        this.notificationStorage = notificationStorage;
        this.noti = noti;
    }

    private static String getKey(String project_id, String user_id) {
        return user_id + ":" + project_id;
    }

    @GetMapping
    @Operation(
summary = "Retrieve my Firebase notification session token",
            operationId = "getMyFirebaseChannelToken",
            description = """
Fetch the latest GCS token for notifications specific to the authenticated user. This token is crucial for sending targeted notifications to the user's registered device. Ensure that the user has opted in for notifications to effectively utilize this feature.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('notifications.create')")
    public Channel getFirebaseChannel(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        String project_id = PrincipleUtils.tryGetClientId(user);
        String id = getKey(user.getName(), project_id);
        SubscriptionChannels sub = this.notificationStorage.subs().getObject(id);
        if (sub == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        Optional<Channel> match = sub.channels.stream().filter(x -> x.type.equalsIgnoreCase("gcs")).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return match.get();
    }

    @PostMapping
    @Operation(
operationId = "registerMyFirebaseChannel", summary = "Register GCS token for user notifications",
            description = """
This endpoint allows users to register their GCS token for notifications. Proper registration ensures users receive relevant updates through Firebase channels. This is essential for effective communication and timely notification delivery.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('notifications.create')")
    public Channel addFirebaseChannel(@RequestBody Request request,
                                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        String project_id = PrincipleUtils.tryGetClientId(user);
        String id = getKey(user.getName(), project_id);
        SubscriptionChannels sub = this.notificationStorage.subs().getObject(id);

        if (sub == null) {
            sub = new SubscriptionChannels();
            sub.project_id = project_id;
            sub.user_id = user.getName();
            sub.id = id;
        }

        Optional<Channel> match = sub.channels.stream().filter(x -> x.type.equalsIgnoreCase("gcs")).findFirst();
        if (match.isEmpty()) {
            Channel gcs = new Channel();
            gcs.type = "gcs";
            gcs.last_modified = DateTime.now();
            gcs.token = request.token;
            sub.channels.add(gcs);
            this.notificationStorage.subs().insertObject(id, sub);
            return gcs;
        } else {
            match.get().token = request.token;
            match.get().last_modified = DateTime.now();
            this.notificationStorage.subs().updateObject(id, sub);
            return match.get();
        }
    }

    @PostMapping("/send")
    @Operation(
operationId = "sendNotificationToUser", description = """
This endpoint facilitates the sending of notifications via Firebase to a specific user. It verifies that the user has a registered device and notification preferences. This operation enhances user engagement through personalized messaging, ensuring effective communication.
""",
            summary = "Send a notification message to the user",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('notifications.create')")
    public void send(
            @PathVariable("userId") String userId,
            @RequestBody Notification payload,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        String project_id = PrincipleUtils.tryGetClientId(user);
        payload.projectId = project_id;
        payload.userId = userId;
        payload.send = DateTime.now();
        this.noti.send(payload);
    }

    public static class Request {

        public String token;
    }

}
