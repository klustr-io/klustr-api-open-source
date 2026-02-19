
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectSecretBasicCredential
 * <p>
 * Basic credentials issued for this project for a specific product or services
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "username",
    "password"
})
@Generated("jsonschema2pojo")
public class ProjectSecretBasicCredential {

    /**
     * The username to use for this credential
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("The username to use for this credential")
    private String username;
    /**
     * The password to use or leverage for this credential.
     * 
     */
    @JsonProperty("password")
    @JsonPropertyDescription("The password to use or leverage for this credential.")
    private String password;

    /**
     * The username to use for this credential
     * 
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * The username to use for this credential
     * 
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public ProjectSecretBasicCredential withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * The password to use or leverage for this credential.
     * 
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * The password to use or leverage for this credential.
     * 
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public ProjectSecretBasicCredential withPassword(String password) {
        this.password = password;
        return this;
    }

}
