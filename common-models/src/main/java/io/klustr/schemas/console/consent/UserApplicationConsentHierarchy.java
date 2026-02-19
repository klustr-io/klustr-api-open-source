
package io.klustr.schemas.console.consent;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserApplicationConsentHierarchy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "app_id",
    "app_name",
    "app_logo",
    "scopes",
    "experiments"
})
@Generated("jsonschema2pojo")
public class UserApplicationConsentHierarchy {

    /**
     * The application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The application identifier for this consent.")
    private String appId;
    /**
     * The application name
     * 
     */
    @JsonProperty("app_name")
    @JsonPropertyDescription("The application name")
    private String appName;
    /**
     * The application logo
     * 
     */
    @JsonProperty("app_logo")
    @JsonPropertyDescription("The application logo")
    private URI appLogo;
    @JsonProperty("scopes")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();
    /**
     * Experiment level consent
     * 
     */
    @JsonProperty("experiments")
    @JsonPropertyDescription("Experiment level consent")
    private List<UserExperimentConsentHierarchy> experiments = new ArrayList<UserExperimentConsentHierarchy>();

    /**
     * The application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public UserApplicationConsentHierarchy withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The application name
     * 
     */
    @JsonProperty("app_name")
    public String getAppName() {
        return appName;
    }

    /**
     * The application name
     * 
     */
    @JsonProperty("app_name")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public UserApplicationConsentHierarchy withAppName(String appName) {
        this.appName = appName;
        return this;
    }

    /**
     * The application logo
     * 
     */
    @JsonProperty("app_logo")
    public URI getAppLogo() {
        return appLogo;
    }

    /**
     * The application logo
     * 
     */
    @JsonProperty("app_logo")
    public void setAppLogo(URI appLogo) {
        this.appLogo = appLogo;
    }

    public UserApplicationConsentHierarchy withAppLogo(URI appLogo) {
        this.appLogo = appLogo;
        return this;
    }

    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserApplicationConsentHierarchy withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * Experiment level consent
     * 
     */
    @JsonProperty("experiments")
    public List<UserExperimentConsentHierarchy> getExperiments() {
        return experiments;
    }

    /**
     * Experiment level consent
     * 
     */
    @JsonProperty("experiments")
    public void setExperiments(List<UserExperimentConsentHierarchy> experiments) {
        this.experiments = experiments;
    }

    public UserApplicationConsentHierarchy withExperiments(List<UserExperimentConsentHierarchy> experiments) {
        this.experiments = experiments;
        return this;
    }

}
