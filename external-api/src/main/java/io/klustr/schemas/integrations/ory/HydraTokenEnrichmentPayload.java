
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraTokenEnrichmentPayload
 * <p>
 * A mapping to the hydra webhook enrichment process. This does not contain all properties only a select few used for the purposes of group and enrichment.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "session",
    "request"
})
@Generated("jsonschema2pojo")
public class HydraTokenEnrichmentPayload {

    /**
     * HydraTokenEnrichmentPayloadSession
     * <p>
     * 
     * 
     */
    @JsonProperty("session")
    private HydraTokenEnrichmentPayloadSession session;
    /**
     * HydraTokenEnrichmentPayloadRequest
     * <p>
     * 
     * 
     */
    @JsonProperty("request")
    private HydraTokenEnrichmentPayloadRequest request;

    /**
     * HydraTokenEnrichmentPayloadSession
     * <p>
     * 
     * 
     */
    @JsonProperty("session")
    public HydraTokenEnrichmentPayloadSession getSession() {
        return session;
    }

    /**
     * HydraTokenEnrichmentPayloadSession
     * <p>
     * 
     * 
     */
    @JsonProperty("session")
    public void setSession(HydraTokenEnrichmentPayloadSession session) {
        this.session = session;
    }

    public HydraTokenEnrichmentPayload withSession(HydraTokenEnrichmentPayloadSession session) {
        this.session = session;
        return this;
    }

    /**
     * HydraTokenEnrichmentPayloadRequest
     * <p>
     * 
     * 
     */
    @JsonProperty("request")
    public HydraTokenEnrichmentPayloadRequest getRequest() {
        return request;
    }

    /**
     * HydraTokenEnrichmentPayloadRequest
     * <p>
     * 
     * 
     */
    @JsonProperty("request")
    public void setRequest(HydraTokenEnrichmentPayloadRequest request) {
        this.request = request;
    }

    public HydraTokenEnrichmentPayload withRequest(HydraTokenEnrichmentPayloadRequest request) {
        this.request = request;
        return this;
    }

}
