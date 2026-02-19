
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeDnsZone
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name"
})
@Generated("jsonschema2pojo")
public class ComputeDnsZone {

    /**
     * The name for this zone for example example.org
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this zone for example example.org")
    private String name;

    /**
     * The name for this zone for example example.org
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this zone for example example.org
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ComputeDnsZone withName(String name) {
        this.name = name;
        return this;
    }

}
