
package io.klustr.schemas.console.billing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BillingPlanUsagePrice
 * <p>
 * The specific usage pricing for this plan for a specific metric.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "invoice_display_name",
    "billable_metric_codes",
    "description",
    "package_pricing",
    "volume_pricing"
})
@Generated("jsonschema2pojo")
public class BillingPlanUsagePrice {

    /**
     * Unique identifier of the charge created by Lago.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Unique identifier of the charge created by Lago.")
    private String id;
    /**
     * Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.
     * 
     */
    @JsonProperty("invoice_display_name")
    @JsonPropertyDescription("Specifies the name that will be displayed on an invoice. If no value is set for this field, the name of the plan will be used as the default display name.")
    private String invoiceDisplayName;
    /**
     * The billable metrics codes that this plan is associate with.
     * 
     */
    @JsonProperty("billable_metric_codes")
    @JsonPropertyDescription("The billable metrics codes that this plan is associate with.")
    private List<String> billableMetricCodes = new ArrayList<String>();
    /**
     * A friendly description for this plan.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("A friendly description for this plan.")
    private String description;
    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("package_pricing")
    @JsonPropertyDescription("The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.")
    private BillingPlanChargePackagePricing packagePricing;
    /**
     * The pricing to use based on tiered volumes
     * 
     */
    @JsonProperty("volume_pricing")
    @JsonPropertyDescription("The pricing to use based on tiered volumes")
    private BillingPlanChargeVolumePricing volumePricing;

    /**
     * Unique identifier of the charge created by Lago.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Unique identifier of the charge created by Lago.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BillingPlanUsagePrice withId(String id) {
        this.id = id;
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

    public BillingPlanUsagePrice withInvoiceDisplayName(String invoiceDisplayName) {
        this.invoiceDisplayName = invoiceDisplayName;
        return this;
    }

    /**
     * The billable metrics codes that this plan is associate with.
     * 
     */
    @JsonProperty("billable_metric_codes")
    public List<String> getBillableMetricCodes() {
        return billableMetricCodes;
    }

    /**
     * The billable metrics codes that this plan is associate with.
     * 
     */
    @JsonProperty("billable_metric_codes")
    public void setBillableMetricCodes(List<String> billableMetricCodes) {
        this.billableMetricCodes = billableMetricCodes;
    }

    public BillingPlanUsagePrice withBillableMetricCodes(List<String> billableMetricCodes) {
        this.billableMetricCodes = billableMetricCodes;
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

    public BillingPlanUsagePrice withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("package_pricing")
    public BillingPlanChargePackagePricing getPackagePricing() {
        return packagePricing;
    }

    /**
     * The interval used for recurring billing. It represents the frequency at which subscription billing occurs. The interval can be one of the following values: yearly, quarterly, monthly or weekly.
     * 
     */
    @JsonProperty("package_pricing")
    public void setPackagePricing(BillingPlanChargePackagePricing packagePricing) {
        this.packagePricing = packagePricing;
    }

    public BillingPlanUsagePrice withPackagePricing(BillingPlanChargePackagePricing packagePricing) {
        this.packagePricing = packagePricing;
        return this;
    }

    /**
     * The pricing to use based on tiered volumes
     * 
     */
    @JsonProperty("volume_pricing")
    public BillingPlanChargeVolumePricing getVolumePricing() {
        return volumePricing;
    }

    /**
     * The pricing to use based on tiered volumes
     * 
     */
    @JsonProperty("volume_pricing")
    public void setVolumePricing(BillingPlanChargeVolumePricing volumePricing) {
        this.volumePricing = volumePricing;
    }

    public BillingPlanUsagePrice withVolumePricing(BillingPlanChargeVolumePricing volumePricing) {
        this.volumePricing = volumePricing;
        return this;
    }

}
