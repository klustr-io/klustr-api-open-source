
package io.klustr.schemas.console.accounts;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Address;
import org.joda.time.DateTime;


/**
 * Account
 * <p>
 * The account and billing information.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "external_id",
    "external_source",
    "external_sales_force_id",
    "name",
    "creation_date",
    "modified_date",
    "contact",
    "address"
})
@Generated("jsonschema2pojo")
public class Account {

    /**
     * The ID of this billing account
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The ID of this billing account")
    private String id;
    /**
     * The ID linked to this account from an external system.
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The ID linked to this account from an external system.")
    private String externalId;
    /**
     * The source of the external ID.
     * 
     */
    @JsonProperty("external_source")
    @JsonPropertyDescription("The source of the external ID.")
    private String externalSource;
    /**
     * The external ID of this account if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    @JsonPropertyDescription("The external ID of this account if loaded into salesforce.")
    private String externalSalesForceId;
    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A friendly description for this billing account.")
    private String name;
    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this billing info was created.")
    private DateTime creationDate;
    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The date this billing info was modified.")
    private DateTime modifiedDate;
    /**
     * AccountContact
     * <p>
     * The billing contact information.
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("The billing contact information.")
    private AccountContact contact;
    /**
     * Address
     * <p>
     * The billing address information.
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("The billing address information.")
    private Address address;

    /**
     * The ID of this billing account
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The ID of this billing account
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Account withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The ID linked to this account from an external system.
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * The ID linked to this account from an external system.
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Account withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * The source of the external ID.
     * 
     */
    @JsonProperty("external_source")
    public String getExternalSource() {
        return externalSource;
    }

    /**
     * The source of the external ID.
     * 
     */
    @JsonProperty("external_source")
    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }

    public Account withExternalSource(String externalSource) {
        this.externalSource = externalSource;
        return this;
    }

    /**
     * The external ID of this account if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public String getExternalSalesForceId() {
        return externalSalesForceId;
    }

    /**
     * The external ID of this account if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public void setExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
    }

    public Account withExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
        return this;
    }

    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Account withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Account withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Account withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * AccountContact
     * <p>
     * The billing contact information.
     * 
     */
    @JsonProperty("contact")
    public AccountContact getContact() {
        return contact;
    }

    /**
     * AccountContact
     * <p>
     * The billing contact information.
     * 
     */
    @JsonProperty("contact")
    public void setContact(AccountContact contact) {
        this.contact = contact;
    }

    public Account withContact(AccountContact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Address
     * <p>
     * The billing address information.
     * 
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * Address
     * <p>
     * The billing address information.
     * 
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    public Account withAddress(Address address) {
        this.address = address;
        return this;
    }

}
