package io.klustr.console.jobs.billing;

import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;

public interface BillableResource {
    void bill(Project project,
              BillingUsageApi usageApi);
}
