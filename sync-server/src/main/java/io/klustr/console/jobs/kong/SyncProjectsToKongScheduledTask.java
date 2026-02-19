package io.klustr.console.jobs.kong;

import io.klustr.console.storage.Storage;
import io.klustr.integrations.kong.interfaces.GatewayConsumerProvider;
import io.klustr.integrations.kong.models.KongConsumer;
import io.klustr.integrations.kong.models.KongConsumerRequest;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Projects sync to kong to enable subscriptions and activation
 * or deactivation of projects via ACLs.
 */
@Component
public class SyncProjectsToKongScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(SyncProjectsToKongScheduledTask.class);

    private LagoBillingAdapter adapter;

    private Storage storage;

    private GatewayConsumerProvider kong;

    public SyncProjectsToKongScheduledTask(Storage storage,
                                           GatewayConsumerProvider kong,
                                           LagoBillingAdapter adapter) {

        try {
            this.adapter = adapter;
            this.storage = storage;
            this.kong = kong;

            sync_projects_to_kong();
        } catch (Exception ex) {
            // ignore and eat for now
        }
    }

    @Scheduled(initialDelay = 1000 * 10, fixedRate = 1000 * 60 * 10)
    public void sync_projects_to_kong() {
        log.info("Sync Kong");
        List<String> projects = this.storage.projects().listObjectIds(Db.all(), Pagination.all()).docs;
        projects.forEach(x -> {
            Project project = this.storage.projects().getObject(x);
            KongConsumerRequest consumerRequest = new KongConsumerRequest();
            consumerRequest.tags.add("console");
            consumerRequest.username = project.getId();
            consumerRequest.custom_id = project.getId();
            Optional<KongConsumer> customer = this.kong.getCustomer(project.getId());
            if (customer.isEmpty()) {
                KongConsumer kongConsumer = this.kong.createConsumer(consumerRequest);
            } else {
                // do nothing for now...
            }
        });
    }
}
