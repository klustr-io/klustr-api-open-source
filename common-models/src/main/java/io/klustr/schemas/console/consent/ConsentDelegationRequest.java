
package io.klustr.schemas.console.consent;

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
import org.joda.time.DateTime;


/**
 * ConsentDelegationRequest
 * <p>
 * Enables a person to delegate consent to another party.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "challenge_id",
    "household_id",
    "subject_id",
    "act",
    "client_id",
    "scopes",
    "app_id",
    "project_id",
    "org_id",
    "status",
    "creation_date",
    "approved_date"
})
@Generated("jsonschema2pojo")
public class ConsentDelegationRequest {

    /**
     * The unique ID of this delegation request.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this delegation request.")
    private String id;
    /**
     * The unique ID of this consent request.
     * 
     */
    @JsonProperty("challenge_id")
    @JsonPropertyDescription("The unique ID of this consent request.")
    private String challengeId;
    /**
     * The household that this consent delegation is targeted against.
     * 
     */
    @JsonProperty("household_id")
    @JsonPropertyDescription("The household that this consent delegation is targeted against.")
    private String householdId;
    /**
     * The person in the household that is requesting consent.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The person in the household that is requesting consent.")
    private String subjectId;
    /**
     * The delegated person who has approved the consent request.
     * 
     */
    @JsonProperty("act")
    @JsonPropertyDescription("The delegated person who has approved the consent request.")
    private String act;
    /**
     * The client that is targeted for this delegation.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The client that is targeted for this delegation.")
    private String clientId;
    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes to apply and access for this client")
    private List<String> scopes = new ArrayList<String>();
    /**
     * The application that is targeted for this delegation.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The application that is targeted for this delegation.")
    private String appId;
    /**
     * The project that is targetd for this delegation.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project that is targetd for this delegation.")
    private String projectId;
    /**
     * The organization that this consent delegation was performed on.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that this consent delegation was performed on.")
    private String orgId;
    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this consent attribute.")
    private ConsentDelegationRequest.Status status;
    /**
     * The date this request was made in UTC time.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this request was made in UTC time.")
    private DateTime creationDate;
    /**
     * The date that this delegation request was approved.
     * 
     */
    @JsonProperty("approved_date")
    @JsonPropertyDescription("The date that this delegation request was approved.")
    private DateTime approvedDate;

    /**
     * The unique ID of this delegation request.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this delegation request.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentDelegationRequest withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The unique ID of this consent request.
     * 
     */
    @JsonProperty("challenge_id")
    public String getChallengeId() {
        return challengeId;
    }

    /**
     * The unique ID of this consent request.
     * 
     */
    @JsonProperty("challenge_id")
    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public ConsentDelegationRequest withChallengeId(String challengeId) {
        this.challengeId = challengeId;
        return this;
    }

    /**
     * The household that this consent delegation is targeted against.
     * 
     */
    @JsonProperty("household_id")
    public String getHouseholdId() {
        return householdId;
    }

    /**
     * The household that this consent delegation is targeted against.
     * 
     */
    @JsonProperty("household_id")
    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public ConsentDelegationRequest withHouseholdId(String householdId) {
        this.householdId = householdId;
        return this;
    }

    /**
     * The person in the household that is requesting consent.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The person in the household that is requesting consent.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public ConsentDelegationRequest withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The delegated person who has approved the consent request.
     * 
     */
    @JsonProperty("act")
    public String getAct() {
        return act;
    }

    /**
     * The delegated person who has approved the consent request.
     * 
     */
    @JsonProperty("act")
    public void setAct(String act) {
        this.act = act;
    }

    public ConsentDelegationRequest withAct(String act) {
        this.act = act;
        return this;
    }

    /**
     * The client that is targeted for this delegation.
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The client that is targeted for this delegation.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ConsentDelegationRequest withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * The scopes to apply and access for this client
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public ConsentDelegationRequest withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * The application that is targeted for this delegation.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The application that is targeted for this delegation.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ConsentDelegationRequest withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The project that is targetd for this delegation.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project that is targetd for this delegation.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ConsentDelegationRequest withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The organization that this consent delegation was performed on.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that this consent delegation was performed on.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ConsentDelegationRequest withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    public ConsentDelegationRequest.Status getStatus() {
        return status;
    }

    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ConsentDelegationRequest.Status status) {
        this.status = status;
    }

    public ConsentDelegationRequest withStatus(ConsentDelegationRequest.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The date this request was made in UTC time.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this request was made in UTC time.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ConsentDelegationRequest withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date that this delegation request was approved.
     * 
     */
    @JsonProperty("approved_date")
    public DateTime getApprovedDate() {
        return approvedDate;
    }

    /**
     * The date that this delegation request was approved.
     * 
     */
    @JsonProperty("approved_date")
    public void setApprovedDate(DateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public ConsentDelegationRequest withApprovedDate(DateTime approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }


    /**
     * The status of this consent attribute.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        PENDING("pending"),
        REJECTED("rejected"),
        COMPLETED("completed"),
        EXPIRED("expired");
        private final String value;
        private final static Map<String, ConsentDelegationRequest.Status> CONSTANTS = new HashMap<String, ConsentDelegationRequest.Status>();

        static {
            for (ConsentDelegationRequest.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
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
        public static ConsentDelegationRequest.Status fromValue(String value) {
            ConsentDelegationRequest.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
