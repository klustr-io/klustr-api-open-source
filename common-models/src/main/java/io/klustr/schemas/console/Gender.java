
package io.klustr.schemas.console;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Gender
 * <p>
 * 
 * 
 */
@Generated("jsonschema2pojo")
public enum Gender {


    /**
     * Determines themselves to associate with male sex characteristics
     * 
     */
    male("male"),

    /**
     * Determines themselves to associate with female sex characteristics
     * 
     */
    female("female"),

    /**
     * Determines themselves not to be associated with any specific sex.
     * 
     */
    unknown("unknown");
    private final String value;
    private final static Map<String, Gender> CONSTANTS = new HashMap<String, Gender>();

    static {
        for (Gender c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    Gender(String value) {
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
    public static Gender fromValue(String value) {
        Gender constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
