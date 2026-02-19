package io.klustr.billing;

import io.klustr.schemas.console.billing.BillingPlan;

import java.util.Optional;

public interface BillingPlanApi {

    Optional<BillingPlan> getPlan(String code);

    void createPlan(BillingPlan plan);

    void updatePlan(BillingPlan plan);
}
