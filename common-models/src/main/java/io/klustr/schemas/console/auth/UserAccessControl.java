
package io.klustr.schemas.console.auth;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserAccessControl
 * <p>
 * Enables the ability to map users who have logged in to be managed or authorized to access specific roles and groups.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "user_id",
    "project_id",
    "groups"
})
@Generated("jsonschema2pojo")
public class UserAccessControl {

    /**
     * The unique ID of the record.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the record.")
    private String id;
    /**
     * The user id to grant permissions against.
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The user id to grant permissions against.")
    private String userId;
    /**
     * The project id that a user was granted right to.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project id that a user was granted right to.")
    private String projectId;
    /**
     * The groups related to a user.
     * 
     */
    @JsonProperty("groups")
    @JsonPropertyDescription("The groups related to a user.")
    private List<String> groups = new ArrayList<String>();

    /**
     * The unique ID of the record.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of the record.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserAccessControl withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The user id to grant permissions against.
     * 
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * The user id to grant permissions against.
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserAccessControl withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * The project id that a user was granted right to.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project id that a user was granted right to.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public UserAccessControl withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The groups related to a user.
     * 
     */
    @JsonProperty("groups")
    public List<String> getGroups() {
        return groups;
    }

    /**
     * The groups related to a user.
     * 
     */
    @JsonProperty("groups")
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public UserAccessControl withGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

}
