package io.klustr.console.kafka;


import io.klustr.console.repository.UserLoginTrackingRepository;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.integrations.svix.WebhookProvider;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.consent.ConsentAudit;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.UserPairwiseIds;
import io.klustr.schemas.console.projects.Project;
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
public class KafkaConsentAuditListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsentAuditListener.class);

    private final UserLoginTrackingRepository logins;

    private final Storage storage;
    private final WebhookProvider webhooks;

    public KafkaConsentAuditListener(Storage storage,
                                     HydraApi hydra,
                                     UserLoginTrackingRepository logins,
                                     KafkaTemplate<ConsentAudit> kafka,
                                     WebhookProvider webhooks,
                                     @Value(value = "${consent.listener.kafka.consumer}") String groupId) {
        log.info("Starting kafka consent audit listener");
        this.logins = logins;
        this.storage = storage;
        this.webhooks = webhooks;
        kafka.listen(groupId).progress(this::update);
    }

    public void update(ConsumerRecord<String, ConsentAudit> record) {
        ConsentAudit payload = record.value();
        String uid = payload.getSubjectId();
        String projectId = payload.getProjectId();
        if (StringUtils.isBlank(projectId)) {
            log.error("Client " + projectId + " is missing a project metadata attribute.");
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Received consent for project " + projectId + " with user " + uid);
        }

        String client_id = payload.getClientId();
        this.logins.trackUserSignIn(projectId, client_id, uid);

        // send notification to any webhooks
        Project project = this.storage.projects().getObject(projectId);
        if (project.getApp() != null && project.getApp().getWebhooks() != null && project.getApp().getWebhooks().getEndpoints() != null) {
            project.getApp().getWebhooks().getEndpoints().forEach(hook -> {
                Optional<WebhookExternalId> projectExternalId = this.webhooks.getProjectExternalId(projectId);
                if (projectExternalId.isEmpty()) {
                    return;
                }
                String eventType = "user.consent";

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
