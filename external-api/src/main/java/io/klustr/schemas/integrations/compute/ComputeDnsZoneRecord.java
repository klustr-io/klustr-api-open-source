
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeDnsZoneRecord
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "content",
    "disabled"
})
@Generated("jsonschema2pojo")
public class ComputeDnsZoneRecord {

    @JsonProperty("content")
    private String content;
    @JsonProperty("disabled")
    private Boolean disabled;

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    public ComputeDnsZoneRecord withContent(String content) {
        this.content = content;
        return this;
    }

    @JsonProperty("disabled")
    public Boolean getDisabled() {
        return disabled;
    }

    @JsonProperty("disabled")
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public ComputeDnsZoneRecord withDisabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

}
