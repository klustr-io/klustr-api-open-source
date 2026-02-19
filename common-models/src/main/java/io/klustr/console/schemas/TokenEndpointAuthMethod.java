
package io.klustr.console.schemas;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * The strategy for the token
 * 
 */
@Generated("jsonschema2pojo")
public enum TokenEndpointAuthMethod {

    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    PRIVATE_KEY_JWT("private_key_jwt");
    private final String value;
    private final static Map<String, TokenEndpointAuthMethod> CONSTANTS = new HashMap<String, TokenEndpointAuthMethod>();

    static {
        for (TokenEndpointAuthMethod c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    TokenEndpointAuthMethod(String value) {
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
    public static TokenEndpointAuthMethod fromValue(String value) {
        TokenEndpointAuthMethod constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
