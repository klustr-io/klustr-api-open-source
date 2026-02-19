
package io.klustr.schemas.console.oidc;

import java.util.HashMap;
import java.util.LinkedHashSet;
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
import io.klustr.schemas.console.TokenEndpointAuthMethod;
import org.joda.time.DateTime;


/**
 * OIDCCredential
 * <p>
 * A client that is a basic credential flow for authentication.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "client_id",
    "secret_key",
    "access_token_strategy",
    "token_endpoint_auth_method",
    "creation_date",
    "audience",
    "status",
    "metadata"
})
@Generated("jsonschema2pojo")
public class OIDCCredential {

    /**
     * The name of this client credential.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of this client credential.")
    private java.lang.String name;
    /**
     * The unique ID for this client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The unique ID for this client.")
    private java.lang.String clientId;
    /**
     * The secret key for this client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    @JsonPropertyDescription("The secret key for this client")
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
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    @JsonProperty("audience")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<String> audience = new LinkedHashSet<String>();
    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this client")
    private OIDCCredential.OidcStatus status;
    /**
     * Additional metadata related to the client to tag or otherwise manage it.
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Additional metadata related to the client to tag or otherwise manage it.")
    private Map<String, String> metadata;

    /**
     * The name of this client credential.
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * The name of this client credential.
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public OIDCCredential withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * The unique ID for this client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * The unique ID for this client.
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    public OIDCCredential withClientId(java.lang.String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The secret key for this client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    public java.lang.String getSecretKey() {
        return secretKey;
    }

    /**
     * The secret key for this client
     * (Required)
     * 
     */
    @JsonProperty("secret_key")
    public void setSecretKey(java.lang.String secretKey) {
        this.secretKey = secretKey;
    }

    public OIDCCredential withSecretKey(java.lang.String secretKey) {
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

    public OIDCCredential withAccessTokenStrategy(AccessTokenStrategy accessTokenStrategy) {
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

    public OIDCCredential withTokenEndpointAuthMethod(TokenEndpointAuthMethod tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
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

    public OIDCCredential withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
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

    public OIDCCredential withAudience(Set<String> audience) {
        this.audience = audience;
        return this;
    }

    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    public OIDCCredential.OidcStatus getStatus() {
        return status;
    }

    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @JsonProperty("status")
    public void setStatus(OIDCCredential.OidcStatus status) {
        this.status = status;
    }

    public OIDCCredential withStatus(OIDCCredential.OidcStatus status) {
        this.status = status;
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

    public OIDCCredential withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }


    /**
     * OidcStatus
     * <p>
     * The status of this client
     * 
     */
    @Generated("jsonschema2pojo")
    public enum OidcStatus {

        ENABLED("enabled"),
        DELETED("deleted");
        private final java.lang.String value;
        private final static Map<java.lang.String, OIDCCredential.OidcStatus> CONSTANTS = new HashMap<java.lang.String, OIDCCredential.OidcStatus>();

        static {
            for (OIDCCredential.OidcStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        OidcStatus(java.lang.String value) {
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
        public static OIDCCredential.OidcStatus fromValue(java.lang.String value) {
            OIDCCredential.OidcStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
