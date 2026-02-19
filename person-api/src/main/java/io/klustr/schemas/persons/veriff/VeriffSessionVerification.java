
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffSessionVerification
 * <p>
 * The request
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "person",
    "callback",
    "vendorData"
})
@Generated("jsonschema2pojo")
public class VeriffSessionVerification {

    /**
     * VeriffPersonRequest
     * <p>
     * The person for this request.
     * 
     */
    @JsonProperty("person")
    @JsonPropertyDescription("The person for this request.")
    private VeriffPersonRequest person;
    /**
     * The callbackUrl to where the end-user is redirected after the verification session is completed. 
     * 
     */
    @JsonProperty("callback")
    @JsonPropertyDescription("The callbackUrl to where the end-user is redirected after the verification session is completed. ")
    private String callback;
    /**
     * Data that will be used to link requests to local data and system.
     * 
     */
    @JsonProperty("vendorData")
    @JsonPropertyDescription("Data that will be used to link requests to local data and system.")
    private String vendorData;

    /**
     * VeriffPersonRequest
     * <p>
     * The person for this request.
     * 
     */
    @JsonProperty("person")
    public VeriffPersonRequest getPerson() {
        return person;
    }

    /**
     * VeriffPersonRequest
     * <p>
     * The person for this request.
     * 
     */
    @JsonProperty("person")
    public void setPerson(VeriffPersonRequest person) {
        this.person = person;
    }

    public VeriffSessionVerification withPerson(VeriffPersonRequest person) {
        this.person = person;
        return this;
    }

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

    public VeriffSessionVerification withCallback(String callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Data that will be used to link requests to local data and system.
     * 
     */
    @JsonProperty("vendorData")
    public String getVendorData() {
        return vendorData;
    }

    /**
     * Data that will be used to link requests to local data and system.
     * 
     */
    @JsonProperty("vendorData")
    public void setVendorData(String vendorData) {
        this.vendorData = vendorData;
    }

    public VeriffSessionVerification withVendorData(String vendorData) {
        this.vendorData = vendorData;
        return this;
    }

}
