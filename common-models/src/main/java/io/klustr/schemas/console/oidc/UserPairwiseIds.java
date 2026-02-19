
package io.klustr.schemas.console.oidc;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * UserPairwiseIds
 * <p>
 * Enabling mapping an identifier that is pairwise to the original subject identifier.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "pairwise_id",
    "subject_id",
    "client_id",
    "project_id",
    "org_id",
    "timestamp"
})
@Generated("jsonschema2pojo")
public class UserPairwiseIds {

    /**
     * A uuid for this pairwise id map.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("A uuid for this pairwise id map.")
    private String id;
    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("pairwise_id")
    @JsonPropertyDescription("The pairwise ID mapped to oidc provider")
    private String pairwiseId;
    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The pairwise ID mapped to oidc provider")
    private String subjectId;
    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The client ID that was associated to this pairwise ID")
    private String clientId;
    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The client ID that was associated to this pairwise ID")
    private String projectId;
    /**
     * The org ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The org ID that was associated to this pairwise ID")
    private String orgId;
    /**
     * The timestamp that this membership was updated.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The timestamp that this membership was updated.")
    private DateTime timestamp;

    /**
     * A uuid for this pairwise id map.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * A uuid for this pairwise id map.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserPairwiseIds withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("pairwise_id")
    public String getPairwiseId() {
        return pairwiseId;
    }

    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("pairwise_id")
    public void setPairwiseId(String pairwiseId) {
        this.pairwiseId = pairwiseId;
    }

    public UserPairwiseIds withPairwiseId(String pairwiseId) {
        this.pairwiseId = pairwiseId;
        return this;
    }

    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The pairwise ID mapped to oidc provider
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public UserPairwiseIds withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public UserPairwiseIds withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The client ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public UserPairwiseIds withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The org ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The org ID that was associated to this pairwise ID
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserPairwiseIds withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The timestamp that this membership was updated.
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The timestamp that this membership was updated.
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserPairwiseIds withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

}
