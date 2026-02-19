package io.klustr.billing.models;

import io.klustr.schemas.console.billing.BillingMetric;

import java.util.Optional;

public interface BillingMetricApi {
    Optional<BillingMetric> getMetric(String code);

    void createMetric(BillingMetric metric);
}
