
package io.klustr.schemas.integrations.pdns;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.integrations.compute.ComputeDnsZone;


/**
 * PdnsZone
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "kind",
    "name",
    "rrsets"
})
@Generated("jsonschema2pojo")
public class PdnsZone {

    @JsonProperty("id")
    private String id;
    @JsonProperty("kind")
    private String kind = "Master";
    @JsonProperty("name")
    private String name;
    @JsonProperty("rrsets")
    private List<ComputeDnsZone> rrsets = new ArrayList<ComputeDnsZone>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public PdnsZone withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    public PdnsZone withKind(String kind) {
        this.kind = kind;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public PdnsZone withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("rrsets")
    public List<ComputeDnsZone> getRrsets() {
        return rrsets;
    }

    @JsonProperty("rrsets")
    public void setRrsets(List<ComputeDnsZone> rrsets) {
        this.rrsets = rrsets;
    }

    public PdnsZone withRrsets(List<ComputeDnsZone> rrsets) {
        this.rrsets = rrsets;
        return this;
    }

}
