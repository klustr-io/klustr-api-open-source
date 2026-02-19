
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraTokenEnrichmentPayloadSession
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id_token",
    "client_id",
    "consent_challenge"
})
@Generated("jsonschema2pojo")
public class HydraTokenEnrichmentPayloadSession {

    /**
     * HydraIdToken
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token")
    private HydraIdToken idToken;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("consent_challenge")
    private String consentChallenge;

    /**
     * HydraIdToken
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token")
    public HydraIdToken getIdToken() {
        return idToken;
    }

    /**
     * HydraIdToken
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token")
    public void setIdToken(HydraIdToken idToken) {
        this.idToken = idToken;
    }

    public HydraTokenEnrichmentPayloadSession withIdToken(HydraIdToken idToken) {
        this.idToken = idToken;
        return this;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public HydraTokenEnrichmentPayloadSession withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @JsonProperty("consent_challenge")
    public String getConsentChallenge() {
        return consentChallenge;
    }

    @JsonProperty("consent_challenge")
    public void setConsentChallenge(String consentChallenge) {
        this.consentChallenge = consentChallenge;
    }

    public HydraTokenEnrichmentPayloadSession withConsentChallenge(String consentChallenge) {
        this.consentChallenge = consentChallenge;
        return this;
    }

}
