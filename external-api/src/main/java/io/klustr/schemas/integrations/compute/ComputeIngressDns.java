
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeIngressDns
 * <p>
 * The DNS related to this compute instance
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "active",
    "zone",
    "name",
    "ip"
})
@Generated("jsonschema2pojo")
public class ComputeIngressDns {

    /**
     * If this DNS is active.
     * 
     */
    @JsonProperty("active")
    @JsonPropertyDescription("If this DNS is active.")
    private Boolean active;
    /**
     * The zone that this compute is bound to.
     * 
     */
    @JsonProperty("zone")
    @JsonPropertyDescription("The zone that this compute is bound to.")
    private String zone;
    /**
     * The name for this dns .
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this dns .")
    private String name;
    /**
     * ComputeIngressDnsIpRecord
     * <p>
     * 
     * 
     */
    @JsonProperty("ip")
    private ComputeIngressDnsIpRecord ip;

    /**
     * If this DNS is active.
     * 
     */
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     * If this DNS is active.
     * 
     */
    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    public ComputeIngressDns withActive(Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * The zone that this compute is bound to.
     * 
     */
    @JsonProperty("zone")
    public String getZone() {
        return zone;
    }

    /**
     * The zone that this compute is bound to.
     * 
     */
    @JsonProperty("zone")
    public void setZone(String zone) {
        this.zone = zone;
    }

    public ComputeIngressDns withZone(String zone) {
        this.zone = zone;
        return this;
    }

    /**
     * The name for this dns .
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this dns .
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ComputeIngressDns withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * ComputeIngressDnsIpRecord
     * <p>
     * 
     * 
     */
    @JsonProperty("ip")
    public ComputeIngressDnsIpRecord getIp() {
        return ip;
    }

    /**
     * ComputeIngressDnsIpRecord
     * <p>
     * 
     * 
     */
    @JsonProperty("ip")
    public void setIp(ComputeIngressDnsIpRecord ip) {
        this.ip = ip;
    }

    public ComputeIngressDns withIp(ComputeIngressDnsIpRecord ip) {
        this.ip = ip;
        return this;
    }

}
