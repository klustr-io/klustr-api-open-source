
package io.klustr.schemas.console.features.core;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureConstraints
 * <p>
 * Constraints that limit how this feature may be applied.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "limit_one_per_project"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureConstraints {

    /**
     * If true, only one instance of this feature may exist per project.
     * 
     */
    @JsonProperty("limit_one_per_project")
    @JsonPropertyDescription("If true, only one instance of this feature may exist per project.")
    private Boolean limitOnePerProject;

    /**
     * If true, only one instance of this feature may exist per project.
     * 
     */
    @JsonProperty("limit_one_per_project")
    public Boolean getLimitOnePerProject() {
        return limitOnePerProject;
    }

    /**
     * If true, only one instance of this feature may exist per project.
     * 
     */
    @JsonProperty("limit_one_per_project")
    public void setLimitOnePerProject(Boolean limitOnePerProject) {
        this.limitOnePerProject = limitOnePerProject;
    }

    public ProjectFeatureConstraints withLimitOnePerProject(Boolean limitOnePerProject) {
        this.limitOnePerProject = limitOnePerProject;
        return this;
    }

}
