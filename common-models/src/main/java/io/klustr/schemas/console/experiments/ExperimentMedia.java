
package io.klustr.schemas.console.experiments;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ExperimentMedia
 * <p>
 * The media associated with this experiment for display to a user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hero_image_url"
})
@Generated("jsonschema2pojo")
public class ExperimentMedia {

    /**
     * The URL of the image used when displaying the hero of this experiment
     * 
     */
    @JsonProperty("hero_image_url")
    @JsonPropertyDescription("The URL of the image used when displaying the hero of this experiment")
    private String heroImageUrl;

    /**
     * The URL of the image used when displaying the hero of this experiment
     * 
     */
    @JsonProperty("hero_image_url")
    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    /**
     * The URL of the image used when displaying the hero of this experiment
     * 
     */
    @JsonProperty("hero_image_url")
    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public ExperimentMedia withHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
        return this;
    }

}
