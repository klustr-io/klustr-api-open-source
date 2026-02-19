
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * OAuthTokenResponse
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "active",
    "scope",
    "client_id",
    "sub",
    "exp",
    "iat",
    "nbf",
    "aud",
    "iss",
    "token_type",
    "token_use",
    "ext"
})
@Generated("jsonschema2pojo")
public class OAuthTokenResponse {

    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("exp")
    private Integer exp;
    @JsonProperty("iat")
    private Integer iat;
    @JsonProperty("nbf")
    private Integer nbf;
    @JsonProperty("aud")
    private List<String> aud = new ArrayList<String>();
    /**
     * The issuer of this token
     * 
     */
    @JsonProperty("iss")
    @JsonPropertyDescription("The issuer of this token")
    private String iss;
    /**
     * The type of token being issued.
     * 
     */
    @JsonProperty("token_type")
    @JsonPropertyDescription("The type of token being issued.")
    private String tokenType;
    /**
     * The use for this token.
     * 
     */
    @JsonProperty("token_use")
    @JsonPropertyDescription("The use for this token.")
    private String tokenUse;
    /**
     * Extension information regarding the token which is free form.
     * 
     */
    @JsonProperty("ext")
    @JsonPropertyDescription("Extension information regarding the token which is free form.")
    private Ext ext;

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    public OAuthTokenResponse withActive(Boolean active) {
        this.active = active;
        return this;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public OAuthTokenResponse withScope(String scope) {
        this.scope = scope;
        return this;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OAuthTokenResponse withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @JsonProperty("sub")
    public String getSub() {
        return sub;
    }

    @JsonProperty("sub")
    public void setSub(String sub) {
        this.sub = sub;
    }

    public OAuthTokenResponse withSub(String sub) {
        this.sub = sub;
        return this;
    }

    @JsonProperty("exp")
    public Integer getExp() {
        return exp;
    }

    @JsonProperty("exp")
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public OAuthTokenResponse withExp(Integer exp) {
        this.exp = exp;
        return this;
    }

    @JsonProperty("iat")
    public Integer getIat() {
        return iat;
    }

    @JsonProperty("iat")
    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public OAuthTokenResponse withIat(Integer iat) {
        this.iat = iat;
        return this;
    }

    @JsonProperty("nbf")
    public Integer getNbf() {
        return nbf;
    }

    @JsonProperty("nbf")
    public void setNbf(Integer nbf) {
        this.nbf = nbf;
    }

    public OAuthTokenResponse withNbf(Integer nbf) {
        this.nbf = nbf;
        return this;
    }

    @JsonProperty("aud")
    public List<String> getAud() {
        return aud;
    }

    @JsonProperty("aud")
    public void setAud(List<String> aud) {
        this.aud = aud;
    }

    public OAuthTokenResponse withAud(List<String> aud) {
        this.aud = aud;
        return this;
    }

    /**
     * The issuer of this token
     * 
     */
    @JsonProperty("iss")
    public String getIss() {
        return iss;
    }

    /**
     * The issuer of this token
     * 
     */
    @JsonProperty("iss")
    public void setIss(String iss) {
        this.iss = iss;
    }

    public OAuthTokenResponse withIss(String iss) {
        this.iss = iss;
        return this;
    }

    /**
     * The type of token being issued.
     * 
     */
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    /**
     * The type of token being issued.
     * 
     */
    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public OAuthTokenResponse withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    /**
     * The use for this token.
     * 
     */
    @JsonProperty("token_use")
    public String getTokenUse() {
        return tokenUse;
    }

    /**
     * The use for this token.
     * 
     */
    @JsonProperty("token_use")
    public void setTokenUse(String tokenUse) {
        this.tokenUse = tokenUse;
    }

    public OAuthTokenResponse withTokenUse(String tokenUse) {
        this.tokenUse = tokenUse;
        return this;
    }

    /**
     * Extension information regarding the token which is free form.
     * 
     */
    @JsonProperty("ext")
    public Ext getExt() {
        return ext;
    }

    /**
     * Extension information regarding the token which is free form.
     * 
     */
    @JsonProperty("ext")
    public void setExt(Ext ext) {
        this.ext = ext;
    }

    public OAuthTokenResponse withExt(Ext ext) {
        this.ext = ext;
        return this;
    }

}
