
package io.klustr.schemas.persons;

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
 * PersonVerification
 * <p>
 * The namespace for this person which owns the record or operates it.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "reason",
    "timestamp"
})
@Generated("jsonschema2pojo")
public class PersonVerification {

    /**
     * PersonVerificationStatus
     * <p>
     * The status of the current verification
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of the current verification")
    private PersonVerification.PersonVerificationStatus status;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("timestamp")
    private DateTime timestamp;

    /**
     * PersonVerificationStatus
     * <p>
     * The status of the current verification
     * 
     */
    @JsonProperty("status")
    public PersonVerification.PersonVerificationStatus getStatus() {
        return status;
    }

    /**
     * PersonVerificationStatus
     * <p>
     * The status of the current verification
     * 
     */
    @JsonProperty("status")
    public void setStatus(PersonVerification.PersonVerificationStatus status) {
        this.status = status;
    }

    public PersonVerification withStatus(PersonVerification.PersonVerificationStatus status) {
        this.status = status;
        return this;
    }

    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public PersonVerification withReason(String reason) {
        this.reason = reason;
        return this;
    }

    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public PersonVerification withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }


    /**
     * PersonVerificationStatus
     * <p>
     * The status of the current verification
     * 
     */
    @Generated("jsonschema2pojo")
    public enum PersonVerificationStatus {

        STARTED("started"),
        EXPIRED("expired"),
        ABANDONED("abandoned"),
        REJECTED("rejected"),
        SUCCESS("success"),
        SUBMITTED("submitted"),
        DECLINED("declined"),
        APPROVED("approved");
        private final String value;
        private final static Map<String, PersonVerification.PersonVerificationStatus> CONSTANTS = new HashMap<String, PersonVerification.PersonVerificationStatus>();

        static {
            for (PersonVerification.PersonVerificationStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PersonVerificationStatus(String value) {
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
        public static PersonVerification.PersonVerificationStatus fromValue(String value) {
            PersonVerification.PersonVerificationStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
