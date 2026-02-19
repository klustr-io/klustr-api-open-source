
package io.klustr.schemas.console.users;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Status;
import org.joda.time.DateTime;


/**
 * UserAccount
 * <p>
 * An account linked to a specific user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "account_code",
    "org_id",
    "subject_id",
    "status",
    "creation_date",
    "effective_date",
    "expiration_date"
})
@Generated("jsonschema2pojo")
public class UserAccount {

    /**
     * The ID of the user account.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The ID of the user account.")
    private String id;
    /**
     * The account code for this user account, combined with organization we get the unique code.
     * 
     */
    @JsonProperty("account_code")
    @JsonPropertyDescription("The account code for this user account, combined with organization we get the unique code.")
    private String accountCode;
    /**
     * The organization that this account is linked to.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that this account is linked to.")
    private String orgId;
    /**
     * The subject that has been given this account.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The subject that has been given this account.")
    private String subjectId;
    /**
     * The status of the user account.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of the user account.")
    private Status status;
    /**
     * The date this account was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this account was created.")
    private DateTime creationDate;
    /**
     * The date this account is effective.
     * 
     */
    @JsonProperty("effective_date")
    @JsonPropertyDescription("The date this account is effective.")
    private DateTime effectiveDate;
    /**
     * The date this account expires.
     * 
     */
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("The date this account expires.")
    private DateTime expirationDate;

    /**
     * The ID of the user account.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The ID of the user account.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserAccount withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The account code for this user account, combined with organization we get the unique code.
     * 
     */
    @JsonProperty("account_code")
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * The account code for this user account, combined with organization we get the unique code.
     * 
     */
    @JsonProperty("account_code")
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public UserAccount withAccountCode(String accountCode) {
        this.accountCode = accountCode;
        return this;
    }

    /**
     * The organization that this account is linked to.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that this account is linked to.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserAccount withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The subject that has been given this account.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The subject that has been given this account.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public UserAccount withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The status of the user account.
     * 
     */
    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    /**
     * The status of the user account.
     * 
     */
    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    public UserAccount withStatus(Status status) {
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

    public UserAccount withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this account is effective.
     * 
     */
    @JsonProperty("effective_date")
    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * The date this account is effective.
     * 
     */
    @JsonProperty("effective_date")
    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public UserAccount withEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * The date this account expires.
     * 
     */
    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date this account expires.
     * 
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UserAccount withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

}
