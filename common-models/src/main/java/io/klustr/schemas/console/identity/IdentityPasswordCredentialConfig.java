
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityPasswordCredentialConfig
 * <p>
 * The password configuration containing the users password.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "password"
})
@Generated("jsonschema2pojo")
public class IdentityPasswordCredentialConfig {

    /**
     * The password to configure for the user.
     * 
     */
    @JsonProperty("password")
    @JsonPropertyDescription("The password to configure for the user.")
    private String password;

    /**
     * The password to configure for the user.
     * 
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * The password to configure for the user.
     * 
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public IdentityPasswordCredentialConfig withPassword(String password) {
        this.password = password;
        return this;
    }

}
