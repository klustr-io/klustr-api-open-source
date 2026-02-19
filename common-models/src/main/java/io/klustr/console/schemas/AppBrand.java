
package io.klustr.console.schemas;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AppBrand
 * <p>
 * The brand information for this application.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "logo"
})
@Generated("jsonschema2pojo")
public class AppBrand {

    /**
     * Name of this application which would be potentially used in consent screens.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Name of this application which would be potentially used in consent screens.")
    private String name;
    /**
     * The logo for this application as presented in the application
     * 
     */
    @JsonProperty("logo")
    @JsonPropertyDescription("The logo for this application as presented in the application")
    private Logo logo;

    /**
     * Name of this application which would be potentially used in consent screens.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name of this application which would be potentially used in consent screens.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public AppBrand withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The logo for this application as presented in the application
     * 
     */
    @JsonProperty("logo")
    public Logo getLogo() {
        return logo;
    }

    /**
     * The logo for this application as presented in the application
     * 
     */
    @JsonProperty("logo")
    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public AppBrand withLogo(Logo logo) {
        this.logo = logo;
        return this;
    }

}
