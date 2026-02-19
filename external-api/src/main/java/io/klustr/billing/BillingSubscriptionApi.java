package io.klustr.billing;

import io.klustr.schemas.console.billing.BillingSubscription;

import java.util.Optional;

public interface BillingSubscriptionApi {
    /**
     * Registers a customer that owns a subscription. One subscription could be
     * for a tier or other type of access.
     */
    void createSubscription(String external_id, BillingSubscription subscription);

    void updateSubscriptionName(String external_id, String name);

    Optional<BillingSubscription> getSubscription(String externalId);
}
