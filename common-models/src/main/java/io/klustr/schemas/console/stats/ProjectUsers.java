
package io.klustr.schemas.console.stats;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectUsers
 * <p>
 * Information on the users who logged in and accessed an app.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "project_id",
    "client_id",
    "users"
})
@Generated("jsonschema2pojo")
public class ProjectUsers {

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
     * The users who have been in the project
     * 
     */
    @JsonProperty("users")
    @JsonPropertyDescription("The users who have been in the project")
    private List<ProjectUserReference> users = new ArrayList<ProjectUserReference>();

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

    public ProjectUsers withId(String id) {
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

    public ProjectUsers withProjectId(String projectId) {
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

    public ProjectUsers withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The users who have been in the project
     * 
     */
    @JsonProperty("users")
    public List<ProjectUserReference> getUsers() {
        return users;
    }

    /**
     * The users who have been in the project
     * 
     */
    @JsonProperty("users")
    public void setUsers(List<ProjectUserReference> users) {
        this.users = users;
    }

    public ProjectUsers withUsers(List<ProjectUserReference> users) {
        this.users = users;
        return this;
    }

}
