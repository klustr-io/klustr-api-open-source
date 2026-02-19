
package io.klustr.schemas.console.features.credentials;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureCredentialsConfiguration
 * <p>
 * Credential capability required to generate and inject secrets.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "description",
    "basic_credentials",
    "oidc_credentials"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureCredentialsConfiguration {

    /**
     * Human-readable explanation for why credentials are required.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("Human-readable explanation for why credentials are required.")
    private String description;
    /**
     * ProjectFeatureBasicCredentialsConfiguration
     * <p>
     * Basic username/password credential configuration.
     * 
     */
    @JsonProperty("basic_credentials")
    @JsonPropertyDescription("Basic username/password credential configuration.")
    private ProjectFeatureBasicCredentialsConfiguration basicCredentials;
    /**
     * ProjectFeatureOIDCCredentialsConfiguration
     * <p>
     * OIDC configuration credentials required for the project
     * 
     */
    @JsonProperty("oidc_credentials")
    @JsonPropertyDescription("OIDC configuration credentials required for the project")
    private ProjectFeatureOIDCCredentialsConfiguration oidcCredentials;

    /**
     * Human-readable explanation for why credentials are required.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Human-readable explanation for why credentials are required.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectFeatureCredentialsConfiguration withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * ProjectFeatureBasicCredentialsConfiguration
     * <p>
     * Basic username/password credential configuration.
     * 
     */
    @JsonProperty("basic_credentials")
    public ProjectFeatureBasicCredentialsConfiguration getBasicCredentials() {
        return basicCredentials;
    }

    /**
     * ProjectFeatureBasicCredentialsConfiguration
     * <p>
     * Basic username/password credential configuration.
     * 
     */
    @JsonProperty("basic_credentials")
    public void setBasicCredentials(ProjectFeatureBasicCredentialsConfiguration basicCredentials) {
        this.basicCredentials = basicCredentials;
    }

    public ProjectFeatureCredentialsConfiguration withBasicCredentials(ProjectFeatureBasicCredentialsConfiguration basicCredentials) {
        this.basicCredentials = basicCredentials;
        return this;
    }

    /**
     * ProjectFeatureOIDCCredentialsConfiguration
     * <p>
     * OIDC configuration credentials required for the project
     * 
     */
    @JsonProperty("oidc_credentials")
    public ProjectFeatureOIDCCredentialsConfiguration getOidcCredentials() {
        return oidcCredentials;
    }

    /**
     * ProjectFeatureOIDCCredentialsConfiguration
     * <p>
     * OIDC configuration credentials required for the project
     * 
     */
    @JsonProperty("oidc_credentials")
    public void setOidcCredentials(ProjectFeatureOIDCCredentialsConfiguration oidcCredentials) {
        this.oidcCredentials = oidcCredentials;
    }

    public ProjectFeatureCredentialsConfiguration withOidcCredentials(ProjectFeatureOIDCCredentialsConfiguration oidcCredentials) {
        this.oidcCredentials = oidcCredentials;
        return this;
    }

}
