
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitPipelineJobReference
 * <p>
 * A specific step in the pipeline that executed or failed to execute. Includes trace and log level information.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "stage",
    "name",
    "created_at",
    "updated_at",
    "started_at",
    "finished_at",
    "duration",
    "queued_duration",
    "web_url"
})
@Generated("jsonschema2pojo")
public class GitPipelineJobReference {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("stage")
    private String stage;
    @JsonProperty("name")
    private String name;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("started_at")
    private String startedAt;
    @JsonProperty("finished_at")
    private String finishedAt;
    @JsonProperty("duration")
    private Double duration;
    @JsonProperty("queued_duration")
    private Double queuedDuration;
    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public GitPipelineJobReference withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public GitPipelineJobReference withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("stage")
    public String getStage() {
        return stage;
    }

    @JsonProperty("stage")
    public void setStage(String stage) {
        this.stage = stage;
    }

    public GitPipelineJobReference withStage(String stage) {
        this.stage = stage;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitPipelineJobReference withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public GitPipelineJobReference withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GitPipelineJobReference withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonProperty("started_at")
    public String getStartedAt() {
        return startedAt;
    }

    @JsonProperty("started_at")
    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public GitPipelineJobReference withStartedAt(String startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    @JsonProperty("finished_at")
    public String getFinishedAt() {
        return finishedAt;
    }

    @JsonProperty("finished_at")
    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public GitPipelineJobReference withFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    @JsonProperty("duration")
    public Double getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public GitPipelineJobReference withDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    @JsonProperty("queued_duration")
    public Double getQueuedDuration() {
        return queuedDuration;
    }

    @JsonProperty("queued_duration")
    public void setQueuedDuration(Double queuedDuration) {
        this.queuedDuration = queuedDuration;
    }

    public GitPipelineJobReference withQueuedDuration(Double queuedDuration) {
        this.queuedDuration = queuedDuration;
        return this;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public GitPipelineJobReference withWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

}
