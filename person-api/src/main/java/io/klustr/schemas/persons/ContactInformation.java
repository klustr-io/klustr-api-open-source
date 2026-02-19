
package io.klustr.schemas.persons;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Address;


/**
 * ContactInformation
 * <p>
 * The contact information for a person
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "phones",
    "addresses",
    "email"
})
@Generated("jsonschema2pojo")
public class ContactInformation {

    /**
     * The phone numbers available for contacting this entity.
     * 
     */
    @JsonProperty("phones")
    @JsonPropertyDescription("The phone numbers available for contacting this entity.")
    private List<Phone> phones = new ArrayList<Phone>();
    /**
     * The addresses registered for this entity.
     * 
     */
    @JsonProperty("addresses")
    @JsonPropertyDescription("The addresses registered for this entity.")
    private Address addresses;
    /**
     * The primary email address for this entity.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The primary email address for this entity.")
    private String email;

    /**
     * The phone numbers available for contacting this entity.
     * 
     */
    @JsonProperty("phones")
    public List<Phone> getPhones() {
        return phones;
    }

    /**
     * The phone numbers available for contacting this entity.
     * 
     */
    @JsonProperty("phones")
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public ContactInformation withPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }

    /**
     * The addresses registered for this entity.
     * 
     */
    @JsonProperty("addresses")
    public Address getAddresses() {
        return addresses;
    }

    /**
     * The addresses registered for this entity.
     * 
     */
    @JsonProperty("addresses")
    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public ContactInformation withAddresses(Address addresses) {
        this.addresses = addresses;
        return this;
    }

    /**
     * The primary email address for this entity.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The primary email address for this entity.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public ContactInformation withEmail(String email) {
        this.email = email;
        return this;
    }

}
