
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitCreateUserRequest
 * <p>
 * The request used to create a new user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "extern_uid",
    "provider",
    "username",
    "name",
    "email",
    "avatar",
    "organization",
    "force_random_password"
})
@Generated("jsonschema2pojo")
public class GitCreateUserRequest {

    /**
     * External UID used when linking to OIDC or federated logins
     * 
     */
    @JsonProperty("extern_uid")
    @JsonPropertyDescription("External UID used when linking to OIDC or federated logins")
    private String externUid;
    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    @JsonPropertyDescription("The provider that created this user, used to link your system to gitlab user.")
    private String provider;
    /**
     * The username of the user in the actual gitlab instance.
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("The username of the user in the actual gitlab instance.")
    private String username;
    /**
     * The name of the user for displaying such as given name and family name.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the user for displaying such as given name and family name.")
    private String name;
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
     * Will force the password of the user to a random password
     * 
     */
    @JsonProperty("force_random_password")
    @JsonPropertyDescription("Will force the password of the user to a random password")
    private Boolean forceRandomPassword;

    /**
     * External UID used when linking to OIDC or federated logins
     * 
     */
    @JsonProperty("extern_uid")
    public String getExternUid() {
        return externUid;
    }

    /**
     * External UID used when linking to OIDC or federated logins
     * 
     */
    @JsonProperty("extern_uid")
    public void setExternUid(String externUid) {
        this.externUid = externUid;
    }

    public GitCreateUserRequest withExternUid(String externUid) {
        this.externUid = externUid;
        return this;
    }

    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public GitCreateUserRequest withProvider(String provider) {
        this.provider = provider;
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

    public GitCreateUserRequest withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * The name of the user for displaying such as given name and family name.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the user for displaying such as given name and family name.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitCreateUserRequest withName(String name) {
        this.name = name;
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

    public GitCreateUserRequest withEmail(String email) {
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

    public GitCreateUserRequest withAvatar(String avatar) {
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

    public GitCreateUserRequest withOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    /**
     * Will force the password of the user to a random password
     * 
     */
    @JsonProperty("force_random_password")
    public Boolean getForceRandomPassword() {
        return forceRandomPassword;
    }

    /**
     * Will force the password of the user to a random password
     * 
     */
    @JsonProperty("force_random_password")
    public void setForceRandomPassword(Boolean forceRandomPassword) {
        this.forceRandomPassword = forceRandomPassword;
    }

    public GitCreateUserRequest withForceRandomPassword(Boolean forceRandomPassword) {
        this.forceRandomPassword = forceRandomPassword;
        return this;
    }

}
