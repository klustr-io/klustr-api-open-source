
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
 * Name
 * <p>
 * A name for the person.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "given_name",
    "family_name",
    "middle_name",
    "source",
    "locale"
})
@Generated("jsonschema2pojo")
public class Name {

    /**
     * Given name of the person.
     * (Required)
     * 
     */
    @JsonProperty("given_name")
    @JsonPropertyDescription("Given name of the person.")
    private String givenName;
    /**
     * Family name of the person.
     * (Required)
     * 
     */
    @JsonProperty("family_name")
    @JsonPropertyDescription("Family name of the person.")
    private String familyName;
    /**
     * Middle name for the person.
     * 
     */
    @JsonProperty("middle_name")
    @JsonPropertyDescription("Middle name for the person.")
    private String middleName;
    /**
     * The source for this name.
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The source for this name.")
    private Name.Source source;
    /**
     * The locale for this username if applicable.
     * (Required)
     * 
     */
    @JsonProperty("locale")
    @JsonPropertyDescription("The locale for this username if applicable.")
    private String locale;

    /**
     * Given name of the person.
     * (Required)
     * 
     */
    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    /**
     * Given name of the person.
     * (Required)
     * 
     */
    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Name withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    /**
     * Family name of the person.
     * (Required)
     * 
     */
    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Family name of the person.
     * (Required)
     * 
     */
    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Name withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    /**
     * Middle name for the person.
     * 
     */
    @JsonProperty("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Middle name for the person.
     * 
     */
    @JsonProperty("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Name withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    /**
     * The source for this name.
     * 
     */
    @JsonProperty("source")
    public Name.Source getSource() {
        return source;
    }

    /**
     * The source for this name.
     * 
     */
    @JsonProperty("source")
    public void setSource(Name.Source source) {
        this.source = source;
    }

    public Name withSource(Name.Source source) {
        this.source = source;
        return this;
    }

    /**
     * The locale for this username if applicable.
     * (Required)
     * 
     */
    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    /**
     * The locale for this username if applicable.
     * (Required)
     * 
     */
    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Name withLocale(String locale) {
        this.locale = locale;
        return this;
    }


    /**
     * The source for this name.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Source {

        USER("user"),
        EKYC("ekyc"),
        ADMIN("admin"),
        FAMILY_MEMBER("family_member");
        private final String value;
        private final static Map<String, Name.Source> CONSTANTS = new HashMap<String, Name.Source>();

        static {
            for (Name.Source c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Source(String value) {
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
        public static Name.Source fromValue(String value) {
            Name.Source constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
