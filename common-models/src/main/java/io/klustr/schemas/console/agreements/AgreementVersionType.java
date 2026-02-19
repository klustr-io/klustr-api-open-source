
package io.klustr.schemas.console.agreements;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * AgreementVersionType
 * <p>
 * The type of change made.
 * 
 */
@Generated("jsonschema2pojo")
public enum AgreementVersionType {

    MINOR("minor"),
    MAJOR("major");
    private final String value;
    private final static Map<String, AgreementVersionType> CONSTANTS = new HashMap<String, AgreementVersionType>();

    static {
        for (AgreementVersionType c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    AgreementVersionType(String value) {
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
    public static AgreementVersionType fromValue(String value) {
        AgreementVersionType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
