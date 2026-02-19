
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateAppConfiguration
 * <p>
 * Requires a user to setup their application.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "logo_url",
    "app_links",
    "domains",
    "developers",
    "support_email",
    "scopes"
})
@Generated("jsonschema2pojo")
public class TemplateAppConfiguration {

    /**
     * The application name to use.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The application name to use.")
    private String name;
    /**
     * The application logo to use.
     * 
     */
    @JsonProperty("logo_url")
    @JsonPropertyDescription("The application logo to use.")
    private String logoUrl;
    /**
     * TemplateAppLinksConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("app_links")
    private TemplateAppLinksConfiguration appLinks;
    @JsonProperty("domains")
    private List<String> domains = new ArrayList<String>();
    @JsonProperty("developers")
    private List<String> developers = new ArrayList<String>();
    @JsonProperty("support_email")
    private String supportEmail;
    /**
     * The consent scopes that the credential is needed by this template.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The consent scopes that the credential is needed by this template.")
    private List<String> scopes = new ArrayList<String>();

    /**
     * The application name to use.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The application name to use.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public TemplateAppConfiguration withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The application logo to use.
     * 
     */
    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * The application logo to use.
     * 
     */
    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public TemplateAppConfiguration withLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    /**
     * TemplateAppLinksConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("app_links")
    public TemplateAppLinksConfiguration getAppLinks() {
        return appLinks;
    }

    /**
     * TemplateAppLinksConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("app_links")
    public void setAppLinks(TemplateAppLinksConfiguration appLinks) {
        this.appLinks = appLinks;
    }

    public TemplateAppConfiguration withAppLinks(TemplateAppLinksConfiguration appLinks) {
        this.appLinks = appLinks;
        return this;
    }

    @JsonProperty("domains")
    public List<String> getDomains() {
        return domains;
    }

    @JsonProperty("domains")
    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public TemplateAppConfiguration withDomains(List<String> domains) {
        this.domains = domains;
        return this;
    }

    @JsonProperty("developers")
    public List<String> getDevelopers() {
        return developers;
    }

    @JsonProperty("developers")
    public void setDevelopers(List<String> developers) {
        this.developers = developers;
    }

    public TemplateAppConfiguration withDevelopers(List<String> developers) {
        this.developers = developers;
        return this;
    }

    @JsonProperty("support_email")
    public String getSupportEmail() {
        return supportEmail;
    }

    @JsonProperty("support_email")
    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public TemplateAppConfiguration withSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
        return this;
    }

    /**
     * The consent scopes that the credential is needed by this template.
     * 
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * The consent scopes that the credential is needed by this template.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public TemplateAppConfiguration withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

}
