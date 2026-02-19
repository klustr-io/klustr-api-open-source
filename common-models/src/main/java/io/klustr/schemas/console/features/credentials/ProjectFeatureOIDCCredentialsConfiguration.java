
package io.klustr.schemas.console.features.credentials;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureOIDCCredentialsConfiguration
 * <p>
 * OIDC configuration credentials required for the project
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "label",
    "env_var_name_client_id",
    "env_var_name_client_secret"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureOIDCCredentialsConfiguration {

    /**
     * Unique identifier for the credential.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Unique identifier for the credential.")
    private String id;
    /**
     * Label shown to users for this credential.
     * 
     */
    @JsonProperty("label")
    @JsonPropertyDescription("Label shown to users for this credential.")
    private String label;
    /**
     * Environment variable name for the client ID.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_id")
    @JsonPropertyDescription("Environment variable name for the client ID.")
    private String envVarNameClientId;
    /**
     * Environment variable name for the client secret.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_secret")
    @JsonPropertyDescription("Environment variable name for the client secret.")
    private String envVarNameClientSecret;

    /**
     * Unique identifier for the credential.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Unique identifier for the credential.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectFeatureOIDCCredentialsConfiguration withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Label shown to users for this credential.
     * 
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * Label shown to users for this credential.
     * 
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    public ProjectFeatureOIDCCredentialsConfiguration withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * Environment variable name for the client ID.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_id")
    public String getEnvVarNameClientId() {
        return envVarNameClientId;
    }

    /**
     * Environment variable name for the client ID.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_id")
    public void setEnvVarNameClientId(String envVarNameClientId) {
        this.envVarNameClientId = envVarNameClientId;
    }

    public ProjectFeatureOIDCCredentialsConfiguration withEnvVarNameClientId(String envVarNameClientId) {
        this.envVarNameClientId = envVarNameClientId;
        return this;
    }

    /**
     * Environment variable name for the client secret.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_secret")
    public String getEnvVarNameClientSecret() {
        return envVarNameClientSecret;
    }

    /**
     * Environment variable name for the client secret.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_client_secret")
    public void setEnvVarNameClientSecret(String envVarNameClientSecret) {
        this.envVarNameClientSecret = envVarNameClientSecret;
    }

    public ProjectFeatureOIDCCredentialsConfiguration withEnvVarNameClientSecret(String envVarNameClientSecret) {
        this.envVarNameClientSecret = envVarNameClientSecret;
        return this;
    }

}
