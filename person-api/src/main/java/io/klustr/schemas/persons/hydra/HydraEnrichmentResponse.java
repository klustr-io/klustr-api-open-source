
package io.klustr.schemas.persons.hydra;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraEnrichmentResponse
 * <p>
 * Enrichment information returned to Hydra to populate the ID token and Access Token.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "session"
})
@Generated("jsonschema2pojo")
public class HydraEnrichmentResponse {

    /**
     * HydraSessionEnrichment
     * <p>
     * Enrichment information for the session.
     * (Required)
     * 
     */
    @JsonProperty("session")
    @JsonPropertyDescription("Enrichment information for the session.")
    private HydraSessionEnrichment session;

    /**
     * HydraSessionEnrichment
     * <p>
     * Enrichment information for the session.
     * (Required)
     * 
     */
    @JsonProperty("session")
    public HydraSessionEnrichment getSession() {
        return session;
    }

    /**
     * HydraSessionEnrichment
     * <p>
     * Enrichment information for the session.
     * (Required)
     * 
     */
    @JsonProperty("session")
    public void setSession(HydraSessionEnrichment session) {
        this.session = session;
    }

    public HydraEnrichmentResponse withSession(HydraSessionEnrichment session) {
        this.session = session;
        return this;
    }

}
