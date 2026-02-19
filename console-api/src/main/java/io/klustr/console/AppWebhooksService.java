package io.klustr.console;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.svix.WebhookProvider;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.apps.AppWebhooks;
import io.klustr.schemas.console.apps.WebhookEndpoint;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import io.klustr.schemas.console.webhooks.WebhookMessageAudit;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.*;

@RestController
@Component
@RequestMapping("/console/projects/{projectId}/webhooks")
@Tag(name = "Project Webhook APIs", description = "Enable applications and services to subscribe and register webhooks for notifications.")
public class AppWebhooksService {

    private static final Logger log = LoggerFactory.getLogger(AppWebhooksService.class);

    private final ProjectSecurityPolicy policy;

    private final PermissionProvider permissions;

    private final Storage storage;

    private final WebhookProvider webhooks;

    private final SetupAppWebHooksTask task;

    public AppWebhooksService(Storage storage,
                              WebhookProvider webhooks,
                              PermissionProvider permissions,
                              ProjectSecurityPolicy policy,
                              SetupAppWebHooksTask task) {
        this.storage = storage;
        this.permissions = permissions;
        this.policy = policy;
        this.webhooks = webhooks;
        this.task = task;
    }

    @GetMapping
    @Operation(
description = """
This endpoint fetches the webhooks associated with the provided project ID. It allows applications and services to access webhook configurations, which are essential for managing notifications effectively. Understanding these webhooks is crucial for developers to ensure proper integration and functionality within their projects.
""",
            operationId = "getProjectWebhooks",
            summary = "Retrieve webhooks for the specified project.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppWebhooks getAppWebhooks(@PathVariable("projectId") String projectId,
                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, oauth);
        return project.getApp().getWebhooks();
    }

    @GetMapping("/{uid}/history")
    @Operation(
description = """
This endpoint fetches detailed audit logs of messages sent through the specified app webhook. It provides insights into message delivery status and historical data for the given project. This is essential for debugging and tracking notifications sent to users.
""",
            operationId = "getProjectWebhookMessageAudit",
            summary = "Retrieve audit logs for project webhooks",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public WebhookProvider.Page<WebhookMessageAudit> audit(@PathVariable("projectId") String projectId,
                                                           @PathVariable("uid") String uid,
                                                           @RequestParam(value = "iterator", required = false) String iterator,
                                                           @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
                                                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, oauth);
        Optional<WebhookExternalId> projectExternalId = this.webhooks.getProjectExternalId(project.getId());
        Optional<WebhookEndpoint> match = project.getApp().getWebhooks().getEndpoints().stream().filter(x -> {
            return x.getUid().equalsIgnoreCase(uid);
        }).findFirst();
        return this.webhooks.listMessageAttempts(projectExternalId.get(), match.get().getExternalId(),
                new WebhookProvider.Pagination().withIterator(iterator).withLimit(limit));
    }

    @GetMapping("/{uid}/stats")
    @Operation(
description = """
This endpoint allows authorized users to access detailed statistics on message metrics for a specified webhook. It is designed to help users monitor the performance and delivery of notifications sent through the webhook. Access is restricted to users associated with the specified project.
""",
            operationId = "getWebhookMessageStats",
            summary = "Retrieve statistics for a specific webhook message.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public WebhookMessageStats stats(@PathVariable("projectId") String projectId,
                                     @PathVariable("uid") String uid,
                                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, oauth);
        Optional<WebhookExternalId> projectExternalId = this.webhooks.getProjectExternalId(project.getId());
        Optional<WebhookEndpoint> match = project.getApp().getWebhooks().getEndpoints().stream().filter(x -> {
            return x.getUid().equalsIgnoreCase(uid);
        }).findFirst();
        WebhookProvider.Pagination pagination = new WebhookProvider.Pagination().withIterator(null).withLimit(100);
        WebhookProvider.Page<WebhookMessageAudit> data = this.webhooks.listMessageAttempts(projectExternalId.get(), match.get().getExternalId(), pagination);
        boolean hasNext = !data.docs.isEmpty();
        WebhookMessageStats stats = new WebhookMessageStats();
        while (hasNext) {
            for (WebhookMessageAudit audit: data.docs) {
                switch (audit.getStatus()) {
                    case FAIL -> stats.errors += 1;
                    case PENDING ->  stats.pending += 1;
                    case SUCCESS ->  stats.success += 1;
                    case SENDING -> stats.sending += 1;
                }
            }
            if (!data.done) {
                data = this.webhooks.listMessageAttempts(projectExternalId.get(), match.get().getExternalId(), pagination.withIterator(data.iterator));
                hasNext = !data.docs.isEmpty();
            } else {
                hasNext = false;
            }
        }
        return stats;
    }

    public static class WebhookMessageStats {
        public int success = 0;
        public int errors  = 0;
        public int pending  = 0;
        public int sending = 0;
    }

    @PostMapping
    @Operation(
description = """
This endpoint allows users to register a webhook that receives notifications about project activities. Proper configuration of the webhook endpoint is essential for handling incoming notifications effectively. This functionality is crucial for real-time updates and seamless integration with external services.
""",
            operationId = "addProjectWebhook",
            summary = "Register a new webhook for project notifications",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppWebhooks postWebhooks(@PathVariable("projectId") String projectId,
                               @RequestBody WebhookEndpointUpdate body,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        body.id = UUID.randomUUID().toString();
        Project project = checkPermissionAndGetProject(projectId, oauth);
        AppWebhooks webhooks = project.getApp().getWebhooks();
        if (webhooks == null || webhooks.getEndpoints() == null || webhooks.getEndpoints().isEmpty()) {
            webhooks = new AppWebhooks().withEndpoints(new ArrayList<>());
        }
        webhooks.getEndpoints().add(body.build());

        this.storage.projects().updateObject(projectId, project);
        return this.task.syncProjectWebhooks(projectId).get();
    }

    @DeleteMapping("/{uid}")
    @Operation(
description = """
This endpoint allows users to remove a webhook endpoint linked to a project. It ensures the webhook is inactive for notifications, thereby helping manage webhook subscriptions effectively. This operation is essential for maintaining control over notification channels.
""",
            operationId = "removeProjectWebhook",
            summary = "Remove a specific webhook for project notifications.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppWebhooks removeWebhooks(@PathVariable("projectId") String projectId,
                               @PathVariable("uid") String uid,
                               @RequestBody WebhookEndpointUpdate body,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, oauth);
        AppWebhooks webhooks = project.getApp().getWebhooks();
        if (webhooks == null || webhooks.getEndpoints() == null || webhooks.getEndpoints().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Webhook not found to update."
            );
        }
        webhooks.getEndpoints().removeIf(x -> x.getId().equalsIgnoreCase(uid));

        this.storage.projects().updateObject(projectId, project);
        return this.task.syncProjectWebhooks(projectId).get();
    }

    @PutMapping
    @Operation(
description = """
This endpoint enables users to efficiently update all webhook endpoints linked to a specific project. It ensures that all relevant webhook configurations are modified in a single request, maintaining accurate notification settings across various channels. This operation is essential for effective management of webhook notifications.
""",
            operationId = "updateProjectWebhookDetailsBulk",
            summary = "Update multiple webhook configurations for a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppWebhooks updateWebhooksBulk(@PathVariable("projectId") String projectId,
                               @RequestBody WebhookBulkEndpointUpdate body,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = checkPermissionAndGetProject(projectId, oauth);
        AppWebhooks webhooks = project.getApp().getWebhooks();
        if (webhooks == null) {
            webhooks = new AppWebhooks();
            project.getApp().setWebhooks(webhooks);
        }
        if (webhooks.getEndpoints() == null) {
            webhooks.setEndpoints(new ArrayList<>());
        }
        webhooks.getEndpoints().clear();

        List<WebhookEndpoint> list = body.endpoints.stream().map(WebhookEndpointUpdate::build).toList();
        webhooks.getEndpoints().addAll(list);

        // ensure all have ids
        webhooks.getEndpoints().forEach(x -> {
            if (StringUtils.isBlank(x.getId())) {
                x.setId(UUID.randomUUID().toString());
            }
        });

        this.storage.projects().updateObject(projectId, project);
        return this.task.syncProjectWebhooks(projectId).get();
    }

    @PutMapping("/{uid}")
    @Operation(
description = """
This endpoint allows authorized users to modify webhook settings for a designated project. Ensure that the webhook details provided are correct to guarantee effective notification delivery. This operation is essential for maintaining the integrity of webhook functionalities.
""",
            operationId = "updateProjectWebhook",
            summary = "Update webhook configurations for a specific project.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppWebhooks updateWebhooks(@PathVariable("projectId") String projectId,
                               @PathVariable("uid") String uid,
                               @RequestBody WebhookEndpointUpdate body,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = checkPermissionAndGetProject(projectId, oauth);
        AppWebhooks webhooks = project.getApp().getWebhooks();
        if (webhooks == null || webhooks.getEndpoints() == null || webhooks.getEndpoints().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Webhook not found to update."
            );
        }

        Optional<WebhookEndpoint> match = webhooks.getEndpoints().stream().filter(x -> {
            return x.getUid().equalsIgnoreCase(uid);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Webhook not found to update."
            );
        }

        webhooks.getEndpoints().removeIf(x -> x.getId().equalsIgnoreCase(uid));
        webhooks.getEndpoints().add(body.build());

        this.storage.projects().updateObject(projectId, project);
        return this.task.syncProjectWebhooks(projectId).get();
    }

    public static class WebhookBulkEndpointUpdate {
        public List<WebhookEndpointUpdate> endpoints = Lists.newArrayList();
    }

    public static class WebhookEndpointUpdate {
        public Set<String> filter_types = Sets.newConcurrentHashSet();
        public Set<String> channels = Sets.newConcurrentHashSet();
        public String id;
        public String uid;
        public String url;
        public String secret;
        public Boolean disabled;
        public String description;
        public WebhookExternalId external_id;
        public Map<String, String> metadata = Maps.newConcurrentMap();

        private WebhookEndpoint build() {
            return new WebhookEndpoint().withDescription(this.description)
                    .withDisabled(this.disabled)
                    .withMetadata(this.metadata)
                    .withSecret(this.secret)
                    .withFilterTypes(this.filter_types)
                    .withChannels(this.channels)
                    .withId(this.id)
                    .withUid(this.uid)
                    .withExternalId(external_id)
                    .withUrl(URI.create(this.url));
        }
    }

    private Project checkPermissionAndGetProject(String projectId, OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found."
            );
        }

        if (!this.policy.hasUpdateAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Does not have update access for application."
            );
        }

        if (project.getApp() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project does not contain the app."
            );
        }

        return project;
    }


}
