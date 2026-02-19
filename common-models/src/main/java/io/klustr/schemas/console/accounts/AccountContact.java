
package io.klustr.schemas.console.accounts;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AccountContact
 * <p>
 * The billing contact information.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "legal_name",
    "email",
    "phone"
})
@Generated("jsonschema2pojo")
public class AccountContact {

    /**
     * The legal name for this billing entity.
     * 
     */
    @JsonProperty("legal_name")
    @JsonPropertyDescription("The legal name for this billing entity.")
    private String legalName;
    /**
     * The email for this billing entity.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email for this billing entity.")
    private String email;
    /**
     * The phone number for this billing contact
     * 
     */
    @JsonProperty("phone")
    @JsonPropertyDescription("The phone number for this billing contact")
    private String phone;

    /**
     * The legal name for this billing entity.
     * 
     */
    @JsonProperty("legal_name")
    public String getLegalName() {
        return legalName;
    }

    /**
     * The legal name for this billing entity.
     * 
     */
    @JsonProperty("legal_name")
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public AccountContact withLegalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    /**
     * The email for this billing entity.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email for this billing entity.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public AccountContact withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The phone number for this billing contact
     * 
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * The phone number for this billing contact
     * 
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountContact withPhone(String phone) {
        this.phone = phone;
        return this;
    }

}
