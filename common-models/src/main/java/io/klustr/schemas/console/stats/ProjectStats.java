
package io.klustr.schemas.console.stats;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectStats
 * <p>
 * Stats related to this project.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "project_id",
    "client_id",
    "unique_users"
})
@Generated("jsonschema2pojo")
public class ProjectStats {

    /**
     * The ID of this record.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The ID of this record.")
    private String id;
    /**
     * The project ID to reference.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project ID to reference.")
    private String projectId;
    /**
     * The the client ID linked to this project
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The the client ID linked to this project")
    private String clientId;
    /**
     * The total number of unique users
     * 
     */
    @JsonProperty("unique_users")
    @JsonPropertyDescription("The total number of unique users")
    private Integer uniqueUsers;

    /**
     * The ID of this record.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The ID of this record.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectStats withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The project ID to reference.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project ID to reference.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ProjectStats withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The the client ID linked to this project
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The the client ID linked to this project
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ProjectStats withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The total number of unique users
     * 
     */
    @JsonProperty("unique_users")
    public Integer getUniqueUsers() {
        return uniqueUsers;
    }

    /**
     * The total number of unique users
     * 
     */
    @JsonProperty("unique_users")
    public void setUniqueUsers(Integer uniqueUsers) {
        this.uniqueUsers = uniqueUsers;
    }

    public ProjectStats withUniqueUsers(Integer uniqueUsers) {
        this.uniqueUsers = uniqueUsers;
        return this;
    }

}
