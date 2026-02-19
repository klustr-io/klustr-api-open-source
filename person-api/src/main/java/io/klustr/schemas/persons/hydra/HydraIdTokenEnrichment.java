
package io.klustr.schemas.persons.hydra;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * HydraIdTokenEnrichment
 * <p>
 * Information in the OIDC ID token for the ability to returned type information
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "aud",
    "sso_provider",
    "roles",
    "orgs",
    "projects",
    "groups",
    "experiments",
    "permissions",
    "policy"
})
@Generated("jsonschema2pojo")
public class HydraIdTokenEnrichment {

    /**
     * HydraSessionSource
     * <p>
     * The client and sourcing information which generated this token
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The client and sourcing information which generated this token")
    private HydraSessionSource source;
    /**
     * The audiences that this token is valid for.
     * 
     */
    @JsonProperty("aud")
    @JsonPropertyDescription("The audiences that this token is valid for.")
    private List<java.lang.String> aud = new ArrayList<java.lang.String>();
    /**
     * The login sso provider for this login attempt.
     * 
     */
    @JsonProperty("sso_provider")
    @JsonPropertyDescription("The login sso provider for this login attempt.")
    private java.lang.String ssoProvider;
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
     * The additional groups information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The additional groups information given the client, project, and organization.")
    private Set<String> groups = new LinkedHashSet<String>();
    /**
     * The experiments that this user has active.
     * 
     */
    @JsonProperty("experiments")
    @JsonPropertyDescription("The experiments that this user has active.")
    private List<java.lang.String> experiments = new ArrayList<java.lang.String>();
    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    @JsonPropertyDescription("The additional role information given the client, project, and organization.")
    private List<java.lang.String> permissions = new ArrayList<java.lang.String>();
    /**
     * Policy based access controls used with things like minio and s3 and other IAM providers.
     * 
     */
    @JsonProperty("policy")
    @JsonPropertyDescription("Policy based access controls used with things like minio and s3 and other IAM providers.")
    private java.lang.String policy;

    /**
     * HydraSessionSource
     * <p>
     * The client and sourcing information which generated this token
     * 
     */
    @JsonProperty("source")
    public HydraSessionSource getSource() {
        return source;
    }

    /**
     * HydraSessionSource
     * <p>
     * The client and sourcing information which generated this token
     * 
     */
    @JsonProperty("source")
    public void setSource(HydraSessionSource source) {
        this.source = source;
    }

    public HydraIdTokenEnrichment withSource(HydraSessionSource source) {
        this.source = source;
        return this;
    }

    /**
     * The audiences that this token is valid for.
     * 
     */
    @JsonProperty("aud")
    public List<java.lang.String> getAud() {
        return aud;
    }

    /**
     * The audiences that this token is valid for.
     * 
     */
    @JsonProperty("aud")
    public void setAud(List<java.lang.String> aud) {
        this.aud = aud;
    }

    public HydraIdTokenEnrichment withAud(List<java.lang.String> aud) {
        this.aud = aud;
        return this;
    }

    /**
     * The login sso provider for this login attempt.
     * 
     */
    @JsonProperty("sso_provider")
    public java.lang.String getSsoProvider() {
        return ssoProvider;
    }

    /**
     * The login sso provider for this login attempt.
     * 
     */
    @JsonProperty("sso_provider")
    public void setSsoProvider(java.lang.String ssoProvider) {
        this.ssoProvider = ssoProvider;
    }

    public HydraIdTokenEnrichment withSsoProvider(java.lang.String ssoProvider) {
        this.ssoProvider = ssoProvider;
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

    public HydraIdTokenEnrichment withRoles(Set<String> roles) {
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

    public HydraIdTokenEnrichment withOrgs(Set<String> orgs) {
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

    public HydraIdTokenEnrichment withProjects(Set<String> projects) {
        this.projects = projects;
        return this;
    }

    /**
     * The additional groups information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    public Set<String> getGroups() {
        return groups;
    }

    /**
     * The additional groups information given the client, project, and organization.
     * 
     */
    @JsonProperty("groups")
    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public HydraIdTokenEnrichment withGroups(Set<String> groups) {
        this.groups = groups;
        return this;
    }

    /**
     * The experiments that this user has active.
     * 
     */
    @JsonProperty("experiments")
    public List<java.lang.String> getExperiments() {
        return experiments;
    }

    /**
     * The experiments that this user has active.
     * 
     */
    @JsonProperty("experiments")
    public void setExperiments(List<java.lang.String> experiments) {
        this.experiments = experiments;
    }

    public HydraIdTokenEnrichment withExperiments(List<java.lang.String> experiments) {
        this.experiments = experiments;
        return this;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    public List<java.lang.String> getPermissions() {
        return permissions;
    }

    /**
     * The additional role information given the client, project, and organization.
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(List<java.lang.String> permissions) {
        this.permissions = permissions;
    }

    public HydraIdTokenEnrichment withPermissions(List<java.lang.String> permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * Policy based access controls used with things like minio and s3 and other IAM providers.
     * 
     */
    @JsonProperty("policy")
    public java.lang.String getPolicy() {
        return policy;
    }

    /**
     * Policy based access controls used with things like minio and s3 and other IAM providers.
     * 
     */
    @JsonProperty("policy")
    public void setPolicy(java.lang.String policy) {
        this.policy = policy;
    }

    public HydraIdTokenEnrichment withPolicy(java.lang.String policy) {
        this.policy = policy;
        return this;
    }

}
