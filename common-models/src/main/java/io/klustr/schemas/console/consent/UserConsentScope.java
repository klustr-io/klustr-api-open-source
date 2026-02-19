
package io.klustr.schemas.console.consent;

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
 * UserConsentScope
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "consent_method",
    "date_created",
    "date_revoked",
    "date_modified"
})
@Generated("jsonschema2pojo")
public class UserConsentScope {

    /**
     * The scope that is associated.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The scope that is associated.")
    private String id;
    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of the consent granted")
    private UserConsentScope.Status status;
    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("consent_method")
    @JsonPropertyDescription("The status of the consent granted")
    private UserConsentScope.ConsentMethod consentMethod;
    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The date record was created.")
    private DateTime dateCreated;
    /**
     * The date record was revoked
     * 
     */
    @JsonProperty("date_revoked")
    @JsonPropertyDescription("The date record was revoked")
    private DateTime dateRevoked;
    /**
     * The date record was modified
     * 
     */
    @JsonProperty("date_modified")
    @JsonPropertyDescription("The date record was modified")
    private DateTime dateModified;

    /**
     * The scope that is associated.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The scope that is associated.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserConsentScope withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("status")
    public UserConsentScope.Status getStatus() {
        return status;
    }

    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("status")
    public void setStatus(UserConsentScope.Status status) {
        this.status = status;
    }

    public UserConsentScope withStatus(UserConsentScope.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("consent_method")
    public UserConsentScope.ConsentMethod getConsentMethod() {
        return consentMethod;
    }

    /**
     * The status of the consent granted
     * 
     */
    @JsonProperty("consent_method")
    public void setConsentMethod(UserConsentScope.ConsentMethod consentMethod) {
        this.consentMethod = consentMethod;
    }

    public UserConsentScope withConsentMethod(UserConsentScope.ConsentMethod consentMethod) {
        this.consentMethod = consentMethod;
        return this;
    }

    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserConsentScope withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The date record was revoked
     * 
     */
    @JsonProperty("date_revoked")
    public DateTime getDateRevoked() {
        return dateRevoked;
    }

    /**
     * The date record was revoked
     * 
     */
    @JsonProperty("date_revoked")
    public void setDateRevoked(DateTime dateRevoked) {
        this.dateRevoked = dateRevoked;
    }

    public UserConsentScope withDateRevoked(DateTime dateRevoked) {
        this.dateRevoked = dateRevoked;
        return this;
    }

    /**
     * The date record was modified
     * 
     */
    @JsonProperty("date_modified")
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * The date record was modified
     * 
     */
    @JsonProperty("date_modified")
    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public UserConsentScope withDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }


    /**
     * The status of the consent granted
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ConsentMethod {

        IMPLICIT("implicit"),
        EXPLICIT("explicit");
        private final String value;
        private final static Map<String, UserConsentScope.ConsentMethod> CONSTANTS = new HashMap<String, UserConsentScope.ConsentMethod>();

        static {
            for (UserConsentScope.ConsentMethod c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ConsentMethod(String value) {
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
        public static UserConsentScope.ConsentMethod fromValue(String value) {
            UserConsentScope.ConsentMethod constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The status of the consent granted
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        REVOKED("revoked"),
        EXPIRED("expired");
        private final String value;
        private final static Map<String, UserConsentScope.Status> CONSTANTS = new HashMap<String, UserConsentScope.Status>();

        static {
            for (UserConsentScope.Status c: values()) {
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
        public static UserConsentScope.Status fromValue(String value) {
            UserConsentScope.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
