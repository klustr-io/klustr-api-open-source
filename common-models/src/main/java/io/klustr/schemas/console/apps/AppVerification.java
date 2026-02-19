
package io.klustr.schemas.console.apps;

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
 * AppVerification
 * <p>
 * Contains approval status and overall status indicators for publication.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "approval",
    "update_date",
    "additional_details"
})
@Generated("jsonschema2pojo")
public class AppVerification {

    /**
     * AppStatus
     * <p>
     * The current status of the workflow.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The current status of the workflow.")
    private AppVerification.AppStatus status;
    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("approval")
    @JsonPropertyDescription("The current status of the application.")
    private AppVerification.Approval approval;
    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("update_date")
    @JsonPropertyDescription("The current status of the application.")
    private DateTime updateDate;
    /**
     * The additional details given the application status.
     * 
     */
    @JsonProperty("additional_details")
    @JsonPropertyDescription("The additional details given the application status.")
    private List<String> additionalDetails = new ArrayList<String>();

    /**
     * AppStatus
     * <p>
     * The current status of the workflow.
     * 
     */
    @JsonProperty("status")
    public AppVerification.AppStatus getStatus() {
        return status;
    }

    /**
     * AppStatus
     * <p>
     * The current status of the workflow.
     * 
     */
    @JsonProperty("status")
    public void setStatus(AppVerification.AppStatus status) {
        this.status = status;
    }

    public AppVerification withStatus(AppVerification.AppStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("approval")
    public AppVerification.Approval getApproval() {
        return approval;
    }

    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("approval")
    public void setApproval(AppVerification.Approval approval) {
        this.approval = approval;
    }

    public AppVerification withApproval(AppVerification.Approval approval) {
        this.approval = approval;
        return this;
    }

    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("update_date")
    public DateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * The current status of the application.
     * 
     */
    @JsonProperty("update_date")
    public void setUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
    }

    public AppVerification withUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    /**
     * The additional details given the application status.
     * 
     */
    @JsonProperty("additional_details")
    public List<String> getAdditionalDetails() {
        return additionalDetails;
    }

    /**
     * The additional details given the application status.
     * 
     */
    @JsonProperty("additional_details")
    public void setAdditionalDetails(List<String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public AppVerification withAdditionalDetails(List<String> additionalDetails) {
        this.additionalDetails = additionalDetails;
        return this;
    }


    /**
     * AppStatus
     * <p>
     * The current status of the workflow.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AppStatus {

        NONE("none"),
        TESTING("testing"),
        PUBLISHED("published");
        private final String value;
        private final static Map<String, AppVerification.AppStatus> CONSTANTS = new HashMap<String, AppVerification.AppStatus>();

        static {
            for (AppVerification.AppStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AppStatus(String value) {
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
        public static AppVerification.AppStatus fromValue(String value) {
            AppVerification.AppStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The current status of the application.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Approval {

        NONE("none"),
        PENDING("pending"),
        VERIFIED("verified"),
        UNVERIFIED("unverified"),
        RESTRICTED("restricted"),
        REJECTED("rejected");
        private final String value;
        private final static Map<String, AppVerification.Approval> CONSTANTS = new HashMap<String, AppVerification.Approval>();

        static {
            for (AppVerification.Approval c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Approval(String value) {
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
        public static AppVerification.Approval fromValue(String value) {
            AppVerification.Approval constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
