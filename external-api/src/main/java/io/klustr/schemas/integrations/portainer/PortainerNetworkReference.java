
package io.klustr.schemas.integrations.portainer;

import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PortainerNetworkReference
 * <p>
 * Request to make a new network in portainer (docker)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Id",
    "Name",
    "Driver",
    "Internal",
    "Labels"
})
@Generated("jsonschema2pojo")
public class PortainerNetworkReference {

    /**
     * The Id for this network
     * 
     */
    @JsonProperty("Id")
    @JsonPropertyDescription("The Id for this network")
    private java.lang.String id;
    /**
     * The Name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    @JsonPropertyDescription("The Name for this network")
    private java.lang.String name;
    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    @JsonPropertyDescription("The driver for this network (defaults to bridge)")
    private java.lang.String driver = "bridge";
    /**
     * If this network is an internal network only between instances. Default is true
     * (Required)
     * 
     */
    @JsonProperty("Internal")
    @JsonPropertyDescription("If this network is an internal network only between instances. Default is true")
    private Boolean internal = true;
    @JsonProperty("Labels")
    private Map<String, String> labels;

    /**
     * The Id for this network
     * 
     */
    @JsonProperty("Id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * The Id for this network
     * 
     */
    @JsonProperty("Id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public PortainerNetworkReference withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * The Name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * The Name for this network
     * (Required)
     * 
     */
    @JsonProperty("Name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public PortainerNetworkReference withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    public java.lang.String getDriver() {
        return driver;
    }

    /**
     * The driver for this network (defaults to bridge)
     * (Required)
     * 
     */
    @JsonProperty("Driver")
    public void setDriver(java.lang.String driver) {
        this.driver = driver;
    }

    public PortainerNetworkReference withDriver(java.lang.String driver) {
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

    public PortainerNetworkReference withInternal(Boolean internal) {
        this.internal = internal;
        return this;
    }

    @JsonProperty("Labels")
    public Map<String, String> getLabels() {
        return labels;
    }

    @JsonProperty("Labels")
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public PortainerNetworkReference withLabels(Map<String, String> labels) {
        this.labels = labels;
        return this;
    }

}
