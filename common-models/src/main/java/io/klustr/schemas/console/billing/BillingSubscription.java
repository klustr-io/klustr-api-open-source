
package io.klustr.schemas.console.billing;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * BillingSubscription
 * <p>
 * This object represents a plan. This plan can then be assigned to a customer.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "plan_code",
    "external_id",
    "external_customer_id",
    "ending_at",
    "subscription_at",
    "billing_time"
})
@Generated("jsonschema2pojo")
public class BillingSubscription {

    /**
     * The unique external identifier for the subscription. This identifier serves as an idempotency key, ensuring that each subscription is unique.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique external identifier for the subscription. This identifier serves as an idempotency key, ensuring that each subscription is unique.")
    private String id;
    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.")
    private String name;
    /**
     * The unique code representing the plan to be attached to the customer. This code must correspond to the code property of one of the active plans.
     * 
     */
    @JsonProperty("plan_code")
    @JsonPropertyDescription("The unique code representing the plan to be attached to the customer. This code must correspond to the code property of one of the active plans.")
    private String planCode;
    /**
     * The external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The external unique identifier (provided by your own application)")
    private String externalId;
    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_customer_id")
    @JsonPropertyDescription("The customer external unique identifier (provided by your own application)")
    private String externalCustomerId;
    /**
     * The effective end date of the subscription. If this field is set to null, the subscription will automatically renew. This date should be provided in ISO 8601 datetime format, and use Coordinated Universal Time (UTC).
     * 
     */
    @JsonProperty("ending_at")
    @JsonPropertyDescription("The effective end date of the subscription. If this field is set to null, the subscription will automatically renew. This date should be provided in ISO 8601 datetime format, and use Coordinated Universal Time (UTC).")
    private DateTime endingAt;
    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("subscription_at")
    @JsonPropertyDescription("The customer external unique identifier (provided by your own application)")
    private DateTime subscriptionAt;
    /**
     * The billing time for the subscription, which can be set as either anniversary or calendar. If not explicitly provided, it will default to calendar. The billing time determines the timing of recurring billing cycles for the subscription. By specifying anniversary, the billing cycle will be based on the specific date the subscription started (billed fully), while calendar sets the billing cycle at the first day of the week/month/year (billed with proration).
     * 
     */
    @JsonProperty("billing_time")
    @JsonPropertyDescription("The billing time for the subscription, which can be set as either anniversary or calendar. If not explicitly provided, it will default to calendar. The billing time determines the timing of recurring billing cycles for the subscription. By specifying anniversary, the billing cycle will be based on the specific date the subscription started (billed fully), while calendar sets the billing cycle at the first day of the week/month/year (billed with proration).")
    private BillingSubscription.BillingTime billingTime;

    /**
     * The unique external identifier for the subscription. This identifier serves as an idempotency key, ensuring that each subscription is unique.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique external identifier for the subscription. This identifier serves as an idempotency key, ensuring that each subscription is unique.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BillingSubscription withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public BillingSubscription withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The unique code representing the plan to be attached to the customer. This code must correspond to the code property of one of the active plans.
     * 
     */
    @JsonProperty("plan_code")
    public String getPlanCode() {
        return planCode;
    }

    /**
     * The unique code representing the plan to be attached to the customer. This code must correspond to the code property of one of the active plans.
     * 
     */
    @JsonProperty("plan_code")
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public BillingSubscription withPlanCode(String planCode) {
        this.planCode = planCode;
        return this;
    }

    /**
     * The external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * The external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public BillingSubscription withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_customer_id")
    public String getExternalCustomerId() {
        return externalCustomerId;
    }

    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("external_customer_id")
    public void setExternalCustomerId(String externalCustomerId) {
        this.externalCustomerId = externalCustomerId;
    }

    public BillingSubscription withExternalCustomerId(String externalCustomerId) {
        this.externalCustomerId = externalCustomerId;
        return this;
    }

    /**
     * The effective end date of the subscription. If this field is set to null, the subscription will automatically renew. This date should be provided in ISO 8601 datetime format, and use Coordinated Universal Time (UTC).
     * 
     */
    @JsonProperty("ending_at")
    public DateTime getEndingAt() {
        return endingAt;
    }

    /**
     * The effective end date of the subscription. If this field is set to null, the subscription will automatically renew. This date should be provided in ISO 8601 datetime format, and use Coordinated Universal Time (UTC).
     * 
     */
    @JsonProperty("ending_at")
    public void setEndingAt(DateTime endingAt) {
        this.endingAt = endingAt;
    }

    public BillingSubscription withEndingAt(DateTime endingAt) {
        this.endingAt = endingAt;
        return this;
    }

    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("subscription_at")
    public DateTime getSubscriptionAt() {
        return subscriptionAt;
    }

    /**
     * The customer external unique identifier (provided by your own application)
     * 
     */
    @JsonProperty("subscription_at")
    public void setSubscriptionAt(DateTime subscriptionAt) {
        this.subscriptionAt = subscriptionAt;
    }

    public BillingSubscription withSubscriptionAt(DateTime subscriptionAt) {
        this.subscriptionAt = subscriptionAt;
        return this;
    }

    /**
     * The billing time for the subscription, which can be set as either anniversary or calendar. If not explicitly provided, it will default to calendar. The billing time determines the timing of recurring billing cycles for the subscription. By specifying anniversary, the billing cycle will be based on the specific date the subscription started (billed fully), while calendar sets the billing cycle at the first day of the week/month/year (billed with proration).
     * 
     */
    @JsonProperty("billing_time")
    public BillingSubscription.BillingTime getBillingTime() {
        return billingTime;
    }

    /**
     * The billing time for the subscription, which can be set as either anniversary or calendar. If not explicitly provided, it will default to calendar. The billing time determines the timing of recurring billing cycles for the subscription. By specifying anniversary, the billing cycle will be based on the specific date the subscription started (billed fully), while calendar sets the billing cycle at the first day of the week/month/year (billed with proration).
     * 
     */
    @JsonProperty("billing_time")
    public void setBillingTime(BillingSubscription.BillingTime billingTime) {
        this.billingTime = billingTime;
    }

    public BillingSubscription withBillingTime(BillingSubscription.BillingTime billingTime) {
        this.billingTime = billingTime;
        return this;
    }


    /**
     * The billing time for the subscription, which can be set as either anniversary or calendar. If not explicitly provided, it will default to calendar. The billing time determines the timing of recurring billing cycles for the subscription. By specifying anniversary, the billing cycle will be based on the specific date the subscription started (billed fully), while calendar sets the billing cycle at the first day of the week/month/year (billed with proration).
     * 
     */
    @Generated("jsonschema2pojo")
    public enum BillingTime {

        CALENDAR("calendar"),
        ANNIVERSARY("anniversary");
        private final String value;
        private final static Map<String, BillingSubscription.BillingTime> CONSTANTS = new HashMap<String, BillingSubscription.BillingTime>();

        static {
            for (BillingSubscription.BillingTime c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        BillingTime(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static BillingSubscription.BillingTime fromValue(String value) {
            BillingSubscription.BillingTime constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
