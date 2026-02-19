
package io.klustr.schemas.integrations.git;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * GitProjectReference
 * <p>
 * Response from gitlab for successful creation of a user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "avatar_url",
    "created_at",
    "updated_at",
    "last_activity_at",
    "path",
    "path_with_namespace",
    "repository_storage",
    "description",
    "default_branch",
    "ssh_url_to_repo",
    "http_url_to_repo",
    "web_url",
    "readme_url",
    "license_url",
    "topics",
    "owner",
    "namespace",
    "shared_with_groups",
    "issues_enabled",
    "shared_runners_enabled",
    "group_runners_enabled",
    "runners_token",
    "container_registry_image_prefix",
    "open_issues_count",
    "merge_requests_enabled",
    "archived",
    "container_registry_enabled",
    "jobs_enabled",
    "wiki_enabled",
    "snippets_enabled",
    "auto_devops_deploy_strategy",
    "auto_devops_enabled",
    "statistics"
})
@Generated("jsonschema2pojo")
public class GitProjectReference {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private DateTime createdAt;
    @JsonProperty("updated_at")
    private DateTime updatedAt;
    @JsonProperty("last_activity_at")
    private DateTime lastActivityAt;
    @JsonProperty("path")
    private String path;
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;
    @JsonProperty("repository_storage")
    private String repositoryStorage;
    @JsonProperty("description")
    private String description;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("ssh_url_to_repo")
    private String sshUrlToRepo;
    @JsonProperty("http_url_to_repo")
    private String httpUrlToRepo;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("readme_url")
    private String readmeUrl;
    @JsonProperty("license_url")
    private String licenseUrl;
    @JsonProperty("topics")
    private List<String> topics = new ArrayList<String>();
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("namespace")
    private Namespace namespace;
    @JsonProperty("shared_with_groups")
    private List<ProjectGroupRelationship> sharedWithGroups = new ArrayList<ProjectGroupRelationship>();
    @JsonProperty("issues_enabled")
    private Boolean issuesEnabled;
    @JsonProperty("shared_runners_enabled")
    private Boolean sharedRunnersEnabled;
    @JsonProperty("group_runners_enabled")
    private Boolean groupRunnersEnabled;
    @JsonProperty("runners_token")
    private String runnersToken;
    @JsonProperty("container_registry_image_prefix")
    private String containerRegistryImagePrefix;
    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;
    @JsonProperty("merge_requests_enabled")
    private Boolean mergeRequestsEnabled;
    @JsonProperty("archived")
    private Boolean archived;
    @JsonProperty("container_registry_enabled")
    private Boolean containerRegistryEnabled;
    @JsonProperty("jobs_enabled")
    private Boolean jobsEnabled;
    @JsonProperty("wiki_enabled")
    private Boolean wikiEnabled;
    @JsonProperty("snippets_enabled")
    private Boolean snippetsEnabled;
    @JsonProperty("auto_devops_deploy_strategy")
    private GitProjectReference.AutoDevopsDeployStrategy autoDevopsDeployStrategy = GitProjectReference.AutoDevopsDeployStrategy.fromValue("continuous");
    @JsonProperty("auto_devops_enabled")
    private Boolean autoDevopsEnabled = true;
    /**
     * GitProjectStatistics
     * <p>
     * 
     * 
     */
    @JsonProperty("statistics")
    private GitProjectStatistics statistics;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public GitProjectReference withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public GitProjectReference withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public GitProjectReference withAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public GitProjectReference withCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GitProjectReference withUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonProperty("last_activity_at")
    public DateTime getLastActivityAt() {
        return lastActivityAt;
    }

