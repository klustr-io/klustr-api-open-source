
package io.klustr.schemas.console.projects;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ProjectServiceReference
 * <p>
 * A reference to a service that a project is enabled to consume.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "uri"
})
@Generated("jsonschema2pojo")
public class ProjectServiceReference {

    /**
     * The service that is registered with this project
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The service that is registered with this project")
    private String id;
    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The URI for this project.")
    private ProjectServiceReference.Type type;
    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("uri")
    @JsonPropertyDescription("The URI for this project.")
    private String uri;

    /**
     * The service that is registered with this project
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The service that is registered with this project
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectServiceReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("type")
    public ProjectServiceReference.Type getType() {
        return type;
    }

    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("type")
    public void setType(ProjectServiceReference.Type type) {
        this.type = type;
    }

    public ProjectServiceReference withType(ProjectServiceReference.Type type) {
        this.type = type;
        return this;
    }

    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    /**
     * The URI for this project.
     * 
     */
    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    public ProjectServiceReference withUri(String uri) {
        this.uri = uri;
        return this;
    }


    /**
     * The URI for this project.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        API("api"),
        DATABASE("database"),
        COMPUTE("compute");
        private final String value;
        private final static Map<String, ProjectServiceReference.Type> CONSTANTS = new HashMap<String, ProjectServiceReference.Type>();

        static {
            for (ProjectServiceReference.Type c: values()) {
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
        public static ProjectServiceReference.Type fromValue(String value) {
            ProjectServiceReference.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
