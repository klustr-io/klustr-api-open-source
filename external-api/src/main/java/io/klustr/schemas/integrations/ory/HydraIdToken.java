
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraIdToken
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "subject",
    "id_token_claims"
})
@Generated("jsonschema2pojo")
public class HydraIdToken {

    /**
     * The real subject ID and is not impacted by pairwise ID
     * 
     */
    @JsonProperty("subject")
    @JsonPropertyDescription("The real subject ID and is not impacted by pairwise ID")
    private String subject;
    /**
     * HydraIdTokenClaims
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token_claims")
    private HydraIdTokenClaims idTokenClaims;

    /**
     * The real subject ID and is not impacted by pairwise ID
     * 
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * The real subject ID and is not impacted by pairwise ID
     * 
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HydraIdToken withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * HydraIdTokenClaims
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token_claims")
    public HydraIdTokenClaims getIdTokenClaims() {
        return idTokenClaims;
    }

    /**
     * HydraIdTokenClaims
     * <p>
     * 
     * 
     */
    @JsonProperty("id_token_claims")
    public void setIdTokenClaims(HydraIdTokenClaims idTokenClaims) {
        this.idTokenClaims = idTokenClaims;
    }

    public HydraIdToken withIdTokenClaims(HydraIdTokenClaims idTokenClaims) {
        this.idTokenClaims = idTokenClaims;
        return this;
    }

}
