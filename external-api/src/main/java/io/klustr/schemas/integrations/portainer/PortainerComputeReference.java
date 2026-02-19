
package io.klustr.schemas.integrations.portainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PortainerComputeReference
 * <p>
 * A reference to a compute instance
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Id",
    "Image",
    "Created",
    "ImageID",
    "State",
    "Status",
    "Ports",
    "Labels"
})
@Generated("jsonschema2pojo")
public class PortainerComputeReference {

    @JsonProperty("Id")
    private java.lang.String id;
    @JsonProperty("Image")
    private java.lang.String image;
    @JsonProperty("Created")
    private Integer created;
    @JsonProperty("ImageID")
    private java.lang.String imageID;
    @JsonProperty("State")
    private java.lang.String state;
    @JsonProperty("Status")
    private java.lang.String status;
    @JsonProperty("Ports")
    private List<Port> ports = new ArrayList<Port>();
    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Id")
    public java.lang.String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public PortainerComputeReference withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("Image")
    public java.lang.String getImage() {
        return image;
    }

    @JsonProperty("Image")
    public void setImage(java.lang.String image) {
        this.image = image;
    }

    public PortainerComputeReference withImage(java.lang.String image) {
        this.image = image;
        return this;
    }

    @JsonProperty("Created")
    public Integer getCreated() {
        return created;
    }

    @JsonProperty("Created")
    public void setCreated(Integer created) {
        this.created = created;
    }

    public PortainerComputeReference withCreated(Integer created) {
        this.created = created;
        return this;
    }

    @JsonProperty("ImageID")
    public java.lang.String getImageID() {
        return imageID;
    }

    @JsonProperty("ImageID")
    public void setImageID(java.lang.String imageID) {
        this.imageID = imageID;
    }

    public PortainerComputeReference withImageID(java.lang.String imageID) {
        this.imageID = imageID;
        return this;
    }

    @JsonProperty("State")
    public java.lang.String getState() {
        return state;
    }

    @JsonProperty("State")
    public void setState(java.lang.String state) {
        this.state = state;
    }

    public PortainerComputeReference withState(java.lang.String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("Status")
    public java.lang.String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    public PortainerComputeReference withStatus(java.lang.String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("Ports")
    public List<Port> getPorts() {
        return ports;
    }

    @JsonProperty("Ports")
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public PortainerComputeReference withPorts(List<Port> ports) {
        this.ports = ports;
        return this;
    }

    @JsonProperty("Labels")
    public Map<String, String> getLabels() {
        return labels;
    }

    @JsonProperty("Labels")
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public PortainerComputeReference withLabels(Map<String, String> labels) {
        this.labels = labels;
        return this;
    }

}
