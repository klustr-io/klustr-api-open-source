
package io.klustr.schemas.console.features.core;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProvisioningSpec
 * <p>
 * Provisioning hints for deploying the feature.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "image",
    "manifest_filename",
    "default_machine_type",
    "volumes"
})
@Generated("jsonschema2pojo")
public class ProvisioningSpec {

    /**
     * Container image.
     * 
     */
    @JsonProperty("image")
    @JsonPropertyDescription("Container image.")
    private String image;
    /**
     * Compose/manifest template filename.
     * 
     */
    @JsonProperty("manifest_filename")
    @JsonPropertyDescription("Compose/manifest template filename.")
    private String manifestFilename;
    /**
     * Default machine type.
     * 
     */
    @JsonProperty("default_machine_type")
    @JsonPropertyDescription("Default machine type.")
    private String defaultMachineType;
    /**
     * Volume mounts attached to the container.
     * 
     */
    @JsonProperty("volumes")
    @JsonPropertyDescription("Volume mounts attached to the container.")
    private List<ProvisioningVolumeSpec> volumes = new ArrayList<ProvisioningVolumeSpec>();

    /**
     * Container image.
     * 
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     * Container image.
     * 
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    public ProvisioningSpec withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * Compose/manifest template filename.
     * 
     */
    @JsonProperty("manifest_filename")
    public String getManifestFilename() {
        return manifestFilename;
    }

    /**
     * Compose/manifest template filename.
     * 
     */
    @JsonProperty("manifest_filename")
    public void setManifestFilename(String manifestFilename) {
        this.manifestFilename = manifestFilename;
    }

    public ProvisioningSpec withManifestFilename(String manifestFilename) {
        this.manifestFilename = manifestFilename;
        return this;
    }

    /**
     * Default machine type.
     * 
     */
    @JsonProperty("default_machine_type")
    public String getDefaultMachineType() {
        return defaultMachineType;
    }

    /**
     * Default machine type.
     * 
     */
    @JsonProperty("default_machine_type")
    public void setDefaultMachineType(String defaultMachineType) {
        this.defaultMachineType = defaultMachineType;
    }

    public ProvisioningSpec withDefaultMachineType(String defaultMachineType) {
        this.defaultMachineType = defaultMachineType;
        return this;
    }

    /**
     * Volume mounts attached to the container.
     * 
     */
    @JsonProperty("volumes")
    public List<ProvisioningVolumeSpec> getVolumes() {
        return volumes;
    }

    /**
     * Volume mounts attached to the container.
     * 
     */
    @JsonProperty("volumes")
    public void setVolumes(List<ProvisioningVolumeSpec> volumes) {
        this.volumes = volumes;
    }

    public ProvisioningSpec withVolumes(List<ProvisioningVolumeSpec> volumes) {
        this.volumes = volumes;
        return this;
    }

}
