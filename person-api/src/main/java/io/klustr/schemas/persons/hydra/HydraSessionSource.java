
package io.klustr.schemas.persons.hydra;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraSessionSource
 * <p>
 * The client and sourcing information which generated this token
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "client_id",
    "project_id",
    "app_id",
    "org_id"
})
@Generated("jsonschema2pojo")
public class HydraSessionSource {

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The source client ID that generated this access token.")
    private String clientId;
    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project ID that owns the client that generated this token.")
    private String projectId;
    /**
     * The app ID that is linked to this request.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The app ID that is linked to this request.")
    private String appId;
    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization ID that initiated this access token.")
    private String orgId;

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The source client ID that generated this access token.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public HydraSessionSource withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project ID that owns the client that generated this token.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public HydraSessionSource withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The app ID that is linked to this request.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The app ID that is linked to this request.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public HydraSessionSource withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization ID that initiated this access token.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public HydraSessionSource withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

}
