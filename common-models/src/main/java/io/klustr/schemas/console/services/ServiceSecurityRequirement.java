
package io.klustr.schemas.console.services;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ServiceEntryStatus
 * <p>
 * The security to activate on this service.
 * 
 */
@Generated("jsonschema2pojo")
public enum ServiceSecurityRequirement {

    NONE("none"),
    TOKEN("token");
    private final String value;
    private final static Map<String, ServiceSecurityRequirement> CONSTANTS = new HashMap<String, ServiceSecurityRequirement>();

    static {
        for (ServiceSecurityRequirement c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ServiceSecurityRequirement(String value) {
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
    public static ServiceSecurityRequirement fromValue(String value) {
        ServiceSecurityRequirement constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
