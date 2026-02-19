
package io.klustr.schemas.persons.supervised;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ThirdPartyPermission
 * <p>
 * The supervised account can approve consent for basic operations or not.
 * 
 */
@Generated("jsonschema2pojo")
public enum ThirdPartyPermission {

    DENY_ALL("deny_all"),
    ALLOW_BASIC("allow_basic");
    private final String value;
    private final static Map<String, ThirdPartyPermission> CONSTANTS = new HashMap<String, ThirdPartyPermission>();

    static {
        for (ThirdPartyPermission c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ThirdPartyPermission(String value) {
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
    public static ThirdPartyPermission fromValue(String value) {
        ThirdPartyPermission constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
