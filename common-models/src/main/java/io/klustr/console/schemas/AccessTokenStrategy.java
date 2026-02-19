
package io.klustr.console.schemas;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * The method for how a token is generated, opaque is preferred for security and requires introspection.
 * 
 */
@Generated("jsonschema2pojo")
public enum AccessTokenStrategy {

    JWT("jwt"),
    OPAQUE("opaque");
    private final String value;
    private final static Map<String, AccessTokenStrategy> CONSTANTS = new HashMap<String, AccessTokenStrategy>();

    static {
        for (AccessTokenStrategy c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    AccessTokenStrategy(String value) {
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
    public static AccessTokenStrategy fromValue(String value) {
        AccessTokenStrategy constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
