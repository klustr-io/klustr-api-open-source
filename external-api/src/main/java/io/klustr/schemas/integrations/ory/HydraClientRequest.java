
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.klustr.schemas.console.AccessTokenStrategy;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.SubjectType;
import io.klustr.schemas.console.TokenEndpointAuthMethod;


/**
 * HydraClientRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "access_token_strategy",
    "token_endpoint_auth_method",
    "subject_type",
    "name",
    "client_id",
    "audience",
    "status",
    "access_token_lifespan",
    "refresh_token_lifespan",
    "authorized_redirect_urls",
    "scopes",
    "authorized_origins",
    "userinfo_signed_response_alg"
})
@Generated("jsonschema2pojo")
public class HydraClientRequest {

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
    @JsonProperty("subject_type")
    private SubjectType subjectType;
    /**
     * The display name for this client credential
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name for this client credential")
    private java.lang.String name;
    /**
     * The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The optional client_id to specify which must be a unique UUID v4 format, if not specified will be generated automatically.")
    private java.lang.String clientId;
    @JsonProperty("audience")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<String> audience = new LinkedHashSet<String>();
    /**
     * The status of this OIDC credential which can be disabled or otherwise paused for security reasons.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this OIDC credential which can be disabled or otherwise paused for security reasons.")
    private OidcStatus status = null;
    /**
     * How long the access token is valid for before expiring.
     * 
     */
    @JsonProperty("access_token_lifespan")
    @JsonPropertyDescription("How long the access token is valid for before expiring.")
    private java.lang.String accessTokenLifespan;
    /**
     * How long the refresh token is valid for before expiring.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    @JsonPropertyDescription("How long the refresh token is valid for before expiring.")
    private java.lang.String refreshTokenLifespan;
    /**
     * The HTTP redirect urls that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_redirect_urls")
    @JsonPropertyDescription("The HTTP redirect urls that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080")
    private List<java.lang.String> authorizedRedirectUrls = new ArrayList<java.lang.String>();
    /**
     * The scopes to apply and update for the client.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes to apply and update for the client.")
    private List<java.lang.String> scopes = new ArrayList<java.lang.String>();
    /**
     * The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_origins")
    @JsonPropertyDescription("The HTTP origins that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080")
    private List<java.lang.String> authorizedOrigins = new ArrayList<java.lang.String>();
    /**
     * UserInfoResponseAlgo
     * <p>
     * The algorithim to use when returning user info. Switching to RS256 will return a signed JWT token. By default this is the return.
     * 
     */
    @JsonProperty("userinfo_signed_response_alg")
    @JsonPropertyDescription("The algorithim to use when returning user info. Switching to RS256 will return a signed JWT token. By default this is the return.")
    private HydraClientRequest.UserInfoResponseAlgo userinfoSignedResponseAlg;

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

    public HydraClientRequest withAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
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

    public HydraClientRequest withTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
        return this;
    }

    @JsonProperty("subject_type")
    public SubjectType getSubjectType() {
        return subjectType;
    }

    @JsonProperty("subject_type")
    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public HydraClientRequest withSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
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

    public HydraClientRequest withName(java.lang.String name) {
        this.name = name;
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

    public HydraClientRequest withClientId(java.lang.String clientId) {
        this.clientId = clientId;
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

    public HydraClientRequest withAudience(Set<String> audience) {
        this.audience = audience;
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

    public HydraClientRequest withStatus(OidcStatus status) {
        this.status = status;
        return this;
    }

    /**
     * How long the access token is valid for before expiring.
     * 
     */
    @JsonProperty("access_token_lifespan")
    public java.lang.String getAccessTokenLifespan() {
        return accessTokenLifespan;
    }

    /**
     * How long the access token is valid for before expiring.
     * 
     */
    @JsonProperty("access_token_lifespan")
    public void setAccessTokenLifespan(java.lang.String accessTokenLifespan) {
        this.accessTokenLifespan = accessTokenLifespan;
    }

    public HydraClientRequest withAccessTokenLifespan(java.lang.String accessTokenLifespan) {
        this.accessTokenLifespan = accessTokenLifespan;
        return this;
    }

    /**
     * How long the refresh token is valid for before expiring.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    public java.lang.String getRefreshTokenLifespan() {
        return refreshTokenLifespan;
    }

    /**
     * How long the refresh token is valid for before expiring.
     * 
     */
    @JsonProperty("refresh_token_lifespan")
    public void setRefreshTokenLifespan(java.lang.String refreshTokenLifespan) {
        this.refreshTokenLifespan = refreshTokenLifespan;
    }

    public HydraClientRequest withRefreshTokenLifespan(java.lang.String refreshTokenLifespan) {
        this.refreshTokenLifespan = refreshTokenLifespan;
        return this;
    }

    /**
     * The HTTP redirect urls that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_redirect_urls")
    public List<java.lang.String> getAuthorizedRedirectUrls() {
        return authorizedRedirectUrls;
    }

    /**
     * The HTTP redirect urls that host your web application. This value can't contain wildcards or paths. If you use a port other than 80, you must specify it. For example: https://example.com:8080
     * 
     */
    @JsonProperty("authorized_redirect_urls")
    public void setAuthorizedRedirectUrls(List<java.lang.String> authorizedRedirectUrls) {
        this.authorizedRedirectUrls = authorizedRedirectUrls;
    }

    public HydraClientRequest withAuthorizedRedirectUrls(List<java.lang.String> authorizedRedirectUrls) {
        this.authorizedRedirectUrls = authorizedRedirectUrls;
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

    public HydraClientRequest withScopes(List<java.lang.String> scopes) {
        this.scopes = scopes;
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

    public HydraClientRequest withAuthorizedOrigins(List<java.lang.String> authorizedOrigins) {
        this.authorizedOrigins = authorizedOrigins;
        return this;
    }

    /**
     * UserInfoResponseAlgo
     * <p>
     * The algorithim to use when returning user info. Switching to RS256 will return a signed JWT token. By default this is the return.
     * 
     */
    @JsonProperty("userinfo_signed_response_alg")
    public HydraClientRequest.UserInfoResponseAlgo getUserinfoSignedResponseAlg() {
        return userinfoSignedResponseAlg;
    }

    /**
     * UserInfoResponseAlgo
     * <p>
     * The algorithim to use when returning user info. Switching to RS256 will return a signed JWT token. By default this is the return.
     * 
     */
    @JsonProperty("userinfo_signed_response_alg")
    public void setUserinfoSignedResponseAlg(HydraClientRequest.UserInfoResponseAlgo userinfoSignedResponseAlg) {
        this.userinfoSignedResponseAlg = userinfoSignedResponseAlg;
    }

    public HydraClientRequest withUserinfoSignedResponseAlg(HydraClientRequest.UserInfoResponseAlgo userinfoSignedResponseAlg) {
        this.userinfoSignedResponseAlg = userinfoSignedResponseAlg;
        return this;
    }


    /**
     * UserInfoResponseAlgo
     * <p>
     * The algorithim to use when returning user info. Switching to RS256 will return a signed JWT token. By default this is the return.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum UserInfoResponseAlgo {

        RS_256("RS256"),
        NONE("none");
        private final java.lang.String value;
        private final static Map<java.lang.String, HydraClientRequest.UserInfoResponseAlgo> CONSTANTS = new HashMap<java.lang.String, HydraClientRequest.UserInfoResponseAlgo>();

        static {
            for (HydraClientRequest.UserInfoResponseAlgo c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        UserInfoResponseAlgo(java.lang.String value) {
            this.value = value;
        }

        @Override
        public java.lang.String toString() {
            return this.value;
        }

        @JsonValue
        public java.lang.String value() {
            return this.value;
        }

        @JsonCreator
        public static HydraClientRequest.UserInfoResponseAlgo fromValue(java.lang.String value) {
            HydraClientRequest.UserInfoResponseAlgo constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
