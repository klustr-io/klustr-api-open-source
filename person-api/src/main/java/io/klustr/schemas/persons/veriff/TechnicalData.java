
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Reference and technical data about this verification
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ip"
})
@Generated("jsonschema2pojo")
public class TechnicalData {

    /**
     * The origin IP for this verification
     * 
     */
    @JsonProperty("ip")
    @JsonPropertyDescription("The origin IP for this verification")
    private String ip;

    /**
     * The origin IP for this verification
     * 
     */
    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    /**
     * The origin IP for this verification
     * 
     */
    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public TechnicalData withIp(String ip) {
        this.ip = ip;
        return this;
    }

}
