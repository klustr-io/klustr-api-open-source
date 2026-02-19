
package io.klustr.schemas.console.features.core;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProvisioningVolumeSpec
 * <p>
 * A single volume mount definition.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "target",
    "read_only"
})
@Generated("jsonschema2pojo")
public class ProvisioningVolumeSpec {

    /**
     * Source volume name or host path.
     * (Required)
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("Source volume name or host path.")
    private String source;
    /**
     * Target path inside the container.
     * (Required)
     * 
     */
    @JsonProperty("target")
    @JsonPropertyDescription("Target path inside the container.")
    private String target;
    /**
     * Whether the volume should be mounted read-only.
     * 
     */
    @JsonProperty("read_only")
    @JsonPropertyDescription("Whether the volume should be mounted read-only.")
    private Boolean readOnly;

    /**
     * Source volume name or host path.
     * (Required)
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * Source volume name or host path.
     * (Required)
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public ProvisioningVolumeSpec withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * Target path inside the container.
     * (Required)
     * 
     */
    @JsonProperty("target")
    public String getTarget() {
        return target;
    }

    /**
     * Target path inside the container.
     * (Required)
     * 
     */
    @JsonProperty("target")
    public void setTarget(String target) {
        this.target = target;
    }

    public ProvisioningVolumeSpec withTarget(String target) {
        this.target = target;
        return this;
    }

    /**
     * Whether the volume should be mounted read-only.
     * 
     */
    @JsonProperty("read_only")
    public Boolean getReadOnly() {
        return readOnly;
    }

    /**
     * Whether the volume should be mounted read-only.
     * 
     */
    @JsonProperty("read_only")
    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public ProvisioningVolumeSpec withReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

}
