
package io.klustr.schemas.console.users;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ekyc_required",
    "totp_required",
    "email_required",
    "payment_required",
    "employer_required"
})
@Generated("jsonschema2pojo")
public class Requirements {

    /**
     * If ekyc is required in order to create an account
     * 
     */
    @JsonProperty("ekyc_required")
    @JsonPropertyDescription("If ekyc is required in order to create an account")
    private Boolean ekycRequired;
    /**
     * If a one-time password is required to be setup.
     * 
     */
    @JsonProperty("totp_required")
    @JsonPropertyDescription("If a one-time password is required to be setup.")
    private Boolean totpRequired;
    /**
     * If email verification is required.
     * 
     */
    @JsonProperty("email_required")
    @JsonPropertyDescription("If email verification is required.")
    private Boolean emailRequired;
    /**
     * If the account should have payment information setup.
     * 
     */
    @JsonProperty("payment_required")
    @JsonPropertyDescription("If the account should have payment information setup.")
    private Boolean paymentRequired;
    /**
     * If the account should have employer information setup.
     * 
     */
    @JsonProperty("employer_required")
    @JsonPropertyDescription("If the account should have employer information setup.")
    private Boolean employerRequired;

    /**
     * If ekyc is required in order to create an account
     * 
     */
    @JsonProperty("ekyc_required")
    public Boolean getEkycRequired() {
        return ekycRequired;
    }

    /**
     * If ekyc is required in order to create an account
     * 
     */
    @JsonProperty("ekyc_required")
    public void setEkycRequired(Boolean ekycRequired) {
        this.ekycRequired = ekycRequired;
    }

    public Requirements withEkycRequired(Boolean ekycRequired) {
        this.ekycRequired = ekycRequired;
        return this;
    }

    /**
     * If a one-time password is required to be setup.
     * 
     */
    @JsonProperty("totp_required")
    public Boolean getTotpRequired() {
        return totpRequired;
    }

    /**
     * If a one-time password is required to be setup.
     * 
     */
    @JsonProperty("totp_required")
    public void setTotpRequired(Boolean totpRequired) {
        this.totpRequired = totpRequired;
    }

    public Requirements withTotpRequired(Boolean totpRequired) {
        this.totpRequired = totpRequired;
        return this;
    }

    /**
     * If email verification is required.
     * 
     */
    @JsonProperty("email_required")
    public Boolean getEmailRequired() {
        return emailRequired;
    }

    /**
     * If email verification is required.
     * 
     */
    @JsonProperty("email_required")
    public void setEmailRequired(Boolean emailRequired) {
        this.emailRequired = emailRequired;
    }

    public Requirements withEmailRequired(Boolean emailRequired) {
        this.emailRequired = emailRequired;
        return this;
    }

    /**
     * If the account should have payment information setup.
     * 
     */
    @JsonProperty("payment_required")
    public Boolean getPaymentRequired() {
        return paymentRequired;
    }

    /**
     * If the account should have payment information setup.
     * 
     */
    @JsonProperty("payment_required")
    public void setPaymentRequired(Boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public Requirements withPaymentRequired(Boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
        return this;
    }

    /**
     * If the account should have employer information setup.
     * 
     */
    @JsonProperty("employer_required")
    public Boolean getEmployerRequired() {
        return employerRequired;
    }

    /**
     * If the account should have employer information setup.
     * 
     */
    @JsonProperty("employer_required")
    public void setEmployerRequired(Boolean employerRequired) {
        this.employerRequired = employerRequired;
    }

    public Requirements withEmployerRequired(Boolean employerRequired) {
        this.employerRequired = employerRequired;
        return this;
    }

}
