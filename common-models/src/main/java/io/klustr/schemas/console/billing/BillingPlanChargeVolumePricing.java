
package io.klustr.schemas.console.billing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BillingPlanChargeVolumePricing
 * <p>
 * Pricing that is determined by how much is consumed. Typically this offers discounts as you approach higher tiers.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "range"
})
@Generated("jsonschema2pojo")
public class BillingPlanChargeVolumePricing {

    /**
     * The range to define
     * 
     */
    @JsonProperty("range")
    @JsonPropertyDescription("The range to define")
    private List<BillingPlanChargeVolumePricingRange> range = new ArrayList<BillingPlanChargeVolumePricingRange>();

    /**
     * The range to define
     * 
     */
    @JsonProperty("range")
    public List<BillingPlanChargeVolumePricingRange> getRange() {
        return range;
    }

    /**
     * The range to define
     * 
     */
    @JsonProperty("range")
    public void setRange(List<BillingPlanChargeVolumePricingRange> range) {
        this.range = range;
    }

    public BillingPlanChargeVolumePricing withRange(List<BillingPlanChargeVolumePricingRange> range) {
        this.range = range;
        return this;
    }

}
