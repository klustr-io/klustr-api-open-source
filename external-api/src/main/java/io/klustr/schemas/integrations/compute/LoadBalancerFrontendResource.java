
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
 * LoadBalancerFrontendResource
 * <p>
 * A frontend for the loadbalancer for serving content
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "mode"
})
@Generated("jsonschema2pojo")
public class LoadBalancerFrontendResource {

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the front end.")
    private String name;
    /**
     * The mode of operation such as http or tcp
     * 
     */
    @JsonProperty("mode")
    @JsonPropertyDescription("The mode of operation such as http or tcp")
    private LoadBalancerFrontendResource.Mode mode;

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public LoadBalancerFrontendResource withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The mode of operation such as http or tcp
     * 
     */
    @JsonProperty("mode")
    public LoadBalancerFrontendResource.Mode getMode() {
        return mode;
    }

    /**
     * The mode of operation such as http or tcp
     * 
     */
    @JsonProperty("mode")
    public void setMode(LoadBalancerFrontendResource.Mode mode) {
        this.mode = mode;
    }

    public LoadBalancerFrontendResource withMode(LoadBalancerFrontendResource.Mode mode) {
        this.mode = mode;
        return this;
    }


    /**
     * The mode of operation such as http or tcp
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Mode {

        HTTP("http"),
        TCP("tcp");
        private final String value;
        private final static Map<String, LoadBalancerFrontendResource.Mode> CONSTANTS = new HashMap<String, LoadBalancerFrontendResource.Mode>();

        static {
            for (LoadBalancerFrontendResource.Mode c: values()) {
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
        public static LoadBalancerFrontendResource.Mode fromValue(String value) {
            LoadBalancerFrontendResource.Mode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
