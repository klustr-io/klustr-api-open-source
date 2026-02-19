package io.klustr.console;

import com.google.common.collect.Maps;
import io.klustr.setup.SetupTask;
import io.klustr.setup.SetupTaskRunnable;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.svix.WebhookProvider;
import io.klustr.schemas.console.apps.AppWebhooks;
import io.klustr.schemas.console.apps.WebhookEndpoint;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import io.klustr.schemas.console.webhooks.WebhookProject;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@SetupTask
public class SetupAppWebHooksTask implements SetupTaskRunnable {

    private static final Logger log = LoggerFactory.getLogger(SetupAppWebHooksTask.class);

    private final Storage storage;

    private final WebhookProvider webhooks;

    public SetupAppWebHooksTask(Storage storage, WebhookProvider webhooks) {
        this.storage = storage;
        this.webhooks = webhooks;
    }

    public void setup() {
        DocumentResult<String> projects = this.storage.projects().listObjectIds(Db.all(), Pagination.all());
        projects.docs.forEach(this::syncProjectWebhooks);
    }

    public Optional<AppWebhooks> syncProjectWebhooks(String projectId) {
        Project project = this.storage.projects().getObject(projectId);
        Optional<WebhookExternalId> projectExternalId = this.webhooks.getProjectExternalId(projectId);

        // app
        if (project.getApp() == null) {
            projectExternalId.ifPresent(this.webhooks::deleteProject);
            return Optional.empty();
        }

        // webhooks
        if (project.getApp().getWebhooks() == null) {
            projectExternalId.ifPresent(this.webhooks::deleteProject);
            return Optional.empty();
        }

        // have any webhooks
        List<WebhookEndpoint> endpoints = project.getApp().getWebhooks().getEndpoints();
        if (endpoints == null) {
            projectExternalId.ifPresent(this.webhooks::deleteProject);
            return Optional.empty();
        }

        Optional<WebhookProject> p = projectExternalId.isPresent() ? this.webhooks.getProject(projectExternalId.get()) : Optional.empty();
        if (projectExternalId.isEmpty()) {
            WebhookProject v = new WebhookProject()
                    .withDateCreated(DateTime.now())
                    .withName(projectId)
                    .withId(projectId)
                    .withUid(projectId)
                    .withMetadata(Maps.newHashMap());
            v.getMetadata().put("org_id", project.getOrgId());
            v.getMetadata().put("app_id", project.getApp().getId());
            v.getMetadata().put("project_id", project.getId());
            v = this.webhooks.registerProject(v);
            p = Optional.of(v);
            projectExternalId = Optional.of(v.getExternalId());
        }

        Optional<WebhookProject> apps = p;
        endpoints.forEach(x -> {
            if (StringUtils.isBlank(x.getUid())) {
                x.setUid(UUID.randomUUID().toString());
            }
        });

        // refresh endpoints
        endpoints = this.webhooks.createEndpoints(projectExternalId.get(), endpoints);
        project.getApp().getWebhooks().setEndpoints(endpoints);

        this.storage.projects().updateObject(projectId, project);

        return Optional.of(project.getApp().getWebhooks());
    }

    @Override
    public boolean repeat() {
        return true;
    }
}
