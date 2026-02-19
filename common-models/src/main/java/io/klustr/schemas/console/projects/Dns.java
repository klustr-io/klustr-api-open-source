
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Requires that DNS be configured and setup for this. Assumes {project}.dev.klustr.io
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "zone",
    "name"
})
@Generated("jsonschema2pojo")
public class Dns {

    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("zone")
    @JsonPropertyDescription("The zone that this DNS will provision under.")
    private String zone = "dev.klustr.io.";
    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The zone that this DNS will provision under.")
    private String name = "myproject.dev.klustr.io.";

    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("zone")
    public String getZone() {
        return zone;
    }

    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("zone")
    public void setZone(String zone) {
        this.zone = zone;
    }

    public Dns withZone(String zone) {
        this.zone = zone;
        return this;
    }

    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The zone that this DNS will provision under.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Dns withName(String name) {
        this.name = name;
        return this;
    }

}
