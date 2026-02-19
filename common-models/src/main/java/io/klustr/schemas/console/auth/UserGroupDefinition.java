
package io.klustr.schemas.console.auth;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserGroupDefinition
 * <p>
 * The definition of the group.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "project_id",
    "users"
})
@Generated("jsonschema2pojo")
public class UserGroupDefinition {

    /**
     * The unique ID of the record.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the record.")
    private String id;
    /**
     * The friendly name for this group.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The friendly name for this group.")
    private String name;
    /**
     * The description of this group and how it is used.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of this group and how it is used.")
    private String description;
    /**
     * The project id that a user was granted right to.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project id that a user was granted right to.")
    private String projectId;
    /**
     * The users that are in this group as referenced by their email address
     * 
     */
    @JsonProperty("users")
    @JsonPropertyDescription("The users that are in this group as referenced by their email address")
    private List<String> users = new ArrayList<String>();

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

    public UserGroupDefinition withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The friendly name for this group.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The friendly name for this group.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UserGroupDefinition withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The description of this group and how it is used.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description of this group and how it is used.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public UserGroupDefinition withDescription(String description) {
        this.description = description;
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

    public UserGroupDefinition withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The users that are in this group as referenced by their email address
     * 
     */
    @JsonProperty("users")
    public List<String> getUsers() {
        return users;
    }

    /**
     * The users that are in this group as referenced by their email address
     * 
     */
    @JsonProperty("users")
    public void setUsers(List<String> users) {
        this.users = users;
    }

    public UserGroupDefinition withUsers(List<String> users) {
        this.users = users;
        return this;
    }

}
