
package io.klustr.schemas.integrations.compute;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeDnsZoneRequest
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "records",
    "name",
    "changetype",
    "ttl",
    "type"
})
@Generated("jsonschema2pojo")
public class ComputeDnsZoneRequest {

    @JsonProperty("records")
    private List<ComputeDnsZoneRecord> records = new ArrayList<ComputeDnsZoneRecord>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("changetype")
    private String changetype = "REPLACE";
    @JsonProperty("ttl")
    private Integer ttl;
    @JsonProperty("type")
    private String type;

    @JsonProperty("records")
    public List<ComputeDnsZoneRecord> getRecords() {
        return records;
    }

    @JsonProperty("records")
    public void setRecords(List<ComputeDnsZoneRecord> records) {
        this.records = records;
    }

    public ComputeDnsZoneRequest withRecords(List<ComputeDnsZoneRecord> records) {
        this.records = records;
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

    public ComputeDnsZoneRequest withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("changetype")
    public String getChangetype() {
        return changetype;
    }

    @JsonProperty("changetype")
    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    public ComputeDnsZoneRequest withChangetype(String changetype) {
        this.changetype = changetype;
        return this;
    }

    @JsonProperty("ttl")
    public Integer getTtl() {
        return ttl;
    }

    @JsonProperty("ttl")
    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public ComputeDnsZoneRequest withTtl(Integer ttl) {
        this.ttl = ttl;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ComputeDnsZoneRequest withType(String type) {
        this.type = type;
        return this;
    }

}
