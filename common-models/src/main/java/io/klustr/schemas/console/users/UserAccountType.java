
package io.klustr.schemas.console.users;

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
 * UserAccountType
 * <p>
 * What is required for when a user creates an acocunt.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "description",
    "org_id",
    "status",
    "creation_date",
    "modified_date",
    "requirements",
    "specifications",
    "agreements",
    "subscriptions",
    "permissions",
    "roles"
})
@Generated("jsonschema2pojo")
public class UserAccountType {

    /**
     * The unique identifier for this type of account.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique identifier for this type of account.")
    private String id;
    /**
     * The unique identifier for this type of account must be lowercase.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The unique identifier for this type of account must be lowercase.")
    private String code;
    /**
     * The display name for this
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name for this")
    private String name;
    /**
     * The description of this account type
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of this account type")
    private String description;
    /**
     * The organization that owns this type of account
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that owns this type of account")
    private String orgId;
    /**
     * The status of this account type in the organization which allows for removal of an account type.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this account type in the organization which allows for removal of an account type.")
    private UserAccountType.Status status;
    /**
     * The date this account was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this account was created.")
    private DateTime creationDate;
    /**
     * The date this account was modified.
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The date this account was modified.")
    private DateTime modifiedDate;
    @JsonProperty("requirements")
    private Requirements requirements;
    @JsonProperty("specifications")
    private Specifications specifications;
    /**
     * The agreements a user must have signed in order to have an account. Different accounts can share common agreements. Ordering matters in terms of presentation to a person.
     * 
     */
    @JsonProperty("agreements")
    @JsonPropertyDescription("The agreements a user must have signed in order to have an account. Different accounts can share common agreements. Ordering matters in terms of presentation to a person.")
    private List<UserAccountTypeAgreement> agreements = new ArrayList<UserAccountTypeAgreement>();
    /**
     * The subscriptions this account can have access to.
     * 
     */
    @JsonProperty("subscriptions")
    @JsonPropertyDescription("The subscriptions this account can have access to.")
    private List<String> subscriptions = new ArrayList<String>();
    /**
     * The permissions to always grant this specific account type
     * 
     */
    @JsonProperty("permissions")
    @JsonPropertyDescription("The permissions to always grant this specific account type")
    private List<String> permissions = new ArrayList<String>();
    /**
     * The roles to always grant this specific account type
     * 
     */
    @JsonProperty("roles")
    @JsonPropertyDescription("The roles to always grant this specific account type")
    private List<Role> roles = new ArrayList<Role>();

    /**
     * The unique identifier for this type of account.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique identifier for this type of account.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserAccountType withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The unique identifier for this type of account must be lowercase.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The unique identifier for this type of account must be lowercase.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public UserAccountType withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * The display name for this
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name for this
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UserAccountType withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The description of this account type
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description of this account type
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccountType withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The organization that owns this type of account
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that owns this type of account
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserAccountType withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The status of this account type in the organization which allows for removal of an account type.
     * 
     */
    @JsonProperty("status")
    public UserAccountType.Status getStatus() {
        return status;
    }

    /**
     * The status of this account type in the organization which allows for removal of an account type.
     * 
     */
    @JsonProperty("status")
    public void setStatus(UserAccountType.Status status) {
        this.status = status;
    }

    public UserAccountType withStatus(UserAccountType.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The date this account was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this account was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UserAccountType withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this account was modified.
     * 
     */
    @JsonProperty("modified_date")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The date this account was modified.
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public UserAccountType withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    @JsonProperty("requirements")
    public Requirements getRequirements() {
        return requirements;
    }

    @JsonProperty("requirements")
    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public UserAccountType withRequirements(Requirements requirements) {
        this.requirements = requirements;
        return this;
    }

    @JsonProperty("specifications")
    public Specifications getSpecifications() {
        return specifications;
    }

    @JsonProperty("specifications")
    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    public UserAccountType withSpecifications(Specifications specifications) {
        this.specifications = specifications;
        return this;
    }

    /**
     * The agreements a user must have signed in order to have an account. Different accounts can share common agreements. Ordering matters in terms of presentation to a person.
     * 
     */
    @JsonProperty("agreements")
    public List<UserAccountTypeAgreement> getAgreements() {
        return agreements;
    }

    /**
     * The agreements a user must have signed in order to have an account. Different accounts can share common agreements. Ordering matters in terms of presentation to a person.
     * 
     */
    @JsonProperty("agreements")
    public void setAgreements(List<UserAccountTypeAgreement> agreements) {
        this.agreements = agreements;
    }

    public UserAccountType withAgreements(List<UserAccountTypeAgreement> agreements) {
        this.agreements = agreements;
        return this;
    }

    /**
     * The subscriptions this account can have access to.
     * 
     */
    @JsonProperty("subscriptions")
    public List<String> getSubscriptions() {
        return subscriptions;
    }

    /**
     * The subscriptions this account can have access to.
     * 
     */
    @JsonProperty("subscriptions")
    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public UserAccountType withSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }

    /**
     * The permissions to always grant this specific account type
     * 
     */
    @JsonProperty("permissions")
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * The permissions to always grant this specific account type
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public UserAccountType withPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * The roles to always grant this specific account type
     * 
     */
    @JsonProperty("roles")
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * The roles to always grant this specific account type
     * 
     */
    @JsonProperty("roles")
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserAccountType withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }


    /**
     * The status of this account type in the organization which allows for removal of an account type.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        DRAFT("draft"),
        TEST("test"),
        INACTIVE("inactive"),
        PUBLISHED("published"),
        DELETED("deleted");
        private final String value;
        private final static Map<String, UserAccountType.Status> CONSTANTS = new HashMap<String, UserAccountType.Status>();

        static {
            for (UserAccountType.Status c: values()) {
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
        public static UserAccountType.Status fromValue(String value) {
            UserAccountType.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
