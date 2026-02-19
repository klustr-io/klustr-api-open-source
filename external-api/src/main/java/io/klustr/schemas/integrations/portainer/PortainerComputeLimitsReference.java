
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PortainerComputeLimitsReference
 * <p>
 * Describes the compute and memory limits applied to a container as defined in its HostConfig, retrieved via Portainer's Docker API proxy.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Id",
    "HostConfig"
})
@Generated("jsonschema2pojo")
public class PortainerComputeLimitsReference {

    /**
     * Unique container identifier (the Docker container ID).
     * 
     */
    @JsonProperty("Id")
    @JsonPropertyDescription("Unique container identifier (the Docker container ID).")
    private String id;
    /**
     * Runtime configuration options applied when the container was created, including CPU and memory restrictions.
     * 
     */
    @JsonProperty("HostConfig")
    @JsonPropertyDescription("Runtime configuration options applied when the container was created, including CPU and memory restrictions.")
    private HostConfig hostConfig;

    /**
     * Unique container identifier (the Docker container ID).
     * 
     */
    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    /**
     * Unique container identifier (the Docker container ID).
     * 
     */
    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    public PortainerComputeLimitsReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Runtime configuration options applied when the container was created, including CPU and memory restrictions.
     * 
     */
    @JsonProperty("HostConfig")
    public HostConfig getHostConfig() {
        return hostConfig;
    }

    /**
     * Runtime configuration options applied when the container was created, including CPU and memory restrictions.
     * 
     */
    @JsonProperty("HostConfig")
    public void setHostConfig(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    public PortainerComputeLimitsReference withHostConfig(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
        return this;
    }

}
