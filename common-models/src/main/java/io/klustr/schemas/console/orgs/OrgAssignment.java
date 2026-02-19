
package io.klustr.schemas.console.orgs;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * OrgAssignment
 * <p>
 * Defines an assignment of a user to a organizational unit.https://stackoverflow.com/questions/32362286/adding-user-to-organization-unit-and-group-using-google-directory-api
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "customer_id",
    "org_id",
    "org_path",
    "assigned_to",
    "assignee_type"
})
@Generated("jsonschema2pojo")
public class OrgAssignment {

    /**
     * The generated ID for this assignment.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The generated ID for this assignment.")
    private String id;
    /**
     * The customer ID of this organization
     * 
     */
    @JsonProperty("customer_id")
    @JsonPropertyDescription("The customer ID of this organization")
    private String customerId;
    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The unique ID of this organization")
    private String orgId;
    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_path")
    @JsonPropertyDescription("The unique ID of this organization")
    private String orgPath;
    /**
     * The unique ID of the entity this role is assigned to—either the userId of a user, the groupId of a group, or the uniqueId of a service account as defined in Identity and Access Management (IAM).
     * 
     */
    @JsonProperty("assigned_to")
    @JsonPropertyDescription("The unique ID of the entity this role is assigned to\u2014either the userId of a user, the groupId of a group, or the uniqueId of a service account as defined in Identity and Access Management (IAM).")
    private String assignedTo;
    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @JsonProperty("assignee_type")
    @JsonPropertyDescription("Output only. The type of the assignee (USER or GROUP).")
    private OrgAssignment.AssigneeType assigneeType;

    /**
     * The generated ID for this assignment.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The generated ID for this assignment.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OrgAssignment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The customer ID of this organization
     * 
     */
    @JsonProperty("customer_id")
    public String getCustomerId() {
        return customerId;
    }

    /**
     * The customer ID of this organization
     * 
     */
    @JsonProperty("customer_id")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public OrgAssignment withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public OrgAssignment withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_path")
    public String getOrgPath() {
        return orgPath;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("org_path")
    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public OrgAssignment withOrgPath(String orgPath) {
        this.orgPath = orgPath;
        return this;
    }

    /**
     * The unique ID of the entity this role is assigned to—either the userId of a user, the groupId of a group, or the uniqueId of a service account as defined in Identity and Access Management (IAM).
     * 
     */
    @JsonProperty("assigned_to")
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * The unique ID of the entity this role is assigned to—either the userId of a user, the groupId of a group, or the uniqueId of a service account as defined in Identity and Access Management (IAM).
     * 
     */
    @JsonProperty("assigned_to")
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public OrgAssignment withAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @JsonProperty("assignee_type")
    public OrgAssignment.AssigneeType getAssigneeType() {
        return assigneeType;
    }

    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @JsonProperty("assignee_type")
    public void setAssigneeType(OrgAssignment.AssigneeType assigneeType) {
        this.assigneeType = assigneeType;
    }

    public OrgAssignment withAssigneeType(OrgAssignment.AssigneeType assigneeType) {
        this.assigneeType = assigneeType;
        return this;
    }


    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AssigneeType {

        USER("user"),
        GROUP("group");
        private final String value;
        private final static Map<String, OrgAssignment.AssigneeType> CONSTANTS = new HashMap<String, OrgAssignment.AssigneeType>();

        static {
            for (OrgAssignment.AssigneeType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AssigneeType(String value) {
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
        public static OrgAssignment.AssigneeType fromValue(String value) {
            OrgAssignment.AssigneeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
