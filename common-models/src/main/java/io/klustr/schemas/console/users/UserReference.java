
package io.klustr.schemas.console.users;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Status;
import io.klustr.schemas.console.identity.IdentityUserProfile;
import org.joda.time.DateTime;


/**
 * UserReference
 * <p>
 * Reference to a particular user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "identity",
    "org_id",
    "acls",
    "status",
    "creation_date",
    "effective_date"
})
@Generated("jsonschema2pojo")
public class UserReference {

    /**
     * IdentityUserProfile
     * <p>
     * The online persona and profile for a specific identity.
     * 
     */
    @JsonProperty("identity")
    @JsonPropertyDescription("The online persona and profile for a specific identity.")
    private IdentityUserProfile identity;
    /**
     * The organization that this user was referenced under.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that this user was referenced under.")
    private String orgId;
    /**
     * The acls related to this user
     * 
     */
    @JsonProperty("acls")
    @JsonPropertyDescription("The acls related to this user")
    private List<String> acls = new ArrayList<String>();
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
     * IdentityUserProfile
     * <p>
     * The online persona and profile for a specific identity.
     * 
     */
    @JsonProperty("identity")
    public IdentityUserProfile getIdentity() {
        return identity;
    }

    /**
     * IdentityUserProfile
     * <p>
     * The online persona and profile for a specific identity.
     * 
     */
    @JsonProperty("identity")
    public void setIdentity(IdentityUserProfile identity) {
        this.identity = identity;
    }

    public UserReference withIdentity(IdentityUserProfile identity) {
        this.identity = identity;
        return this;
    }

    /**
     * The organization that this user was referenced under.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that this user was referenced under.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserReference withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The acls related to this user
     * 
     */
    @JsonProperty("acls")
    public List<String> getAcls() {
        return acls;
    }

    /**
     * The acls related to this user
     * 
     */
    @JsonProperty("acls")
    public void setAcls(List<String> acls) {
        this.acls = acls;
    }

    public UserReference withAcls(List<String> acls) {
        this.acls = acls;
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

    public UserReference withStatus(Status status) {
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

    public UserReference withCreationDate(DateTime creationDate) {
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

    public UserReference withEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

}
