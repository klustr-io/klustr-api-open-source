
package io.klustr.schemas.console.apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ApiRestriction
 * <p>
 * The restrictions for an API that is used to protect unauthorized access.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "values"
})
@Generated("jsonschema2pojo")
public class ApiRestriction {

    /**
     * Application restrictions limit an API key’s usage to specific websites, IP addresses, Android applications, or iOS applications. You can set one application restriction per key.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Application restrictions limit an API key\u2019s usage to specific websites, IP addresses, Android applications, or iOS applications. You can set one application restriction per key.")
    private ApiRestriction.Type type;
    @JsonProperty("values")
    private List<String> values = new ArrayList<String>();

    /**
     * Application restrictions limit an API key’s usage to specific websites, IP addresses, Android applications, or iOS applications. You can set one application restriction per key.
     * 
     */
    @JsonProperty("type")
    public ApiRestriction.Type getType() {
        return type;
    }

    /**
     * Application restrictions limit an API key’s usage to specific websites, IP addresses, Android applications, or iOS applications. You can set one application restriction per key.
     * 
     */
    @JsonProperty("type")
    public void setType(ApiRestriction.Type type) {
        this.type = type;
    }

    public ApiRestriction withType(ApiRestriction.Type type) {
        this.type = type;
        return this;
    }

    @JsonProperty("values")
    public List<String> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<String> values) {
        this.values = values;
    }

    public ApiRestriction withValues(List<String> values) {
        this.values = values;
        return this;
    }


    /**
     * Application restrictions limit an API key’s usage to specific websites, IP addresses, Android applications, or iOS applications. You can set one application restriction per key.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        NONE("none"),
        WEBSITES("websites"),
        IP_ADDRESSES("ip_addresses"),
        ANDROID_APPS("android_apps"),
        IOS_APPS("ios_apps");
        private final String value;
        private final static Map<String, ApiRestriction.Type> CONSTANTS = new HashMap<String, ApiRestriction.Type>();

        static {
            for (ApiRestriction.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
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
        public static ApiRestriction.Type fromValue(String value) {
            ApiRestriction.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
