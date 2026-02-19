
package io.klustr.schemas.integrations.compute;

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
 * ComputeIngress
 * <p>
 * Examines the current live configuration of the ingress and determines its status.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "connection_string",
    "dns",
    "bindings"
})
@Generated("jsonschema2pojo")
public class ComputeIngress {

    /**
     * If this compute has an ingress active and attached.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("If this compute has an ingress active and attached.")
    private ComputeIngress.Status status;
    /**
     * A connection string to use to connect to the instance or primary service.
     * 
     */
    @JsonProperty("connection_string")
    @JsonPropertyDescription("A connection string to use to connect to the instance or primary service.")
    private String connectionString;
    /**
     * ComputeIngressDns
     * <p>
     * The DNS related to this compute instance
     * 
     */
    @JsonProperty("dns")
    @JsonPropertyDescription("The DNS related to this compute instance")
    private ComputeIngressDns dns;
    /**
     * ComputeIngressBindingDestination
     * <p>
     * If binding should be on https
     * 
     */
    @JsonProperty("bindings")
    @JsonPropertyDescription("If binding should be on https")
    private List<ComputeIngressBinding> bindings = new ArrayList<ComputeIngressBinding>();

    /**
     * If this compute has an ingress active and attached.
     * 
     */
    @JsonProperty("status")
    public ComputeIngress.Status getStatus() {
        return status;
    }

    /**
     * If this compute has an ingress active and attached.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ComputeIngress.Status status) {
        this.status = status;
    }

    public ComputeIngress withStatus(ComputeIngress.Status status) {
        this.status = status;
        return this;
    }

    /**
     * A connection string to use to connect to the instance or primary service.
     * 
     */
    @JsonProperty("connection_string")
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * A connection string to use to connect to the instance or primary service.
     * 
     */
    @JsonProperty("connection_string")
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public ComputeIngress withConnectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    /**
     * ComputeIngressDns
     * <p>
     * The DNS related to this compute instance
     * 
     */
    @JsonProperty("dns")
    public ComputeIngressDns getDns() {
        return dns;
    }

    /**
     * ComputeIngressDns
     * <p>
     * The DNS related to this compute instance
     * 
     */
    @JsonProperty("dns")
    public void setDns(ComputeIngressDns dns) {
        this.dns = dns;
    }

    public ComputeIngress withDns(ComputeIngressDns dns) {
        this.dns = dns;
        return this;
    }

    /**
     * ComputeIngressBindingDestination
     * <p>
     * If binding should be on https
     * 
     */
    @JsonProperty("bindings")
    public List<ComputeIngressBinding> getBindings() {
        return bindings;
    }

    /**
     * ComputeIngressBindingDestination
     * <p>
     * If binding should be on https
     * 
     */
    @JsonProperty("bindings")
    public void setBindings(List<ComputeIngressBinding> bindings) {
        this.bindings = bindings;
    }

    public ComputeIngress withBindings(List<ComputeIngressBinding> bindings) {
        this.bindings = bindings;
        return this;
    }


    /**
     * If this compute has an ingress active and attached.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        DISABLED("disabled");
        private final String value;
        private final static Map<String, ComputeIngress.Status> CONSTANTS = new HashMap<String, ComputeIngress.Status>();

        static {
            for (ComputeIngress.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
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
        public static ComputeIngress.Status fromValue(String value) {
            ComputeIngress.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
