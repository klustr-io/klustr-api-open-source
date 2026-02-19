
package io.klustr.schemas.console.orgs;

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
import io.klustr.schemas.console.Address;
import org.joda.time.DateTime;


/**
 * Org
 * <p>
 * An organizational unit.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "subdomain",
    "domain",
    "logo_url",
    "owner",
    "creation_date",
    "modified_date",
    "contact",
    "address",
    "children",
    "status",
    "external_sales_force_id"
})
@Generated("jsonschema2pojo")
public class Org {

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this organization")
    private String id;
    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A friendly description for this billing account.")
    private String name;
    /**
     * The subdomain to use for this organization
     * 
     */
    @JsonProperty("subdomain")
    @JsonPropertyDescription("The subdomain to use for this organization")
    private String subdomain;
    /**
     * The primary hosted domain for this organization
     * 
     */
    @JsonProperty("domain")
    @JsonPropertyDescription("The primary hosted domain for this organization")
    private String domain;
    /**
     * The logo URL of the organization
     * 
     */
    @JsonProperty("logo_url")
    @JsonPropertyDescription("The logo URL of the organization")
    private String logoUrl;
    /**
     * OrgOwner
     * <p>
     * The singular administrator and owner of this organization.
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("The singular administrator and owner of this organization.")
    private OrgOwner owner;
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
     * OrgContact
     * <p>
     * The contact information.
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("The contact information.")
    private OrgContact contact;
    /**
     * Address
     * <p>
     * The main address information for this customer.
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("The main address information for this customer.")
    private Address address;
    /**
     * The departments for this org
     * 
     */
    @JsonProperty("children")
    @JsonPropertyDescription("The departments for this org")
    private List<OrgUnit> children = new ArrayList<OrgUnit>();
    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this organization.")
    private Org.Status status;
    /**
     * The external ID of this org if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    @JsonPropertyDescription("The external ID of this org if loaded into salesforce.")
    private String externalSalesForceId;

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

    public Org withId(String id) {
        this.id = id;
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

    public Org withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The subdomain to use for this organization
     * 
     */
    @JsonProperty("subdomain")
    public String getSubdomain() {
        return subdomain;
    }

    /**
     * The subdomain to use for this organization
     * 
     */
    @JsonProperty("subdomain")
    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public Org withSubdomain(String subdomain) {
        this.subdomain = subdomain;
        return this;
    }

    /**
     * The primary hosted domain for this organization
     * 
     */
    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    /**
     * The primary hosted domain for this organization
     * 
     */
    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Org withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * The logo URL of the organization
     * 
     */
    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * The logo URL of the organization
     * 
     */
    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Org withLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    /**
     * OrgOwner
     * <p>
     * The singular administrator and owner of this organization.
     * 
     */
    @JsonProperty("owner")
    public OrgOwner getOwner() {
        return owner;
    }

    /**
     * OrgOwner
     * <p>
     * The singular administrator and owner of this organization.
     * 
     */
    @JsonProperty("owner")
    public void setOwner(OrgOwner owner) {
        this.owner = owner;
    }

    public Org withOwner(OrgOwner owner) {
        this.owner = owner;
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

    public Org withCreationDate(DateTime creationDate) {
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

    public Org withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * OrgContact
     * <p>
     * The contact information.
     * 
     */
    @JsonProperty("contact")
    public OrgContact getContact() {
        return contact;
    }

    /**
     * OrgContact
     * <p>
     * The contact information.
     * 
     */
    @JsonProperty("contact")
    public void setContact(OrgContact contact) {
        this.contact = contact;
    }

    public Org withContact(OrgContact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Address
     * <p>
     * The main address information for this customer.
     * 
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * Address
     * <p>
     * The main address information for this customer.
     * 
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    public Org withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * The departments for this org
     * 
     */
    @JsonProperty("children")
    public List<OrgUnit> getChildren() {
        return children;
    }

    /**
     * The departments for this org
     * 
     */
    @JsonProperty("children")
    public void setChildren(List<OrgUnit> children) {
        this.children = children;
    }

    public Org withChildren(List<OrgUnit> children) {
        this.children = children;
        return this;
    }

    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    public Org.Status getStatus() {
        return status;
    }

    /**
     * The status of this organization.
     * 
     */
    @JsonProperty("status")
    public void setStatus(Org.Status status) {
        this.status = status;
    }

    public Org withStatus(Org.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The external ID of this org if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public String getExternalSalesForceId() {
        return externalSalesForceId;
    }

    /**
     * The external ID of this org if loaded into salesforce.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public void setExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
    }

    public Org withExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
        return this;
    }


    /**
     * The status of this organization.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        INACTIVE("inactive");
        private final String value;
        private final static Map<String, Org.Status> CONSTANTS = new HashMap<String, Org.Status>();

        static {
            for (Org.Status c: values()) {
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
        public static Org.Status fromValue(String value) {
            Org.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
