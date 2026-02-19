
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * KratosWebhookLoginPayload
 * <p>
 * Configuration of kratos to send webhook events when a user logins in at any time.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user_id",
    "identity"
})
@Generated("jsonschema2pojo")
public class KratosWebhookLoginPayload {

    @JsonProperty("user_id")
    private String userId;
    /**
     * Identity
     * <p>
     * 
     * 
     */
    @JsonProperty("identity")
    private Identity identity;

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public KratosWebhookLoginPayload withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Identity
     * <p>
     * 
     * 
     */
    @JsonProperty("identity")
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Identity
     * <p>
     * 
     * 
     */
    @JsonProperty("identity")
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public KratosWebhookLoginPayload withIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

}
