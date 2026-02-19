package io.klustr.console.jobs.lago;

import io.klustr.console.storage.Storage;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.integrations.lago.LagoConverter;
import io.klustr.schemas.console.billing.BillingCustomer;
import io.klustr.schemas.console.billing.BillingSubscription;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SyncOrgsAndProjectsToLagoScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(SyncOrgsAndProjectsToLagoScheduledTask.class);
    private LagoConfiguration config;

    private final LagoBillingAdapter billing;

    private final Storage storage;

    public SyncOrgsAndProjectsToLagoScheduledTask(Storage storage, LagoBillingAdapter billing) {
        this.storage = storage;
        this.billing = billing;
    }

    @Scheduled(initialDelay = 1000 * 5, fixedRate = 1000 * 60 * 10)
    public void sync_accounts_to_billing() {
        log.info("Ensure Organizations Exist in Lago");
        List<String> orgs = this.storage.organizations().listObjectIds(Db.all(), Pagination.all()).docs;
        orgs.forEach(id -> {
            Org org = this.storage.organizations().getObject(id);
            BillingCustomer customer = LagoConverter.toCustomer(org);
            if (this.billing.getCustomer(org.getId()).isEmpty()) {
                this.billing.createCustomer(customer);
            } else {
                this.billing.updateCustomer(customer);
            }
        });

        log.info("Sync Projects to Lago");
        List<String> projects = this.storage.projects().listObjectIds(Db.all(), Pagination.all()).docs;

        projects.forEach(x -> {
            Project project = this.storage.projects().getObject(x);
            BillingSubscription subscription = new BillingSubscription()
                    .withSubscriptionAt(DateTime.now().withTimeAtStartOfDay().minusDays(2))
                    .withBillingTime(BillingSubscription.BillingTime.CALENDAR)
                    .withPlanCode(project.getPlan() != null ? project.getPlan() : "basic-plan")
                    .withExternalCustomerId(project.getOrgId())
                    .withExternalId(project.getId())
                    .withId(project.getId())
                    .withName("Project " + project.getId())
                    .withBillingTime(BillingSubscription.BillingTime.CALENDAR);
            if (StringUtils.isNotBlank(project.getOrgId())) {
                this.billing.createSubscription(project.getOrgId(), subscription);
            }
            this.billing.updateSubscriptionName(project.getId(), "Project " + project.getId());
        });
    }
}
