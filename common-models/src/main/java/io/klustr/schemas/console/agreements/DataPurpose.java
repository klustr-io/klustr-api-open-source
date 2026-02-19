
package io.klustr.schemas.console.agreements;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * DataPurpose
 * <p>
 * Specific actions and procedures applied to data to ensure compliance with data policies
 * 
 */
@Generated("jsonschema2pojo")
public enum DataPurpose {

    COLLECTION("COLLECTION"),
    STORAGE("STORAGE"),
    SHARING("SHARING"),
    PERSONALIZATION("PERSONALIZATION"),
    PROCESSING("PROCESSING"),
    TRANSFER("TRANSFER"),
    AGGREGATION("AGGREGATION");
    private final String value;
    private final static Map<String, DataPurpose> CONSTANTS = new HashMap<String, DataPurpose>();

    static {
        for (DataPurpose c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    DataPurpose(String value) {
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
    public static DataPurpose fromValue(String value) {
        DataPurpose constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
