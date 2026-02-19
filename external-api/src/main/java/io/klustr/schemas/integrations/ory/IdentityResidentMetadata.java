
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * IdentityResidentMetadata
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "registration_date",
    "effective_date",
    "expiration_date",
    "verified"
})
@Generated("jsonschema2pojo")
public class IdentityResidentMetadata {

    @JsonProperty("registration_date")
    private DateTime registrationDate;
    @JsonProperty("effective_date")
    private DateTime effectiveDate;
    @JsonProperty("expiration_date")
    private DateTime expirationDate;
    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("registration_date")
    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty("registration_date")
    public void setRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public IdentityResidentMetadata withRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    @JsonProperty("effective_date")
    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    @JsonProperty("effective_date")
    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public IdentityResidentMetadata withEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public IdentityResidentMetadata withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @JsonProperty("verified")
    public Boolean getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public IdentityResidentMetadata withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }

}
