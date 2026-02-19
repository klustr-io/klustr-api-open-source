
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "value"
})
@Generated("jsonschema2pojo")
public class O11yEndpointLabel {

    /**
     * The key for this label
     * 
     */
    @JsonProperty("key")
    @JsonPropertyDescription("The key for this label")
    private String key;
    /**
     * The value for this label
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The value for this label")
    private String value;

    /**
     * The key for this label
     * 
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * The key for this label
     * 
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    public O11yEndpointLabel withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * The value for this label
     * 
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * The value for this label
     * 
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public O11yEndpointLabel withValue(String value) {
        this.value = value;
        return this;
    }

}
