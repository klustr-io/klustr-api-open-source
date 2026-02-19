
package io.klustr.schemas.console.billing;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BillingPlanChargePackagePricing
 * <p>
 * Pricing based on a package which simple offers, a base rate, with some amount of charges per bundle. This enables scenarios such as charging $0.00 for the first 3 users, but $10 for the next.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "free_units",
    "package_size"
})
@Generated("jsonschema2pojo")
public class BillingPlanChargePackagePricing {

    /**
     * The amount to charge per package.
     * (Required)
     * 
     */
    @JsonProperty("amount")
    @JsonPropertyDescription("The amount to charge per package.")
    private Double amount;
    /**
     * The total free units included with the package.
     * (Required)
     * 
     */
    @JsonProperty("free_units")
    @JsonPropertyDescription("The total free units included with the package.")
    private Integer freeUnits;
    /**
     * The package size that is billed, for example if (1) then charged each, if (10) will be charged per 10 units.
     * (Required)
     * 
     */
    @JsonProperty("package_size")
    @JsonPropertyDescription("The package size that is billed, for example if (1) then charged each, if (10) will be charged per 10 units.")
    private Integer packageSize;

    /**
     * The amount to charge per package.
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    /**
     * The amount to charge per package.
     * (Required)
     * 
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BillingPlanChargePackagePricing withAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * The total free units included with the package.
     * (Required)
     * 
     */
    @JsonProperty("free_units")
    public Integer getFreeUnits() {
        return freeUnits;
    }

    /**
     * The total free units included with the package.
     * (Required)
     * 
     */
    @JsonProperty("free_units")
    public void setFreeUnits(Integer freeUnits) {
        this.freeUnits = freeUnits;
    }

    public BillingPlanChargePackagePricing withFreeUnits(Integer freeUnits) {
        this.freeUnits = freeUnits;
        return this;
    }

    /**
     * The package size that is billed, for example if (1) then charged each, if (10) will be charged per 10 units.
     * (Required)
     * 
     */
    @JsonProperty("package_size")
    public Integer getPackageSize() {
        return packageSize;
    }

    /**
     * The package size that is billed, for example if (1) then charged each, if (10) will be charged per 10 units.
     * (Required)
     * 
     */
    @JsonProperty("package_size")
    public void setPackageSize(Integer packageSize) {
        this.packageSize = packageSize;
    }

    public BillingPlanChargePackagePricing withPackageSize(Integer packageSize) {
        this.packageSize = packageSize;
        return this;
    }

}
