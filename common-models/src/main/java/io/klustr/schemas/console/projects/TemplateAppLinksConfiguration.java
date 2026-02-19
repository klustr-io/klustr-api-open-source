
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateAppLinksConfiguration
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "home_page_url",
    "privacy_url",
    "terms_url"
})
@Generated("jsonschema2pojo")
public class TemplateAppLinksConfiguration {

    /**
     * The home page url of the app.
     * 
     */
    @JsonProperty("home_page_url")
    @JsonPropertyDescription("The home page url of the app.")
    private String homePageUrl;
    /**
     * The privacy url for users to view.
     * 
     */
    @JsonProperty("privacy_url")
    @JsonPropertyDescription("The privacy url for users to view.")
    private String privacyUrl;
    /**
     * The terms for the application.
     * 
     */
    @JsonProperty("terms_url")
    @JsonPropertyDescription("The terms for the application.")
    private String termsUrl;

    /**
     * The home page url of the app.
     * 
     */
    @JsonProperty("home_page_url")
    public String getHomePageUrl() {
        return homePageUrl;
    }

    /**
     * The home page url of the app.
     * 
     */
    @JsonProperty("home_page_url")
    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public TemplateAppLinksConfiguration withHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
        return this;
    }

    /**
     * The privacy url for users to view.
     * 
     */
    @JsonProperty("privacy_url")
    public String getPrivacyUrl() {
        return privacyUrl;
    }

    /**
     * The privacy url for users to view.
     * 
     */
    @JsonProperty("privacy_url")
    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }

    public TemplateAppLinksConfiguration withPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
        return this;
    }

    /**
     * The terms for the application.
     * 
     */
    @JsonProperty("terms_url")
    public String getTermsUrl() {
        return termsUrl;
    }

    /**
     * The terms for the application.
     * 
     */
    @JsonProperty("terms_url")
    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }

    public TemplateAppLinksConfiguration withTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
        return this;
    }

}
