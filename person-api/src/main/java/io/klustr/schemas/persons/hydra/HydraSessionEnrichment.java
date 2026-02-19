
package io.klustr.schemas.persons.hydra;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraSessionEnrichment
 * <p>
 * Enrichment information for the session.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id_token",
    "access_token"
})
@Generated("jsonschema2pojo")
public class HydraSessionEnrichment {

    /**
     * HydraIdTokenEnrichment
     * <p>
     * Information in the OIDC ID token for the ability to returned type information
     * 
     */
    @JsonProperty("id_token")
    @JsonPropertyDescription("Information in the OIDC ID token for the ability to returned type information")
    private HydraIdTokenEnrichment idToken;
    /**
     * HydraAccessTokenEnrichment
     * <p>
     * Information in the OIDC Access token for the ability to returned type information
     * 
     */
    @JsonProperty("access_token")
    @JsonPropertyDescription("Information in the OIDC Access token for the ability to returned type information")
    private HydraAccessTokenEnrichment accessToken;

    /**
     * HydraIdTokenEnrichment
     * <p>
     * Information in the OIDC ID token for the ability to returned type information
     * 
     */
    @JsonProperty("id_token")
    public HydraIdTokenEnrichment getIdToken() {
        return idToken;
    }

    /**
     * HydraIdTokenEnrichment
     * <p>
     * Information in the OIDC ID token for the ability to returned type information
     * 
     */
    @JsonProperty("id_token")
    public void setIdToken(HydraIdTokenEnrichment idToken) {
        this.idToken = idToken;
    }

    public HydraSessionEnrichment withIdToken(HydraIdTokenEnrichment idToken) {
        this.idToken = idToken;
        return this;
    }

    /**
     * HydraAccessTokenEnrichment
     * <p>
     * Information in the OIDC Access token for the ability to returned type information
     * 
     */
    @JsonProperty("access_token")
    public HydraAccessTokenEnrichment getAccessToken() {
        return accessToken;
    }

    /**
     * HydraAccessTokenEnrichment
     * <p>
     * Information in the OIDC Access token for the ability to returned type information
     * 
     */
    @JsonProperty("access_token")
    public void setAccessToken(HydraAccessTokenEnrichment accessToken) {
        this.accessToken = accessToken;
    }

    public HydraSessionEnrichment withAccessToken(HydraAccessTokenEnrichment accessToken) {
        this.accessToken = accessToken;
        return this;
    }

}
