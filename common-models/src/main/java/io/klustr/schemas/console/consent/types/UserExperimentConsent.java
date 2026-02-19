
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
 * UserExperimentConsent
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "experiment_id",
    "client_id",
    "app_id",
    "project_id",
    "org_id",
    "first_seen_date",
    "last_seen_date",
    "status"
})
@Generated("jsonschema2pojo")
public class UserExperimentConsent
    extends UserConsent
{

    /**
     * The ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("experiment_id")
    @JsonPropertyDescription("The ID of the experiment that was consented to.")
    private String experimentId;
    /**
     * THe ID of the client that was used when approving the experiment.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("THe ID of the client that was used when approving the experiment.")
    private String clientId;
    /**
     * THe ID of the application that this experiment was connected to.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("THe ID of the application that this experiment was connected to.")
    private String appId;
    /**
     * THe ID of the project that this experiment was connected to.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("THe ID of the project that this experiment was connected to.")
    private String projectId;
    /**
     * THe ID of the organization that this experiment was connected to.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("THe ID of the organization that this experiment was connected to.")
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
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this specific experiment consent.")
    private UserExperimentConsent.Status status;

    /**
     * The ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("experiment_id")
    public String getExperimentId() {
        return experimentId;
    }

    /**
     * The ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("experiment_id")
    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public UserExperimentConsent withExperimentId(String experimentId) {
        this.experimentId = experimentId;
        return this;
    }

    /**
     * THe ID of the client that was used when approving the experiment.
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * THe ID of the client that was used when approving the experiment.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public UserExperimentConsent withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * THe ID of the application that this experiment was connected to.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * THe ID of the application that this experiment was connected to.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public UserExperimentConsent withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * THe ID of the project that this experiment was connected to.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * THe ID of the project that this experiment was connected to.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public UserExperimentConsent withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * THe ID of the organization that this experiment was connected to.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * THe ID of the organization that this experiment was connected to.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserExperimentConsent withOrgId(String orgId) {
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

    public UserExperimentConsent withFirstSeenDate(DateTime firstSeenDate) {
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

    public UserExperimentConsent withLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
        return this;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public UserExperimentConsent.Status getStatus() {
        return status;
    }

    /**
     * The status of this specific experiment consent.
     * 
     */
    @JsonProperty("status")
    public void setStatus(UserExperimentConsent.Status status) {
        this.status = status;
    }

    public UserExperimentConsent withStatus(UserExperimentConsent.Status status) {
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
        private final static Map<String, UserExperimentConsent.Status> CONSTANTS = new HashMap<String, UserExperimentConsent.Status>();

        static {
            for (UserExperimentConsent.Status c: values()) {
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
        public static UserExperimentConsent.Status fromValue(String value) {
            UserExperimentConsent.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
