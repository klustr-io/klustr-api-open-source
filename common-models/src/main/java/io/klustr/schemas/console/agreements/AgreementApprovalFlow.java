
package io.klustr.schemas.console.agreements;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AgreementApprovalFlow
 * <p>
 * Defines the key stakeholders who must sign and agree to the agreement being made active.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "org_id",
    "agreement_type",
    "email",
    "approvers"
})
@Generated("jsonschema2pojo")
public class AgreementApprovalFlow {

    /**
     * The unique ID of this agreement flow.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this agreement flow.")
    private String id;
    /**
     * The organization that owns this flow
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that owns this flow")
    private String orgId;
    /**
     * The type of agreement that is approved
     * 
     */
    @JsonProperty("agreement_type")
    @JsonPropertyDescription("The type of agreement that is approved")
    private String agreementType;
    /**
     * The email address that will notify users when a request was made.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email address that will notify users when a request was made.")
    private String email;
    /**
     * The list of roles that an approvers must have, and is used.
     * 
     */
    @JsonProperty("approvers")
    @JsonPropertyDescription("The list of roles that an approvers must have, and is used.")
    private List<Approver> approvers = new ArrayList<Approver>();

    /**
     * The unique ID of this agreement flow.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this agreement flow.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public AgreementApprovalFlow withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The organization that owns this flow
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that owns this flow
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public AgreementApprovalFlow withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The type of agreement that is approved
     * 
     */
    @JsonProperty("agreement_type")
    public String getAgreementType() {
        return agreementType;
    }

    /**
     * The type of agreement that is approved
     * 
     */
    @JsonProperty("agreement_type")
    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public AgreementApprovalFlow withAgreementType(String agreementType) {
        this.agreementType = agreementType;
        return this;
    }

    /**
     * The email address that will notify users when a request was made.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email address that will notify users when a request was made.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public AgreementApprovalFlow withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The list of roles that an approvers must have, and is used.
     * 
     */
    @JsonProperty("approvers")
    public List<Approver> getApprovers() {
        return approvers;
    }

    /**
     * The list of roles that an approvers must have, and is used.
     * 
     */
    @JsonProperty("approvers")
    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

    public AgreementApprovalFlow withApprovers(List<Approver> approvers) {
        this.approvers = approvers;
        return this;
    }

}
