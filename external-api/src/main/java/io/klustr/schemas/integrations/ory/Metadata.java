
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Key value pairs for registering with the client.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "project_id",
    "app_id",
    "org_id",
    "domain",
    "ref",
    "verified",
    "status",
    "permissions",
    "roles"
})
@Generated("jsonschema2pojo")
public class Metadata {

    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("org_id")
    private String orgId;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("verified")
    private String verified;
    @JsonProperty("status")
    private String status;
    /**
     * The permissions to always grant this specific client
     * 
     */
    @JsonProperty("permissions")
    @JsonPropertyDescription("The permissions to always grant this specific client")
    private List<String> permissions = new ArrayList<String>();
    /**
     * The roles to always grant this specific client
     * 
     */
    @JsonProperty("roles")
    @JsonPropertyDescription("The roles to always grant this specific client")
    private List<String> roles = new ArrayList<String>();

    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Metadata withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Metadata withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Metadata withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Metadata withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @JsonProperty("ref")
    public String getRef() {
        return ref;
    }

    @JsonProperty("ref")
    public void setRef(String ref) {
        this.ref = ref;
    }

    public Metadata withRef(String ref) {
        this.ref = ref;
        return this;
    }

    @JsonProperty("verified")
    public String getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(String verified) {
        this.verified = verified;
    }

    public Metadata withVerified(String verified) {
        this.verified = verified;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Metadata withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * The permissions to always grant this specific client
     * 
     */
    @JsonProperty("permissions")
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * The permissions to always grant this specific client
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Metadata withPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * The roles to always grant this specific client
     * 
     */
    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    /**
     * The roles to always grant this specific client
     * 
     */
    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Metadata withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

}
