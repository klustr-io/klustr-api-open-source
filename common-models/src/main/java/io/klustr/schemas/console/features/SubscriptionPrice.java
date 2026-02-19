
package io.klustr.schemas.console.features;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * SubscriptionPrice
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price_usd",
    "label",
    "description"
})
@Generated("jsonschema2pojo")
public class SubscriptionPrice {

    @JsonProperty("price_usd")
    private Double priceUsd;
    @JsonProperty("label")
    private String label;
    @JsonProperty("description")
    private String description;

    @JsonProperty("price_usd")
    public Double getPriceUsd() {
        return priceUsd;
    }

    @JsonProperty("price_usd")
    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public SubscriptionPrice withPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
        return this;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    public SubscriptionPrice withLabel(String label) {
        this.label = label;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public SubscriptionPrice withDescription(String description) {
        this.description = description;
        return this;
    }

}
