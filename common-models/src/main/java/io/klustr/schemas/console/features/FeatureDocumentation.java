
package io.klustr.schemas.console.features;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * FeatureDocumentation
 * <p>
 * Specific seections of documentation
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "overview",
    "documentation",
    "support"
})
@Generated("jsonschema2pojo")
public class FeatureDocumentation {

    @JsonProperty("overview")
    private String overview;
    @JsonProperty("documentation")
    private String documentation;
    @JsonProperty("support")
    private String support;

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public FeatureDocumentation withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    @JsonProperty("documentation")
    public String getDocumentation() {
        return documentation;
    }

    @JsonProperty("documentation")
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public FeatureDocumentation withDocumentation(String documentation) {
        this.documentation = documentation;
        return this;
    }

    @JsonProperty("support")
    public String getSupport() {
        return support;
    }

    @JsonProperty("support")
    public void setSupport(String support) {
        this.support = support;
    }

    public FeatureDocumentation withSupport(String support) {
        this.support = support;
        return this;
    }

}
