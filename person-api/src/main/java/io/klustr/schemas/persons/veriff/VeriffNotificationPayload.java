
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffNotificationPayload
 * <p>
 * A webhook notification by veriff indicating the success or failure of a verification.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "sessionId",
    "vendorData",
    "attemptId",
    "version",
    "data",
    "technicalData"
})
@Generated("jsonschema2pojo")
public class VeriffNotificationPayload {

    /**
     * The unique ID of this verification, for saving and reference.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this verification, for saving and reference.")
    private String id;
    /**
     * The status of the verification made, can be [sucesss] anything else not good
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of the verification made, can be [sucesss] anything else not good")
    private String status;
    /**
     * The session id.
     * 
     */
    @JsonProperty("sessionId")
    @JsonPropertyDescription("The session id.")
    private String sessionId;
    /**
     * The vendor key data for linking.
     * 
     */
    @JsonProperty("vendorData")
    @JsonPropertyDescription("The vendor key data for linking.")
    private String vendorData;
    /**
     * The attempted id for this.
     * 
     */
    @JsonProperty("attemptId")
    @JsonPropertyDescription("The attempted id for this.")
    private String attemptId;
    /**
     * The version of this verification.
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("The version of this verification.")
    private String version;
    /**
     * VeriffVerificationData
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    private VeriffVerificationData data;
    /**
     * Reference and technical data about this verification
     * 
     */
    @JsonProperty("technicalData")
    @JsonPropertyDescription("Reference and technical data about this verification")
    private TechnicalData technicalData;

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

    public VeriffNotificationPayload withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The status of the verification made, can be [sucesss] anything else not good
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * The status of the verification made, can be [sucesss] anything else not good
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public VeriffNotificationPayload withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * The session id.
     * 
     */
    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    /**
     * The session id.
     * 
     */
    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public VeriffNotificationPayload withSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * The vendor key data for linking.
     * 
     */
    @JsonProperty("vendorData")
    public String getVendorData() {
        return vendorData;
    }

    /**
     * The vendor key data for linking.
     * 
     */
    @JsonProperty("vendorData")
    public void setVendorData(String vendorData) {
        this.vendorData = vendorData;
    }

    public VeriffNotificationPayload withVendorData(String vendorData) {
        this.vendorData = vendorData;
        return this;
    }

    /**
     * The attempted id for this.
     * 
     */
    @JsonProperty("attemptId")
    public String getAttemptId() {
        return attemptId;
    }

    /**
     * The attempted id for this.
     * 
     */
    @JsonProperty("attemptId")
    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public VeriffNotificationPayload withAttemptId(String attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    /**
     * The version of this verification.
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * The version of this verification.
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public VeriffNotificationPayload withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * VeriffVerificationData
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    public VeriffVerificationData getData() {
        return data;
    }

    /**
     * VeriffVerificationData
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    public void setData(VeriffVerificationData data) {
        this.data = data;
    }

    public VeriffNotificationPayload withData(VeriffVerificationData data) {
        this.data = data;
        return this;
    }

    /**
     * Reference and technical data about this verification
     * 
     */
    @JsonProperty("technicalData")
    public TechnicalData getTechnicalData() {
        return technicalData;
    }

    /**
     * Reference and technical data about this verification
     * 
     */
    @JsonProperty("technicalData")
    public void setTechnicalData(TechnicalData technicalData) {
        this.technicalData = technicalData;
    }

    public VeriffNotificationPayload withTechnicalData(TechnicalData technicalData) {
        this.technicalData = technicalData;
        return this;
    }

}
