
package io.klustr.schemas.persons;

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
 * Phone
 * <p>
 * A phone number
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "phone",
    "country_code",
    "phone_type"
})
@Generated("jsonschema2pojo")
public class Phone {

    /**
     * The canonical text of this address
     * 
     */
    @JsonProperty("phone")
    @JsonPropertyDescription("The canonical text of this address")
    private String phone;
    /**
     * The country code for this phone
     * 
     */
    @JsonProperty("country_code")
    @JsonPropertyDescription("The country code for this phone")
    private String countryCode;
    /**
     * The type of phone number
     * 
     */
    @JsonProperty("phone_type")
    @JsonPropertyDescription("The type of phone number")
    private Phone.PhoneType phoneType;

    /**
     * The canonical text of this address
     * 
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * The canonical text of this address
     * 
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Phone withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * The country code for this phone
     * 
     */
    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * The country code for this phone
     * 
     */
    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Phone withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * The type of phone number
     * 
     */
    @JsonProperty("phone_type")
    public Phone.PhoneType getPhoneType() {
        return phoneType;
    }

    /**
     * The type of phone number
     * 
     */
    @JsonProperty("phone_type")
    public void setPhoneType(Phone.PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public Phone withPhoneType(Phone.PhoneType phoneType) {
        this.phoneType = phoneType;
        return this;
    }


    /**
     * The type of phone number
     * 
     */
    @Generated("jsonschema2pojo")
    public enum PhoneType {

        MOBILE("MOBILE"),
        LANDLINE("LANDLINE"),
        OFFICE("OFFICE"),
        HOME("HOME");
        private final String value;
        private final static Map<String, Phone.PhoneType> CONSTANTS = new HashMap<String, Phone.PhoneType>();

        static {
            for (Phone.PhoneType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PhoneType(String value) {
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
        public static Phone.PhoneType fromValue(String value) {
            Phone.PhoneType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
