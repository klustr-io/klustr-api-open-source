
package io.klustr.schemas.persons.veriff;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * VeriffVerification
 * <p>
 * The verification that was performed
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "id",
    "person",
    "reason",
    "decision",
    "comments",
    "document",
    "reasonCode",
    "decisionTime",
    "acceptanceTime"
})
@Generated("jsonschema2pojo")
public class VeriffVerification {

    /**
     * The reason code for this verification
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The reason code for this verification")
    private Double code;
    /**
     * The unique reference to this verification by veriff and third party for linkage.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique reference to this verification by veriff and third party for linkage.")
    private String id;
    /**
     * VeriffPerson
     * <p>
     * A object representing a person in Veriff.
     * 
     */
    @JsonProperty("person")
    @JsonPropertyDescription("A object representing a person in Veriff.")
    private VeriffPerson person;
    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reason")
    @JsonPropertyDescription("Any reason codes for the rejection.")
    private String reason;
    /**
     * Verification decision
     * 
     */
    @JsonProperty("decision")
    @JsonPropertyDescription("Verification decision")
    private String decision;
    /**
     * Comments made by a human verifier.
     * 
     */
    @JsonProperty("comments")
    @JsonPropertyDescription("Comments made by a human verifier.")
    private List<String> comments = new ArrayList<String>();
    /**
     * VeriffDocument
     * <p>
     * The document that was verified
     * 
     */
    @JsonProperty("document")
    @JsonPropertyDescription("The document that was verified")
    private VeriffDocument document;
    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reasonCode")
    @JsonPropertyDescription("Any reason codes for the rejection.")
    private String reasonCode;
    @JsonProperty("decisionTime")
    private DateTime decisionTime;
    @JsonProperty("acceptanceTime")
    private DateTime acceptanceTime;

    /**
     * The reason code for this verification
     * 
     */
    @JsonProperty("code")
    public Double getCode() {
        return code;
    }

    /**
     * The reason code for this verification
     * 
     */
    @JsonProperty("code")
    public void setCode(Double code) {
        this.code = code;
    }

    public VeriffVerification withCode(Double code) {
        this.code = code;
        return this;
    }

    /**
     * The unique reference to this verification by veriff and third party for linkage.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique reference to this verification by veriff and third party for linkage.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public VeriffVerification withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * VeriffPerson
     * <p>
     * A object representing a person in Veriff.
     * 
     */
    @JsonProperty("person")
    public VeriffPerson getPerson() {
        return person;
    }

    /**
     * VeriffPerson
     * <p>
     * A object representing a person in Veriff.
     * 
     */
    @JsonProperty("person")
    public void setPerson(VeriffPerson person) {
        this.person = person;
    }

    public VeriffVerification withPerson(VeriffPerson person) {
        this.person = person;
        return this;
    }

    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public VeriffVerification withReason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * Verification decision
     * 
     */
    @JsonProperty("decision")
    public String getDecision() {
        return decision;
    }

    /**
     * Verification decision
     * 
     */
    @JsonProperty("decision")
    public void setDecision(String decision) {
        this.decision = decision;
    }

    public VeriffVerification withDecision(String decision) {
        this.decision = decision;
        return this;
    }

    /**
     * Comments made by a human verifier.
     * 
     */
    @JsonProperty("comments")
    public List<String> getComments() {
        return comments;
    }

    /**
     * Comments made by a human verifier.
     * 
     */
    @JsonProperty("comments")
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public VeriffVerification withComments(List<String> comments) {
        this.comments = comments;
        return this;
    }

    /**
     * VeriffDocument
     * <p>
     * The document that was verified
     * 
     */
    @JsonProperty("document")
    public VeriffDocument getDocument() {
        return document;
    }

    /**
     * VeriffDocument
     * <p>
     * The document that was verified
     * 
     */
    @JsonProperty("document")
    public void setDocument(VeriffDocument document) {
        this.document = document;
    }

    public VeriffVerification withDocument(VeriffDocument document) {
        this.document = document;
        return this;
    }

    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reasonCode")
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Any reason codes for the rejection.
     * 
     */
    @JsonProperty("reasonCode")
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public VeriffVerification withReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
        return this;
    }

    @JsonProperty("decisionTime")
    public DateTime getDecisionTime() {
        return decisionTime;
    }

    @JsonProperty("decisionTime")
    public void setDecisionTime(DateTime decisionTime) {
        this.decisionTime = decisionTime;
    }

    public VeriffVerification withDecisionTime(DateTime decisionTime) {
        this.decisionTime = decisionTime;
        return this;
    }

    @JsonProperty("acceptanceTime")
    public DateTime getAcceptanceTime() {
        return acceptanceTime;
    }

    @JsonProperty("acceptanceTime")
    public void setAcceptanceTime(DateTime acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public VeriffVerification withAcceptanceTime(DateTime acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
        return this;
    }

}
