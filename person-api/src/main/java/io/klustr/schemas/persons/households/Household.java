
package io.klustr.schemas.persons.households;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.persons.ContactInformation;
import org.joda.time.DateTime;


/**
 * Household
 * <p>
 * A household residence where one or more people reside.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "contact",
    "members",
    "creation_date",
    "modified_date"
})
@Generated("jsonschema2pojo")
public class Household {

    /**
     * The unique ID of this household.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this household.")
    private String id;
    /**
     * The display name for this household.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name for this household.")
    private String name;
    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("The contact information for a person")
    private ContactInformation contact;
    /**
     * The members of this household
     * 
     */
    @JsonProperty("members")
    @JsonPropertyDescription("The members of this household")
    private List<HouseholdMember> members = new ArrayList<HouseholdMember>();
    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this household was created.")
    private DateTime creationDate;
    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The date this household was created.")
    private DateTime modifiedDate;

    /**
     * The unique ID of this household.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this household.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Household withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The display name for this household.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name for this household.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Household withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    public ContactInformation getContact() {
        return contact;
    }

    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    public void setContact(ContactInformation contact) {
        this.contact = contact;
    }

    public Household withContact(ContactInformation contact) {
        this.contact = contact;
        return this;
    }

    /**
     * The members of this household
     * 
     */
    @JsonProperty("members")
    public List<HouseholdMember> getMembers() {
        return members;
    }

    /**
     * The members of this household
     * 
     */
    @JsonProperty("members")
    public void setMembers(List<HouseholdMember> members) {
        this.members = members;
    }

    public Household withMembers(List<HouseholdMember> members) {
        this.members = members;
        return this;
    }

    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Household withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("modified_date")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The date this household was created.
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Household withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

}
