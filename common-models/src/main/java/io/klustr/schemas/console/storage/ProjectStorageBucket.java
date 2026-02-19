
package io.klustr.schemas.console.storage;

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
 * ProjectStorageBucket
 * <p>
 * Configurable storage for a project to use with apps and services compatible with S3 clients.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "description",
    "tags",
    "type",
    "status",
    "project_id",
    "url",
    "tier",
    "limit_mb",
    "usage_mb",
    "objects",
    "date_created"
})
@Generated("jsonschema2pojo")
public class ProjectStorageBucket {

    /**
     * The unique ID of this storage.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this storage.")
    private String id;
    /**
     * A description of this storage for auditing and usage.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("A description of this storage for auditing and usage.")
    private String description;
    /**
     * Tags used to classify and categories the storage.
     * 
     */
    @JsonProperty("tags")
    @JsonPropertyDescription("Tags used to classify and categories the storage.")
    private List<String> tags = new ArrayList<String>();
    /**
     * The type of storage.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of storage.")
    private ProjectStorageBucket.Type type;
    /**
     * The type of storage.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The type of storage.")
    private ProjectStorageBucket.Status status;
    /**
     * The project ID that owns this storage.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project ID that owns this storage.")
    private String projectId;
    /**
     * The url used to connect to this storage.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url used to connect to this storage.")
    private String url;
    /**
     * The tier of this storage.
     * 
     */
    @JsonProperty("tier")
    @JsonPropertyDescription("The tier of this storage.")
    private ProjectStorageBucket.Tier tier;
    /**
     * The limits for this storage in mb
     * 
     */
    @JsonProperty("limit_mb")
    @JsonPropertyDescription("The limits for this storage in mb")
    private Long limitMb;
    /**
     * The current usage in mb
     * 
     */
    @JsonProperty("usage_mb")
    @JsonPropertyDescription("The current usage in mb")
    private Long usageMb;
    /**
     * The count of objects stored.
     * 
     */
    @JsonProperty("objects")
    @JsonPropertyDescription("The count of objects stored.")
    private Long objects;
    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The date the agreement was created.")
    private DateTime dateCreated;

    /**
     * The unique ID of this storage.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this storage.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectStorageBucket withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * A description of this storage for auditing and usage.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * A description of this storage for auditing and usage.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStorageBucket withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Tags used to classify and categories the storage.
     * 
     */
    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    /**
     * Tags used to classify and categories the storage.
     * 
     */
    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ProjectStorageBucket withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * The type of storage.
     * 
     */
    @JsonProperty("type")
    public ProjectStorageBucket.Type getType() {
        return type;
    }

    /**
     * The type of storage.
     * 
     */
    @JsonProperty("type")
    public void setType(ProjectStorageBucket.Type type) {
        this.type = type;
    }

    public ProjectStorageBucket withType(ProjectStorageBucket.Type type) {
        this.type = type;
        return this;
    }

    /**
     * The type of storage.
     * 
     */
    @JsonProperty("status")
    public ProjectStorageBucket.Status getStatus() {
        return status;
    }

    /**
     * The type of storage.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ProjectStorageBucket.Status status) {
        this.status = status;
    }

    public ProjectStorageBucket withStatus(ProjectStorageBucket.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The project ID that owns this storage.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project ID that owns this storage.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ProjectStorageBucket withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The url used to connect to this storage.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url used to connect to this storage.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public ProjectStorageBucket withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The tier of this storage.
     * 
     */
    @JsonProperty("tier")
    public ProjectStorageBucket.Tier getTier() {
        return tier;
    }

    /**
     * The tier of this storage.
     * 
     */
    @JsonProperty("tier")
    public void setTier(ProjectStorageBucket.Tier tier) {
        this.tier = tier;
    }

    public ProjectStorageBucket withTier(ProjectStorageBucket.Tier tier) {
        this.tier = tier;
        return this;
    }

    /**
     * The limits for this storage in mb
     * 
     */
    @JsonProperty("limit_mb")
    public Long getLimitMb() {
        return limitMb;
    }

    /**
     * The limits for this storage in mb
     * 
     */
    @JsonProperty("limit_mb")
    public void setLimitMb(Long limitMb) {
        this.limitMb = limitMb;
    }

    public ProjectStorageBucket withLimitMb(Long limitMb) {
        this.limitMb = limitMb;
        return this;
    }

    /**
     * The current usage in mb
     * 
     */
    @JsonProperty("usage_mb")
    public Long getUsageMb() {
        return usageMb;
    }

    /**
     * The current usage in mb
     * 
     */
    @JsonProperty("usage_mb")
    public void setUsageMb(Long usageMb) {
        this.usageMb = usageMb;
    }

    public ProjectStorageBucket withUsageMb(Long usageMb) {
        this.usageMb = usageMb;
        return this;
    }

    /**
     * The count of objects stored.
     * 
     */
    @JsonProperty("objects")
    public Long getObjects() {
        return objects;
    }

    /**
     * The count of objects stored.
     * 
     */
    @JsonProperty("objects")
    public void setObjects(Long objects) {
        this.objects = objects;
    }

    public ProjectStorageBucket withObjects(Long objects) {
        this.objects = objects;
        return this;
    }

    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ProjectStorageBucket withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }


    /**
     * The type of storage.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        CREATING("creating"),
        ACTIVE("active"),
        DESTROYING("destroying"),
        DELETED("deleted");
        private final String value;
        private final static Map<String, ProjectStorageBucket.Status> CONSTANTS = new HashMap<String, ProjectStorageBucket.Status>();

        static {
            for (ProjectStorageBucket.Status c: values()) {
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
        public static ProjectStorageBucket.Status fromValue(String value) {
            ProjectStorageBucket.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The tier of this storage.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Tier {

        FREE("free"),
        MICRO("micro"),
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        XLARGE("xlarge");
        private final String value;
        private final static Map<String, ProjectStorageBucket.Tier> CONSTANTS = new HashMap<String, ProjectStorageBucket.Tier>();

        static {
            for (ProjectStorageBucket.Tier c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Tier(String value) {
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
        public static ProjectStorageBucket.Tier fromValue(String value) {
            ProjectStorageBucket.Tier constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The type of storage.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        PRIVATE("private"),
        PUBLIC("public");
        private final String value;
        private final static Map<String, ProjectStorageBucket.Type> CONSTANTS = new HashMap<String, ProjectStorageBucket.Type>();

        static {
            for (ProjectStorageBucket.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
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
        public static ProjectStorageBucket.Type fromValue(String value) {
            ProjectStorageBucket.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
