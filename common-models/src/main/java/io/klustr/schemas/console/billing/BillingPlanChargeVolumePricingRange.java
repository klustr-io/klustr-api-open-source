
package io.klustr.schemas.console.billing;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BillingPlanChargeVolumePricingRange
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "start",
    "stop",
    "per_unit_price",
    "flat_fee"
})
@Generated("jsonschema2pojo")
public class BillingPlanChargeVolumePricingRange {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("start")
    private Integer start;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stop")
    private Integer stop;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("per_unit_price")
    private Double perUnitPrice;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("flat_fee")
    private Double flatFee;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    public BillingPlanChargeVolumePricingRange withStart(Integer start) {
        this.start = start;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stop")
    public Integer getStop() {
        return stop;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("stop")
    public void setStop(Integer stop) {
        this.stop = stop;
    }

    public BillingPlanChargeVolumePricingRange withStop(Integer stop) {
        this.stop = stop;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("per_unit_price")
    public Double getPerUnitPrice() {
        return perUnitPrice;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("per_unit_price")
    public void setPerUnitPrice(Double perUnitPrice) {
        this.perUnitPrice = perUnitPrice;
    }

    public BillingPlanChargeVolumePricingRange withPerUnitPrice(Double perUnitPrice) {
        this.perUnitPrice = perUnitPrice;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("flat_fee")
    public Double getFlatFee() {
        return flatFee;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("flat_fee")
    public void setFlatFee(Double flatFee) {
        this.flatFee = flatFee;
    }

    public BillingPlanChargeVolumePricingRange withFlatFee(Double flatFee) {
        this.flatFee = flatFee;
        return this;
    }

}
