
package io.klustr.schemas.integrations.compute;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * LoadBalancerBackendResource
 * <p>
 * A backend managed by the loadbalancer
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mode",
    "name"
})
@Generated("jsonschema2pojo")
public class LoadBalancerBackendResource {

    /**
     * The mode for the load balancer
     * 
     */
    @JsonProperty("mode")
    @JsonPropertyDescription("The mode for the load balancer")
    private LoadBalancerBackendResource.Mode mode;
    /**
     * The unique name for this backend resource.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The unique name for this backend resource.")
    private String name;

    /**
     * The mode for the load balancer
     * 
     */
    @JsonProperty("mode")
    public LoadBalancerBackendResource.Mode getMode() {
        return mode;
    }

    /**
     * The mode for the load balancer
     * 
     */
    @JsonProperty("mode")
    public void setMode(LoadBalancerBackendResource.Mode mode) {
        this.mode = mode;
    }

    public LoadBalancerBackendResource withMode(LoadBalancerBackendResource.Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * The unique name for this backend resource.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The unique name for this backend resource.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public LoadBalancerBackendResource withName(String name) {
        this.name = name;
        return this;
    }


    /**
     * The mode for the load balancer
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Mode {

        HTTP("http"),
        TCP("tcp");
        private final String value;
        private final static Map<String, LoadBalancerBackendResource.Mode> CONSTANTS = new HashMap<String, LoadBalancerBackendResource.Mode>();

        static {
            for (LoadBalancerBackendResource.Mode c: values()) {
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
        public static LoadBalancerBackendResource.Mode fromValue(String value) {
            LoadBalancerBackendResource.Mode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
