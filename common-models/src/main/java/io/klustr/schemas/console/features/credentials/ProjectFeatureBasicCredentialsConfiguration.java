
package io.klustr.schemas.console.features.credentials;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureBasicCredentialsConfiguration
 * <p>
 * Basic username/password credential configuration.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "label",
    "env_var_name_username",
    "env_var_name_password",
    "required_password_length"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureBasicCredentialsConfiguration {

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
     * Environment variable name for the username.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_username")
    @JsonPropertyDescription("Environment variable name for the username.")
    private String envVarNameUsername;
    /**
     * Environment variable name for the password.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_password")
    @JsonPropertyDescription("Environment variable name for the password.")
    private String envVarNamePassword;
    /**
     * Length of generated password.
     * 
     */
    @JsonProperty("required_password_length")
    @JsonPropertyDescription("Length of generated password.")
    private Integer requiredPasswordLength;

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

    public ProjectFeatureBasicCredentialsConfiguration withId(String id) {
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

    public ProjectFeatureBasicCredentialsConfiguration withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * Environment variable name for the username.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_username")
    public String getEnvVarNameUsername() {
        return envVarNameUsername;
    }

    /**
     * Environment variable name for the username.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_username")
    public void setEnvVarNameUsername(String envVarNameUsername) {
        this.envVarNameUsername = envVarNameUsername;
    }

    public ProjectFeatureBasicCredentialsConfiguration withEnvVarNameUsername(String envVarNameUsername) {
        this.envVarNameUsername = envVarNameUsername;
        return this;
    }

    /**
     * Environment variable name for the password.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_password")
    public String getEnvVarNamePassword() {
        return envVarNamePassword;
    }

    /**
     * Environment variable name for the password.
     * (Required)
     * 
     */
    @JsonProperty("env_var_name_password")
    public void setEnvVarNamePassword(String envVarNamePassword) {
        this.envVarNamePassword = envVarNamePassword;
    }

    public ProjectFeatureBasicCredentialsConfiguration withEnvVarNamePassword(String envVarNamePassword) {
        this.envVarNamePassword = envVarNamePassword;
        return this;
    }

    /**
     * Length of generated password.
     * 
     */
    @JsonProperty("required_password_length")
    public Integer getRequiredPasswordLength() {
        return requiredPasswordLength;
    }

    /**
     * Length of generated password.
     * 
     */
    @JsonProperty("required_password_length")
    public void setRequiredPasswordLength(Integer requiredPasswordLength) {
        this.requiredPasswordLength = requiredPasswordLength;
    }

    public ProjectFeatureBasicCredentialsConfiguration withRequiredPasswordLength(Integer requiredPasswordLength) {
        this.requiredPasswordLength = requiredPasswordLength;
        return this;
    }

}
