
package io.klustr.schemas.persons;

import java.net.URI;
import java.util.HashMap;
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
 * PersonIdentityDocument
 * <p>
 * The identification document that proves the identity of the person.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "country_code",
    "link",
    "date_effective",
    "date_expires",
    "date_created",
    "vendor_reference_data",
    "vendor"
})
@Generated("jsonschema2pojo")
public class PersonIdentityDocument {

    /**
     * IdentificationType
     * <p>
     * The type of identification.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of identification.")
    private PersonIdentityDocument.IdentificationType type;
    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("country_code")
    @JsonPropertyDescription("The country code of citizenship for the person (different from where the person resides).")
    private String countryCode;
    /**
     * The photos or evidence for this identification.
     * 
     */
    @JsonProperty("link")
    @JsonPropertyDescription("The photos or evidence for this identification.")
    private URI link;
    /**
     * The date of this document is valid from.
     * 
     */
    @JsonProperty("date_effective")
    @JsonPropertyDescription("The date of this document is valid from.")
    private DateTime dateEffective;
    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("date_expires")
    @JsonPropertyDescription("The date of expiration for residency (if applicable).")
    private DateTime dateExpires;
    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The date record was created.")
    private DateTime dateCreated;
    /**
     * The data that supports linking back to the original source.
     * 
     */
    @JsonProperty("vendor_reference_data")
    @JsonPropertyDescription("The data that supports linking back to the original source.")
    private String vendorReferenceData;
    /**
     * The vendor that provided the verification
     * 
     */
    @JsonProperty("vendor")
    @JsonPropertyDescription("The vendor that provided the verification")
    private String vendor;

    /**
     * IdentificationType
     * <p>
     * The type of identification.
     * 
     */
    @JsonProperty("type")
    public PersonIdentityDocument.IdentificationType getType() {
        return type;
    }

    /**
     * IdentificationType
     * <p>
     * The type of identification.
     * 
     */
    @JsonProperty("type")
    public void setType(PersonIdentityDocument.IdentificationType type) {
        this.type = type;
    }

    public PersonIdentityDocument withType(PersonIdentityDocument.IdentificationType type) {
        this.type = type;
        return this;
    }

    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public PersonIdentityDocument withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * The photos or evidence for this identification.
     * 
     */
    @JsonProperty("link")
    public URI getLink() {
        return link;
    }

    /**
     * The photos or evidence for this identification.
     * 
     */
    @JsonProperty("link")
    public void setLink(URI link) {
        this.link = link;
    }

    public PersonIdentityDocument withLink(URI link) {
        this.link = link;
        return this;
    }

    /**
     * The date of this document is valid from.
     * 
     */
    @JsonProperty("date_effective")
    public DateTime getDateEffective() {
        return dateEffective;
    }

    /**
     * The date of this document is valid from.
     * 
     */
    @JsonProperty("date_effective")
    public void setDateEffective(DateTime dateEffective) {
        this.dateEffective = dateEffective;
    }

    public PersonIdentityDocument withDateEffective(DateTime dateEffective) {
        this.dateEffective = dateEffective;
        return this;
    }

    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("date_expires")
    public DateTime getDateExpires() {
        return dateExpires;
    }

    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("date_expires")
    public void setDateExpires(DateTime dateExpires) {
        this.dateExpires = dateExpires;
    }

    public PersonIdentityDocument withDateExpires(DateTime dateExpires) {
        this.dateExpires = dateExpires;
        return this;
    }

    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The date record was created.
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public PersonIdentityDocument withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The data that supports linking back to the original source.
     * 
     */
    @JsonProperty("vendor_reference_data")
    public String getVendorReferenceData() {
        return vendorReferenceData;
    }

    /**
     * The data that supports linking back to the original source.
     * 
     */
    @JsonProperty("vendor_reference_data")
    public void setVendorReferenceData(String vendorReferenceData) {
        this.vendorReferenceData = vendorReferenceData;
    }

    public PersonIdentityDocument withVendorReferenceData(String vendorReferenceData) {
        this.vendorReferenceData = vendorReferenceData;
        return this;
    }

    /**
     * The vendor that provided the verification
     * 
     */
    @JsonProperty("vendor")
    public String getVendor() {
        return vendor;
    }

    /**
     * The vendor that provided the verification
     * 
     */
    @JsonProperty("vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public PersonIdentityDocument withVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }


    /**
     * IdentificationType
     * <p>
     * The type of identification.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum IdentificationType {


        /**
         * Visa issued by government.
         * 
         */
        VISA("VISA"),

        /**
         * RESIDENCE_PERMIT issued by government.
         * 
         */
        RESIDENCE_PERMIT("RESIDENCE_PERMIT"),

        /**
         * The persons residency is based on a resident visa with an expiration.
         * 
         */
        PASSPORT("PASSPORT"),

        /**
         * The persons residency is of a natural citizen.
         * 
         */
        ID_CARD("ID_CARD"),

        /**
         * Drivers license provided by a state or issuing country.
         * 
         */
        DRIVERS_LICENSE("DRIVERS_LICENSE");
        private final String value;
        private final static Map<String, PersonIdentityDocument.IdentificationType> CONSTANTS = new HashMap<String, PersonIdentityDocument.IdentificationType>();

        static {
            for (PersonIdentityDocument.IdentificationType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        IdentificationType(String value) {
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
        public static PersonIdentityDocument.IdentificationType fromValue(String value) {
            PersonIdentityDocument.IdentificationType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
