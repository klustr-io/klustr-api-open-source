
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.klustr.schemas.console.apis.ApiKey;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.oidc.OIDCClient;
import io.klustr.schemas.console.oidc.OIDCCredential;
import org.joda.time.DateTime;


/**
 * Project
 * <p>
 * The project owned by one or more people
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "customer_id",
    "org_id",
    "description",
    "creation_date",
    "owner",
    "app",
    "clients",
    "credentials",
    "api_keys",
    "services",
    "metadata",
    "status",
    "plan",
    "template_id",
    "secrets",
    "o11y"
})
@Generated("jsonschema2pojo")
public class Project {

    /**
     * The unique identifier for the project
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique identifier for the project")
    private java.lang.String id;
    /**
     * Name of the project
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Name of the project")
    private java.lang.String name;
    /**
     * Customer who owns this project, used for billing and directory management.
     * 
     */
    @JsonProperty("customer_id")
    @JsonPropertyDescription("Customer who owns this project, used for billing and directory management.")
    private java.lang.String customerId;
    /**
     * The optional organization that owns this project. Used for project management and sharing.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The optional organization that owns this project. Used for project management and sharing.")
    private java.lang.String orgId;
    /**
     * The description for this project
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description for this project")
    private java.lang.String description;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * ProjectOwner
     * <p>
     * The owner for this project
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("The owner for this project")
    private ProjectOwner owner;
    /**
     * App
     * <p>
     * A app that is defined for a project
     * 
     */
    @JsonProperty("app")
    @JsonPropertyDescription("A app that is defined for a project")
    private App app;
    /**
     * The API clients registered with this project.
     * 
     */
    @JsonProperty("clients")
    @JsonPropertyDescription("The API clients registered with this project.")
    private List<OIDCClient> clients = new ArrayList<OIDCClient>();
    /**
     * The API credentials registered with this project.
     * 
     */
    @JsonProperty("credentials")
    @JsonPropertyDescription("The API credentials registered with this project.")
    private List<OIDCCredential> credentials = new ArrayList<OIDCCredential>();
    /**
     * API keys available for this client to use to authenticate to services.
     * 
     */
    @JsonProperty("api_keys")
    @JsonPropertyDescription("API keys available for this client to use to authenticate to services.")
    private List<ApiKey> apiKeys = new ArrayList<ApiKey>();
    /**
     * The enabled services for this project
     * 
     */
    @JsonProperty("services")
    @JsonPropertyDescription("The enabled services for this project")
    private List<ProjectServiceReference> services = new ArrayList<ProjectServiceReference>();
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this organization.")
    private Project.Status status;
    /**
     * The billing plan that this project is using, these map to external billing providers.
     * 
     */
    @JsonProperty("plan")
    @JsonPropertyDescription("The billing plan that this project is using, these map to external billing providers.")
    private java.lang.String plan = "basic-plan";
    /**
     * The template ID bound to this project if applicable.
     * 
     */
    @JsonProperty("template_id")
    @JsonPropertyDescription("The template ID bound to this project if applicable.")
    private java.lang.String templateId;
    /**
     * Secrets linked to this project
     * 
     */
    @JsonProperty("secrets")
    @JsonPropertyDescription("Secrets linked to this project")
    private List<ProjectSecret> secrets = new ArrayList<ProjectSecret>();
    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    @JsonPropertyDescription("Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.")
    private O11y o11y;

    /**
     * The unique identifier for the project
     * (Required)
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * The unique identifier for the project
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public Project withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * Name of the project
     * (Required)
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * Name of the project
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public Project withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * Customer who owns this project, used for billing and directory management.
     * 
     */
    @JsonProperty("customer_id")
    public java.lang.String getCustomerId() {
        return customerId;
    }

    /**
     * Customer who owns this project, used for billing and directory management.
     * 
     */
    @JsonProperty("customer_id")
    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }

    public Project withCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * The optional organization that owns this project. Used for project management and sharing.
     * 
     */
    @JsonProperty("org_id")
    public java.lang.String getOrgId() {
        return orgId;
    }

    /**
     * The optional organization that owns this project. Used for project management and sharing.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(java.lang.String orgId) {
        this.orgId = orgId;
    }

    public Project withOrgId(java.lang.String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The description for this project
     * 
     */
    @JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * The description for this project
     * 
     */
    @JsonProperty("description")
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public Project withDescription(java.lang.String description) {
        this.description = description;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Project withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * ProjectOwner
     * <p>
     * The owner for this project
     * 
     */
    @JsonProperty("owner")
    public ProjectOwner getOwner() {
        return owner;
    }

    /**
     * ProjectOwner
     * <p>
     * The owner for this project
     * 
     */
    @JsonProperty("owner")
    public void setOwner(ProjectOwner owner) {
        this.owner = owner;
    }

    public Project withOwner(ProjectOwner owner) {
        this.owner = owner;
        return this;
    }

    /**
     * App
     * <p>
     * A app that is defined for a project
     * 
     */
    @JsonProperty("app")
    public App getApp() {
        return app;
    }

    /**
     * App
     * <p>
     * A app that is defined for a project
     * 
     */
    @JsonProperty("app")
    public void setApp(App app) {
        this.app = app;
    }

    public Project withApp(App app) {
        this.app = app;
        return this;
    }

    /**
     * The API clients registered with this project.
     * 
     */
    @JsonProperty("clients")
    public List<OIDCClient> getClients() {
        return clients;
    }

    /**
     * The API clients registered with this project.
     * 
     */
    @JsonProperty("clients")
    public void setClients(List<OIDCClient> clients) {
        this.clients = clients;
    }

    public Project withClients(List<OIDCClient> clients) {
        this.clients = clients;
        return this;
    }

    /**
     * The API credentials registered with this project.
     * 
     */
    @JsonProperty("credentials")
    public List<OIDCCredential> getCredentials() {
        return credentials;
    }

    /**
     * The API credentials registered with this project.
     * 
     */
    @JsonProperty("credentials")
    public void setCredentials(List<OIDCCredential> credentials) {
        this.credentials = credentials;
    }

    public Project withCredentials(List<OIDCCredential> credentials) {
        this.credentials = credentials;
        return this;
    }

    /**
     * API keys available for this client to use to authenticate to services.
     * 
     */
    @JsonProperty("api_keys")
    public List<ApiKey> getApiKeys() {
        return apiKeys;
    }

    /**
     * API keys available for this client to use to authenticate to services.
     * 
     */
    @JsonProperty("api_keys")
    public void setApiKeys(List<ApiKey> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public Project withApiKeys(List<ApiKey> apiKeys) {
        this.apiKeys = apiKeys;
        return this;
    }

    /**
     * The enabled services for this project
     * 
     */
    @JsonProperty("services")
    public List<ProjectServiceReference> getServices() {
        return services;
    }

    /**
     * The enabled services for this project
     * 
     */
    @JsonProperty("services")
    public void setServices(List<ProjectServiceReference> services) {
        this.services = services;
    }

    public Project withServices(List<ProjectServiceReference> services) {
        this.services = services;
        return this;
    }

    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Project withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    public Project.Status getStatus() {
        return status;
    }

    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    public void setStatus(Project.Status status) {
        this.status = status;
    }

    public Project withStatus(Project.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The billing plan that this project is using, these map to external billing providers.
     * 
     */
    @JsonProperty("plan")
    public java.lang.String getPlan() {
        return plan;
    }

    /**
     * The billing plan that this project is using, these map to external billing providers.
     * 
     */
    @JsonProperty("plan")
    public void setPlan(java.lang.String plan) {
        this.plan = plan;
    }

    public Project withPlan(java.lang.String plan) {
        this.plan = plan;
        return this;
    }

    /**
     * The template ID bound to this project if applicable.
     * 
     */
    @JsonProperty("template_id")
    public java.lang.String getTemplateId() {
        return templateId;
    }

    /**
     * The template ID bound to this project if applicable.
     * 
     */
    @JsonProperty("template_id")
    public void setTemplateId(java.lang.String templateId) {
        this.templateId = templateId;
    }

    public Project withTemplateId(java.lang.String templateId) {
        this.templateId = templateId;
        return this;
    }

    /**
     * Secrets linked to this project
     * 
     */
    @JsonProperty("secrets")
    public List<ProjectSecret> getSecrets() {
        return secrets;
    }

    /**
     * Secrets linked to this project
     * 
     */
    @JsonProperty("secrets")
    public void setSecrets(List<ProjectSecret> secrets) {
        this.secrets = secrets;
    }

    public Project withSecrets(List<ProjectSecret> secrets) {
        this.secrets = secrets;
        return this;
    }

    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    public O11y getO11y() {
        return o11y;
    }

    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    public void setO11y(O11y o11y) {
        this.o11y = o11y;
    }

    public Project withO11y(O11y o11y) {
        this.o11y = o11y;
        return this;
    }


    /**
     * The status of this organization.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        INACTIVE("inactive");
        private final java.lang.String value;
        private final static Map<java.lang.String, Project.Status> CONSTANTS = new HashMap<java.lang.String, Project.Status>();

        static {
            for (Project.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(java.lang.String value) {
            this.value = value;
        }

        @Override
        public java.lang.String toString() {
            return this.value;
        }

        @JsonValue
        public java.lang.String value() {
            return this.value;
        }

        @JsonCreator
        public static Project.Status fromValue(java.lang.String value) {
            Project.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
