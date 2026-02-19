
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeIngressBinding
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "server",
    "port",
    "weight"
})
@Generated("jsonschema2pojo")
public class ComputeIngressBinding {

    /**
     * The destination server to route against.
     * 
     */
    @JsonProperty("server")
    @JsonPropertyDescription("The destination server to route against.")
    private String server;
    /**
     * The destination to map to.
     * 
     */
    @JsonProperty("port")
    @JsonPropertyDescription("The destination to map to.")
    private Integer port;
    /**
     * Optional weight for this server instance
     * 
     */
    @JsonProperty("weight")
    @JsonPropertyDescription("Optional weight for this server instance")
    private Integer weight;

    /**
     * The destination server to route against.
     * 
     */
    @JsonProperty("server")
    public String getServer() {
        return server;
    }

    /**
     * The destination server to route against.
     * 
     */
    @JsonProperty("server")
    public void setServer(String server) {
        this.server = server;
    }

    public ComputeIngressBinding withServer(String server) {
        this.server = server;
        return this;
    }

    /**
     * The destination to map to.
     * 
     */
    @JsonProperty("port")
    public Integer getPort() {
        return port;
    }

    /**
     * The destination to map to.
     * 
     */
    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
    }

    public ComputeIngressBinding withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Optional weight for this server instance
     * 
     */
    @JsonProperty("weight")
    public Integer getWeight() {
        return weight;
    }

    /**
     * Optional weight for this server instance
     * 
     */
    @JsonProperty("weight")
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public ComputeIngressBinding withWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

}
