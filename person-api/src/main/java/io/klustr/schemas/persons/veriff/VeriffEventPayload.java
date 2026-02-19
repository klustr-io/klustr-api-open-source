
package io.klustr.schemas.persons.veriff;

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
 * VeriffEventPayload
 * <p>
 * When veriff calls with their event payload it will have an action ['started','submitted']
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "action",
    "attemptId",
    "vendorData",
    "feature",
    "timestamp"
})
@Generated("jsonschema2pojo")
public class VeriffEventPayload {

    /**
     * The unique ID of this verification, for saving and reference.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this verification, for saving and reference.")
    private String id;
    /**
     * The status of the verification made, can be [started, submitted] anything else not good
     * 
     */
    @JsonProperty("action")
    @JsonPropertyDescription("The status of the verification made, can be [started, submitted] anything else not good")
    private VeriffEventPayload.Action action;
    /**
     * The specific attempt that was generated.
     * 
     */
    @JsonProperty("attemptId")
    @JsonPropertyDescription("The specific attempt that was generated.")
    private String attemptId;
    /**
     * Link to the original user ID requested.
     * 
     */
    @JsonProperty("vendorData")
    @JsonPropertyDescription("Link to the original user ID requested.")
    private String vendorData;
    /**
     * The feature that was generated
     * 
     */
    @JsonProperty("feature")
    @JsonPropertyDescription("The feature that was generated")
    private String feature;
    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The feature that was generated, populated on server side.")
    private DateTime timestamp;

    /**
     * The unique ID of this verification, for saving and reference.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this verification, for saving and reference.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public VeriffEventPayload withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The status of the verification made, can be [started, submitted] anything else not good
     * 
     */
    @JsonProperty("action")
    public VeriffEventPayload.Action getAction() {
        return action;
    }

    /**
     * The status of the verification made, can be [started, submitted] anything else not good
     * 
     */
    @JsonProperty("action")
    public void setAction(VeriffEventPayload.Action action) {
        this.action = action;
    }

    public VeriffEventPayload withAction(VeriffEventPayload.Action action) {
        this.action = action;
        return this;
    }

    /**
     * The specific attempt that was generated.
     * 
     */
    @JsonProperty("attemptId")
    public String getAttemptId() {
        return attemptId;
    }

    /**
     * The specific attempt that was generated.
     * 
     */
    @JsonProperty("attemptId")
    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public VeriffEventPayload withAttemptId(String attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    /**
     * Link to the original user ID requested.
     * 
     */
    @JsonProperty("vendorData")
    public String getVendorData() {
        return vendorData;
    }

    /**
     * Link to the original user ID requested.
     * 
     */
    @JsonProperty("vendorData")
    public void setVendorData(String vendorData) {
        this.vendorData = vendorData;
    }

    public VeriffEventPayload withVendorData(String vendorData) {
        this.vendorData = vendorData;
        return this;
    }

    /**
     * The feature that was generated
     * 
     */
    @JsonProperty("feature")
    public String getFeature() {
        return feature;
    }

    /**
     * The feature that was generated
     * 
     */
    @JsonProperty("feature")
    public void setFeature(String feature) {
        this.feature = feature;
    }

    public VeriffEventPayload withFeature(String feature) {
        this.feature = feature;
        return this;
    }

    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public VeriffEventPayload withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }


    /**
     * The status of the verification made, can be [started, submitted] anything else not good
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Action {

        STARTED("started"),
        SUBMITTED("submitted");
        private final String value;
        private final static Map<String, VeriffEventPayload.Action> CONSTANTS = new HashMap<String, VeriffEventPayload.Action>();

        static {
            for (VeriffEventPayload.Action c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Action(String value) {
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
        public static VeriffEventPayload.Action fromValue(String value) {
            VeriffEventPayload.Action constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
