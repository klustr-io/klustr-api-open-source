
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * GitProjectDockerImageReference
 * <p>
 * The request to create a new group.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "path",
    "location",
    "image_id",
    "revision",
    "created_at",
    "total_size"
})
@Generated("jsonschema2pojo")
public class GitProjectDockerImageReference {

    @JsonProperty("name")
    private String name;
    @JsonProperty("path")
    private String path;
    @JsonProperty("location")
    private String location;
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("revision")
    private String revision;
    @JsonProperty("created_at")
    private DateTime createdAt;
    @JsonProperty("total_size")
    private Long totalSize;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitProjectDockerImageReference withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public GitProjectDockerImageReference withPath(String path) {
        this.path = path;
        return this;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    public GitProjectDockerImageReference withLocation(String location) {
        this.location = location;
        return this;
    }

    @JsonProperty("image_id")
    public String getImageId() {
        return imageId;
    }

    @JsonProperty("image_id")
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public GitProjectDockerImageReference withImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }

    @JsonProperty("revision")
    public String getRevision() {
        return revision;
    }

    @JsonProperty("revision")
    public void setRevision(String revision) {
        this.revision = revision;
    }

    public GitProjectDockerImageReference withRevision(String revision) {
        this.revision = revision;
        return this;
    }

    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public GitProjectDockerImageReference withCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("total_size")
    public Long getTotalSize() {
        return totalSize;
    }

    @JsonProperty("total_size")
    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public GitProjectDockerImageReference withTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

}