    @JsonProperty("last_activity_at")
    public void setLastActivityAt(DateTime lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public GitProjectReference withLastActivityAt(DateTime lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
        return this;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public GitProjectReference withPath(String path) {
        this.path = path;
        return this;
    }

    @JsonProperty("path_with_namespace")
    public String getPathWithNamespace() {
        return pathWithNamespace;
    }

    @JsonProperty("path_with_namespace")
    public void setPathWithNamespace(String pathWithNamespace) {
        this.pathWithNamespace = pathWithNamespace;
    }

    public GitProjectReference withPathWithNamespace(String pathWithNamespace) {
        this.pathWithNamespace = pathWithNamespace;
        return this;
    }

    @JsonProperty("repository_storage")
    public String getRepositoryStorage() {
        return repositoryStorage;
    }

    @JsonProperty("repository_storage")
    public void setRepositoryStorage(String repositoryStorage) {
        this.repositoryStorage = repositoryStorage;
    }

    public GitProjectReference withRepositoryStorage(String repositoryStorage) {
        this.repositoryStorage = repositoryStorage;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public GitProjectReference withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("default_branch")
    public String getDefaultBranch() {
        return defaultBranch;
    }

    @JsonProperty("default_branch")
    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public GitProjectReference withDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
        return this;
    }

    @JsonProperty("ssh_url_to_repo")
    public String getSshUrlToRepo() {
        return sshUrlToRepo;
    }

    @JsonProperty("ssh_url_to_repo")
    public void setSshUrlToRepo(String sshUrlToRepo) {
        this.sshUrlToRepo = sshUrlToRepo;
    }

    public GitProjectReference withSshUrlToRepo(String sshUrlToRepo) {
        this.sshUrlToRepo = sshUrlToRepo;
        return this;
    }

    @JsonProperty("http_url_to_repo")
    public String getHttpUrlToRepo() {
        return httpUrlToRepo;
    }

    @JsonProperty("http_url_to_repo")
    public void setHttpUrlToRepo(String httpUrlToRepo) {
        this.httpUrlToRepo = httpUrlToRepo;
    }

    public GitProjectReference withHttpUrlToRepo(String httpUrlToRepo) {
        this.httpUrlToRepo = httpUrlToRepo;
        return this;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public GitProjectReference withWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    @JsonProperty("readme_url")
    public String getReadmeUrl() {
        return readmeUrl;
    }

    @JsonProperty("readme_url")
    public void setReadmeUrl(String readmeUrl) {
        this.readmeUrl = readmeUrl;
    }

    public GitProjectReference withReadmeUrl(String readmeUrl) {
        this.readmeUrl = readmeUrl;
        return this;
    }

    @JsonProperty("license_url")
    public String getLicenseUrl() {
        return licenseUrl;
    }

    @JsonProperty("license_url")
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public GitProjectReference withLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
        return this;
    }

    @JsonProperty("topics")
    public List<String> getTopics() {
        return topics;
    }

    @JsonProperty("topics")
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public GitProjectReference withTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public GitProjectReference withOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    @JsonProperty("namespace")
    public Namespace getNamespace() {
        return namespace;
    }

    @JsonProperty("namespace")
    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public GitProjectReference withNamespace(Namespace namespace) {
        this.namespace = namespace;
        return this;
    }

    @JsonProperty("shared_with_groups")
    public List<ProjectGroupRelationship> getSharedWithGroups() {
        return sharedWithGroups;
    }

    @JsonProperty("shared_with_groups")
    public void setSharedWithGroups(List<ProjectGroupRelationship> sharedWithGroups) {
        this.sharedWithGroups = sharedWithGroups;
    }

    public GitProjectReference withSharedWithGroups(List<ProjectGroupRelationship> sharedWithGroups) {
        this.sharedWithGroups = sharedWithGroups;
        return this;
    }

    @JsonProperty("issues_enabled")
    public Boolean getIssuesEnabled() {
        return issuesEnabled;
    }

    @JsonProperty("issues_enabled")
    public void setIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public GitProjectReference withIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
        return this;
    }

    @JsonProperty("shared_runners_enabled")
    public Boolean getSharedRunnersEnabled() {
        return sharedRunnersEnabled;
    }

    @JsonProperty("shared_runners_enabled")
    public void setSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
    }

