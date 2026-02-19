
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitCreateGroupRequest
 * <p>
 * The request to create a new group.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "path",
    "avatar"
})
@Generated("jsonschema2pojo")
public class GitCreateGroupRequest {

    /**
     * The name for this specific group.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this specific group.")
    private String name;
    /**
     * The path for this specific group
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The path for this specific group")
    private String path;
    /**
     * The avatar for this group for display its logo.
     * 
     */
    @JsonProperty("avatar")
    @JsonPropertyDescription("The avatar for this group for display its logo.")
    private String avatar;

    /**
     * The name for this specific group.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this specific group.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitCreateGroupRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The path for this specific group
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The path for this specific group
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public GitCreateGroupRequest withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * The avatar for this group for display its logo.
     * 
     */
    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    /**
     * The avatar for this group for display its logo.
     * 
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public GitCreateGroupRequest withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
