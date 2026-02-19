package io.klustr.integrations.svix;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.apps.WebhookEndpoint;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import io.klustr.schemas.console.webhooks.WebhookMessage;
import io.klustr.schemas.console.webhooks.WebhookMessageAudit;
import io.klustr.schemas.console.webhooks.WebhookProject;

import java.util.List;
import java.util.Optional;

public interface WebhookProvider {

    WebhookProject registerProject(WebhookProject project);

    Optional<WebhookProject> getProject(WebhookExternalId projectRef);

    List<WebhookEndpoint> createEndpoints(WebhookExternalId projectRef, List<WebhookEndpoint> endpoints);

    Optional<WebhookEndpoint> getEndpoint(WebhookExternalId projectRef, WebhookExternalId endpointRef);

    void deleteProject(WebhookExternalId projectRef);

    Optional<WebhookExternalId> getProjectExternalId(String projectId);

    void createMessage(WebhookExternalId projectRef, WebhookMessage message);

    Page<WebhookMessageAudit> listMessageAttempts(WebhookExternalId projectRef, WebhookExternalId endpointRef, Pagination pagination);

    public static class Page<T> {
        public List<T> docs = Lists.newArrayList();
        public String iterator;
        public Integer limit;
        public boolean done;
    }

    public static class Pagination {
        public String iterator;
        public Integer limit = 10;
        public Pagination() {}

        public Pagination withIterator(String iterator) {
            this.iterator = iterator;
            return this;
        }
        public  Pagination withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }
    }
}
