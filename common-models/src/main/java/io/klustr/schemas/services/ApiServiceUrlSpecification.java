
package io.klustr.schemas.services;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ApiServiceUrlSpecification
 * <p>
 * Reference to a service.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "path",
    "port",
    "protocol",
    "host"
})
@Generated("jsonschema2pojo")
public class ApiServiceUrlSpecification {

    /**
     * The path for this service
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The path for this service")
    private String path;
    /**
     * The port for this service
     * 
     */
    @JsonProperty("port")
    @JsonPropertyDescription("The port for this service")
    private String port;
    /**
     * The protocol for this service
     * 
     */
    @JsonProperty("protocol")
    @JsonPropertyDescription("The protocol for this service")
    private String protocol;
    /**
     * The host for this service.
     * 
     */
    @JsonProperty("host")
    @JsonPropertyDescription("The host for this service.")
    private String host;

    /**
     * The path for this service
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The path for this service
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public ApiServiceUrlSpecification withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * The port for this service
     * 
     */
    @JsonProperty("port")
    public String getPort() {
        return port;
    }

    /**
     * The port for this service
     * 
     */
    @JsonProperty("port")
    public void setPort(String port) {
        this.port = port;
    }

    public ApiServiceUrlSpecification withPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * The protocol for this service
     * 
     */
    @JsonProperty("protocol")
    public String getProtocol() {
        return protocol;
    }

    /**
     * The protocol for this service
     * 
     */
    @JsonProperty("protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ApiServiceUrlSpecification withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * The host for this service.
     * 
     */
    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    /**
     * The host for this service.
     * 
     */
    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    public ApiServiceUrlSpecification withHost(String host) {
        this.host = host;
        return this;
    }

}
