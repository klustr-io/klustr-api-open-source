
package io.klustr.schemas.console.consent.types;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.klustr.schemas.console.consent.UserConsent;
import org.joda.time.DateTime;


/**
 * UserOrganizationConsent
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "org_id",
    "first_seen_date",
    "last_seen_date",
    "type",
    "status"
})
@Generated("jsonschema2pojo")
public class UserOrganizationConsent
    extends UserConsent
{

    /**
     * The organization that the user has accepted.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that the user has accepted.")
    private String orgId;
    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    @JsonPropertyDescription("The first consent date this experiment was last consented to.")
    private DateTime firstSeenDate;
    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    @JsonPropertyDescription("The last consent date this experiment was last consented to.")
    private DateTime lastSeenDate;
    /**
     * The type of consent granted.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of consent granted.")
    private UserOrganizationConsent.Type type;
    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this specific experiment consent.")
    private UserOrganizationConsent.Status status;

    /**
     * The organization that the user has accepted.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that the user has accepted.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserOrganizationConsent withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    public DateTime getFirstSeenDate() {
        return firstSeenDate;
    }

    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    public void setFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
    }

    public UserOrganizationConsent withFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
        return this;
    }

    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    public DateTime getLastSeenDate() {
        return lastSeenDate;
    }

    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    public void setLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public UserOrganizationConsent withLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
        return this;
    }

    /**
     * The type of consent granted.
     * 
     */
    @JsonProperty("type")
    public UserOrganizationConsent.Type getType() {
        return type;
    }

    /**
     * The type of consent granted.
     * 
     */
    @JsonProperty("type")
    public void setType(UserOrganizationConsent.Type type) {
        this.type = type;
    }

    public UserOrganizationConsent withType(UserOrganizationConsent.Type type) {
        this.type = type;
        return this;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public UserOrganizationConsent.Status getStatus() {
        return status;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public void setStatus(UserOrganizationConsent.Status status) {
        this.status = status;
    }

    public UserOrganizationConsent withStatus(UserOrganizationConsent.Status status) {
        this.status = status;
        return this;
    }


    /**
     * The status of this specific experiment consent.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        REVOKED("revoked"),
        EXPIRED("expired");
        private final String value;
        private final static Map<String, UserOrganizationConsent.Status> CONSTANTS = new HashMap<String, UserOrganizationConsent.Status>();

        static {
            for (UserOrganizationConsent.Status c: values()) {
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
        public static UserOrganizationConsent.Status fromValue(String value) {
            UserOrganizationConsent.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The type of consent granted.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        EXPLICIT("explicit"),
        IMPLICIT("implicit");
        private final String value;
        private final static Map<String, UserOrganizationConsent.Type> CONSTANTS = new HashMap<String, UserOrganizationConsent.Type>();

        static {
            for (UserOrganizationConsent.Type c: values()) {
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
        public static UserOrganizationConsent.Type fromValue(String value) {
            UserOrganizationConsent.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
