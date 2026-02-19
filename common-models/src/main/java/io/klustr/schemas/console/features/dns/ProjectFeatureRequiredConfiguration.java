
package io.klustr.schemas.console.features.dns;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.features.credentials.ProjectFeatureCredentialsConfiguration;


/**
 * ProjectFeatureRequiredConfiguration
 * <p>
 * Project-level capabilities required by this feature. Each capability is a first-class, typed object containing any configuration required to satisfy it.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dns",
    "credentials"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureRequiredConfiguration {

    /**
     * ProjectFeatureDnsConfiguration
     * <p>
     * DNS capability required to expose network endpoints.
     * 
     */
    @JsonProperty("dns")
    @JsonPropertyDescription("DNS capability required to expose network endpoints.")
    private ProjectFeatureDnsConfiguration dns;
    /**
     * ProjectFeatureCredentialsConfiguration
     * <p>
     * Credential capability required to generate and inject secrets.
     * 
     */
    @JsonProperty("credentials")
    @JsonPropertyDescription("Credential capability required to generate and inject secrets.")
    private ProjectFeatureCredentialsConfiguration credentials;

    /**
     * ProjectFeatureDnsConfiguration
     * <p>
     * DNS capability required to expose network endpoints.
     * 
     */
    @JsonProperty("dns")
    public ProjectFeatureDnsConfiguration getDns() {
        return dns;
    }

    /**
     * ProjectFeatureDnsConfiguration
     * <p>
     * DNS capability required to expose network endpoints.
     * 
     */
    @JsonProperty("dns")
    public void setDns(ProjectFeatureDnsConfiguration dns) {
        this.dns = dns;
    }

    public ProjectFeatureRequiredConfiguration withDns(ProjectFeatureDnsConfiguration dns) {
        this.dns = dns;
        return this;
    }

    /**
     * ProjectFeatureCredentialsConfiguration
     * <p>
     * Credential capability required to generate and inject secrets.
     * 
     */
    @JsonProperty("credentials")
    public ProjectFeatureCredentialsConfiguration getCredentials() {
        return credentials;
    }

    /**
     * ProjectFeatureCredentialsConfiguration
     * <p>
     * Credential capability required to generate and inject secrets.
     * 
     */
    @JsonProperty("credentials")
    public void setCredentials(ProjectFeatureCredentialsConfiguration credentials) {
        this.credentials = credentials;
    }

    public ProjectFeatureRequiredConfiguration withCredentials(ProjectFeatureCredentialsConfiguration credentials) {
        this.credentials = credentials;
        return this;
    }

}
