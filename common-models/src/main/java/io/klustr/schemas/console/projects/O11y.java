
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "endpoints"
})
@Generated("jsonschema2pojo")
public class O11y {

    @JsonProperty("endpoints")
    private List<O11yEndpoint> endpoints = new ArrayList<O11yEndpoint>();

    @JsonProperty("endpoints")
    public List<O11yEndpoint> getEndpoints() {
        return endpoints;
    }

    @JsonProperty("endpoints")
    public void setEndpoints(List<O11yEndpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public O11y withEndpoints(List<O11yEndpoint> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

}
