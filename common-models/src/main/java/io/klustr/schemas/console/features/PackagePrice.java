
package io.klustr.schemas.console.features;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PackagePrice
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "billable_metric_codes",
    "free_units",
    "amount_usd",
    "package_size"
})
@Generated("jsonschema2pojo")
public class PackagePrice {

    @JsonProperty("billable_metric_codes")
    private List<String> billableMetricCodes = new ArrayList<String>();
    @JsonProperty("free_units")
    private Double freeUnits;
    @JsonProperty("amount_usd")
    private Double amountUsd;
    @JsonProperty("package_size")
    private Double packageSize;

    @JsonProperty("billable_metric_codes")
    public List<String> getBillableMetricCodes() {
        return billableMetricCodes;
    }

    @JsonProperty("billable_metric_codes")
    public void setBillableMetricCodes(List<String> billableMetricCodes) {
        this.billableMetricCodes = billableMetricCodes;
    }

    public PackagePrice withBillableMetricCodes(List<String> billableMetricCodes) {
        this.billableMetricCodes = billableMetricCodes;
        return this;
    }

    @JsonProperty("free_units")
    public Double getFreeUnits() {
        return freeUnits;
    }

    @JsonProperty("free_units")
    public void setFreeUnits(Double freeUnits) {
        this.freeUnits = freeUnits;
    }

    public PackagePrice withFreeUnits(Double freeUnits) {
        this.freeUnits = freeUnits;
        return this;
    }

    @JsonProperty("amount_usd")
    public Double getAmountUsd() {
        return amountUsd;
    }

    @JsonProperty("amount_usd")
    public void setAmountUsd(Double amountUsd) {
        this.amountUsd = amountUsd;
    }

    public PackagePrice withAmountUsd(Double amountUsd) {
        this.amountUsd = amountUsd;
        return this;
    }

    @JsonProperty("package_size")
    public Double getPackageSize() {
        return packageSize;
    }

    @JsonProperty("package_size")
    public void setPackageSize(Double packageSize) {
        this.packageSize = packageSize;
    }

    public PackagePrice withPackageSize(Double packageSize) {
        this.packageSize = packageSize;
        return this;
    }

}
