
package io.klustr.schemas.integrations.git;

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
 * GitCreateProjectRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "path",
    "user_id",
    "description",
    "visibility",
    "namespace_id",
    "initialize_with_readme",
    "lfs_enabled",
    "issues_enabled",
    "merge_requests_enabled",
    "wiki_enabled",
    "snippets_enabled",
    "shared_runners_enabled",
    "auto_devops_deploy_strategy",
    "auto_devops_enabled"
})
@Generated("jsonschema2pojo")
public class GitCreateProjectRequest {

    /**
     * The name of the new project.
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the new project.")
    private String name;
    /**
     * The unique path for the project.
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The unique path for the project.")
    private String path;
    /**
     * The user ID of the project owner.
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The user ID of the project owner.")
    private Integer userId;
    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("Short project description.")
    private String description;
    /**
     * Project visibility level.
     * 
     */
    @JsonProperty("visibility")
    @JsonPropertyDescription("Project visibility level.")
    private GitCreateProjectRequest.Visibility visibility = GitCreateProjectRequest.Visibility.fromValue("private");
    /**
     * Namespace for the new project. Specify a group ID or subgroup ID. If not provided, defaults to the current user’s personal namespace.
     * 
     */
    @JsonProperty("namespace_id")
    @JsonPropertyDescription("Namespace for the new project. Specify a group ID or subgroup ID. If not provided, defaults to the current user\u2019s personal namespace.")
    private Integer namespaceId;
    /**
     * Initialize repository with a README.
     * 
     */
    @JsonProperty("initialize_with_readme")
    @JsonPropertyDescription("Initialize repository with a README.")
    private Boolean initializeWithReadme = false;
    /**
     * Enable Git LFS support.
     * 
     */
    @JsonProperty("lfs_enabled")
    @JsonPropertyDescription("Enable Git LFS support.")
    private Boolean lfsEnabled = true;
    /**
     * Enable issue tracking.
     * 
     */
    @JsonProperty("issues_enabled")
    @JsonPropertyDescription("Enable issue tracking.")
    private Boolean issuesEnabled = true;
    /**
     * Enable merge requests.
     * 
     */
    @JsonProperty("merge_requests_enabled")
    @JsonPropertyDescription("Enable merge requests.")
    private Boolean mergeRequestsEnabled = true;
    /**
     * Enable wiki.
     * 
     */
    @JsonProperty("wiki_enabled")
    @JsonPropertyDescription("Enable wiki.")
    private Boolean wikiEnabled = true;
    /**
     * Enable snippets.
     * 
     */
    @JsonProperty("snippets_enabled")
    @JsonPropertyDescription("Enable snippets.")
    private Boolean snippetsEnabled = false;
    /**
     * Enable shared runners for CI/CD.
     * 
     */
    @JsonProperty("shared_runners_enabled")
    @JsonPropertyDescription("Enable shared runners for CI/CD.")
    private Boolean sharedRunnersEnabled = true;
    @JsonProperty("auto_devops_deploy_strategy")
    private GitCreateProjectRequest.AutoDevopsDeployStrategy autoDevopsDeployStrategy = GitCreateProjectRequest.AutoDevopsDeployStrategy.fromValue("continuous");
    @JsonProperty("auto_devops_enabled")
    private Boolean autoDevopsEnabled = true;

    /**
     * The name of the new project.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the new project.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitCreateProjectRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The unique path for the project.
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The unique path for the project.
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public GitCreateProjectRequest withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * The user ID of the project owner.
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    public Integer getUserId() {
        return userId;
    }

    /**
     * The user ID of the project owner.
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GitCreateProjectRequest withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Short project description.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public GitCreateProjectRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Project visibility level.
     * 
     */
    @JsonProperty("visibility")
    public GitCreateProjectRequest.Visibility getVisibility() {
        return visibility;
    }

    /**
     * Project visibility level.
     * 
     */
    @JsonProperty("visibility")
    public void setVisibility(GitCreateProjectRequest.Visibility visibility) {
        this.visibility = visibility;
    }

