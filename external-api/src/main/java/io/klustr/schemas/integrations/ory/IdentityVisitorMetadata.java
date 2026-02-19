
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * IdentityVisitorMetadata
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "registration_date",
    "effective_date",
    "expiration_date",
    "first_visit_date",
    "last_visit_date",
    "verified"
})
@Generated("jsonschema2pojo")
public class IdentityVisitorMetadata {

    @JsonProperty("registration_date")
    private DateTime registrationDate;
    @JsonProperty("effective_date")
    private DateTime effectiveDate;
    @JsonProperty("expiration_date")
    private DateTime expirationDate;
    @JsonProperty("first_visit_date")
    private DateTime firstVisitDate;
    @JsonProperty("last_visit_date")
    private DateTime lastVisitDate;
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

    public IdentityVisitorMetadata withRegistrationDate(DateTime registrationDate) {
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

    public IdentityVisitorMetadata withEffectiveDate(DateTime effectiveDate) {
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

    public IdentityVisitorMetadata withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @JsonProperty("first_visit_date")
    public DateTime getFirstVisitDate() {
        return firstVisitDate;
    }

    @JsonProperty("first_visit_date")
    public void setFirstVisitDate(DateTime firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }

    public IdentityVisitorMetadata withFirstVisitDate(DateTime firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
        return this;
    }

    @JsonProperty("last_visit_date")
    public DateTime getLastVisitDate() {
        return lastVisitDate;
    }

    @JsonProperty("last_visit_date")
    public void setLastVisitDate(DateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public IdentityVisitorMetadata withLastVisitDate(DateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
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

    public IdentityVisitorMetadata withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }

}
