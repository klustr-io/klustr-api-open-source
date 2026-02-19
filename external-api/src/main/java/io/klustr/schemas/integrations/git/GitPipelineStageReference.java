
package io.klustr.schemas.integrations.git;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitPipelineStageReference
 * <p>
 * Bundled information for a CI job which may have multiple stages
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stage",
    "jobs"
})
@Generated("jsonschema2pojo")
public class GitPipelineStageReference {

    /**
     * The stage that is referenced.
     * 
     */
    @JsonProperty("stage")
    @JsonPropertyDescription("The stage that is referenced.")
    private String stage;
    /**
     * The jobs made up of this stage.
     * 
     */
    @JsonProperty("jobs")
    @JsonPropertyDescription("The jobs made up of this stage.")
    private List<GitPipelineJobReference> jobs = new ArrayList<GitPipelineJobReference>();

    /**
     * The stage that is referenced.
     * 
     */
    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }

    /**
     * The stage that is referenced.
     * 
     */
    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    public GitPipelineStageReference withStage(String stage) {
        this.stage = stage;
        return this;
    }

    /**
     * The jobs made up of this stage.
     * 
     */
    @JsonProperty("jobs")
    public List<GitPipelineJobReference> getJobs() {
        return jobs;
    }

    /**
     * The jobs made up of this stage.
     * 
     */
    @JsonProperty("jobs")
    public void setJobs(List<GitPipelineJobReference> jobs) {
        this.jobs = jobs;
    }

    public GitPipelineStageReference withJobs(List<GitPipelineJobReference> jobs) {
        this.jobs = jobs;
        return this;
    }

}
