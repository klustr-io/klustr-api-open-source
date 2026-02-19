
package io.klustr.schemas.console.roles;

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
 * RoleAssignment
 * <p>
 * Defines an assignment of a role.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "role_id",
    "assigned_to",
    "assignee_type",
    "org_id"
})
@Generated("jsonschema2pojo")
public class RoleAssignment {

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this organization")
    private String id;
    /**
     * A reference the the role
     * 
     */
    @JsonProperty("role_id")
    @JsonPropertyDescription("A reference the the role")
    private String roleId;
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
    private RoleAssignment.AssigneeType assigneeType;
    /**
     * A reference the org that owns this role assignment.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("A reference the org that owns this role assignment.")
    private String orgId;

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public RoleAssignment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * A reference the the role
     * 
     */
    @JsonProperty("role_id")
    public String getRoleId() {
        return roleId;
    }

    /**
     * A reference the the role
     * 
     */
    @JsonProperty("role_id")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleAssignment withRoleId(String roleId) {
        this.roleId = roleId;
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

    public RoleAssignment withAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @JsonProperty("assignee_type")
    public RoleAssignment.AssigneeType getAssigneeType() {
        return assigneeType;
    }

    /**
     * Output only. The type of the assignee (USER or GROUP).
     * 
     */
    @JsonProperty("assignee_type")
    public void setAssigneeType(RoleAssignment.AssigneeType assigneeType) {
        this.assigneeType = assigneeType;
    }

    public RoleAssignment withAssigneeType(RoleAssignment.AssigneeType assigneeType) {
        this.assigneeType = assigneeType;
        return this;
    }

    /**
     * A reference the org that owns this role assignment.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * A reference the org that owns this role assignment.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public RoleAssignment withOrgId(String orgId) {
        this.orgId = orgId;
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
        private final static Map<String, RoleAssignment.AssigneeType> CONSTANTS = new HashMap<String, RoleAssignment.AssigneeType>();

        static {
            for (RoleAssignment.AssigneeType c: values()) {
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
        public static RoleAssignment.AssigneeType fromValue(String value) {
            RoleAssignment.AssigneeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
