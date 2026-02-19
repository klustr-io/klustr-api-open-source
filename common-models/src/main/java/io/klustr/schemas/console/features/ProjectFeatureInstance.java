
package io.klustr.schemas.console.features;

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
 * ProjectFeatureInstance
 * <p>
 * Runtime instance of a feature attached to a specific project.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "project_id",
    "feature_id",
    "status",
    "env",
    "metadata",
    "creation_date"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureInstance {

    /**
     * Unique identifier for this feature instance.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Unique identifier for this feature instance.")
    private java.lang.String id;
    /**
     * Project this feature instance belongs to.
     * (Required)
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("Project this feature instance belongs to.")
    private java.lang.String projectId;
    /**
     * Reference to the catalog feature ID.
     * (Required)
     * 
     */
    @JsonProperty("feature_id")
    @JsonPropertyDescription("Reference to the catalog feature ID.")
    private java.lang.String featureId;
    /**
     * Runtime status of the feature instance.
     * (Required)
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("Runtime status of the feature instance.")
    private ProjectFeatureInstance.Status status;
    /**
     * Resolved environment variables for runtime.
     * 
     */
    @JsonProperty("env")
    @JsonPropertyDescription("Resolved environment variables for runtime.")
    private Map<String, String> env;
    /**
     * Runtime metadata for hydration and idempotency.
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Runtime metadata for hydration and idempotency.")
    private Map<String, String> metadata;
    /**
     * Creation timestamp.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("Creation timestamp.")
    private DateTime creationDate;

    /**
     * Unique identifier for this feature instance.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * Unique identifier for this feature instance.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public ProjectFeatureInstance withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * Project this feature instance belongs to.
     * (Required)
     * 
     */
    @JsonProperty("project_id")
    public java.lang.String getProjectId() {
        return projectId;
    }

    /**
     * Project this feature instance belongs to.
     * (Required)
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    public ProjectFeatureInstance withProjectId(java.lang.String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Reference to the catalog feature ID.
     * (Required)
     * 
     */
    @JsonProperty("feature_id")
    public java.lang.String getFeatureId() {
        return featureId;
    }

    /**
     * Reference to the catalog feature ID.
     * (Required)
     * 
     */
    @JsonProperty("feature_id")
    public void setFeatureId(java.lang.String featureId) {
        this.featureId = featureId;
    }

    public ProjectFeatureInstance withFeatureId(java.lang.String featureId) {
        this.featureId = featureId;
        return this;
    }

    /**
     * Runtime status of the feature instance.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public ProjectFeatureInstance.Status getStatus() {
        return status;
    }

    /**
     * Runtime status of the feature instance.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(ProjectFeatureInstance.Status status) {
        this.status = status;
    }

    public ProjectFeatureInstance withStatus(ProjectFeatureInstance.Status status) {
        this.status = status;
        return this;
    }

    /**
     * Resolved environment variables for runtime.
     * 
     */
    @JsonProperty("env")
    public Map<String, String> getEnv() {
        return env;
    }

    /**
     * Resolved environment variables for runtime.
     * 
     */
    @JsonProperty("env")
    public void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public ProjectFeatureInstance withEnv(Map<String, String> env) {
        this.env = env;
        return this;
    }

    /**
     * Runtime metadata for hydration and idempotency.
     * 
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Runtime metadata for hydration and idempotency.
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public ProjectFeatureInstance withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Creation timestamp.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Creation timestamp.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ProjectFeatureInstance withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }


    /**
     * Runtime status of the feature instance.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        PROVISIONING("provisioning"),
        ACTIVE("active"),
        ERROR("error"),
        DELETING("deleting");
        private final java.lang.String value;
        private final static Map<java.lang.String, ProjectFeatureInstance.Status> CONSTANTS = new HashMap<java.lang.String, ProjectFeatureInstance.Status>();

        static {
            for (ProjectFeatureInstance.Status c: values()) {
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
        public static ProjectFeatureInstance.Status fromValue(java.lang.String value) {
            ProjectFeatureInstance.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
