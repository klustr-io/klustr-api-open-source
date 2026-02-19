
package io.klustr.schemas.console;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * OidcStatus
 * <p>
 * The status of this client
 * 
 */
@Generated("jsonschema2pojo")
public enum OidcStatus {

    ENABLED("enabled"),
    DISABLED("disabled"),
    DELETED("deleted");
    private final String value;
    private final static Map<String, OidcStatus> CONSTANTS = new HashMap<String, OidcStatus>();

    static {
        for (OidcStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    OidcStatus(String value) {
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
    public static OidcStatus fromValue(String value) {
        OidcStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
