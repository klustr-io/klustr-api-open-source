
package io.klustr.schemas.console.experiments;

import java.util.HashMap;
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
 * ExperimentMembership
 * <p>
 * Manages the membership of users into experiments.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "experiment_id",
    "subject_id",
    "project_id",
    "app_id",
    "org_id",
    "timestamp",
    "status"
})
@Generated("jsonschema2pojo")
public class ExperimentMembership {

    /**
     * The unique ID of this membership
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this membership")
    private String id;
    /**
     * The ID of the experiment being issued.
     * 
     */
    @JsonProperty("experiment_id")
    @JsonPropertyDescription("The ID of the experiment being issued.")
    private String experimentId;
    /**
     * The ID of the subject involved with the experiment.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The ID of the subject involved with the experiment.")
    private String subjectId;
    /**
     * The ID of the project that owns this experiment.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The ID of the project that owns this experiment.")
    private String projectId;
    /**
     * The ID of the application that subscribed this person to the experiment.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The ID of the application that subscribed this person to the experiment.")
    private String appId;
    /**
     * The ID of the organization that owns this experiment.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The ID of the organization that owns this experiment.")
    private String orgId;
    /**
     * The timestamp that this membership was updated.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The timestamp that this membership was updated.")
    private DateTime timestamp;
    /**
     * The action that the user performed for this membership
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The action that the user performed for this membership")
    private ExperimentMembership.Status status;

    /**
     * The unique ID of this membership
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this membership
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ExperimentMembership withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The ID of the experiment being issued.
     * 
     */
    @JsonProperty("experiment_id")
    public String getExperimentId() {
        return experimentId;
    }

    /**
     * The ID of the experiment being issued.
     * 
     */
    @JsonProperty("experiment_id")
    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public ExperimentMembership withExperimentId(String experimentId) {
        this.experimentId = experimentId;
        return this;
    }

    /**
     * The ID of the subject involved with the experiment.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The ID of the subject involved with the experiment.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public ExperimentMembership withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The ID of the project that owns this experiment.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The ID of the project that owns this experiment.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ExperimentMembership withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The ID of the application that subscribed this person to the experiment.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The ID of the application that subscribed this person to the experiment.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ExperimentMembership withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The ID of the organization that owns this experiment.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The ID of the organization that owns this experiment.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ExperimentMembership withOrgId(String orgId) {
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

    public ExperimentMembership withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * The action that the user performed for this membership
     * 
     */
    @JsonProperty("status")
    public ExperimentMembership.Status getStatus() {
        return status;
    }

    /**
     * The action that the user performed for this membership
     * 
     */
    @JsonProperty("status")
    public void setStatus(ExperimentMembership.Status status) {
        this.status = status;
    }

    public ExperimentMembership withStatus(ExperimentMembership.Status status) {
        this.status = status;
        return this;
    }


    /**
     * The action that the user performed for this membership
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        OPT_IN("opt-in"),
        OPT_OUT("opt-out"),
        REVOKED("revoked");
        private final String value;
        private final static Map<String, ExperimentMembership.Status> CONSTANTS = new HashMap<String, ExperimentMembership.Status>();

        static {
            for (ExperimentMembership.Status c: values()) {
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
        public static ExperimentMembership.Status fromValue(String value) {
            ExperimentMembership.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
