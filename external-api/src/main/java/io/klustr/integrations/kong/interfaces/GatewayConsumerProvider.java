package io.klustr.integrations.kong.interfaces;

import io.klustr.integrations.kong.models.KongConsumer;
import io.klustr.integrations.kong.models.KongConsumerRequest;

import java.util.Optional;

public interface GatewayConsumerProvider {
    KongConsumer createConsumer(KongConsumerRequest req);
    Optional<KongConsumer> getCustomer(String consumerId);
    void deleteConsumer(String consumerId);
    void enableServiceForConsumer(String consumerId, String serviceId);
    EnabledServices getEnabledServicesForConsumer(String consumerId);
    void disableServiceForConsumer(String consumerId, String serviceId);
}
