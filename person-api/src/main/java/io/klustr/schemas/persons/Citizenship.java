
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
import org.joda.time.DateTime;


/**
 * Citizenship
 * <p>
 * The citizenship for the person
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "citizenship_country_code",
    "residency_country_code",
    "residency_type",
    "residency_expiration"
})
@Generated("jsonschema2pojo")
public class Citizenship {

    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("citizenship_country_code")
    @JsonPropertyDescription("The country code of citizenship for the person (different from where the person resides).")
    private String citizenshipCountryCode;
    /**
     * The country code of the residency for this person
     * 
     */
    @JsonProperty("residency_country_code")
    @JsonPropertyDescription("The country code of the residency for this person")
    private String residencyCountryCode;
    /**
     * The status of residency and status of the person.
     * 
     */
    @JsonProperty("residency_type")
    @JsonPropertyDescription("The status of residency and status of the person.")
    private Citizenship.ResidencyType residencyType;
    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("residency_expiration")
    @JsonPropertyDescription("The date of expiration for residency (if applicable).")
    private DateTime residencyExpiration;

    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("citizenship_country_code")
    public String getCitizenshipCountryCode() {
        return citizenshipCountryCode;
    }

    /**
     * The country code of citizenship for the person (different from where the person resides).
     * 
     */
    @JsonProperty("citizenship_country_code")
    public void setCitizenshipCountryCode(String citizenshipCountryCode) {
        this.citizenshipCountryCode = citizenshipCountryCode;
    }

    public Citizenship withCitizenshipCountryCode(String citizenshipCountryCode) {
        this.citizenshipCountryCode = citizenshipCountryCode;
        return this;
    }

    /**
     * The country code of the residency for this person
     * 
     */
    @JsonProperty("residency_country_code")
    public String getResidencyCountryCode() {
        return residencyCountryCode;
    }

    /**
     * The country code of the residency for this person
     * 
     */
    @JsonProperty("residency_country_code")
    public void setResidencyCountryCode(String residencyCountryCode) {
        this.residencyCountryCode = residencyCountryCode;
    }

    public Citizenship withResidencyCountryCode(String residencyCountryCode) {
        this.residencyCountryCode = residencyCountryCode;
        return this;
    }

    /**
     * The status of residency and status of the person.
     * 
     */
    @JsonProperty("residency_type")
    public Citizenship.ResidencyType getResidencyType() {
        return residencyType;
    }

    /**
     * The status of residency and status of the person.
     * 
     */
    @JsonProperty("residency_type")
    public void setResidencyType(Citizenship.ResidencyType residencyType) {
        this.residencyType = residencyType;
    }

    public Citizenship withResidencyType(Citizenship.ResidencyType residencyType) {
        this.residencyType = residencyType;
        return this;
    }

    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("residency_expiration")
    public DateTime getResidencyExpiration() {
        return residencyExpiration;
    }

    /**
     * The date of expiration for residency (if applicable).
     * 
     */
    @JsonProperty("residency_expiration")
    public void setResidencyExpiration(DateTime residencyExpiration) {
        this.residencyExpiration = residencyExpiration;
    }

    public Citizenship withResidencyExpiration(DateTime residencyExpiration) {
        this.residencyExpiration = residencyExpiration;
        return this;
    }


    /**
     * The status of residency and status of the person.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ResidencyType {


        /**
         * The persons residency is permanent visa
         * 
         */
        PERMANENT_VISA("PERMANENT_VISA"),

        /**
         * The persons residency is based on a resident visa with an expiration.
         * 
         */
        RESIDENT_VISA("RESIDENT_VISA"),

        /**
         * The persons residency is of a natural citizen.
         * 
         */
        NATURAL_CITIZEN("NATURAL_CITIZEN");
        private final String value;
        private final static Map<String, Citizenship.ResidencyType> CONSTANTS = new HashMap<String, Citizenship.ResidencyType>();

        static {
            for (Citizenship.ResidencyType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ResidencyType(String value) {
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
        public static Citizenship.ResidencyType fromValue(String value) {
            Citizenship.ResidencyType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
