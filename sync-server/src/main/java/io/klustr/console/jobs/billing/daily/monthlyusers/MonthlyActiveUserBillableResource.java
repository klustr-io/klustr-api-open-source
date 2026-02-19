package io.klustr.console.jobs.billing.daily.monthlyusers;

import com.google.common.collect.Sets;
import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEvent;
import io.klustr.billing.models.BillingEventForProjectMonthlyActiveUser;
import io.klustr.billing.models.BillingKey;
import io.klustr.console.jobs.billing.DailyBillableResource;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.stats.ProjectUserReference;
import io.klustr.schemas.console.stats.ProjectUsers;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Will charge people for usage of APIs using prometheus
 * adapter to get the metrics for the specific project. Requires
 * prometheus to be reporting this. We expect this to be called
 * every minute.
 */
@Component
public class MonthlyActiveUserBillableResource implements DailyBillableResource {

    private static final Logger log = LoggerFactory.getLogger(MonthlyActiveUserBillableResource.class);

    private final Storage storage;

    public MonthlyActiveUserBillableResource(Storage storage) {
        this.storage = storage;
    }

    public void bill(Project project, BillingUsageApi usageApi) {
        // find all users for a given project for the current day
        DbQuery dbQuery = Db.query("project_id").eq(project.getId());
        DocumentResult<ProjectUsers> users = storage.project_users().insecureQuery(dbQuery, Pagination.all());
        Set<String> activeMonthlyUsers = Sets.newHashSet();
        users.docs.forEach(rec -> {
            activeMonthlyUsers.addAll(rec.getUsers().stream().filter(x -> {
                return x.getLastAccess().isAfter(DateTime.now().withTimeAtStartOfDay().withDayOfMonth(1));
            }).map(ProjectUserReference::getId).collect(Collectors.toSet()));
        });
        if (activeMonthlyUsers.isEmpty()) {
            return;
        }
        log.info("Billing Lago account {} for MAU for project {} with {} users", project.getOrgId(), project.getId(), activeMonthlyUsers.size());
        billApiUsage(usageApi, project.getOrgId(), project.getId(), activeMonthlyUsers);
    }

    private void billApiUsage(BillingUsageApi usageApi, String orgId, String projectId, Set<String> users) {

        DateTime now = DateTime.now();

        for (String userId : users) {
            BillingEvent event = new BillingEventForProjectMonthlyActiveUser(new BillingKey(orgId, projectId), userId, now);
            try {
                usageApi.recordUsage(projectId, event);
            } catch (Exception ex) {
                log.warn("Billing Lago for MAU for project {} with {} users", projectId, users.size(), ex);
            }
        }
    }
}
