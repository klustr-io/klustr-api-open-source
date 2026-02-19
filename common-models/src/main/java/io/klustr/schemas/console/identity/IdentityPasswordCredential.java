
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityPasswordCredential
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "config"
})
@Generated("jsonschema2pojo")
public class IdentityPasswordCredential {

    @JsonProperty("type")
    private String type;
    /**
     * IdentityPasswordCredentialConfig
     * <p>
     * The password configuration containing the users password.
     * 
     */
    @JsonProperty("config")
    @JsonPropertyDescription("The password configuration containing the users password.")
    private IdentityPasswordCredentialConfig config;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public IdentityPasswordCredential withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * IdentityPasswordCredentialConfig
     * <p>
     * The password configuration containing the users password.
     * 
     */
    @JsonProperty("config")
    public IdentityPasswordCredentialConfig getConfig() {
        return config;
    }

    /**
     * IdentityPasswordCredentialConfig
     * <p>
     * The password configuration containing the users password.
     * 
     */
    @JsonProperty("config")
    public void setConfig(IdentityPasswordCredentialConfig config) {
        this.config = config;
    }

    public IdentityPasswordCredential withConfig(IdentityPasswordCredentialConfig config) {
        this.config = config;
        return this;
    }

}
