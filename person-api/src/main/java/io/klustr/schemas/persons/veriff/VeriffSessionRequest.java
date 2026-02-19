
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffSessionRequest
 * <p>
 * A request for veriff to verify a session
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "verification"
})
@Generated("jsonschema2pojo")
public class VeriffSessionRequest {

    /**
     * VeriffSessionVerification
     * <p>
     * The request
     * 
     */
    @JsonProperty("verification")
    @JsonPropertyDescription("The request")
    private VeriffSessionVerification verification;

    /**
     * VeriffSessionVerification
     * <p>
     * The request
     * 
     */
    @JsonProperty("verification")
    public VeriffSessionVerification getVerification() {
        return verification;
    }

    /**
     * VeriffSessionVerification
     * <p>
     * The request
     * 
     */
    @JsonProperty("verification")
    public void setVerification(VeriffSessionVerification verification) {
        this.verification = verification;
    }

    public VeriffSessionRequest withVerification(VeriffSessionVerification verification) {
        this.verification = verification;
        return this;
    }

}
