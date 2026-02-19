package io.klustr.billing.models;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class BillingEvent {

    /**
     * The transaction ID to uniquely identify this, use this as YYYY-MM-DD
     * for when you are aggregating billing by day. And send over and over.
     */
    public String transaction_id;

    /**
     * Maps to billable item that was registered in Lago.
     */
    public abstract String getLagoMappedBillingCode();

    /**
     * Maps to project ID
     */
    public String external_subscription_id;


    /**
     * The timestamp of this event to bill against.
     */
    public Long timestamp;

    /**
     * Properties which map to what we bill, this will be
     * http_requests_total.
     */
    public Map<String, Object> properties = Maps.newConcurrentMap();
}
