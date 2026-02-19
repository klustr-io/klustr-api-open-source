
package io.klustr.schemas.console.billing;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.klustr.schemas.console.Address;


/**
 * BillingCustomer
 * <p>
 * This object represents a customer of your business. It lets you create or update a customer, but also track usage and create invoices for the same customer.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "legal_name",
    "legal_number",
    "logo_url",
    "firstname",
    "lastname",
    "customer_type",
    "address",
    "currency",
    "email",
    "phone",
    "url",
    "timezone",
    "external_id",
    "external_source",
    "external_sales_force_id"
})
@Generated("jsonschema2pojo")
public class BillingCustomer {

    /**
     * The unique ID of the customer as managed by your system.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the customer as managed by your system.")
    private String id;
    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.")
    private String name;
    /**
     * The legal company name of the customer
     * 
     */
    @JsonProperty("legal_name")
    @JsonPropertyDescription("The legal company name of the customer")
    private String legalName;
    /**
     * The legal number of this customer.
     * 
     */
    @JsonProperty("legal_number")
    @JsonPropertyDescription("The legal number of this customer.")
    private String legalNumber;
    /**
     * The logo URL of the customer
     * 
     */
    @JsonProperty("logo_url")
    @JsonPropertyDescription("The logo URL of the customer")
    private String logoUrl;
    /**
     * First name of the customer
     * 
     */
    @JsonProperty("firstname")
    @JsonPropertyDescription("First name of the customer")
    private String firstname;
    /**
     * Last name of the customer
     * 
     */
    @JsonProperty("lastname")
    @JsonPropertyDescription("Last name of the customer")
    private String lastname;
    /**
     * BillingCustomerType
     * <p>
     * The type of the customer. It can have one of the following values:
     * 
     * company: the customer is a company.
     * individual: the customer is an individual.
     * 
     */
    @JsonProperty("customer_type")
    @JsonPropertyDescription("The type of the customer. It can have one of the following values:\n\ncompany: the customer is a company.\nindividual: the customer is an individual.")
    private BillingCustomer.BillingCustomerType customerType;
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
     * Currency of the customer. Format must be ISO 4217
     * 
     */
    @JsonProperty("currency")
    @JsonPropertyDescription("Currency of the customer. Format must be ISO 4217")
    private String currency;
    /**
     * The email of the customer
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email of the customer")
    private String email;
    /**
     * The phone of this customer
     * 
     */
    @JsonProperty("phone")
    @JsonPropertyDescription("The phone of this customer")
    private String phone;
    /**
     * The url of this customer
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url of this customer")
    private String url;
    /**
     * The timezone for  this customer
     * 
     */
    @JsonProperty("timezone")
    @JsonPropertyDescription("The timezone for  this customer")
    private String timezone;
    /**
     * The external ID of this customer in another system
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The external ID of this customer in another system")
    private String externalId;
    /**
     * The source of the external resource
     * 
     */
    @JsonProperty("external_source")
    @JsonPropertyDescription("The source of the external resource")
    private String externalSource;
    /**
     * The external sales force identifier for this customer.
     * 
     */
    @JsonProperty("external_sales_force_id")
    @JsonPropertyDescription("The external sales force identifier for this customer.")
    private String externalSalesForceId;

    /**
     * The unique ID of the customer as managed by your system.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of the customer as managed by your system.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BillingCustomer withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name of the subscription on an invoice. This field allows for customization of the subscription's name for billing purposes, especially useful when a single customer has multiple subscriptions using the same plan.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public BillingCustomer withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The legal company name of the customer
     * 
     */
    @JsonProperty("legal_name")
    public String getLegalName() {
        return legalName;
    }

    /**
     * The legal company name of the customer
     * 
     */
    @JsonProperty("legal_name")
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public BillingCustomer withLegalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    /**
     * The legal number of this customer.
     * 
     */
    @JsonProperty("legal_number")
    public String getLegalNumber() {
        return legalNumber;
    }

    /**
     * The legal number of this customer.
     * 
     */
    @JsonProperty("legal_number")
    public void setLegalNumber(String legalNumber) {
        this.legalNumber = legalNumber;
    }

    public BillingCustomer withLegalNumber(String legalNumber) {
        this.legalNumber = legalNumber;
        return this;
    }

    /**
     * The logo URL of the customer
     * 
     */
    @JsonProperty("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * The logo URL of the customer
     * 
     */
    @JsonProperty("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public BillingCustomer withLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    /**
     * First name of the customer
     * 
     */
    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    /**
     * First name of the customer
     * 
     */
    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public BillingCustomer withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * Last name of the customer
     * 
     */
    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    /**
     * Last name of the customer
     * 
     */
    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BillingCustomer withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * BillingCustomerType
     * <p>
     * The type of the customer. It can have one of the following values:
     * 
     * company: the customer is a company.
     * individual: the customer is an individual.
     * 
     */
    @JsonProperty("customer_type")
    public BillingCustomer.BillingCustomerType getCustomerType() {
        return customerType;
    }

    /**
     * BillingCustomerType
     * <p>
     * The type of the customer. It can have one of the following values:
     * 
     * company: the customer is a company.
     * individual: the customer is an individual.
     * 
     */
    @JsonProperty("customer_type")
    public void setCustomerType(BillingCustomer.BillingCustomerType customerType) {
        this.customerType = customerType;
    }

    public BillingCustomer withCustomerType(BillingCustomer.BillingCustomerType customerType) {
        this.customerType = customerType;
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

    public BillingCustomer withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Currency of the customer. Format must be ISO 4217
     * 
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    /**
     * Currency of the customer. Format must be ISO 4217
     * 
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BillingCustomer withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * The email of the customer
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email of the customer
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public BillingCustomer withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The phone of this customer
     * 
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * The phone of this customer
     * 
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BillingCustomer withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * The url of this customer
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url of this customer
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public BillingCustomer withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The timezone for  this customer
     * 
     */
    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    /**
     * The timezone for  this customer
     * 
     */
    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public BillingCustomer withTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    /**
     * The external ID of this customer in another system
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * The external ID of this customer in another system
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public BillingCustomer withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * The source of the external resource
     * 
     */
    @JsonProperty("external_source")
    public String getExternalSource() {
        return externalSource;
    }

    /**
     * The source of the external resource
     * 
     */
    @JsonProperty("external_source")
    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }

    public BillingCustomer withExternalSource(String externalSource) {
        this.externalSource = externalSource;
        return this;
    }

    /**
     * The external sales force identifier for this customer.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public String getExternalSalesForceId() {
        return externalSalesForceId;
    }

    /**
     * The external sales force identifier for this customer.
     * 
     */
    @JsonProperty("external_sales_force_id")
    public void setExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
    }

    public BillingCustomer withExternalSalesForceId(String externalSalesForceId) {
        this.externalSalesForceId = externalSalesForceId;
        return this;
    }


    /**
     * BillingCustomerType
     * <p>
     * The type of the customer. It can have one of the following values:
     * 
     * company: the customer is a company.
     * individual: the customer is an individual.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum BillingCustomerType {

        COMPANY("company"),
        INDIVIDUAL("individual");
        private final String value;
        private final static Map<String, BillingCustomer.BillingCustomerType> CONSTANTS = new HashMap<String, BillingCustomer.BillingCustomerType>();

        static {
            for (BillingCustomer.BillingCustomerType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        BillingCustomerType(String value) {
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
        public static BillingCustomer.BillingCustomerType fromValue(String value) {
            BillingCustomer.BillingCustomerType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
