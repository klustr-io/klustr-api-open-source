
package io.klustr.schemas.console.features.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ObservabilitySpec
 * <p>
 * Observability configuration for metrics and health checks.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "endpoints"
})
@Generated("jsonschema2pojo")
public class ObservabilitySpec {

    /**
     * Metrics or health endpoints.
     * 
     */
    @JsonProperty("endpoints")
    @JsonPropertyDescription("Metrics or health endpoints.")
    private List<Endpoint> endpoints = new ArrayList<Endpoint>();

    /**
     * Metrics or health endpoints.
     * 
     */
    @JsonProperty("endpoints")
    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    /**
     * Metrics or health endpoints.
     * 
     */
    @JsonProperty("endpoints")
    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public ObservabilitySpec withEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

}
