
package io.klustr.schemas.persons.households;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Status;
import org.joda.time.DateTime;


/**
 * HouseholdMember
 * <p>
 * A member of a particular household and their role or affiliation.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "person_id",
    "primary",
    "create_date",
    "start_date",
    "expiration_date",
    "status"
})
@Generated("jsonschema2pojo")
public class HouseholdMember {

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("person_id")
    @JsonPropertyDescription("The unique ID of the person.")
    private String personId;
    /**
     * If this person can be a primary contact for the household.
     * 
     */
    @JsonProperty("primary")
    @JsonPropertyDescription("If this person can be a primary contact for the household.")
    private Boolean primary;
    /**
     * The date this member was created, but could be earlier than start date given status.
     * 
     */
    @JsonProperty("create_date")
    @JsonPropertyDescription("The date this member was created, but could be earlier than start date given status.")
    private DateTime createDate;
    /**
     * The date this member was or will be added to this household.
     * 
     */
    @JsonProperty("start_date")
    @JsonPropertyDescription("The date this member was or will be added to this household.")
    private DateTime startDate;
    /**
     * The date this member was removed from the household.
     * 
     */
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("The date this member was removed from the household.")
    private DateTime expirationDate;
    /**
     * The status of this household member.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this household member.")
    private Status status;

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("person_id")
    public String getPersonId() {
        return personId;
    }

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("person_id")
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public HouseholdMember withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    /**
     * If this person can be a primary contact for the household.
     * 
     */
    @JsonProperty("primary")
    public Boolean getPrimary() {
        return primary;
    }

    /**
     * If this person can be a primary contact for the household.
     * 
     */
    @JsonProperty("primary")
    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public HouseholdMember withPrimary(Boolean primary) {
        this.primary = primary;
        return this;
    }

    /**
     * The date this member was created, but could be earlier than start date given status.
     * 
     */
    @JsonProperty("create_date")
    public DateTime getCreateDate() {
        return createDate;
    }

    /**
     * The date this member was created, but could be earlier than start date given status.
     * 
     */
    @JsonProperty("create_date")
    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }

    public HouseholdMember withCreateDate(DateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    /**
     * The date this member was or will be added to this household.
     * 
     */
    @JsonProperty("start_date")
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * The date this member was or will be added to this household.
     * 
     */
    @JsonProperty("start_date")
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public HouseholdMember withStartDate(DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * The date this member was removed from the household.
     * 
     */
    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date this member was removed from the household.
     * 
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public HouseholdMember withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * The status of this household member.
     * 
     */
    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    /**
     * The status of this household member.
     * 
     */
    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    public HouseholdMember withStatus(Status status) {
        this.status = status;
        return this;
    }

}
