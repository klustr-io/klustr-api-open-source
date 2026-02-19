
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
 * UserAgreementConsent
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "org_id",
    "agreement_id",
    "version_number",
    "first_seen_date",
    "last_seen_date",
    "status"
})
@Generated("jsonschema2pojo")
public class UserAgreementConsent
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
     * The unique ID of the agreement that was signed off on.
     * 
     */
    @JsonProperty("agreement_id")
    @JsonPropertyDescription("The unique ID of the agreement that was signed off on.")
    private String agreementId;
    /**
     * The semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    @JsonPropertyDescription("The semantic version number of the agreement.")
    private String versionNumber;
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
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this specific experiment consent.")
    private UserAgreementConsent.Status status;

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

    public UserAgreementConsent withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The unique ID of the agreement that was signed off on.
     * 
     */
    @JsonProperty("agreement_id")
    public String getAgreementId() {
        return agreementId;
    }

    /**
     * The unique ID of the agreement that was signed off on.
     * 
     */
    @JsonProperty("agreement_id")
    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public UserAgreementConsent withAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    /**
     * The semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * The semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public UserAgreementConsent withVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
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

    public UserAgreementConsent withFirstSeenDate(DateTime firstSeenDate) {
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

    public UserAgreementConsent withLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
        return this;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public UserAgreementConsent.Status getStatus() {
        return status;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public void setStatus(UserAgreementConsent.Status status) {
        this.status = status;
    }

    public UserAgreementConsent withStatus(UserAgreementConsent.Status status) {
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
        private final static Map<String, UserAgreementConsent.Status> CONSTANTS = new HashMap<String, UserAgreementConsent.Status>();

        static {
            for (UserAgreementConsent.Status c: values()) {
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
        public static UserAgreementConsent.Status fromValue(String value) {
            UserAgreementConsent.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
