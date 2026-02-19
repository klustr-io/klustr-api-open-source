
package io.klustr.schemas.console.users;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "expiry_period",
    "validity_period"
})
@Generated("jsonschema2pojo")
public class Specifications {

    /**
     * The default time it takes to expire the account expressed in NOW/DAY+3DAYS
     * 
     */
    @JsonProperty("expiry_period")
    @JsonPropertyDescription("The default time it takes to expire the account expressed in NOW/DAY+3DAYS")
    private String expiryPeriod;
    /**
     * The time it takes that the user account is available before being expired
     * 
     */
    @JsonProperty("validity_period")
    @JsonPropertyDescription("The time it takes that the user account is available before being expired")
    private String validityPeriod;

    /**
     * The default time it takes to expire the account expressed in NOW/DAY+3DAYS
     * 
     */
    @JsonProperty("expiry_period")
    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    /**
     * The default time it takes to expire the account expressed in NOW/DAY+3DAYS
     * 
     */
    @JsonProperty("expiry_period")
    public void setExpiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public Specifications withExpiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
        return this;
    }

    /**
     * The time it takes that the user account is available before being expired
     * 
     */
    @JsonProperty("validity_period")
    public String getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * The time it takes that the user account is available before being expired
     * 
     */
    @JsonProperty("validity_period")
    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Specifications withValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
        return this;
    }

}