    public GitCreateProjectRequest withVisibility(GitCreateProjectRequest.Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     * Namespace for the new project. Specify a group ID or subgroup ID. If not provided, defaults to the current user’s personal namespace.
     * 
     */
    @JsonProperty("namespace_id")
    public Integer getNamespaceId() {
        return namespaceId;
    }

    /**
     * Namespace for the new project. Specify a group ID or subgroup ID. If not provided, defaults to the current user’s personal namespace.
     * 
     */
    @JsonProperty("namespace_id")
    public void setNamespaceId(Integer namespaceId) {
        this.namespaceId = namespaceId;
    }

    public GitCreateProjectRequest withNamespaceId(Integer namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    /**
     * Initialize repository with a README.
     * 
     */
    @JsonProperty("initialize_with_readme")
    public Boolean getInitializeWithReadme() {
        return initializeWithReadme;
    }

    /**
     * Initialize repository with a README.
     * 
     */
    @JsonProperty("initialize_with_readme")
    public void setInitializeWithReadme(Boolean initializeWithReadme) {
        this.initializeWithReadme = initializeWithReadme;
    }

    public GitCreateProjectRequest withInitializeWithReadme(Boolean initializeWithReadme) {
        this.initializeWithReadme = initializeWithReadme;
        return this;
    }

    /**
     * Enable Git LFS support.
     * 
     */
    @JsonProperty("lfs_enabled")
    public Boolean getLfsEnabled() {
        return lfsEnabled;
    }

    /**
     * Enable Git LFS support.
     * 
     */
    @JsonProperty("lfs_enabled")
    public void setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
    }

    public GitCreateProjectRequest withLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
        return this;
    }

    /**
     * Enable issue tracking.
     * 
     */
    @JsonProperty("issues_enabled")
    public Boolean getIssuesEnabled() {
        return issuesEnabled;
    }

    /**
     * Enable issue tracking.
     * 
     */
    @JsonProperty("issues_enabled")
    public void setIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public GitCreateProjectRequest withIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
        return this;
    }

    /**
     * Enable merge requests.
     * 
     */
    @JsonProperty("merge_requests_enabled")
    public Boolean getMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    /**
     * Enable merge requests.
     * 
     */
    @JsonProperty("merge_requests_enabled")
    public void setMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public GitCreateProjectRequest withMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
        return this;
    }

    /**
     * Enable wiki.
     * 
     */
    @JsonProperty("wiki_enabled")
    public Boolean getWikiEnabled() {
        return wikiEnabled;
    }

    /**
     * Enable wiki.
     * 
     */
    @JsonProperty("wiki_enabled")
    public void setWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public GitCreateProjectRequest withWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
        return this;
    }

    /**
     * Enable snippets.
     * 
     */
    @JsonProperty("snippets_enabled")
    public Boolean getSnippetsEnabled() {
        return snippetsEnabled;
    }

    /**
     * Enable snippets.
     * 
     */
    @JsonProperty("snippets_enabled")
    public void setSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public GitCreateProjectRequest withSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
        return this;
    }

    /**
     * Enable shared runners for CI/CD.
     * 
     */
    @JsonProperty("shared_runners_enabled")
    public Boolean getSharedRunnersEnabled() {
        return sharedRunnersEnabled;
    }

    /**
     * Enable shared runners for CI/CD.
     * 
     */
    @JsonProperty("shared_runners_enabled")
    public void setSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
    }

    public GitCreateProjectRequest withSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
        return this;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public GitCreateProjectRequest.AutoDevopsDeployStrategy getAutoDevopsDeployStrategy() {
        return autoDevopsDeployStrategy;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public void setAutoDevopsDeployStrategy(GitCreateProjectRequest.AutoDevopsDeployStrategy autoDevopsDeployStrategy) {
        this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
    }

    public GitCreateProjectRequest withAutoDevopsDeployStrategy(GitCreateProjectRequest.AutoDevopsDeployStrategy autoDevopsDeployStrategy) {
        this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
        return this;
    }

    @JsonProperty("auto_devops_enabled")
    public Boolean getAutoDevopsEnabled() {
        return autoDevopsEnabled;
    }

    @JsonProperty("auto_devops_enabled")
    public void setAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
    }

    public GitCreateProjectRequest withAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
        return this;
    }

    @Generated("jsonschema2pojo")
    public enum AutoDevopsDeployStrategy {

        CONTINUOUS("continuous"),
        MANUAL("manual"),
        TIMED_INCREMENTAL("timed_incremental");
        private final String value;
        private final static Map<String, GitCreateProjectRequest.AutoDevopsDeployStrategy> CONSTANTS = new HashMap<String, GitCreateProjectRequest.AutoDevopsDeployStrategy>();

        static {
            for (GitCreateProjectRequest.AutoDevopsDeployStrategy c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AutoDevopsDeployStrategy(String value) {
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
        public static GitCreateProjectRequest.AutoDevopsDeployStrategy fromValue(String value) {
            GitCreateProjectRequest.AutoDevopsDeployStrategy constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Project visibility level.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Visibility {

        PRIVATE("private"),
        INTERNAL("internal"),
        PUBLIC("public");
        private final String value;
        private final static Map<String, GitCreateProjectRequest.Visibility> CONSTANTS = new HashMap<String, GitCreateProjectRequest.Visibility>();

        static {
            for (GitCreateProjectRequest.Visibility c: values()) {
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
        public static GitCreateProjectRequest.Visibility fromValue(String value) {
            GitCreateProjectRequest.Visibility constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
