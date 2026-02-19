
package io.klustr.schemas.console;

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
 * Address
 * <p>
 * A physical address for any office, residence, or other type..
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "address_line_1",
    "address_line_2",
    "postal_code",
    "city_ward",
    "floor",
    "usage",
    "building_name",
    "state_prefecture",
    "metadata",
    "country_code"
})
@Generated("jsonschema2pojo")
public class Address {

    /**
     * The first line of the address which typically contains the street number.
     * 
     */
    @JsonProperty("address_line_1")
    @JsonPropertyDescription("The first line of the address which typically contains the street number.")
    private java.lang.String addressLine1;
    /**
     * The second line of the address which typically contains the apartment number.
     * 
     */
    @JsonProperty("address_line_2")
    @JsonPropertyDescription("The second line of the address which typically contains the apartment number.")
    private java.lang.String addressLine2;
    /**
     * The postal code or zip code to use for this address
     * 
     */
    @JsonProperty("postal_code")
    @JsonPropertyDescription("The postal code or zip code to use for this address")
    private java.lang.String postalCode;
    /**
     * The city or ward information for this address
     * 
     */
    @JsonProperty("city_ward")
    @JsonPropertyDescription("The city or ward information for this address")
    private java.lang.String cityWard;
    /**
     * The floor information should this be an office or other related sub location.
     * 
     */
    @JsonProperty("floor")
    @JsonPropertyDescription("The floor information should this be an office or other related sub location.")
    private java.lang.String floor;
    /**
     * The optional type information for this address
     * 
     */
    @JsonProperty("usage")
    @JsonPropertyDescription("The optional type information for this address")
    private Address.Usage usage;
    /**
     * The name of the building in relation to the address.
     * 
     */
    @JsonProperty("building_name")
    @JsonPropertyDescription("The name of the building in relation to the address.")
    private java.lang.String buildingName;
    /**
     * The state or prefecture to use for this address
     * 
     */
    @JsonProperty("state_prefecture")
    @JsonPropertyDescription("The state or prefecture to use for this address")
    private java.lang.String statePrefecture;
    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Additional metadata related to the address")
    private Map<String, String> metadata;
    /**
     * The country code (2) for this address.
     * 
     */
    @JsonProperty("country_code")
    @JsonPropertyDescription("The country code (2) for this address.")
    private Address.CountryCode countryCode;

    /**
     * The first line of the address which typically contains the street number.
     * 
     */
    @JsonProperty("address_line_1")
    public java.lang.String getAddressLine1() {
        return addressLine1;
    }

    /**
     * The first line of the address which typically contains the street number.
     * 
     */
    @JsonProperty("address_line_1")
    public void setAddressLine1(java.lang.String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Address withAddressLine1(java.lang.String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    /**
     * The second line of the address which typically contains the apartment number.
     * 
     */
    @JsonProperty("address_line_2")
    public java.lang.String getAddressLine2() {
        return addressLine2;
    }

    /**
     * The second line of the address which typically contains the apartment number.
     * 
     */
    @JsonProperty("address_line_2")
    public void setAddressLine2(java.lang.String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Address withAddressLine2(java.lang.String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    /**
     * The postal code or zip code to use for this address
     * 
     */
    @JsonProperty("postal_code")
    public java.lang.String getPostalCode() {
        return postalCode;
    }

    /**
     * The postal code or zip code to use for this address
     * 
     */
    @JsonProperty("postal_code")
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }

    public Address withPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * The city or ward information for this address
     * 
     */
    @JsonProperty("city_ward")
    public java.lang.String getCityWard() {
        return cityWard;
    }

    /**
     * The city or ward information for this address
     * 
     */
    @JsonProperty("city_ward")
    public void setCityWard(java.lang.String cityWard) {
        this.cityWard = cityWard;
    }

    public Address withCityWard(java.lang.String cityWard) {
        this.cityWard = cityWard;
        return this;
    }

    /**
     * The floor information should this be an office or other related sub location.
     * 
     */
    @JsonProperty("floor")
    public java.lang.String getFloor() {
        return floor;
    }

    /**
     * The floor information should this be an office or other related sub location.
     * 
     */
    @JsonProperty("floor")
    public void setFloor(java.lang.String floor) {
        this.floor = floor;
    }

    public Address withFloor(java.lang.String floor) {
        this.floor = floor;
        return this;
    }

    /**
     * The optional type information for this address
     * 
     */
    @JsonProperty("usage")
    public Address.Usage getUsage() {
        return usage;
    }

    /**
     * The optional type information for this address
     * 
     */
    @JsonProperty("usage")
    public void setUsage(Address.Usage usage) {
        this.usage = usage;
    }

    public Address withUsage(Address.Usage usage) {
        this.usage = usage;
        return this;
    }

    /**
     * The name of the building in relation to the address.
     * 
     */
    @JsonProperty("building_name")
    public java.lang.String getBuildingName() {
        return buildingName;
    }

    /**
     * The name of the building in relation to the address.
     * 
     */
    @JsonProperty("building_name")
    public void setBuildingName(java.lang.String buildingName) {
        this.buildingName = buildingName;
    }

    public Address withBuildingName(java.lang.String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    /**
     * The state or prefecture to use for this address
     * 
     */
    @JsonProperty("state_prefecture")
    public java.lang.String getStatePrefecture() {
        return statePrefecture;
    }

    /**
     * The state or prefecture to use for this address
     * 
     */
    @JsonProperty("state_prefecture")
    public void setStatePrefecture(java.lang.String statePrefecture) {
        this.statePrefecture = statePrefecture;
    }

    public Address withStatePrefecture(java.lang.String statePrefecture) {
        this.statePrefecture = statePrefecture;
        return this;
    }

    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Address withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * The country code (2) for this address.
     * 
     */
    @JsonProperty("country_code")
    public Address.CountryCode getCountryCode() {
        return countryCode;
    }

    /**
     * The country code (2) for this address.
     * 
     */
    @JsonProperty("country_code")
    public void setCountryCode(Address.CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public Address withCountryCode(Address.CountryCode countryCode) {
        this.countryCode = countryCode;
        return this;
    }


    /**
     * The country code (2) for this address.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum CountryCode {

        US("US"),
        JP("JP"),
        FR("FR"),
        UK("UK"),
        IT("IT");
        private final java.lang.String value;
        private final static Map<java.lang.String, Address.CountryCode> CONSTANTS = new HashMap<java.lang.String, Address.CountryCode>();

        static {
            for (Address.CountryCode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CountryCode(java.lang.String value) {
            this.value = value;
        }

        @Override
        public java.lang.String toString() {
            return this.value;
        }

        @JsonValue
        public java.lang.String value() {
            return this.value;
        }

        @JsonCreator
        public static Address.CountryCode fromValue(java.lang.String value) {
            Address.CountryCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The optional type information for this address
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Usage {

        RESIDENCE("residence"),
        BUSINESS("business");
        private final java.lang.String value;
        private final static Map<java.lang.String, Address.Usage> CONSTANTS = new HashMap<java.lang.String, Address.Usage>();

        static {
            for (Address.Usage c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Usage(java.lang.String value) {
            this.value = value;
        }

        @Override
        public java.lang.String toString() {
            return this.value;
        }

        @JsonValue
        public java.lang.String value() {
            return this.value;
        }

        @JsonCreator
        public static Address.Usage fromValue(java.lang.String value) {
            Address.Usage constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
