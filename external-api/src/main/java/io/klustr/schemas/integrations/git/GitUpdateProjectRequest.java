
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitUpdateProjectRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "avatar",
    "auto_devops_enabled",
    "auto_devops_deploy_strategy"
})
@Generated("jsonschema2pojo")
public class GitUpdateProjectRequest {

    /**
     * The name of the new project.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the new project.")
    private String name;
    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("Short project description.")
    private String description;
    /**
     * Short project description.
     * 
     */
    @JsonProperty("avatar")
    @JsonPropertyDescription("Short project description.")
    private String avatar;
    @JsonProperty("auto_devops_enabled")
    private Boolean autoDevopsEnabled = true;
    @JsonProperty("auto_devops_deploy_strategy")
    private String autoDevopsDeployStrategy = "continuous";

    /**
     * The name of the new project.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the new project.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitUpdateProjectRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public GitUpdateProjectRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public GitUpdateProjectRequest withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @JsonProperty("auto_devops_enabled")
    public Boolean getAutoDevopsEnabled() {
        return autoDevopsEnabled;
    }

    @JsonProperty("auto_devops_enabled")
    public void setAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
    }

    public GitUpdateProjectRequest withAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
        return this;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public String getAutoDevopsDeployStrategy() {
        return autoDevopsDeployStrategy;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public void setAutoDevopsDeployStrategy(String autoDevopsDeployStrategy) {
        this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
    }

    public GitUpdateProjectRequest withAutoDevopsDeployStrategy(String autoDevopsDeployStrategy) {
        this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
        return this;
    }

}
