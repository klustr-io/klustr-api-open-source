
package io.klustr.schemas.persons.verification;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PersonVerificationRequest
 * <p>
 * Request to start a verification for a user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "callback",
    "org_id"
})
@Generated("jsonschema2pojo")
public class PersonVerificationRequest {

    /**
     * The callbackUrl to where the end-user is redirected after the verification session is completed.
     * 
     */
    @JsonProperty("callback")
    @JsonPropertyDescription("The callbackUrl to where the end-user is redirected after the verification session is completed.")
    private String callback;
    /**
     * The organization ID making this request for verification, if applicable.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization ID making this request for verification, if applicable.")
    private String orgId;

    /**
     * The callbackUrl to where the end-user is redirected after the verification session is completed.
     * 
     */
    @JsonProperty("callback")
    public String getCallback() {
        return callback;
    }

    /**
     * The callbackUrl to where the end-user is redirected after the verification session is completed.
     * 
     */
    @JsonProperty("callback")
    public void setCallback(String callback) {
        this.callback = callback;
    }

    public PersonVerificationRequest withCallback(String callback) {
        this.callback = callback;
        return this;
    }

    /**
     * The organization ID making this request for verification, if applicable.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization ID making this request for verification, if applicable.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public PersonVerificationRequest withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

}
