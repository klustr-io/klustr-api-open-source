
package io.klustr.console.schemas;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Activates pseudo anonymous identifiers when set to pairwise
 * 
 */
@Generated("jsonschema2pojo")
public enum SubjectType {

    PAIRWISE("pairwise"),
    PUBLIC("public");
    private final String value;
    private final static Map<String, SubjectType> CONSTANTS = new HashMap<String, SubjectType>();

    static {
        for (SubjectType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    SubjectType(String value) {
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
    public static SubjectType fromValue(String value) {
        SubjectType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
