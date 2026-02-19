
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateGitConfiguration
 * <p>
 * Requires that a git project be configured on this.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "git_url"
})
@Generated("jsonschema2pojo")
public class TemplateGitConfiguration {

    /**
     * The git URL that this will clone from.
     * 
     */
    @JsonProperty("git_url")
    @JsonPropertyDescription("The git URL that this will clone from.")
    private String gitUrl;

    /**
     * The git URL that this will clone from.
     * 
     */
    @JsonProperty("git_url")
    public String getGitUrl() {
        return gitUrl;
    }

    /**
     * The git URL that this will clone from.
     * 
     */
    @JsonProperty("git_url")
    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public TemplateGitConfiguration withGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
        return this;
    }

}
