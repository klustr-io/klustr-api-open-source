
package io.klustr.schemas.console.features;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * High-level category of the feature.
 * 
 */
@Generated("jsonschema2pojo")
public enum Category {

    DATABASE("database"),
    STORAGE("storage"),
    DOCKER("docker"),
    NETWORKING("networking"),
    OBSERVABILITY("observability"),
    COMPUTE("compute"),
    ANALYTICS("analytics"),
    SERVERLESS("serverless"),
    AI("ai"),
    TOOLS("tools"),
    CI_CD("ci_cd"),
    OPERATIONS("operations"),
    SECURITY("security");
    private final String value;
    private final static Map<String, Category> CONSTANTS = new HashMap<String, Category>();

    static {
        for (Category c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    Category(String value) {
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
    public static Category fromValue(String value) {
        Category constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
