
package io.klustr.schemas.console.oidc;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.klustr.schemas.console.AccessTokenStrategy;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.TokenEndpointAuthMethod;
import org.joda.time.DateTime;


/**
 * OIDCClient
 * <p>
 * A client that uses web to authenticate and consent to scopes.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "client_id",
    "secret_key",
    "access_token_strategy",
    "token_endpoint_auth_method",
    "subject_type",
    "scopes",
    "audience",
    "creation_date",
    "status",
    "authorized_origins",
    "authorized_redirect_uris",
    "access_token_lifespan",
    "id_token_lifespan",
    "refresh_token_lifespan",
    "skip_consent",
    "metadata"
})
@Generated("jsonschema2pojo")
public class OIDCClient {

    /**
     * The name of your OAuth 2.0 client. This name is only used to identify the client in the console and will not be shown to end users.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of your OAuth 2.0 client. This name is only used to identify the client in the console and will not be shown to end users.")
    private java.lang.String name;
    /**
     * The unique ID for this webapp client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The unique ID for this webapp client.")
    private java.lang.String clientId;
    /**
     * The secret key for this web app client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    @JsonPropertyDescription("The secret key for this web app client")
    private java.lang.String secretKey;
    /**
     * The method for how a token is generated, opaque is preferred for security and requires introspection.
     * 
     */
    @JsonProperty("access_token_strategy")
    @JsonPropertyDescription("The method for how a token is generated, opaque is preferred for security and requires introspection.")
    private AccessTokenStrategy accessTokenStrategy = AccessTokenStrategy.fromValue("opaque");
    /**
     * The strategy for the token
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    @JsonPropertyDescription("The strategy for the token")
    private TokenEndpointAuthMethod tokenEndpointAuthMethod = TokenEndpointAuthMethod.fromValue("client_secret_basic");
    /**
     * Activates pseudo anonymous identifiers when set to pairwise
     * 
     */
    @JsonProperty("subject_type")
    @JsonPropertyDescription("Activates pseudo anonymous identifiers when set to pairwise")
    private SubjectType subjectType;
    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes to apply and access for this client")
    private List<java.lang.String> scopes = new ArrayList<java.lang.String>();
    @JsonProperty("audience")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<String> audience = new LinkedHashSet<String>();
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this client")
    private OidcStatus status = OidcStatus.fromValue("enabled");
    /**
     * The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_origins")
    @JsonPropertyDescription("The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080")
    private List<java.lang.String> authorizedOrigins = new ArrayList<java.lang.String>();
    /**
     * Users will be redirected to this path after they have authenticated with Google. The path will be appended with the authorization code for access, and must have a protocol. It can’t contain URL fragments, relative paths, or wildcards, and can’t be a public IP address.
     * 
     */
    @JsonProperty("authorized_redirect_uris")
    @JsonPropertyDescription("Users will be redirected to this path after they have authenticated with Google. The path will be appended with the authorization code for access, and must have a protocol. It can\u2019t contain URL fragments, relative paths, or wildcards, and can\u2019t be a public IP address.")
    private List<java.lang.String> authorizedRedirectUris = new ArrayList<java.lang.String>();
    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("access_token_lifespan")
    @JsonPropertyDescription("Specify a time duration in milliseconds, seconds, minutes, hours.")
    private java.lang.String accessTokenLifespan;
    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("id_token_lifespan")
    @JsonPropertyDescription("Specify a time duration in milliseconds, seconds, minutes, hours.")
    private java.lang.String idTokenLifespan;
    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    @JsonPropertyDescription("Specify a time duration in milliseconds, seconds, minutes, hours.")
    private java.lang.String refreshTokenLifespan;
    /**
     * Enables a client to skip consent if so trusted
     * 
     */
    @JsonProperty("skip_consent")
    @JsonPropertyDescription("Enables a client to skip consent if so trusted")
    private Boolean skipConsent;
    /**
     * Additional metadata related to the client to tag or otherwise manage it.
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Additional metadata related to the client to tag or otherwise manage it.")
    private Map<String, String> metadata;

    /**
     * The name of your OAuth 2.0 client. This name is only used to identify the client in the console and will not be shown to end users.
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * The name of your OAuth 2.0 client. This name is only used to identify the client in the console and will not be shown to end users.
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public OIDCClient withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * The unique ID for this webapp client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * The unique ID for this webapp client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    public OIDCClient withClientId(java.lang.String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The secret key for this web app client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    public java.lang.String getSecretKey() {
        return secretKey;
    }

    /**
     * The secret key for this web app client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    public void setSecretKey(java.lang.String secretKey) {
        this.secretKey = secretKey;
    }

    public OIDCClient withSecretKey(java.lang.String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    /**
     * The method for how a token is generated, opaque is preferred for security and requires introspection.
     * 
     */
    @JsonProperty("access_token_strategy")
    public AccessTokenStrategy getAccessTokenStrategy() {
        return accessTokenStrategy;
    }

    /**
     * The method for how a token is generated, opaque is preferred for security and requires introspection.
     * 
     */
    @JsonProperty("access_token_strategy")
    public void setAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
    }

    public OIDCClient withAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
        return this;
    }

    /**
     * The strategy for the token
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    public TokenEndpointAuthMethod getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    /**
     * The strategy for the token
     * 
     */
    @JsonProperty("token_endpoint_auth_method")
    public void setTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public OIDCClient withTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
        return this;
    }

    /**
     * Activates pseudo anonymous identifiers when set to pairwise
     * 
     */
    @JsonProperty("subject_type")
    public SubjectType getSubjectType() {
        return subjectType;
    }

    /**
     * Activates pseudo anonymous identifiers when set to pairwise
     * 
     */
    @JsonProperty("subject_type")
    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public OIDCClient withSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    public List<java.lang.String> getScopes() {
        return scopes;
    }

    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<java.lang.String> scopes) {
        this.scopes = scopes;
    }

    public OIDCClient withScopes(List<java.lang.String> scopes) {
        this.scopes = scopes;
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

    public OIDCClient withAudience(Set<String> audience) {
        this.audience = audience;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OIDCClient withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    public OidcStatus getStatus() {
        return status;
    }

    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    public void setStatus(OidcStatus status) {
        this.status = status;
    }

    public OIDCClient withStatus(OidcStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_origins")
    public List<java.lang.String> getAuthorizedOrigins() {
        return authorizedOrigins;
    }

    /**
     * The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_origins")
    public void setAuthorizedOrigins(List<java.lang.String> authorizedOrigins) {
        this.authorizedOrigins = authorizedOrigins;
    }

    public OIDCClient withAuthorizedOrigins(List<java.lang.String> authorizedOrigins) {
        this.authorizedOrigins = authorizedOrigins;
        return this;
    }

    /**
     * Users will be redirected to this path after they have authenticated with Google. The path will be appended with the authorization code for access, and must have a protocol. It can’t contain URL fragments, relative paths, or wildcards, and can’t be a public IP address.
     * 
     */
    @JsonProperty("authorized_redirect_uris")
    public List<java.lang.String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }

    /**
     * Users will be redirected to this path after they have authenticated with Google. The path will be appended with the authorization code for access, and must have a protocol. It can’t contain URL fragments, relative paths, or wildcards, and can’t be a public IP address.
     * 
     */
    @JsonProperty("authorized_redirect_uris")
    public void setAuthorizedRedirectUris(List<java.lang.String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
    }

    public OIDCClient withAuthorizedRedirectUris(List<java.lang.String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
        return this;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("access_token_lifespan")
    public java.lang.String getAccessTokenLifespan() {
        return accessTokenLifespan;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("access_token_lifespan")
    public void setAccessTokenLifespan(java.lang.String accessTokenLifespan) {
        this.accessTokenLifespan = accessTokenLifespan;
    }

    public OIDCClient withAccessTokenLifespan(java.lang.String accessTokenLifespan) {
        this.accessTokenLifespan = accessTokenLifespan;
        return this;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("id_token_lifespan")
    public java.lang.String getIdTokenLifespan() {
        return idTokenLifespan;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("id_token_lifespan")
    public void setIdTokenLifespan(java.lang.String idTokenLifespan) {
        this.idTokenLifespan = idTokenLifespan;
    }

    public OIDCClient withIdTokenLifespan(java.lang.String idTokenLifespan) {
        this.idTokenLifespan = idTokenLifespan;
        return this;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    public java.lang.String getRefreshTokenLifespan() {
        return refreshTokenLifespan;
    }

    /**
     * Specify a time duration in milliseconds, seconds, minutes, hours.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    public void setRefreshTokenLifespan(java.lang.String refreshTokenLifespan) {
        this.refreshTokenLifespan = refreshTokenLifespan;
    }

    public OIDCClient withRefreshTokenLifespan(java.lang.String refreshTokenLifespan) {
        this.refreshTokenLifespan = refreshTokenLifespan;
        return this;
    }

    /**
     * Enables a client to skip consent if so trusted
     * 
     */
    @JsonProperty("skip_consent")
    public Boolean getSkipConsent() {
        return skipConsent;
    }

    /**
     * Enables a client to skip consent if so trusted
     * 
     */
    @JsonProperty("skip_consent")
    public void setSkipConsent(Boolean skipConsent) {
        this.skipConsent = skipConsent;
    }

    public OIDCClient withSkipConsent(Boolean skipConsent) {
        this.skipConsent = skipConsent;
        return this;
    }

    /**
     * Additional metadata related to the client to tag or otherwise manage it.
     * 
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Additional metadata related to the client to tag or otherwise manage it.
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public OIDCClient withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

}
