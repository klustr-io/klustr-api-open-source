
package io.klustr.schemas.console.features;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VolumePriceRange
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "start",
    "stop",
    "per_unit_price_usd",
    "flat_fee"
})
@Generated("jsonschema2pojo")
public class VolumePriceRange {

    @JsonProperty("start")
    private Double start;
    @JsonProperty("stop")
    private Double stop;
    @JsonProperty("per_unit_price_usd")
    private Double perUnitPriceUsd;
    @JsonProperty("flat_fee")
    private Double flatFee;

    @JsonProperty("start")
    public Double getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Double start) {
        this.start = start;
    }

    public VolumePriceRange withStart(Double start) {
        this.start = start;
        return this;
    }

    @JsonProperty("stop")
    public Double getStop() {
        return stop;
    }

    @JsonProperty("stop")
    public void setStop(Double stop) {
        this.stop = stop;
    }

    public VolumePriceRange withStop(Double stop) {
        this.stop = stop;
        return this;
    }

    @JsonProperty("per_unit_price_usd")
    public Double getPerUnitPriceUsd() {
        return perUnitPriceUsd;
    }

    @JsonProperty("per_unit_price_usd")
    public void setPerUnitPriceUsd(Double perUnitPriceUsd) {
        this.perUnitPriceUsd = perUnitPriceUsd;
    }

    public VolumePriceRange withPerUnitPriceUsd(Double perUnitPriceUsd) {
        this.perUnitPriceUsd = perUnitPriceUsd;
        return this;
    }

    @JsonProperty("flat_fee")
    public Double getFlatFee() {
        return flatFee;
    }

    @JsonProperty("flat_fee")
    public void setFlatFee(Double flatFee) {
        this.flatFee = flatFee;
    }

    public VolumePriceRange withFlatFee(Double flatFee) {
        this.flatFee = flatFee;
        return this;
    }

}
