
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The required features for this project template in order to function.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dns",
    "credentials",
    "git",
    "oauth_client",
    "app",
    "services"
})
@Generated("jsonschema2pojo")
public class RequiredFeatures {

    /**
     * Requires that DNS be configured and setup for this. Assumes {project}.dev.klustr.io
     * 
     */
    @JsonProperty("dns")
    @JsonPropertyDescription("Requires that DNS be configured and setup for this. Assumes {project}.dev.klustr.io")
    private Dns dns;
    /**
     * TemplateCredentialsConfiguration
     * <p>
     * Credentials are required for this project to leverage and connect. Can configure 
     * 
     */
    @JsonProperty("credentials")
    @JsonPropertyDescription("Credentials are required for this project to leverage and connect. Can configure ")
    private TemplateCredentialsConfiguration credentials;
    /**
     * TemplateGitConfiguration
     * <p>
     * Requires that a git project be configured on this.
     * 
     */
    @JsonProperty("git")
    @JsonPropertyDescription("Requires that a git project be configured on this.")
    private TemplateGitConfiguration git;
    /**
     * TemplateOauthConfiguration
     * <p>
     * Requires that credentials are active for this project.
     * 
     */
    @JsonProperty("oauth_client")
    @JsonPropertyDescription("Requires that credentials are active for this project.")
    private TemplateOauthConfiguration oauthClient;
    /**
     * TemplateAppConfiguration
     * <p>
     * Requires a user to setup their application.
     * 
     */
    @JsonProperty("app")
    @JsonPropertyDescription("Requires a user to setup their application.")
    private TemplateAppConfiguration app;
    /**
     * The services that this project relies on for example Identity, Consent, etc
     * 
     */
    @JsonProperty("services")
    @JsonPropertyDescription("The services that this project relies on for example Identity, Consent, etc")
    private List<String> services = new ArrayList<String>();

    /**
     * Requires that DNS be configured and setup for this. Assumes {project}.dev.klustr.io
     * 
     */
    @JsonProperty("dns")
    public Dns getDns() {
        return dns;
    }

    /**
     * Requires that DNS be configured and setup for this. Assumes {project}.dev.klustr.io
     * 
     */
    @JsonProperty("dns")
    public void setDns(Dns dns) {
        this.dns = dns;
    }

    public RequiredFeatures withDns(Dns dns) {
        this.dns = dns;
        return this;
    }

    /**
     * TemplateCredentialsConfiguration
     * <p>
     * Credentials are required for this project to leverage and connect. Can configure 
     * 
     */
    @JsonProperty("credentials")
    public TemplateCredentialsConfiguration getCredentials() {
        return credentials;
    }

    /**
     * TemplateCredentialsConfiguration
     * <p>
     * Credentials are required for this project to leverage and connect. Can configure 
     * 
     */
    @JsonProperty("credentials")
    public void setCredentials(TemplateCredentialsConfiguration credentials) {
        this.credentials = credentials;
    }

    public RequiredFeatures withCredentials(TemplateCredentialsConfiguration credentials) {
        this.credentials = credentials;
        return this;
    }

    /**
     * TemplateGitConfiguration
     * <p>
     * Requires that a git project be configured on this.
     * 
     */
    @JsonProperty("git")
    public TemplateGitConfiguration getGit() {
        return git;
    }

    /**
     * TemplateGitConfiguration
     * <p>
     * Requires that a git project be configured on this.
     * 
     */
    @JsonProperty("git")
    public void setGit(TemplateGitConfiguration git) {
        this.git = git;
    }

    public RequiredFeatures withGit(TemplateGitConfiguration git) {
        this.git = git;
        return this;
    }

    /**
     * TemplateOauthConfiguration
     * <p>
     * Requires that credentials are active for this project.
     * 
     */
    @JsonProperty("oauth_client")
    public TemplateOauthConfiguration getOauthClient() {
        return oauthClient;
    }

    /**
     * TemplateOauthConfiguration
     * <p>
     * Requires that credentials are active for this project.
     * 
     */
    @JsonProperty("oauth_client")
    public void setOauthClient(TemplateOauthConfiguration oauthClient) {
        this.oauthClient = oauthClient;
    }

    public RequiredFeatures withOauthClient(TemplateOauthConfiguration oauthClient) {
        this.oauthClient = oauthClient;
        return this;
    }

    /**
     * TemplateAppConfiguration
     * <p>
     * Requires a user to setup their application.
     * 
     */
    @JsonProperty("app")
    public TemplateAppConfiguration getApp() {
        return app;
    }

    /**
     * TemplateAppConfiguration
     * <p>
     * Requires a user to setup their application.
     * 
     */
    @JsonProperty("app")
    public void setApp(TemplateAppConfiguration app) {
        this.app = app;
    }

    public RequiredFeatures withApp(TemplateAppConfiguration app) {
        this.app = app;
        return this;
    }

    /**
     * The services that this project relies on for example Identity, Consent, etc
     * 
     */
    @JsonProperty("services")
    public List<String> getServices() {
        return services;
    }

    /**
     * The services that this project relies on for example Identity, Consent, etc
     * 
     */
    @JsonProperty("services")
    public void setServices(List<String> services) {
        this.services = services;
    }

    public RequiredFeatures withServices(List<String> services) {
        this.services = services;
        return this;
    }

}
