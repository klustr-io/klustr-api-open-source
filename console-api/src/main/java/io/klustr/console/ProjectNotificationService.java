package io.klustr.console;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * Enables setting up messages about a specific project so users can see
 * the latest messages. Useful for long running processes and notifying
 * users when a change occurs.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectNotificationService {

    private final Storage storage;

    public ProjectNotificationService(Storage storage) {
        this.storage = storage;
    }

    @PostMapping("/{projectId}/notifications/{topic}/{topicId}")
    @Operation(
operationId = "notifyProjectNotification", summary = "Send real-time notifications for project updates",
            description = """
This endpoint allows users to send notifications regarding specific project events. Notifications can be displayed in the web console or other channels, ensuring users are informed about ongoing changes and long-running processes. It enhances user engagement by providing timely updates.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId",description = "The project ID"),
                    @Parameter(required = true, name = "topic",description = "The topic for this particular notification"),
                    @Parameter(required = true, name = "topicId",description = "The unique ID that related topics converge on for 'latest' message on that subject.")
            }
)
    public void notify(@PathVariable("projectId") String projectId,
                       @PathVariable("topic") ProjectNotificationTopic topic,
                       @PathVariable("topicId") String topicId,
                       @RequestBody ProjectNotification notification,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (!notifications.containsKey(projectId)) {
            notifications.put(projectId, new HashMap<>());
        }

        // remove last update for this topic and replace with latest!
        notifications.get(projectId).remove(topicId);

        // validate defaults
        notification.id = UUID.randomUUID().toString();
        notification.timestamp = DateTime.now();
        notification.topic_id =topicId;
        notification.topic = topic;

        if (StringUtils.isBlank(notification.title)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing title");
        }
        if (StringUtils.isBlank(notification.description)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing description");
        }
        if (notification.level == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing level");
        }
        if (notification.status == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing status");
        }

        notifications.get(projectId).put(topicId, notification);
    }

    @GetMapping("/{projectId}/notifications")
    @Operation(
operationId = "getProjectNotifications", summary = "Retrieve active notifications for a specific project.",
            description = """
This endpoint fetches all active notifications related to the specified project. Users can utilize this information to stay updated on ongoing processes and changes. Ensure to manage read statuses on the client side for an optimal user experience.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId",description = "The project ID")
            }
)
    public Map<String, ProjectNotification> getNotifications(@PathVariable("projectId") String projectId,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (!notifications.containsKey(projectId)) {
            return Maps.newConcurrentMap();
        }

        Map<String, ProjectNotification> topics = notifications.get(projectId);

        // any topics dead or expired (10 minutes ago or more)
        List<String> expiredTopics = Lists.newArrayList();
        topics.keySet().forEach(topicId -> {
            if (DateTime.now().minusMinutes(10).isAfter(topics.get(topicId).timestamp)) {
                expiredTopics.add(topicId);
            }
        });
        expiredTopics.forEach(topics::remove);

        return topics;
    }

    // TODO replace with robust implementation, queue, kafka, etc
    private static final Map<String, Map<String, ProjectNotification>> notifications = Maps.newConcurrentMap();

    public static final class ProjectNotification {
        public String id = UUID.randomUUID().toString();
        public String title;
        public String topic_id;   // this is the unique topic id used for [pending -> running -> failed] so the UX can know how to fold to the latest version
        public String description;
        public String url;
        public DateTime timestamp = DateTime.now();
        public String source;
        public ProjectNotificationLevel level = ProjectNotificationLevel.info;
        public ProjectNotificationTopic topic = ProjectNotificationTopic.compute;
        public ProjectNotificationStatus status = ProjectNotificationStatus.started;
    }

    public enum ProjectNotificationLevel {
        info, urgent, critical
    }

    public enum ProjectNotificationTopic {
        build, compute
    }

    public enum ProjectNotificationStatus {
        started, processing, failed, completed
    }
}
