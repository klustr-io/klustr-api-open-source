
package io.klustr.schemas.console.agreements;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AgreementOwner
 * <p>
 * The owners of this agreement and their contact information.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "email"
})
@Generated("jsonschema2pojo")
public class AgreementOwner {

    /**
     * The email of the owner.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email of the owner.")
    private String email;

    /**
     * The email of the owner.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email of the owner.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public AgreementOwner withEmail(String email) {
        this.email = email;
        return this;
    }

}
