
package io.klustr.schemas.console.apis;

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
 * ApiKey
 * <p>
 * A api key that is used to access services.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "key",
    "creation_date",
    "status",
    "tags",
    "restriction"
})
@Generated("jsonschema2pojo")
public class ApiKey {

    /**
     * The unique ID for this key
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this key")
    private String id;
    /**
     * An optional description for this key, for example prod, uat, etc.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("An optional description for this key, for example prod, uat, etc.")
    private String name;
    /**
     * The api key available to use.
     * 
     */
    @JsonProperty("key")
    @JsonPropertyDescription("The api key available to use.")
    private String key;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * ApiKeyStatus
     * <p>
     * The status of this key
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this key")
    private ApiKey.ApiKeyStatus status;
    @JsonProperty("tags")
    private List<String> tags = new ArrayList<String>();
    /**
     * ApiRestriction
     * <p>
     * The restrictions for an API that is used to protect unauthorized access.
     * 
     */
    @JsonProperty("restriction")
    @JsonPropertyDescription("The restrictions for an API that is used to protect unauthorized access.")
    private ApiRestriction restriction;

    /**
     * The unique ID for this key
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this key
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ApiKey withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * An optional description for this key, for example prod, uat, etc.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * An optional description for this key, for example prod, uat, etc.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ApiKey withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The api key available to use.
     * 
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * The api key available to use.
     * 
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    public ApiKey withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ApiKey withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * ApiKeyStatus
     * <p>
     * The status of this key
     * 
     */
    @JsonProperty("status")
    public ApiKey.ApiKeyStatus getStatus() {
        return status;
    }

    /**
     * ApiKeyStatus
     * <p>
     * The status of this key
     * 
     */
    @JsonProperty("status")
    public void setStatus(ApiKey.ApiKeyStatus status) {
        this.status = status;
    }

    public ApiKey withStatus(ApiKey.ApiKeyStatus status) {
        this.status = status;
        return this;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ApiKey withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * ApiRestriction
     * <p>
     * The restrictions for an API that is used to protect unauthorized access.
     * 
     */
    @JsonProperty("restriction")
    public ApiRestriction getRestriction() {
        return restriction;
    }

    /**
     * ApiRestriction
     * <p>
     * The restrictions for an API that is used to protect unauthorized access.
     * 
     */
    @JsonProperty("restriction")
    public void setRestriction(ApiRestriction restriction) {
        this.restriction = restriction;
    }

    public ApiKey withRestriction(ApiRestriction restriction) {
        this.restriction = restriction;
        return this;
    }


    /**
     * ApiKeyStatus
     * <p>
     * The status of this key
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ApiKeyStatus {

        ENABLED("enabled"),
        DELETED("deleted");
        private final String value;
        private final static Map<String, ApiKey.ApiKeyStatus> CONSTANTS = new HashMap<String, ApiKey.ApiKeyStatus>();

        static {
            for (ApiKey.ApiKeyStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ApiKeyStatus(String value) {
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
        public static ApiKey.ApiKeyStatus fromValue(String value) {
            ApiKey.ApiKeyStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
