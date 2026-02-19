
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * LoadBalancerFrontendBindResource
 * <p>
 * A frontend for the loadbalancer for serving content
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crt_list",
    "ssl",
    "name",
    "address"
})
@Generated("jsonschema2pojo")
public class LoadBalancerFrontendBindResource {

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("crt_list")
    @JsonPropertyDescription("The name of the front end.")
    private String crtList;
    /**
     * SSL enabled
     * 
     */
    @JsonProperty("ssl")
    @JsonPropertyDescription("SSL enabled")
    private Boolean ssl;
    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Name wiith *:<port> as convention")
    private String name;
    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("Name wiith *:<port> as convention")
    private String address;

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("crt_list")
    public String getCrtList() {
        return crtList;
    }

    /**
     * The name of the front end.
     * 
     */
    @JsonProperty("crt_list")
    public void setCrtList(String crtList) {
        this.crtList = crtList;
    }

    public LoadBalancerFrontendBindResource withCrtList(String crtList) {
        this.crtList = crtList;
        return this;
    }

    /**
     * SSL enabled
     * 
     */
    @JsonProperty("ssl")
    public Boolean getSsl() {
        return ssl;
    }

    /**
     * SSL enabled
     * 
     */
    @JsonProperty("ssl")
    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public LoadBalancerFrontendBindResource withSsl(Boolean ssl) {
        this.ssl = ssl;
        return this;
    }

    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public LoadBalancerFrontendBindResource withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    /**
     * Name wiith *:<port> as convention
     * 
     */
    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    public LoadBalancerFrontendBindResource withAddress(String address) {
        this.address = address;
        return this;
    }

}
