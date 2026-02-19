package io.klustr.console.jobs.billing;

import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.console.storage.Storage;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO this should run as n8n instead
 */
@Component
public class BillingScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(BillingScheduledTask.class);


    private final BillingUsageApi billingApi; //

    private final BillingCustomerApi customerApi;

    private final Storage storage;

    private final List<BillableResource> hourResources;
    private final List<BillableResource> dayResources;

    public BillingScheduledTask(List<HourlyBillableResource> hourResources,
                                List<DailyBillableResource> dayResources,
                                Storage storage,
                                BillingUsageApi billingApi,
                                BillingCustomerApi customerApi) {
        this.storage = storage;
        this.billingApi = billingApi;
        this.customerApi = customerApi;
        this.hourResources = hourResources.stream().map(x -> (BillableResource)x).collect(Collectors.toList());
        this.dayResources = dayResources.stream().map(x -> (BillableResource)x).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 1000 * 60 * 10, initialDelay = 5 * 1000)
    public void hourly() {
        this.run("hour", this.hourResources);
    }

    @Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 5 * 1000)
    public void daily() {
        this.run("day", this.dayResources);
    }

    private void run(String type, List<BillableResource> resources) {
        log.info("Running {} operation on {} resources", type, resources.size());
        try {
            List<String> ids = this.storage.projects().listObjectIds(Db.all(), Pagination.all()).docs;
            ids.forEach(id -> {
                Optional<Project> project = this.storage.projects().tryGetObject(id);
                if (project.isEmpty()) return;

                String org_id = project.get().getOrgId();
                Org org = this.storage.organizations().getObject(org_id);

                for (BillableResource r : resources) {
                    log.info("Project {} running usage billing {}", project.get().getId(), r.getClass().getName());
                    r.bill(project.get(), billingApi);
                }
            });
        } catch (Exception ex) {
            log.error("Could not process billing", ex);
        }
    }
}
