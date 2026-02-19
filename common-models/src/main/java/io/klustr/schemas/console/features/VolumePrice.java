
package io.klustr.schemas.console.features;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VolumePrice
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rage"
})
@Generated("jsonschema2pojo")
public class VolumePrice {

    @JsonProperty("rage")
    private List<VolumePriceRange> rage = new ArrayList<VolumePriceRange>();

    @JsonProperty("rage")
    public List<VolumePriceRange> getRage() {
        return rage;
    }

    @JsonProperty("rage")
    public void setRage(List<VolumePriceRange> rage) {
        this.rage = rage;
    }

    public VolumePrice withRage(List<VolumePriceRange> rage) {
        this.rage = rage;
        return this;
    }

}
