
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateCredentialsBasicConfiguration
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "label",
    "env_var_username",
    "env_var_password",
    "username",
    "password_length"
})
@Generated("jsonschema2pojo")
public class TemplateCredentialsBasicConfiguration {

    /**
     * The unique ID of this credential stored alongside the project.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this credential stored alongside the project.")
    private String id;
    /**
     * The label for a user to read and understand what this credential is for.
     * 
     */
    @JsonProperty("label")
    @JsonPropertyDescription("The label for a user to read and understand what this credential is for.")
    private String label;
    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_username")
    @JsonPropertyDescription("The environment variable name for username.")
    private String envVarUsername;
    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_password")
    @JsonPropertyDescription("The environment variable name for username.")
    private String envVarPassword;
    /**
     * The default username for the credential
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("The default username for the credential")
    private String username;
    /**
     * The minimum password length
     * 
     */
    @JsonProperty("password_length")
    @JsonPropertyDescription("The minimum password length")
    private Integer passwordLength = 12;

    /**
     * The unique ID of this credential stored alongside the project.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this credential stored alongside the project.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public TemplateCredentialsBasicConfiguration withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The label for a user to read and understand what this credential is for.
     * 
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * The label for a user to read and understand what this credential is for.
     * 
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    public TemplateCredentialsBasicConfiguration withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_username")
    public String getEnvVarUsername() {
        return envVarUsername;
    }

    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_username")
    public void setEnvVarUsername(String envVarUsername) {
        this.envVarUsername = envVarUsername;
    }

    public TemplateCredentialsBasicConfiguration withEnvVarUsername(String envVarUsername) {
        this.envVarUsername = envVarUsername;
        return this;
    }

    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_password")
    public String getEnvVarPassword() {
        return envVarPassword;
    }

    /**
     * The environment variable name for username.
     * 
     */
    @JsonProperty("env_var_password")
    public void setEnvVarPassword(String envVarPassword) {
        this.envVarPassword = envVarPassword;
    }

    public TemplateCredentialsBasicConfiguration withEnvVarPassword(String envVarPassword) {
        this.envVarPassword = envVarPassword;
        return this;
    }

    /**
     * The default username for the credential
     * 
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * The default username for the credential
     * 
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public TemplateCredentialsBasicConfiguration withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * The minimum password length
     * 
     */
    @JsonProperty("password_length")
    public Integer getPasswordLength() {
        return passwordLength;
    }

    /**
     * The minimum password length
     * 
     */
    @JsonProperty("password_length")
    public void setPasswordLength(Integer passwordLength) {
        this.passwordLength = passwordLength;
    }

    public TemplateCredentialsBasicConfiguration withPasswordLength(Integer passwordLength) {
        this.passwordLength = passwordLength;
        return this;
    }

}
