
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


/**
 * BillingMetric
 * <p>
 * This object represents a billable metric used to define how incoming events are aggregated in order to measure consumption.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "invoice_display_name",
    "code",
    "recurring",
    "field_name",
    "aggregation_type"
})
@Generated("jsonschema2pojo")
public class BillingMetric {

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
     * Defines if the billable metric is persisted billing period over billing period.
     * 
     * If set to true: the accumulated number of units calculated from the previous billing period is persisted to the next billing period.
     * If set to false: the accumulated number of units is reset to 0 at the end of the billing period.
     * If not defined in the request, default value is false.
     * 
     */
    @JsonProperty("recurring")
    @JsonPropertyDescription("Defines if the billable metric is persisted billing period over billing period.\n\nIf set to true: the accumulated number of units calculated from the previous billing period is persisted to the next billing period.\nIf set to false: the accumulated number of units is reset to 0 at the end of the billing period.\nIf not defined in the request, default value is false.")
    private Boolean recurring;
    /**
     * Property of the billable metric used for aggregating usage data. This field is not required for count_agg.
     * 
     */
    @JsonProperty("field_name")
    @JsonPropertyDescription("Property of the billable metric used for aggregating usage data. This field is not required for count_agg.")
    private String fieldName;
    /**
     * Aggregation method used to compute usage for this billable metric.
     * 
     */
    @JsonProperty("aggregation_type")
    @JsonPropertyDescription("Aggregation method used to compute usage for this billable metric.")
    private BillingMetric.AggregationType aggregationType;

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

    public BillingMetric withId(String id) {
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

    public BillingMetric withName(String name) {
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

    public BillingMetric withDescription(String description) {
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

    public BillingMetric withInvoiceDisplayName(String invoiceDisplayName) {
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

    public BillingMetric withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Defines if the billable metric is persisted billing period over billing period.
     * 
     * If set to true: the accumulated number of units calculated from the previous billing period is persisted to the next billing period.
     * If set to false: the accumulated number of units is reset to 0 at the end of the billing period.
     * If not defined in the request, default value is false.
     * 
     */
    @JsonProperty("recurring")
    public Boolean getRecurring() {
        return recurring;
    }

    /**
     * Defines if the billable metric is persisted billing period over billing period.
     * 
     * If set to true: the accumulated number of units calculated from the previous billing period is persisted to the next billing period.
     * If set to false: the accumulated number of units is reset to 0 at the end of the billing period.
     * If not defined in the request, default value is false.
     * 
     */
    @JsonProperty("recurring")
    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public BillingMetric withRecurring(Boolean recurring) {
        this.recurring = recurring;
        return this;
    }

    /**
     * Property of the billable metric used for aggregating usage data. This field is not required for count_agg.
     * 
     */
    @JsonProperty("field_name")
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Property of the billable metric used for aggregating usage data. This field is not required for count_agg.
     * 
     */
    @JsonProperty("field_name")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public BillingMetric withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    /**
     * Aggregation method used to compute usage for this billable metric.
     * 
     */
    @JsonProperty("aggregation_type")
    public BillingMetric.AggregationType getAggregationType() {
        return aggregationType;
    }

    /**
     * Aggregation method used to compute usage for this billable metric.
     * 
     */
    @JsonProperty("aggregation_type")
    public void setAggregationType(BillingMetric.AggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }

    public BillingMetric withAggregationType(BillingMetric.AggregationType aggregationType) {
        this.aggregationType = aggregationType;
        return this;
    }


    /**
     * Aggregation method used to compute usage for this billable metric.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AggregationType {

        COUNT_AGG("count_agg"),
        SUM_AGG("sum_agg"),
        MAX_AGG("max_agg"),
        UNIQUE_COUNT_AGG("unique_count_agg"),
        WEIGHTED_SUM_AGG("weighted_sum_agg"),
        LATEST_AGG("latest_agg");
        private final String value;
        private final static Map<String, BillingMetric.AggregationType> CONSTANTS = new HashMap<String, BillingMetric.AggregationType>();

        static {
            for (BillingMetric.AggregationType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AggregationType(String value) {
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
        public static BillingMetric.AggregationType fromValue(String value) {
            BillingMetric.AggregationType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
