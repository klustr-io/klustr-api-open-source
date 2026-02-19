
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityCredentials
 * <p>
 * The online persona and profile for a specific identity.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "oidc",
    "password"
})
@Generated("jsonschema2pojo")
public class IdentityCredentials {

    /**
     * IdentityOidcCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("oidc")
    private IdentityOidcCredential oidc;
    /**
     * IdentityPasswordCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("password")
    private IdentityPasswordCredential password;

    /**
     * IdentityOidcCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("oidc")
    public IdentityOidcCredential getOidc() {
        return oidc;
    }

    /**
     * IdentityOidcCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("oidc")
    public void setOidc(IdentityOidcCredential oidc) {
        this.oidc = oidc;
    }

    public IdentityCredentials withOidc(IdentityOidcCredential oidc) {
        this.oidc = oidc;
        return this;
    }

    /**
     * IdentityPasswordCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("password")
    public IdentityPasswordCredential getPassword() {
        return password;
    }

    /**
     * IdentityPasswordCredential
     * <p>
     * 
     * 
     */
    @JsonProperty("password")
    public void setPassword(IdentityPasswordCredential password) {
        this.password = password;
    }

    public IdentityCredentials withPassword(IdentityPasswordCredential password) {
        this.password = password;
        return this;
    }

}
