
package io.klustr.schemas.persons.veriff;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffValue
 * <p>
 * The value of a property from veriff which would contain the value and confidence score.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "confidenceCategory",
    "value",
    "sources"
})
@Generated("jsonschema2pojo")
public class VeriffValue {

    @JsonProperty("confidenceCategory")
    private String confidenceCategory;
    @JsonProperty("value")
    private String value;
    @JsonProperty("sources")
    private List<String> sources = new ArrayList<String>();

    @JsonProperty("confidenceCategory")
    public String getConfidenceCategory() {
        return confidenceCategory;
    }

    @JsonProperty("confidenceCategory")
    public void setConfidenceCategory(String confidenceCategory) {
        this.confidenceCategory = confidenceCategory;
    }

    public VeriffValue withConfidenceCategory(String confidenceCategory) {
        this.confidenceCategory = confidenceCategory;
        return this;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public VeriffValue withValue(String value) {
        this.value = value;
        return this;
    }

    @JsonProperty("sources")
    public List<String> getSources() {
        return sources;
    }

    @JsonProperty("sources")
    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public VeriffValue withSources(List<String> sources) {
        this.sources = sources;
        return this;
    }

}
