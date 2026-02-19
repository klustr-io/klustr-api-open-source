package io.klustr.console.kafka;

import io.klustr.integrations.svix.WebhookProvider;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.UserPairwiseIds;
import io.klustr.schemas.console.projects.Project;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.schemas.console.webhooks.Payload;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import io.klustr.schemas.console.webhooks.WebhookMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
public class KafkaUserApplicationConsentListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaUserApplicationConsentListener.class);
    private final Storage storage;
    private final WebhookProvider webhooks;

    public KafkaUserApplicationConsentListener(Storage storage,
                                               HydraApi hydra,
                                               KafkaTemplate<UserApplicationConsent> kafka,
                                               WebhookProvider webhooks,
                                               @Value(value = "${consent.listener.kafka.consumer}") String groupId) {
        log.info("Starting kafka user application consent listener");

        this.storage = storage;
        this.webhooks = webhooks;

        kafka.listen(groupId).progress(this::update);
    }

    public void update(ConsumerRecord<String, UserApplicationConsent> record) {
        UserApplicationConsent payload = record.value();
        String projectId = payload.getProjectId();
        String client_id = payload.getClientId();

        if (StringUtils.isBlank(projectId)) {
            log.error("Client " + projectId + " is missing a project metadata attribute.");
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Received consent for project " + projectId + " with user " + payload.getSubjectId());
        }

        // send notification to any webhooks
        Project project = this.storage.projects().getObject(projectId);
        if (project.getApp() != null && project.getApp().getWebhooks() != null && project.getApp().getWebhooks().getEndpoints() != null) {
            project.getApp().getWebhooks().getEndpoints().forEach(hook -> {
                Optional<WebhookExternalId> projectExternalId = this.webhooks.getProjectExternalId(projectId);
                if (projectExternalId.isEmpty()) {
                    return;
                }
                String action = record.key().split(":")[1];
                if (action.contains("delete")) {
                    record.value().getScopes().forEach(x -> x.setStatus(UserConsentScope.Status.REVOKED));
                }

                String eventType = "user.app.consent." + action;

                // TODO refactor as this is common in multiple places
                // maybe make it examined the JSON and check for "subject_id" and then
                // automatically map it.

                // check if the client should use pairwise
                Optional<OIDCClient> client = project.getClients().stream().filter(x -> x.getClientId().equalsIgnoreCase(client_id)).findFirst();
                if (client.isEmpty()) {
                    log.error("Client {} not found in project {}", project.getId(), client_id);
                    return;
                }
                // pairwise mapping to ensure webhooks get the pairwise ID not the real id.
                if (client.get().getSubjectType() == SubjectType.PAIRWISE) {
                    String key = client.get().getClientId() + ":" + payload.getSubjectId();
                    Optional<UserPairwiseIds> ppid = this.storage.user_pairwise_ids().tryGetObject(key);
                    if (ppid.isEmpty()) {
                        // can't send this as the client has been configured
                        // to disable real ID.
                        log.error("Pairwise identifier has not been mapped for client {} for subject {}", client.get().getClientId(), payload.getSubjectId());
                        return;
                    }
                    payload.setSubjectId(ppid.get().getPairwiseId());
                }

                this.webhooks.createMessage(projectExternalId.get(), new WebhookMessage()
                        .withEventId(UUID.randomUUID().toString())
                        .withEventType(eventType)
                        .withPayloadRetentionPeriod(7)
                        .withPayload(new Payload()
                                .withData(payload)
                                .withEventType(eventType)
                        )
                );
            });
        }
    }
}