    public GitProjectReference withSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
        return this;
    }

    @JsonProperty("group_runners_enabled")
    public Boolean getGroupRunnersEnabled() {
        return groupRunnersEnabled;
    }

    @JsonProperty("group_runners_enabled")
    public void setGroupRunnersEnabled(Boolean groupRunnersEnabled) {
        this.groupRunnersEnabled = groupRunnersEnabled;
    }

    public GitProjectReference withGroupRunnersEnabled(Boolean groupRunnersEnabled) {
        this.groupRunnersEnabled = groupRunnersEnabled;
        return this;
    }

    @JsonProperty("runners_token")
    public String getRunnersToken() {
        return runnersToken;
    }

    @JsonProperty("runners_token")
    public void setRunnersToken(String runnersToken) {
        this.runnersToken = runnersToken;
    }

    public GitProjectReference withRunnersToken(String runnersToken) {
        this.runnersToken = runnersToken;
        return this;
    }

    @JsonProperty("container_registry_image_prefix")
    public String getContainerRegistryImagePrefix() {
        return containerRegistryImagePrefix;
    }

    @JsonProperty("container_registry_image_prefix")
    public void setContainerRegistryImagePrefix(String containerRegistryImagePrefix) {
        this.containerRegistryImagePrefix = containerRegistryImagePrefix;
    }

    public GitProjectReference withContainerRegistryImagePrefix(String containerRegistryImagePrefix) {
        this.containerRegistryImagePrefix = containerRegistryImagePrefix;
        return this;
    }

    @JsonProperty("open_issues_count")
    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    @JsonProperty("open_issues_count")
    public void setOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public GitProjectReference withOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
        return this;
    }

    @JsonProperty("merge_requests_enabled")
    public Boolean getMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    @JsonProperty("merge_requests_enabled")
    public void setMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public GitProjectReference withMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
        return this;
    }

    @JsonProperty("archived")
    public Boolean getArchived() {
        return archived;
    }

    @JsonProperty("archived")
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public GitProjectReference withArchived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    @JsonProperty("container_registry_enabled")
    public Boolean getContainerRegistryEnabled() {
        return containerRegistryEnabled;
    }

    @JsonProperty("container_registry_enabled")
    public void setContainerRegistryEnabled(Boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
    }

    public GitProjectReference withContainerRegistryEnabled(Boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
        return this;
    }

    @JsonProperty("jobs_enabled")
    public Boolean getJobsEnabled() {
        return jobsEnabled;
    }

    @JsonProperty("jobs_enabled")
    public void setJobsEnabled(Boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
    }

    public GitProjectReference withJobsEnabled(Boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
        return this;
    }

    @JsonProperty("wiki_enabled")
    public Boolean getWikiEnabled() {
        return wikiEnabled;
    }

    @JsonProperty("wiki_enabled")
    public void setWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public GitProjectReference withWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
        return this;
    }

    @JsonProperty("snippets_enabled")
    public Boolean getSnippetsEnabled() {
        return snippetsEnabled;
    }

    @JsonProperty("snippets_enabled")
    public void setSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public GitProjectReference withSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
        return this;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public GitProjectReference.AutoDevopsDeployStrategy getAutoDevopsDeployStrategy() {
        return autoDevopsDeployStrategy;
    }

    @JsonProperty("auto_devops_deploy_strategy")
    public void setAutoDevopsDeployStrategy(GitProjectReference.AutoDevopsDeployStrategy autoDevopsDeployStrategy) {
        this.autoDevopsDeployStrategy = autoDevopsDeployStrategy;
    }

    public GitProjectReference withAutoDevopsDeployStrategy(GitProjectReference.AutoDevopsDeployStrategy autoDevopsDeployStrategy) {
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

    public GitProjectReference withAutoDevopsEnabled(Boolean autoDevopsEnabled) {
        this.autoDevopsEnabled = autoDevopsEnabled;
        return this;
    }

    /**
     * GitProjectStatistics
     * <p>
     * 
     * 
     */
    @JsonProperty("statistics")
    public GitProjectStatistics getStatistics() {
        return statistics;
    }

    /**
     * GitProjectStatistics
     * <p>
     * 
     * 
     */
    @JsonProperty("statistics")
    public void setStatistics(GitProjectStatistics statistics) {
        this.statistics = statistics;
    }

    public GitProjectReference withStatistics(GitProjectStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    @Generated("jsonschema2pojo")
    public enum AutoDevopsDeployStrategy {

        CONTINUOUS("continuous"),
        MANUAL("manual"),
        TIMED_INCREMENTAL("timed_incremental");
        private final String value;
        private final static Map<String, GitProjectReference.AutoDevopsDeployStrategy> CONSTANTS = new HashMap<String, GitProjectReference.AutoDevopsDeployStrategy>();

        static {
            for (GitProjectReference.AutoDevopsDeployStrategy c: values()) {
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
        public static GitProjectReference.AutoDevopsDeployStrategy fromValue(String value) {
            GitProjectReference.AutoDevopsDeployStrategy constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
