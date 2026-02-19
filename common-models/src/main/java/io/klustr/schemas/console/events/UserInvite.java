
package io.klustr.schemas.console.events;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserInvite
 * <p>
 * A user who was invited to participate in an app
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "email"
})
@Generated("jsonschema2pojo")
public class UserInvite {

    /**
     * The email address of the user who you invited.
     * (Required)
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email address of the user who you invited.")
    private String email;

    /**
     * The email address of the user who you invited.
     * (Required)
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email address of the user who you invited.
     * (Required)
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public UserInvite withEmail(String email) {
        this.email = email;
        return this;
    }

}
