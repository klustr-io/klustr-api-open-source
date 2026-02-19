
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.klustr.schemas.console.AccessTokenStrategy;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.TokenEndpointAuthMethod;


/**
 * HydraCredentialsRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "access_token_strategy",
    "token_endpoint_auth_method",
    "name",
    "audience",
    "client_id",
    "client_secret",
    "status",
    "scopes"
})
@Generated("jsonschema2pojo")
public class HydraCredentialsRequest {

    /**
     * The access token type generated for this credential request. Defaults to 'OPAQUE'.
     * 
     */
    @JsonProperty("access_token_strategy")
    @JsonPropertyDescription("The access token type generated for this credential request. Defaults to 'OPAQUE'.")
    private AccessTokenStrategy accessTokenStrategy = null;
    /**
     * The authentication method to use when issuing the request. Defaults to 'CLIENT_SECRET_BASIC'
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    @JsonPropertyDescription("The authentication method to use when issuing the request. Defaults to 'CLIENT_SECRET_BASIC'")
    private TokenEndpointAuthMethod tokenEndpointAuthMethod = null;
    /**
     * The display name for this client credential
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name for this client credential")
    private java.lang.String name;
    @JsonProperty("audience")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<String> audience = new LinkedHashSet<String>();
    /**
     * The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.")
    private java.lang.String clientId;
    /**
     * The optional secret to specify when creating your credential, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_secret")
    @JsonPropertyDescription("The optional secret to specify when creating your credential, if not specified will be generated automatically.")
    private java.lang.String clientSecret;
    /**
     * The status of this OIDC credential which can be disabled or otherwise paused for security reasons.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this OIDC credential which can be disabled or otherwise paused for security reasons.")
    private OidcStatus status = null;
    /**
     * The scopes to apply and update for the client.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes to apply and update for the client.")
    private List<java.lang.String> scopes = new ArrayList<java.lang.String>();

    /**
     * The access token type generated for this credential request. Defaults to 'OPAQUE'.
     * 
     */
    @JsonProperty("access_token_strategy")
    public AccessTokenStrategy getAccessTokenStrategy() {
        return accessTokenStrategy;
    }

    /**
     * The access token type generated for this credential request. Defaults to 'OPAQUE'.
     * 
     */
    @JsonProperty("access_token_strategy")
    public void setAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
    }

    public HydraCredentialsRequest withAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
        return this;
    }

    /**
     * The authentication method to use when issuing the request. Defaults to 'CLIENT_SECRET_BASIC'
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    public TokenEndpointAuthMethod getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    /**
     * The authentication method to use when issuing the request. Defaults to 'CLIENT_SECRET_BASIC'
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    public void setTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public HydraCredentialsRequest withTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
        return this;
    }

    /**
     * The display name for this client credential
     * (Required)
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * The display name for this client credential
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public HydraCredentialsRequest withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("audience")
    public Set<String> getAudience() {
        return audience;
    }

    @JsonProperty("audience")
    public void setAudience(Set<String> audience) {
        this.audience = audience;
    }

    public HydraCredentialsRequest withAudience(Set<String> audience) {
        this.audience = audience;
        return this;
    }

    /**
     * The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_id")
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    public HydraCredentialsRequest withClientId(java.lang.String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The optional secret to specify when creating your credential, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_secret")
    public java.lang.String getClientSecret() {
        return clientSecret;
    }

    /**
     * The optional secret to specify when creating your credential, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_secret")
    public void setClientSecret(java.lang.String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public HydraCredentialsRequest withClientSecret(java.lang.String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * The status of this OIDC credential which can be disabled or otherwise paused for security reasons.
     * 
     */
    @JsonProperty("status")
    public OidcStatus getStatus() {
        return status;
    }

    /**
     * The status of this OIDC credential which can be disabled or otherwise paused for security reasons.
     * 
     */
    @JsonProperty("status")
    public void setStatus(OidcStatus status) {
        this.status = status;
    }

    public HydraCredentialsRequest withStatus(OidcStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The scopes to apply and update for the client.
     * 
     */
    @JsonProperty("scopes")
    public List<java.lang.String> getScopes() {
        return scopes;
    }

    /**
     * The scopes to apply and update for the client.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<java.lang.String> scopes) {
        this.scopes = scopes;
    }

    public HydraCredentialsRequest withScopes(List<java.lang.String> scopes) {
        this.scopes = scopes;
        return this;
    }

}
