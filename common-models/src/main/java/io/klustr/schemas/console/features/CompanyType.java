
package io.klustr.schemas.console.features;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Generated("jsonschema2pojo")
public enum CompanyType {

    THIRD_PARTY("third_party"),
    FIRST_PARTY("first_party"),
    PARTNER("partner");
    private final String value;
    private final static Map<String, CompanyType> CONSTANTS = new HashMap<String, CompanyType>();

    static {
        for (CompanyType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    CompanyType(String value) {
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
    public static CompanyType fromValue(String value) {
        CompanyType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
