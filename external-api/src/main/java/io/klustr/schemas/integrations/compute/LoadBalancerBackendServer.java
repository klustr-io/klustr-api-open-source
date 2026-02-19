
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * LoadBalancerBackendServer
 * <p>
 * One or more servers linked to the backend
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "port",
    "address",
    "init-addr",
    "resolvers"
})
@Generated("jsonschema2pojo")
public class LoadBalancerBackendServer {

    /**
     * The unique name for this server for roundrobin etc.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The unique name for this server for roundrobin etc.")
    private String name;
    /**
     * The port of the service
     * 
     */
    @JsonProperty("port")
    @JsonPropertyDescription("The port of the service")
    private Integer port;
    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("The server address (machine name or ip or dns)")
    private String address;
    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("init-addr")
    @JsonPropertyDescription("The server address (machine name or ip or dns)")
    private String initAddr = "none";
    /**
     * The DNS resolvers to use, defaults to docker.
     * 
     */
    @JsonProperty("resolvers")
    @JsonPropertyDescription("The DNS resolvers to use, defaults to docker.")
    private String resolvers = "dockerdns";

    /**
     * The unique name for this server for roundrobin etc.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The unique name for this server for roundrobin etc.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public LoadBalancerBackendServer withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The port of the service
     * 
     */
    @JsonProperty("port")
    public Integer getPort() {
        return port;
    }

    /**
     * The port of the service
     * 
     */
    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
    }

    public LoadBalancerBackendServer withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    public LoadBalancerBackendServer withAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("init-addr")
    public String getInitAddr() {
        return initAddr;
    }

    /**
     * The server address (machine name or ip or dns)
     * 
     */
    @JsonProperty("init-addr")
    public void setInitAddr(String initAddr) {
        this.initAddr = initAddr;
    }

    public LoadBalancerBackendServer withInitAddr(String initAddr) {
        this.initAddr = initAddr;
        return this;
    }

    /**
     * The DNS resolvers to use, defaults to docker.
     * 
     */
    @JsonProperty("resolvers")
    public String getResolvers() {
        return resolvers;
    }

    /**
     * The DNS resolvers to use, defaults to docker.
     * 
     */
    @JsonProperty("resolvers")
    public void setResolvers(String resolvers) {
        this.resolvers = resolvers;
    }

    public LoadBalancerBackendServer withResolvers(String resolvers) {
        this.resolvers = resolvers;
        return this;
    }

}
