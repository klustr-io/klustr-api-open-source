
package io.klustr.schemas.persons;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ParentalStatus
 * <p>
 * The relationship status for the user.
 * 
 */
@Generated("jsonschema2pojo")
public enum ParentalStatus {

    PARENT("parent"),
    CAREGIVER("caregiver"),
    NONE("none");
    private final String value;
    private final static Map<String, ParentalStatus> CONSTANTS = new HashMap<String, ParentalStatus>();

    static {
        for (ParentalStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ParentalStatus(String value) {
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
    public static ParentalStatus fromValue(String value) {
        ParentalStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
