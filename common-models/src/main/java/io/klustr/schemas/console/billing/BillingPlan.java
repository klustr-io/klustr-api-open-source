
package io.klustr.schemas.console.billing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.klustr.schemas.console.CurrencyCode;


/**
 * BillingPlan
 * <p>
 * This object represents a plan. This plan can then be assigned to a customer.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "invoice_display_name",
    "code",
    "interval",
    "amount_cents",
    "amount_currency",
    "trial_period",
    "charges"
})
@Generated("jsonschema2pojo")
public class BillingPlan {

    /**
     * The ID of this customer account
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The ID of this customer account")
    private String id;
    /**
     * A friendly name for this plan.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A friendly name for this plan.")
    private String name;
    /**
     * A friendly description for this plan.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("A friendly description for this plan.")
    private String description;
    /**
     * Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.
     * 
     */
    @JsonProperty("invoice_display_name")
    @JsonPropertyDescription("Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.")
    private String invoiceDisplayName;
    /**
     * The code of the plan. It serves as a unique identifier associated with a particular plan. The code is typically used for internal or system-level identification purposes, like assigning a subscription, for instance.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The code of the plan. It serves as a unique identifier associated with a particular plan. The code is typically used for internal or system-level identification purposes, like assigning a subscription, for instance.")
    private String code;
    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("interval")
    @JsonPropertyDescription("The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.")
    private BillingPlan.Interval interval;
    /**
     * The base cost of the plan, excluding any applicable taxes, that is billed on a recurring basis. This value is defined at 0 if your plan is a pay-as-you-go plan.
     * 
     */
    @JsonProperty("amount_cents")
    @JsonPropertyDescription("The base cost of the plan, excluding any applicable taxes, that is billed on a recurring basis. This value is defined at 0 if your plan is a pay-as-you-go plan.")
    private Integer amountCents = 0;
    /**
     * CurrencyCode
     * <p>
     * The plan for this billing
     * 
     */
    @JsonProperty("amount_currency")
    @JsonPropertyDescription("The plan for this billing")
    private CurrencyCode amountCurrency;
    /**
     * The duration in days during which the base cost of the plan is offered for free.
     * 
     */
    @JsonProperty("trial_period")
    @JsonPropertyDescription("The duration in days during which the base cost of the plan is offered for free.")
    private Integer trialPeriod;
    /**
     * BillingPlanCharges
     * <p>
     * 
     * 
     */
    @JsonProperty("charges")
    private List<BillingPlanUsagePrice> charges = new ArrayList<BillingPlanUsagePrice>();

    /**
     * The ID of this customer account
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The ID of this customer account
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BillingPlan withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * A friendly name for this plan.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * A friendly name for this plan.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public BillingPlan withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * A friendly description for this plan.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * A friendly description for this plan.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public BillingPlan withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.
     * 
     */
    @JsonProperty("invoice_display_name")
    public String getInvoiceDisplayName() {
        return invoiceDisplayName;
    }

    /**
     * Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.
     * 
     */
    @JsonProperty("invoice_display_name")
    public void setInvoiceDisplayName(String invoiceDisplayName) {
        this.invoiceDisplayName = invoiceDisplayName;
    }

    public BillingPlan withInvoiceDisplayName(String invoiceDisplayName) {
        this.invoiceDisplayName = invoiceDisplayName;
        return this;
    }

    /**
     * The code of the plan. It serves as a unique identifier associated with a particular plan. The code is typically used for internal or system-level identification purposes, like assigning a subscription, for instance.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The code of the plan. It serves as a unique identifier associated with a particular plan. The code is typically used for internal or system-level identification purposes, like assigning a subscription, for instance.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public BillingPlan withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("interval")
    public BillingPlan.Interval getInterval() {
        return interval;
    }

    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("interval")
    public void setInterval(BillingPlan.Interval interval) {
        this.interval = interval;
    }

    public BillingPlan withInterval(BillingPlan.Interval interval) {
        this.interval = interval;
        return this;
    }

    /**
     * The base cost of the plan, excluding any applicable taxes, that is billed on a recurring basis. This value is defined at 0 if your plan is a pay-as-you-go plan.
     * 
     */
    @JsonProperty("amount_cents")
    public Integer getAmountCents() {
        return amountCents;
    }

    /**
     * The base cost of the plan, excluding any applicable taxes, that is billed on a recurring basis. This value is defined at 0 if your plan is a pay-as-you-go plan.
     * 
     */
    @JsonProperty("amount_cents")
    public void setAmountCents(Integer amountCents) {
        this.amountCents = amountCents;
    }

    public BillingPlan withAmountCents(Integer amountCents) {
        this.amountCents = amountCents;
        return this;
    }

    /**
     * CurrencyCode
     * <p>
     * The plan for this billing
     * 
     */
    @JsonProperty("amount_currency")
    public CurrencyCode getAmountCurrency() {
        return amountCurrency;
    }

    /**
     * CurrencyCode
     * <p>
     * The plan for this billing
     * 
     */
    @JsonProperty("amount_currency")
    public void setAmountCurrency(CurrencyCode amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public BillingPlan withAmountCurrency(CurrencyCode amountCurrency) {
        this.amountCurrency = amountCurrency;
        return this;
    }

    /**
     * The duration in days during which the base cost of the plan is offered for free.
     * 
     */
    @JsonProperty("trial_period")
    public Integer getTrialPeriod() {
        return trialPeriod;
    }

    /**
     * The duration in days during which the base cost of the plan is offered for free.
     * 
     */
    @JsonProperty("trial_period")
    public void setTrialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public BillingPlan withTrialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
        return this;
    }

    /**
     * BillingPlanCharges
     * <p>
     * 
     * 
     */
    @JsonProperty("charges")
    public List<BillingPlanUsagePrice> getCharges() {
        return charges;
    }

    /**
     * BillingPlanCharges
     * <p>
     * 
     * 
     */
    @JsonProperty("charges")
    public void setCharges(List<BillingPlanUsagePrice> charges) {
        this.charges = charges;
    }

    public BillingPlan withCharges(List<BillingPlanUsagePrice> charges) {
        this.charges = charges;
        return this;
    }


    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Interval {

        YEARLY("yearly"),
        QUARTERLY("quarterly"),
        MONTHLY("monthly"),
        WEEKLY("weekly");
        private final String value;
        private final static Map<String, BillingPlan.Interval> CONSTANTS = new HashMap<String, BillingPlan.Interval>();

        static {
            for (BillingPlan.Interval c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Interval(String value) {
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
        public static BillingPlan.Interval fromValue(String value) {
            BillingPlan.Interval constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
