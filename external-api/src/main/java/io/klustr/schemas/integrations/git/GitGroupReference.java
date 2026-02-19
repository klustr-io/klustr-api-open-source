
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitGroupReference
 * <p>
 * Response from gitlab for successful creation of a user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "path",
    "avatar_url",
    "web_url",
    "created_at"
})
@Generated("jsonschema2pojo")
public class GitGroupReference {

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the user in gitlab")
    private Integer id;
    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The username of the user in the actual gitlab instance.")
    private String name;
    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The username of the user in the actual gitlab instance.")
    private String path;
    /**
     * The state of the user
     * 
     */
    @JsonProperty("avatar_url")
    @JsonPropertyDescription("The state of the user")
    private String avatarUrl;
    /**
     * The preferred language for the user (en, jp, etc)
     * 
     */
    @JsonProperty("web_url")
    @JsonPropertyDescription("The preferred language for the user (en, jp, etc)")
    private String webUrl;
    /**
     * If the user can create a group
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("If the user can create a group")
    private String createdAt;

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public GitGroupReference withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitGroupReference withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public GitGroupReference withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * The state of the user
     * 
     */
    @JsonProperty("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * The state of the user
     * 
     */
    @JsonProperty("avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public GitGroupReference withAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    /**
     * The preferred language for the user (en, jp, etc)
     * 
     */
    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * The preferred language for the user (en, jp, etc)
     * 
     */
    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public GitGroupReference withWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    /**
     * If the user can create a group
     * 
     */
    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * If the user can create a group
     * 
     */
    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public GitGroupReference withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

}
