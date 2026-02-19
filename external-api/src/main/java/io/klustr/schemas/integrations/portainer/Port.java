
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "IP",
    "PrivatePort",
    "PublicPort",
    "Type"
})
@Generated("jsonschema2pojo")
public class Port {

    @JsonProperty("IP")
    private String ip;
    @JsonProperty("PrivatePort")
    private Integer privatePort;
    @JsonProperty("PublicPort")
    private Integer publicPort;
    @JsonProperty("Type")
    private String type;

    @JsonProperty("IP")
    public String getIp() {
        return ip;
    }

    @JsonProperty("IP")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Port withIp(String ip) {
        this.ip = ip;
        return this;
    }

    @JsonProperty("PrivatePort")
    public Integer getPrivatePort() {
        return privatePort;
    }

    @JsonProperty("PrivatePort")
    public void setPrivatePort(Integer privatePort) {
        this.privatePort = privatePort;
    }

    public Port withPrivatePort(Integer privatePort) {
        this.privatePort = privatePort;
        return this;
    }

    @JsonProperty("PublicPort")
    public Integer getPublicPort() {
        return publicPort;
    }

    @JsonProperty("PublicPort")
    public void setPublicPort(Integer publicPort) {
        this.publicPort = publicPort;
    }

    public Port withPublicPort(Integer publicPort) {
        this.publicPort = publicPort;
        return this;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    public Port withType(String type) {
        this.type = type;
        return this;
    }

}
