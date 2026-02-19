package io.klustr.billing;

import com.lago.openapi.model.LagoCustomerUsageObject;
import io.klustr.billing.models.BillingEvent;
import io.klustr.billing.models.BillingKey;
import io.klustr.schemas.console.billing.BillingCustomer;

import java.util.Optional;

public interface BillingUsageApi {
    /**
     * Returns the current usage.
     */
    Optional<LagoCustomerUsageObject> getCurrentUsage(BillingKey key);

    /**
     * Records the usage of an API or call.
     */
    void recordUsage(String projectId, BillingEvent event);
}
