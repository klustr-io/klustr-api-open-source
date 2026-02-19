
package io.klustr.schemas.integrations.gitlab;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * GitLabCreateGroupRequest
 * <p>
 * Request made to gitlab to create a group.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "path",
    "auto_devops_enabled",
    "avatar",
    "emails_enabled",
    "mentions_disabled",
    "organization_id",
    "project_creation_level",
    "share_with_group_lock",
    "visibility",
    "shared_runners_minutes_limit"
})
@Generated("jsonschema2pojo")
public class GitLabCreateGroupRequest {

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
     * Default to Auto DevOps pipeline for all projects within this group.
     * 
     */
    @JsonProperty("auto_devops_enabled")
    @JsonPropertyDescription("Default to Auto DevOps pipeline for all projects within this group.")
    private Boolean autoDevopsEnabled;
    /**
     * The avatar for this group for display its logo.
     * 
     */
    @JsonProperty("avatar")
    @JsonPropertyDescription("The avatar for this group for display its logo.")
    private String avatar;
    /**
     * Enable email notifications.
     * 
     */
    @JsonProperty("emails_enabled")
    @JsonPropertyDescription("Enable email notifications.")
    private Boolean emailsEnabled;
    /**
     * Disable the capability of a group from getting mentioned.
     * 
     */
    @JsonProperty("mentions_disabled")
    @JsonPropertyDescription("Disable the capability of a group from getting mentioned.")
    private Boolean mentionsDisabled;
    /**
     * The organization ID for the group.
     * 
     */
    @JsonProperty("organization_id")
    @JsonPropertyDescription("The organization ID for the group.")
    private Integer organizationId;
    /**
     * Determine if developers can create projects in the group. Can be administrator (users with Admin Mode enabled), noone (No one), maintainer (users with the Maintainer role), or developer (users with the Developer or Maintainer role).
     * 
     */
    @JsonProperty("project_creation_level")
    @JsonPropertyDescription("Determine if developers can create projects in the group. Can be administrator (users with Admin Mode enabled), noone (No one), maintainer (users with the Maintainer role), or developer (users with the Developer or Maintainer role).")
    private GitLabCreateGroupRequest.ProjectCreationLevel projectCreationLevel;
    /**
     * Prevent sharing a project with another group within this group.
     * 
     */
    @JsonProperty("share_with_group_lock")
    @JsonPropertyDescription("Prevent sharing a project with another group within this group.")
    private Boolean shareWithGroupLock;
    /**
     * The group’s visibility. Can be private, internal, or public.
     * 
     */
    @JsonProperty("visibility")
    @JsonPropertyDescription("The group\u2019s visibility. Can be private, internal, or public.")
    private GitLabCreateGroupRequest.Visibility visibility;
    /**
     * Can be set by administrators only. Maximum number of monthly compute minutes for this group. Can be nil (default; inherit system default), 0 (unlimited), or > 0. GitLab Self-Managed, Premium and Ultimate only.
     * 
     */
    @JsonProperty("shared_runners_minutes_limit")
    @JsonPropertyDescription("Can be set by administrators only. Maximum number of monthly compute minutes for this group. Can be nil (default; inherit system default), 0 (unlimited), or > 0. GitLab Self-Managed, Premium and Ultimate only.")
    private Object sharedRunnersMinutesLimit;

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

    public GitLabCreateGroupRequest withName(String name) {
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

    public GitLabCreateGroupRequest withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Default to Auto DevOps pipeline for all projects within this group.
     * 
     */
    @JsonProperty("auto_devops_enabled")
    public Boolean getAutoDevopsEnabled() {
        return autoDevopsEnabled;
    }

    /**
     * Default to Auto DevOps pipeline for all projects within this group.
     * 
     */
    @JsonProperty("auto_devops_enabled")
    public void setAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
    }

    public GitLabCreateGroupRequest withAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
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

    public GitLabCreateGroupRequest withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    /**
     * Enable email notifications.
     * 
     */
    @JsonProperty("emails_enabled")
    public Boolean getEmailsEnabled() {
        return emailsEnabled;
    }

    /**
     * Enable email notifications.
     * 
     */
    @JsonProperty("emails_enabled")
    public void setEmailsEnabled(Boolean emailsEnabled) {
        this.emailsEnabled = emailsEnabled;
    }

    public GitLabCreateGroupRequest withEmailsEnabled(Boolean emailsEnabled) {
        this.emailsEnabled = emailsEnabled;
        return this;
    }

    /**
     * Disable the capability of a group from getting mentioned.
     * 
     */
    @JsonProperty("mentions_disabled")
    public Boolean getMentionsDisabled() {
        return mentionsDisabled;
    }

    /**
     * Disable the capability of a group from getting mentioned.
     * 
     */
    @JsonProperty("mentions_disabled")
    public void setMentionsDisabled(Boolean mentionsDisabled) {
        this.mentionsDisabled = mentionsDisabled;
    }

    public GitLabCreateGroupRequest withMentionsDisabled(Boolean mentionsDisabled) {
        this.mentionsDisabled = mentionsDisabled;
        return this;
    }

