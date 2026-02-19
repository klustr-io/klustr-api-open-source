
package io.klustr.schemas.console.projects;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * O11yEndpoint
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "labels"
})
@Generated("jsonschema2pojo")
public class O11yEndpoint {

    /**
     * The endpoint url with replaceable symbols
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The endpoint url with replaceable symbols")
    private String url;
    /**
     * Any custom labels to apply to this o11y for dimensions
     * 
     */
    @JsonProperty("labels")
    @JsonPropertyDescription("Any custom labels to apply to this o11y for dimensions")
    private List<O11yEndpointLabel> labels = new ArrayList<O11yEndpointLabel>();

    /**
     * The endpoint url with replaceable symbols
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The endpoint url with replaceable symbols
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public O11yEndpoint withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Any custom labels to apply to this o11y for dimensions
     * 
     */
    @JsonProperty("labels")
    public List<O11yEndpointLabel> getLabels() {
        return labels;
    }

    /**
     * Any custom labels to apply to this o11y for dimensions
     * 
     */
    @JsonProperty("labels")
    public void setLabels(List<O11yEndpointLabel> labels) {
        this.labels = labels;
    }

    public O11yEndpoint withLabels(List<O11yEndpointLabel> labels) {
        this.labels = labels;
        return this;
    }

}
