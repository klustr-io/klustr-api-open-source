
package io.klustr.schemas.integrations.gitlab;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.integrations.git.GitUserFederatedIdentity;


/**
 * GitLabCreateUserResponse
 * <p>
 * Response from gitlab for successful creation of a user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "username",
    "state",
    "locked",
    "email",
    "avatar",
    "organization",
    "identities"
})
@Generated("jsonschema2pojo")
public class GitLabCreateUserResponse {

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the user in gitlab")
    private Object id;
    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("The username of the user in the actual gitlab instance.")
    private String username;
    /**
     * The state of the user
     * 
     */
    @JsonProperty("state")
    @JsonPropertyDescription("The state of the user")
    private String state;
    /**
     * If the user has been locked
     * 
     */
    @JsonProperty("locked")
    @JsonPropertyDescription("If the user has been locked")
    private Boolean locked;
    /**
     * The email address of the user.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email address of the user.")
    private String email;
    /**
     * The URL of the avatar for the user profile.
     * 
     */
    @JsonProperty("avatar")
    @JsonPropertyDescription("The URL of the avatar for the user profile.")
    private String avatar;
    /**
     * The organization name that this user belongs to.
     * 
     */
    @JsonProperty("organization")
    @JsonPropertyDescription("The organization name that this user belongs to.")
    private String organization;
    /**
     * The list of identities linked to this user.
     * 
     */
    @JsonProperty("identities")
    @JsonPropertyDescription("The list of identities linked to this user.")
    private List<GitUserFederatedIdentity> identities = new ArrayList<GitUserFederatedIdentity>();

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    public Object getId() {
        return id;
    }

    /**
     * The unique ID of the user in gitlab
     * 
     */
    @JsonProperty("id")
    public void setId(Object id) {
        this.id = id;
    }

    public GitLabCreateUserResponse withId(Object id) {
        this.id = id;
        return this;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public GitLabCreateUserResponse withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * The state of the user
     * 
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * The state of the user
     * 
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public GitLabCreateUserResponse withState(String state) {
        this.state = state;
        return this;
    }

    /**
     * If the user has been locked
     * 
     */
    @JsonProperty("locked")
    public Boolean getLocked() {
        return locked;
    }

    /**
     * If the user has been locked
     * 
     */
    @JsonProperty("locked")
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public GitLabCreateUserResponse withLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    /**
     * The email address of the user.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email address of the user.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public GitLabCreateUserResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The URL of the avatar for the user profile.
     * 
     */
    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    /**
     * The URL of the avatar for the user profile.
     * 
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public GitLabCreateUserResponse withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    /**
     * The organization name that this user belongs to.
     * 
     */
    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    /**
     * The organization name that this user belongs to.
     * 
     */
    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public GitLabCreateUserResponse withOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    /**
     * The list of identities linked to this user.
     * 
     */
    @JsonProperty("identities")
    public List<GitUserFederatedIdentity> getIdentities() {
        return identities;
    }

    /**
     * The list of identities linked to this user.
     * 
     */
    @JsonProperty("identities")
    public void setIdentities(List<GitUserFederatedIdentity> identities) {
        this.identities = identities;
    }

    public GitLabCreateUserResponse withIdentities(List<GitUserFederatedIdentity> identities) {
        this.identities = identities;
        return this;
    }

}
