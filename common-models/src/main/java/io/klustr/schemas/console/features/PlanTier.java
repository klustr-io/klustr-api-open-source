
package io.klustr.schemas.console.features;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Generated("jsonschema2pojo")
public enum PlanTier {

    FREE("free"),
    BASIC("basic"),
    PREMIUM("premium"),
    ENTERPRISE("enterprise");
    private final String value;
    private final static Map<String, PlanTier> CONSTANTS = new HashMap<String, PlanTier>();

    static {
        for (PlanTier c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    PlanTier(String value) {
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
    public static PlanTier fromValue(String value) {
        PlanTier constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
