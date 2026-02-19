
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffSessionResponse
 * <p>
 * A response from asking for a session
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "verification"
})
@Generated("jsonschema2pojo")
public class VeriffSessionResponse {

    /**
     * xxx
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("xxx")
    private String status;
    /**
     * VeriffSessionInformation
     * <p>
     * Information on the status and links to execute the session.
     * 
     */
    @JsonProperty("verification")
    @JsonPropertyDescription("Information on the status and links to execute the session.")
    private VeriffSessionInformation verification;

    /**
     * xxx
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * xxx
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public VeriffSessionResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * VeriffSessionInformation
     * <p>
     * Information on the status and links to execute the session.
     * 
     */
    @JsonProperty("verification")
    public VeriffSessionInformation getVerification() {
        return verification;
    }

    /**
     * VeriffSessionInformation
     * <p>
     * Information on the status and links to execute the session.
     * 
     */
    @JsonProperty("verification")
    public void setVerification(VeriffSessionInformation verification) {
        this.verification = verification;
    }

    public VeriffSessionResponse withVerification(VeriffSessionInformation verification) {
        this.verification = verification;
        return this;
    }

}
