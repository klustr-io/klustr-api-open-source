
package io.klustr.schemas.integrations.git;

import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitProjectStatistics
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "commit_count",
    "storage_size",
    "repository_size",
    "wiki_size",
    "lfs_objects_size",
    "job_artifacts_size",
    "pipeline_artifacts_size",
    "packages_size",
    "snippets_size",
    "uploads_size",
    "container_registry_size",
    "languages"
})
@Generated("jsonschema2pojo")
public class GitProjectStatistics {

    @JsonProperty("commit_count")
    private Integer commitCount;
    @JsonProperty("storage_size")
    private Integer storageSize;
    @JsonProperty("repository_size")
    private Integer repositorySize;
    @JsonProperty("wiki_size")
    private Integer wikiSize;
    @JsonProperty("lfs_objects_size")
    private Integer lfsObjectsSize;
    @JsonProperty("job_artifacts_size")
    private Integer jobArtifactsSize;
    @JsonProperty("pipeline_artifacts_size")
    private Integer pipelineArtifactsSize;
    @JsonProperty("packages_size")
    private Integer packagesSize;
    @JsonProperty("snippets_size")
    private Integer snippetsSize;
    @JsonProperty("uploads_size")
    private Integer uploadsSize;
    @JsonProperty("container_registry_size")
    private Integer containerRegistrySize;
    /**
     * statistics about the languages found in the git project. With the percent of each file type.
     * 
     */
    @JsonProperty("languages")
    @JsonPropertyDescription("statistics about the languages found in the git project. With the percent of each file type.")
    private Map<String, Double> languages;

    @JsonProperty("commit_count")
    public Integer getCommitCount() {
        return commitCount;
    }

    @JsonProperty("commit_count")
    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public GitProjectStatistics withCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
        return this;
    }

    @JsonProperty("storage_size")
    public Integer getStorageSize() {
        return storageSize;
    }

    @JsonProperty("storage_size")
    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    public GitProjectStatistics withStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
        return this;
    }

    @JsonProperty("repository_size")
    public Integer getRepositorySize() {
        return repositorySize;
    }

    @JsonProperty("repository_size")
    public void setRepositorySize(Integer repositorySize) {
        this.repositorySize = repositorySize;
    }

    public GitProjectStatistics withRepositorySize(Integer repositorySize) {
        this.repositorySize = repositorySize;
        return this;
    }

    @JsonProperty("wiki_size")
    public Integer getWikiSize() {
        return wikiSize;
    }

    @JsonProperty("wiki_size")
    public void setWikiSize(Integer wikiSize) {
        this.wikiSize = wikiSize;
    }

    public GitProjectStatistics withWikiSize(Integer wikiSize) {
        this.wikiSize = wikiSize;
        return this;
    }

    @JsonProperty("lfs_objects_size")
    public Integer getLfsObjectsSize() {
        return lfsObjectsSize;
    }

    @JsonProperty("lfs_objects_size")
    public void setLfsObjectsSize(Integer lfsObjectsSize) {
        this.lfsObjectsSize = lfsObjectsSize;
    }

    public GitProjectStatistics withLfsObjectsSize(Integer lfsObjectsSize) {
        this.lfsObjectsSize = lfsObjectsSize;
        return this;
    }

    @JsonProperty("job_artifacts_size")
    public Integer getJobArtifactsSize() {
        return jobArtifactsSize;
    }

    @JsonProperty("job_artifacts_size")
    public void setJobArtifactsSize(Integer jobArtifactsSize) {
        this.jobArtifactsSize = jobArtifactsSize;
    }

    public GitProjectStatistics withJobArtifactsSize(Integer jobArtifactsSize) {
        this.jobArtifactsSize = jobArtifactsSize;
        return this;
    }

    @JsonProperty("pipeline_artifacts_size")
    public Integer getPipelineArtifactsSize() {
        return pipelineArtifactsSize;
    }

    @JsonProperty("pipeline_artifacts_size")
    public void setPipelineArtifactsSize(Integer pipelineArtifactsSize) {
        this.pipelineArtifactsSize = pipelineArtifactsSize;
    }

    public GitProjectStatistics withPipelineArtifactsSize(Integer pipelineArtifactsSize) {
        this.pipelineArtifactsSize = pipelineArtifactsSize;
        return this;
    }

    @JsonProperty("packages_size")
    public Integer getPackagesSize() {
        return packagesSize;
    }

    @JsonProperty("packages_size")
    public void setPackagesSize(Integer packagesSize) {
        this.packagesSize = packagesSize;
    }

    public GitProjectStatistics withPackagesSize(Integer packagesSize) {
        this.packagesSize = packagesSize;
        return this;
    }

    @JsonProperty("snippets_size")
    public Integer getSnippetsSize() {
        return snippetsSize;
    }

    @JsonProperty("snippets_size")
    public void setSnippetsSize(Integer snippetsSize) {
        this.snippetsSize = snippetsSize;
    }

    public GitProjectStatistics withSnippetsSize(Integer snippetsSize) {
        this.snippetsSize = snippetsSize;
        return this;
    }

    @JsonProperty("uploads_size")
    public Integer getUploadsSize() {
        return uploadsSize;
    }

    @JsonProperty("uploads_size")
    public void setUploadsSize(Integer uploadsSize) {
        this.uploadsSize = uploadsSize;
    }

    public GitProjectStatistics withUploadsSize(Integer uploadsSize) {
        this.uploadsSize = uploadsSize;
        return this;
    }

    @JsonProperty("container_registry_size")
    public Integer getContainerRegistrySize() {
        return containerRegistrySize;
    }

    @JsonProperty("container_registry_size")
    public void setContainerRegistrySize(Integer containerRegistrySize) {
        this.containerRegistrySize = containerRegistrySize;
    }

    public GitProjectStatistics withContainerRegistrySize(Integer containerRegistrySize) {
        this.containerRegistrySize = containerRegistrySize;
        return this;
    }

    /**
     * statistics about the languages found in the git project. With the percent of each file type.
     * 
     */
    @JsonProperty("languages")
    public Map<String, Double> getLanguages() {
        return languages;
    }

    /**
     * statistics about the languages found in the git project. With the percent of each file type.
     * 
     */
    @JsonProperty("languages")
    public void setLanguages(Map<String, Double> languages) {
        this.languages = languages;
    }

    public GitProjectStatistics withLanguages(Map<String, Double> languages) {
        this.languages = languages;
        return this;
    }

}
