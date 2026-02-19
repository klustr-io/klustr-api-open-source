
package io.klustr.schemas.console;

import java.net.URI;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AppLinks
 * <p>
 * Links referenced by the author for linking to consent.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "home",
    "privacy",
    "tos"
})
@Generated("jsonschema2pojo")
public class AppLinks {

    /**
     * The url for the home page of this application.
     * 
     */
    @JsonProperty("home")
    @JsonPropertyDescription("The url for the home page of this application.")
    private URI home;
    /**
     * The url for the privacy policy of this application
     * 
     */
    @JsonProperty("privacy")
    @JsonPropertyDescription("The url for the privacy policy of this application")
    private URI privacy;
    /**
     * Link to the terms of service for this application.
     * 
     */
    @JsonProperty("tos")
    @JsonPropertyDescription("Link to the terms of service for this application.")
    private URI tos;

    /**
     * The url for the home page of this application.
     * 
     */
    @JsonProperty("home")
    public URI getHome() {
        return home;
    }

    /**
     * The url for the home page of this application.
     * 
     */
    @JsonProperty("home")
    public void setHome(URI home) {
        this.home = home;
    }

    public AppLinks withHome(URI home) {
        this.home = home;
        return this;
    }

    /**
     * The url for the privacy policy of this application
     * 
     */
    @JsonProperty("privacy")
    public URI getPrivacy() {
        return privacy;
    }

    /**
     * The url for the privacy policy of this application
     * 
     */
    @JsonProperty("privacy")
    public void setPrivacy(URI privacy) {
        this.privacy = privacy;
    }

    public AppLinks withPrivacy(URI privacy) {
        this.privacy = privacy;
        return this;
    }

    /**
     * Link to the terms of service for this application.
     * 
     */
    @JsonProperty("tos")
    public URI getTos() {
        return tos;
    }

    /**
     * Link to the terms of service for this application.
     * 
     */
    @JsonProperty("tos")
    public void setTos(URI tos) {
        this.tos = tos;
    }

    public AppLinks withTos(URI tos) {
        this.tos = tos;
        return this;
    }

}
