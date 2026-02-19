
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentAuditAgent
 * <p>
 * The device that was used to initiate this request. See https://github.com/ua-parser/uap-java OR https://github.com/k143408/user-agent-parser-spring-boot-3
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "os",
    "client",
    "device",
    "header"
})
@Generated("jsonschema2pojo")
public class ConsentAuditAgent {

    /**
     * ConsentAuditAgentOs
     * <p>
     * The OS that the user is using.
     * 
     */
    @JsonProperty("os")
    @JsonPropertyDescription("The OS that the user is using.")
    private ConsentAuditAgentOs os;
    /**
     * ConsentAuditAgentClient
     * <p>
     * The client that the user is using.
     * 
     */
    @JsonProperty("client")
    @JsonPropertyDescription("The client that the user is using.")
    private ConsentAuditAgentClient client;
    /**
     * ConsentAuditAgentDevice
     * <p>
     * The device that the user is using.
     * 
     */
    @JsonProperty("device")
    @JsonPropertyDescription("The device that the user is using.")
    private ConsentAuditAgentDevice device;
    /**
     * The raw user agent making this request.
     * 
     */
    @JsonProperty("header")
    @JsonPropertyDescription("The raw user agent making this request.")
    private String header;

    /**
     * ConsentAuditAgentOs
     * <p>
     * The OS that the user is using.
     * 
     */
    @JsonProperty("os")
    public ConsentAuditAgentOs getOs() {
        return os;
    }

    /**
     * ConsentAuditAgentOs
     * <p>
     * The OS that the user is using.
     * 
     */
    @JsonProperty("os")
    public void setOs(ConsentAuditAgentOs os) {
        this.os = os;
    }

    public ConsentAuditAgent withOs(ConsentAuditAgentOs os) {
        this.os = os;
        return this;
    }

    /**
     * ConsentAuditAgentClient
     * <p>
     * The client that the user is using.
     * 
     */
    @JsonProperty("client")
    public ConsentAuditAgentClient getClient() {
        return client;
    }

    /**
     * ConsentAuditAgentClient
     * <p>
     * The client that the user is using.
     * 
     */
    @JsonProperty("client")
    public void setClient(ConsentAuditAgentClient client) {
        this.client = client;
    }

    public ConsentAuditAgent withClient(ConsentAuditAgentClient client) {
        this.client = client;
        return this;
    }

    /**
     * ConsentAuditAgentDevice
     * <p>
     * The device that the user is using.
     * 
     */
    @JsonProperty("device")
    public ConsentAuditAgentDevice getDevice() {
        return device;
    }

    /**
     * ConsentAuditAgentDevice
     * <p>
     * The device that the user is using.
     * 
     */
    @JsonProperty("device")
    public void setDevice(ConsentAuditAgentDevice device) {
        this.device = device;
    }

    public ConsentAuditAgent withDevice(ConsentAuditAgentDevice device) {
        this.device = device;
        return this;
    }

    /**
     * The raw user agent making this request.
     * 
     */
    @JsonProperty("header")
    public String getHeader() {
        return header;
    }

    /**
     * The raw user agent making this request.
     * 
     */
    @JsonProperty("header")
    public void setHeader(String header) {
        this.header = header;
    }

    public ConsentAuditAgent withHeader(String header) {
        this.header = header;
        return this;
    }

}
