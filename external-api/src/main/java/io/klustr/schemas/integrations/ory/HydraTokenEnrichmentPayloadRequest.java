
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraTokenEnrichmentPayloadRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "client_id",
    "granted_scopes",
    "granted_audience",
    "grant_types"
})
@Generated("jsonschema2pojo")
public class HydraTokenEnrichmentPayloadRequest {

    @JsonProperty("client_id")
    private String clientId;
    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_scopes")
    @JsonPropertyDescription("Granted scopes")
    private List<String> grantedScopes = new ArrayList<String>();
    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_audience")
    @JsonPropertyDescription("Granted scopes")
    private List<String> grantedAudience = new ArrayList<String>();
    /**
     * Granted scopes
     * 
     */
    @JsonProperty("grant_types")
    @JsonPropertyDescription("Granted scopes")
    private List<String> grantTypes = new ArrayList<String>();

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public HydraTokenEnrichmentPayloadRequest withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_scopes")
    public List<String> getGrantedScopes() {
        return grantedScopes;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_scopes")
    public void setGrantedScopes(List<String> grantedScopes) {
        this.grantedScopes = grantedScopes;
    }

    public HydraTokenEnrichmentPayloadRequest withGrantedScopes(List<String> grantedScopes) {
        this.grantedScopes = grantedScopes;
        return this;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_audience")
    public List<String> getGrantedAudience() {
        return grantedAudience;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("granted_audience")
    public void setGrantedAudience(List<String> grantedAudience) {
        this.grantedAudience = grantedAudience;
    }

    public HydraTokenEnrichmentPayloadRequest withGrantedAudience(List<String> grantedAudience) {
        this.grantedAudience = grantedAudience;
        return this;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("grant_types")
    public List<String> getGrantTypes() {
        return grantTypes;
    }

    /**
     * Granted scopes
     * 
     */
    @JsonProperty("grant_types")
    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public HydraTokenEnrichmentPayloadRequest withGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

}
