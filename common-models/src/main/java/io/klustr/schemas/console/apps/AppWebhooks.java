
package io.klustr.schemas.console.apps;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AppWebhooks
 * <p>
 * Webhooks configured for this application to apply to events generated in the system.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "endpoints"
})
@Generated("jsonschema2pojo")
public class AppWebhooks {

    /**
     * The endpoints configured for this application
     * 
     */
    @JsonProperty("endpoints")
    @JsonPropertyDescription("The endpoints configured for this application")
    private List<WebhookEndpoint> endpoints = new ArrayList<WebhookEndpoint>();

    /**
     * The endpoints configured for this application
     * 
     */
    @JsonProperty("endpoints")
    public List<WebhookEndpoint> getEndpoints() {
        return endpoints;
    }

    /**
     * The endpoints configured for this application
     * 
     */
    @JsonProperty("endpoints")
    public void setEndpoints(List<WebhookEndpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public AppWebhooks withEndpoints(List<WebhookEndpoint> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

}
