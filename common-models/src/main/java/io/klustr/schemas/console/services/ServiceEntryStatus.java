
package io.klustr.schemas.console.services;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ServiceEntryStatus
 * <p>
 * The status of the service
 * 
 */
@Generated("jsonschema2pojo")
public enum ServiceEntryStatus {

    DISABLED("disabled"),
    DEPRECATED("deprecated"),
    DELETED("deleted"),
    ALPHA("alpha"),
    BETA("beta"),
    GENERAL_AVAILABILITY("general_availability");
    private final String value;
    private final static Map<String, ServiceEntryStatus> CONSTANTS = new HashMap<String, ServiceEntryStatus>();

    static {
        for (ServiceEntryStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ServiceEntryStatus(String value) {
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
    public static ServiceEntryStatus fromValue(String value) {
        ServiceEntryStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
