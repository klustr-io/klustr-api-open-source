
package io.klustr.schemas.persons.hydra;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * HydraAccessTokenEnrichment
 * <p>
 * Information in the OIDC Access token for the ability to returned type information
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "client_id",
    "scope",
    "project_id",
    "org_id",
    "permissions",
    "roles",
    "orgs",
    "projects",
    "groups"
})
@Generated("jsonschema2pojo")
public class HydraAccessTokenEnrichment {

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The source client ID that generated this access token.")
    private java.lang.String clientId;
    /**
     * The additional scopes that they would like to add.
     * 
     */
    @JsonProperty("scope")
    @JsonPropertyDescription("The additional scopes that they would like to add.")
    private java.lang.String scope;
    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project ID that owns the client that generated this token.")
    private java.lang.String projectId;
    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization ID that initiated this access token.")
    private java.lang.String orgId;
    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The additional role information given the client, project, and organization.")
    private Set<String> permissions = new LinkedHashSet<String>();
    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("roles")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The additional role information given the client, project, and organization.")
    private Set<String> roles = new LinkedHashSet<String>();
    /**
     * The organizations this user has access rights to.
     * 
     */
    @JsonProperty("orgs")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The organizations this user has access rights to.")
    private Set<String> orgs = new LinkedHashSet<String>();
    /**
     * The project identifiers this person has access to.
     * 
     */
    @JsonProperty("projects")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The project identifiers this person has access to.")
    private Set<String> projects = new LinkedHashSet<String>();
    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The additional role information given the client, project, and organization.")
    private Set<String> groups = new LinkedHashSet<String>();

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    public HydraAccessTokenEnrichment withClientId(java.lang.String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The additional scopes that they would like to add.
     * 
     */
    @JsonProperty("scope")
    public java.lang.String getScope() {
        return scope;
    }

    /**
     * The additional scopes that they would like to add.
     * 
     */
    @JsonProperty("scope")
    public void setScope(java.lang.String scope) {
        this.scope = scope;
    }

    public HydraAccessTokenEnrichment withScope(java.lang.String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    public java.lang.String getProjectId() {
        return projectId;
    }

    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    public HydraAccessTokenEnrichment withProjectId(java.lang.String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    public java.lang.String getOrgId() {
        return orgId;
    }

    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(java.lang.String orgId) {
        this.orgId = orgId;
    }

    public HydraAccessTokenEnrichment withOrgId(java.lang.String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public HydraAccessTokenEnrichment withPermissions(Set<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("roles")
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("roles")
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public HydraAccessTokenEnrichment withRoles(Set<String> roles) {
        this.roles = roles;
        return this;
    }

    /**
     * The organizations this user has access rights to.
     * 
     */
    @JsonProperty("orgs")
    public Set<String> getOrgs() {
        return orgs;
    }

    /**
     * The organizations this user has access rights to.
     * 
     */
    @JsonProperty("orgs")
    public void setOrgs(Set<String> orgs) {
        this.orgs = orgs;
    }

    public HydraAccessTokenEnrichment withOrgs(Set<String> orgs) {
        this.orgs = orgs;
        return this;
    }

    /**
     * The project identifiers this person has access to.
     * 
     */
    @JsonProperty("projects")
    public Set<String> getProjects() {
        return projects;
    }

    /**
     * The project identifiers this person has access to.
     * 
     */
    @JsonProperty("projects")
    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }

    public HydraAccessTokenEnrichment withProjects(Set<String> projects) {
        this.projects = projects;
        return this;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    public Set<String> getGroups() {
        return groups;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public HydraAccessTokenEnrichment withGroups(Set<String> groups) {
        this.groups = groups;
        return this;
    }

}
