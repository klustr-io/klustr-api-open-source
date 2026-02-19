
package io.klustr.schemas.console.features.dns;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureDnsConfiguration
 * <p>
 * DNS capability required to expose network endpoints.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "template_value"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureDnsConfiguration {

    /**
     * DNS record name template.
     * 
     */
    @JsonProperty("template_value")
    @JsonPropertyDescription("DNS record name template.")
    private String templateValue;

    /**
     * DNS record name template.
     * 
     */
    @JsonProperty("template_value")
    public String getTemplateValue() {
        return templateValue;
    }

    /**
     * DNS record name template.
     * 
     */
    @JsonProperty("template_value")
    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public ProjectFeatureDnsConfiguration withTemplateValue(String templateValue) {
        this.templateValue = templateValue;
        return this;
    }

}
