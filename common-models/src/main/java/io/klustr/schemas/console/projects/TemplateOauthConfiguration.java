
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateOauthConfiguration
 * <p>
 * Requires that credentials are active for this project.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "token_auth_method",
    "authorized_domains",
    "authorized_redirect_uris"
})
@Generated("jsonschema2pojo")
public class TemplateOauthConfiguration {

    @JsonProperty("name")
    private String name;
    @JsonProperty("token_auth_method")
    private String tokenAuthMethod;
    @JsonProperty("authorized_domains")
    private List<String> authorizedDomains = new ArrayList<String>();
    @JsonProperty("authorized_redirect_uris")
    private List<String> authorizedRedirectUris = new ArrayList<String>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public TemplateOauthConfiguration withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("token_auth_method")
    public String getTokenAuthMethod() {
        return tokenAuthMethod;
    }

    @JsonProperty("token_auth_method")
    public void setTokenAuthMethod(String tokenAuthMethod) {
        this.tokenAuthMethod = tokenAuthMethod;
    }

    public TemplateOauthConfiguration withTokenAuthMethod(String tokenAuthMethod) {
        this.tokenAuthMethod = tokenAuthMethod;
        return this;
    }

    @JsonProperty("authorized_domains")
    public List<String> getAuthorizedDomains() {
        return authorizedDomains;
    }

    @JsonProperty("authorized_domains")
    public void setAuthorizedDomains(List<String> authorizedDomains) {
        this.authorizedDomains = authorizedDomains;
    }

    public TemplateOauthConfiguration withAuthorizedDomains(List<String> authorizedDomains) {
        this.authorizedDomains = authorizedDomains;
        return this;
    }

    @JsonProperty("authorized_redirect_uris")
    public List<String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }

    @JsonProperty("authorized_redirect_uris")
    public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
    }

    public TemplateOauthConfiguration withAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
        return this;
    }

}
