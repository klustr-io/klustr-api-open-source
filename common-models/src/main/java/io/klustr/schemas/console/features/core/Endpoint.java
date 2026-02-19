
package io.klustr.schemas.console.features.core;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "labels"
})
@Generated("jsonschema2pojo")
public class Endpoint {

    /**
     * Endpoint URL.
     * (Required)
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("Endpoint URL.")
    private String url;
    /**
     * Static labels for metrics.
     * 
     */
    @JsonProperty("labels")
    @JsonPropertyDescription("Static labels for metrics.")
    private Labels labels;

    /**
     * Endpoint URL.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * Endpoint URL.
     * (Required)
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public Endpoint withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Static labels for metrics.
     * 
     */
    @JsonProperty("labels")
    public Labels getLabels() {
        return labels;
    }

    /**
     * Static labels for metrics.
     * 
     */
    @JsonProperty("labels")
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public Endpoint withLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

}
