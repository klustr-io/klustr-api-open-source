
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffVerificationData
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "verification"
})
@Generated("jsonschema2pojo")
public class VeriffVerificationData {

    /**
     * VeriffVerification
     * <p>
     * The verification that was performed
     * 
     */
    @JsonProperty("verification")
    @JsonPropertyDescription("The verification that was performed")
    private VeriffVerification verification;

    /**
     * VeriffVerification
     * <p>
     * The verification that was performed
     * 
     */
    @JsonProperty("verification")
    public VeriffVerification getVerification() {
        return verification;
    }

    /**
     * VeriffVerification
     * <p>
     * The verification that was performed
     * 
     */
    @JsonProperty("verification")
    public void setVerification(VeriffVerification verification) {
        this.verification = verification;
    }

    public VeriffVerificationData withVerification(VeriffVerification verification) {
        this.verification = verification;
        return this;
    }

}
