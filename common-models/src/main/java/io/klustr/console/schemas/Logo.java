
package io.klustr.console.schemas;

import java.net.URI;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The logo for this application as presented in the application
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filename",
    "url"
})
@Generated("jsonschema2pojo")
public class Logo {

    /**
     * The name of this image for reference.
     * 
     */
    @JsonProperty("filename")
    @JsonPropertyDescription("The name of this image for reference.")
    private String filename;
    /**
     * Allowed image formats are JPG, PNG, and BMP. Logos should be square and 120px by 120px for the best results.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("Allowed image formats are JPG, PNG, and BMP. Logos should be square and 120px by 120px for the best results.")
    private URI url;

    /**
     * The name of this image for reference.
     * 
     */
    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    /**
     * The name of this image for reference.
     * 
     */
    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Logo withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Allowed image formats are JPG, PNG, and BMP. Logos should be square and 120px by 120px for the best results.
     * 
     */
    @JsonProperty("url")
    public URI getUrl() {
        return url;
    }

    /**
     * Allowed image formats are JPG, PNG, and BMP. Logos should be square and 120px by 120px for the best results.
     * 
     */
    @JsonProperty("url")
    public void setUrl(URI url) {
        this.url = url;
    }

    public Logo withUrl(URI url) {
        this.url = url;
        return this;
    }

}
