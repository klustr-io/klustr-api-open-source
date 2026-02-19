
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PortainerNetworkCreateRequest
 * <p>
 * Request to make a new network in portainer (docker)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "Driver",
    "Internal",
    "Labels"
})
@Generated("jsonschema2pojo")
public class PortainerNetworkCreateRequest {

    /**
     * The name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    @JsonPropertyDescription("The name for this network")
    private String name;
    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    @JsonPropertyDescription("The driver for this network (defaults to bridge)")
    private String driver = "bridge";
    /**
     * If this network is an internal network only between instances. Default is true
     * (Required)
     * 
     */
    @JsonProperty("Internal")
    @JsonPropertyDescription("If this network is an internal network only between instances. Default is true")
    private Boolean internal = true;
    /**
     * The common labels to apply for this environment
     * (Required)
     * 
     */
    @JsonProperty("Labels")
    @JsonPropertyDescription("The common labels to apply for this environment")
    private Labels labels;

    /**
     * The name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    /**
     * The name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public PortainerNetworkCreateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    public String getDriver() {
        return driver;
    }

    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public PortainerNetworkCreateRequest withDriver(String driver) {
        this.driver = driver;
        return this;
    }

    /**
     * If this network is an internal network only between instances. Default is true
     * (Required)
     * 
     */
    @JsonProperty("Internal")
    public Boolean getInternal() {
        return internal;
    }

    /**
     * If this network is an internal network only between instances. Default is true
     * (Required)
     * 
     */
    @JsonProperty("Internal")
    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public PortainerNetworkCreateRequest withInternal(Boolean internal) {
        this.internal = internal;
        return this;
    }

    /**
     * The common labels to apply for this environment
     * (Required)
     * 
     */
    @JsonProperty("Labels")
    public Labels getLabels() {
        return labels;
    }

    /**
     * The common labels to apply for this environment
     * (Required)
     * 
     */
    @JsonProperty("Labels")
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public PortainerNetworkCreateRequest withLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

}
