
package io.klustr.schemas.console.features;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Lifecycle status of the feature.
 * 
 */
@Generated("jsonschema2pojo")
public enum FeatureStatus {

    ACTIVE("active"),
    INACTIVE("inactive");
    private final String value;
    private final static Map<String, FeatureStatus> CONSTANTS = new HashMap<String, FeatureStatus>();

    static {
        for (FeatureStatus c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    FeatureStatus(String value) {
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
    public static FeatureStatus fromValue(String value) {
        FeatureStatus constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