    /**
     * The organization ID for the group.
     * 
     */
    @JsonProperty("organization_id")
    public Integer getOrganizationId() {
        return organizationId;
    }

    /**
     * The organization ID for the group.
     * 
     */
    @JsonProperty("organization_id")
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public GitLabCreateGroupRequest withOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    /**
     * Determine if developers can create projects in the group. Can be administrator (users with Admin Mode enabled), noone (No one), maintainer (users with the Maintainer role), or developer (users with the Developer or Maintainer role).
     * 
     */
    @JsonProperty("project_creation_level")
    public GitLabCreateGroupRequest.ProjectCreationLevel getProjectCreationLevel() {
        return projectCreationLevel;
    }

    /**
     * Determine if developers can create projects in the group. Can be administrator (users with Admin Mode enabled), noone (No one), maintainer (users with the Maintainer role), or developer (users with the Developer or Maintainer role).
     * 
     */
    @JsonProperty("project_creation_level")
    public void setProjectCreationLevel(GitLabCreateGroupRequest.ProjectCreationLevel projectCreationLevel) {
        this.projectCreationLevel = projectCreationLevel;
    }

    public GitLabCreateGroupRequest withProjectCreationLevel(GitLabCreateGroupRequest.ProjectCreationLevel projectCreationLevel) {
        this.projectCreationLevel = projectCreationLevel;
        return this;
    }

    /**
     * Prevent sharing a project with another group within this group.
     * 
     */
    @JsonProperty("share_with_group_lock")
    public Boolean getShareWithGroupLock() {
        return shareWithGroupLock;
    }

    /**
     * Prevent sharing a project with another group within this group.
     * 
     */
    @JsonProperty("share_with_group_lock")
    public void setShareWithGroupLock(Boolean shareWithGroupLock) {
        this.shareWithGroupLock = shareWithGroupLock;
    }

    public GitLabCreateGroupRequest withShareWithGroupLock(Boolean shareWithGroupLock) {
        this.shareWithGroupLock = shareWithGroupLock;
        return this;
    }

    /**
     * The group’s visibility. Can be private, internal, or public.
     * 
     */
    @JsonProperty("visibility")
    public GitLabCreateGroupRequest.Visibility getVisibility() {
        return visibility;
    }

    /**
     * The group’s visibility. Can be private, internal, or public.
     * 
     */
    @JsonProperty("visibility")
    public void setVisibility(GitLabCreateGroupRequest.Visibility visibility) {
        this.visibility = visibility;
    }

    public GitLabCreateGroupRequest withVisibility(GitLabCreateGroupRequest.Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     * Can be set by administrators only. Maximum number of monthly compute minutes for this group. Can be nil (default; inherit system default), 0 (unlimited), or > 0. GitLab Self-Managed, Premium and Ultimate only.
     * 
     */
    @JsonProperty("shared_runners_minutes_limit")
    public Object getSharedRunnersMinutesLimit() {
        return sharedRunnersMinutesLimit;
    }

    /**
     * Can be set by administrators only. Maximum number of monthly compute minutes for this group. Can be nil (default; inherit system default), 0 (unlimited), or > 0. GitLab Self-Managed, Premium and Ultimate only.
     * 
     */
    @JsonProperty("shared_runners_minutes_limit")
    public void setSharedRunnersMinutesLimit(Object sharedRunnersMinutesLimit) {
        this.sharedRunnersMinutesLimit = sharedRunnersMinutesLimit;
    }

    public GitLabCreateGroupRequest withSharedRunnersMinutesLimit(Object sharedRunnersMinutesLimit) {
        this.sharedRunnersMinutesLimit = sharedRunnersMinutesLimit;
        return this;
    }


    /**
     * Determine if developers can create projects in the group. Can be administrator (users with Admin Mode enabled), noone (No one), maintainer (users with the Maintainer role), or developer (users with the Developer or Maintainer role).
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ProjectCreationLevel {

        ADMINISTRATOR("administrator"),
        NOONE("noone"),
        MAINTAINER("maintainer"),
        DEVELOPER("developer");
        private final String value;
        private final static Map<String, GitLabCreateGroupRequest.ProjectCreationLevel> CONSTANTS = new HashMap<String, GitLabCreateGroupRequest.ProjectCreationLevel>();

        static {
            for (GitLabCreateGroupRequest.ProjectCreationLevel c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ProjectCreationLevel(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static GitLabCreateGroupRequest.ProjectCreationLevel fromValue(String value) {
            GitLabCreateGroupRequest.ProjectCreationLevel constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The group’s visibility. Can be private, internal, or public.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Visibility {

        PRIVATE("private"),
        INTERNAL("internal"),
        PUBLIC("public");
        private final String value;
        private final static Map<String, GitLabCreateGroupRequest.Visibility> CONSTANTS = new HashMap<String, GitLabCreateGroupRequest.Visibility>();

        static {
            for (GitLabCreateGroupRequest.Visibility c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Visibility(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static GitLabCreateGroupRequest.Visibility fromValue(String value) {
            GitLabCreateGroupRequest.Visibility constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
