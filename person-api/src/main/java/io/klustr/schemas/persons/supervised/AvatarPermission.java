
package io.klustr.schemas.persons.supervised;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * AvatarPermissions
 * <p>
 * The supervised account can change their profile picture
 * 
 */
@Generated("jsonschema2pojo")
public enum AvatarPermission {

    DENY("deny"),
    ALLOW("allow");
    private final String value;
    private final static Map<String, AvatarPermission> CONSTANTS = new HashMap<String, AvatarPermission>();

    static {
        for (AvatarPermission c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    AvatarPermission(String value) {
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
    public static AvatarPermission fromValue(String value) {
        AvatarPermission constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
