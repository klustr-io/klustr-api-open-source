
package io.klustr.schemas.console.apps;

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
 * Configures if any authenticated user can access or if it is restricted access based on user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mode",
    "allow_roles",
    "deny_roles"
})
@Generated("jsonschema2pojo")
public class AccessMode {

    @JsonProperty("mode")
    private AccessMode.Mode mode;
    /**
     * The roles that a user must have in order to continue.
     * 
     */
    @JsonProperty("allow_roles")
    @JsonPropertyDescription("The roles that a user must have in order to continue.")
    private List<String> allowRoles = new ArrayList<String>();
    /**
     * The roles that a user must not have in order to be denied access.
     * 
     */
    @JsonProperty("deny_roles")
    @JsonPropertyDescription("The roles that a user must not have in order to be denied access.")
    private List<String> denyRoles = new ArrayList<String>();

    @JsonProperty("mode")
    public AccessMode.Mode getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(AccessMode.Mode mode) {
        this.mode = mode;
    }

    public AccessMode withMode(AccessMode.Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * The roles that a user must have in order to continue.
     * 
     */
    @JsonProperty("allow_roles")
    public List<String> getAllowRoles() {
        return allowRoles;
    }

    /**
     * The roles that a user must have in order to continue.
     * 
     */
    @JsonProperty("allow_roles")
    public void setAllowRoles(List<String> allowRoles) {
        this.allowRoles = allowRoles;
    }

    public AccessMode withAllowRoles(List<String> allowRoles) {
        this.allowRoles = allowRoles;
        return this;
    }

    /**
     * The roles that a user must not have in order to be denied access.
     * 
     */
    @JsonProperty("deny_roles")
    public List<String> getDenyRoles() {
        return denyRoles;
    }

    /**
     * The roles that a user must not have in order to be denied access.
     * 
     */
    @JsonProperty("deny_roles")
    public void setDenyRoles(List<String> denyRoles) {
        this.denyRoles = denyRoles;
    }

    public AccessMode withDenyRoles(List<String> denyRoles) {
        this.denyRoles = denyRoles;
        return this;
    }

    @Generated("jsonschema2pojo")
    public enum Mode {

        PRIVATE("private"),
        PUBLIC("public");
        private final String value;
        private final static Map<String, AccessMode.Mode> CONSTANTS = new HashMap<String, AccessMode.Mode>();

        static {
            for (AccessMode.Mode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Mode(String value) {
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
        public static AccessMode.Mode fromValue(String value) {
            AccessMode.Mode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
