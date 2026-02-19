
package io.klustr.schemas.integrations.pdns;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PdnsZoneCreateRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "kind"
})
@Generated("jsonschema2pojo")
public class PdnsZoneCreateRequest {

    /**
     * The name for this zone for example example.org
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this zone for example example.org")
    private String name;
    /**
     * Kind of zone file to create.
     * 
     */
    @JsonProperty("kind")
    @JsonPropertyDescription("Kind of zone file to create.")
    private String kind = "Master";

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

    public PdnsZoneCreateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Kind of zone file to create.
     * 
     */
    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    /**
     * Kind of zone file to create.
     * 
     */
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    public PdnsZoneCreateRequest withKind(String kind) {
        this.kind = kind;
        return this;
    }

}
