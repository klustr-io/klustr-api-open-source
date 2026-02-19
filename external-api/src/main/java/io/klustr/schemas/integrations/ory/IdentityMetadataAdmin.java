
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityMetadataAdmin
 * <p>
 * Attributes which can only be modified and read using the /admin/identities APIs. They are never directly exposed to the identity/user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "person_id"
})
@Generated("jsonschema2pojo")
public class IdentityMetadataAdmin {

    /**
     * The unique person identifier that maps to a registered user.
     * 
     */
    @JsonProperty("person_id")
    @JsonPropertyDescription("The unique person identifier that maps to a registered user.")
    private String personId;

    /**
     * The unique person identifier that maps to a registered user.
     * 
     */
    @JsonProperty("person_id")
    public String getPersonId() {
        return personId;
    }

    /**
     * The unique person identifier that maps to a registered user.
     * 
     */
    @JsonProperty("person_id")
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public IdentityMetadataAdmin withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

}
