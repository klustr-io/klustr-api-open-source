
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
 * ComputeIngressDnsIpRecord
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "address"
})
@Generated("jsonschema2pojo")
public class ComputeIngressDnsIpRecord {

    /**
     * ComputeIngressDnsIpRecordType
     * <p>
     * The type of ip address allocated
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of ip address allocated")
    private ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType type;
    /**
     * The ip address available and bound.
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("The ip address available and bound.")
    private String address;

    /**
     * ComputeIngressDnsIpRecordType
     * <p>
     * The type of ip address allocated
     * 
     */
    @JsonProperty("type")
    public ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType getType() {
        return type;
    }

    /**
     * ComputeIngressDnsIpRecordType
     * <p>
     * The type of ip address allocated
     * 
     */
    @JsonProperty("type")
    public void setType(ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType type) {
        this.type = type;
    }

    public ComputeIngressDnsIpRecord withType(ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType type) {
        this.type = type;
        return this;
    }

    /**
     * The ip address available and bound.
     * 
     */
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    /**
     * The ip address available and bound.
     * 
     */
    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    public ComputeIngressDnsIpRecord withAddress(String address) {
        this.address = address;
        return this;
    }


    /**
     * ComputeIngressDnsIpRecordType
     * <p>
     * The type of ip address allocated
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ComputeIngressDnsIpRecordType {

        PRIVATE("private"),
        PUBLIC("public");
        private final String value;
        private final static Map<String, ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType> CONSTANTS = new HashMap<String, ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType>();

        static {
            for (ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ComputeIngressDnsIpRecordType(String value) {
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
        public static ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType fromValue(String value) {
            ComputeIngressDnsIpRecord.ComputeIngressDnsIpRecordType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
