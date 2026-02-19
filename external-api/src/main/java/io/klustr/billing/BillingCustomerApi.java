package io.klustr.billing;

import io.klustr.schemas.console.billing.BillingCustomer;

import java.util.Optional;

public interface BillingCustomerApi {
    Optional<BillingCustomer> getCustomer(String code);

    Optional<BillingCustomer> createCustomer(BillingCustomer obj);

    Optional<BillingCustomer> updateCustomer(BillingCustomer obj);
}
