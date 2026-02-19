
package io.klustr.schemas.persons;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * RelationshipStatus
 * <p>
 * The relationship status for the user.
 * 
 */
@Generated("jsonschema2pojo")
public enum RelationshipStatus {

    SINGLE("single"),
    CO_HABITATION("co-habitation"),
    IN_RELATIONSHIP("in-relationship"),
    MARRIED("married"),
    DIVORCED("divorced"),
    WIDOW("widow");
    private final String value;
    private final static Map<String, RelationshipStatus> CONSTANTS = new HashMap<String, RelationshipStatus>();

    static {
        for (RelationshipStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    RelationshipStatus(String value) {
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
    public static RelationshipStatus fromValue(String value) {
        RelationshipStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
