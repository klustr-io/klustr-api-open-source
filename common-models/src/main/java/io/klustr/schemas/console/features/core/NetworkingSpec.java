
package io.klustr.schemas.console.features.core;

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
 * NetworkingSpec
 * <p>
 * Networking and exposure configuration.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "internal_port",
    "protocol",
    "endpoint_template"
})
@Generated("jsonschema2pojo")
public class NetworkingSpec {

    /**
     * Internal service port.
     * 
     */
    @JsonProperty("internal_port")
    @JsonPropertyDescription("Internal service port.")
    private Integer internalPort;
    /**
     * Transport protocol.
     * 
     */
    @JsonProperty("protocol")
    @JsonPropertyDescription("Transport protocol.")
    private NetworkingSpec.Protocol protocol;
    /**
     * Connection string or endpoint template.
     * 
     */
    @JsonProperty("endpoint_template")
    @JsonPropertyDescription("Connection string or endpoint template.")
    private String endpointTemplate;

    /**
     * Internal service port.
     * 
     */
    @JsonProperty("internal_port")
    public Integer getInternalPort() {
        return internalPort;
    }

    /**
     * Internal service port.
     * 
     */
    @JsonProperty("internal_port")
    public void setInternalPort(Integer internalPort) {
        this.internalPort = internalPort;
    }

    public NetworkingSpec withInternalPort(Integer internalPort) {
        this.internalPort = internalPort;
        return this;
    }

    /**
     * Transport protocol.
     * 
     */
    @JsonProperty("protocol")
    public NetworkingSpec.Protocol getProtocol() {
        return protocol;
    }

    /**
     * Transport protocol.
     * 
     */
    @JsonProperty("protocol")
    public void setProtocol(NetworkingSpec.Protocol protocol) {
        this.protocol = protocol;
    }

    public NetworkingSpec withProtocol(NetworkingSpec.Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Connection string or endpoint template.
     * 
     */
    @JsonProperty("endpoint_template")
    public String getEndpointTemplate() {
        return endpointTemplate;
    }

    /**
     * Connection string or endpoint template.
     * 
     */
    @JsonProperty("endpoint_template")
    public void setEndpointTemplate(String endpointTemplate) {
        this.endpointTemplate = endpointTemplate;
    }

    public NetworkingSpec withEndpointTemplate(String endpointTemplate) {
        this.endpointTemplate = endpointTemplate;
        return this;
    }


    /**
     * Transport protocol.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Protocol {

        TCP("tcp"),
        HTTP("http");
        private final String value;
        private final static Map<String, NetworkingSpec.Protocol> CONSTANTS = new HashMap<String, NetworkingSpec.Protocol>();

        static {
            for (NetworkingSpec.Protocol c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Protocol(String value) {
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
        public static NetworkingSpec.Protocol fromValue(String value) {
            NetworkingSpec.Protocol constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
