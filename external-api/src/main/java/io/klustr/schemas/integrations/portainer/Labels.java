
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The common labels to apply for this environment
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "org_id",
    "source"
})
@Generated("jsonschema2pojo")
public class Labels {

    /**
     * The organization ID that owns this instance.
     * (Required)
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization ID that owns this instance.")
    private String orgId;
    /**
     * The source of this environment
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The source of this environment")
    private String source = "klustr.io";

    /**
     * The organization ID that owns this instance.
     * (Required)
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization ID that owns this instance.
     * (Required)
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Labels withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The source of this environment
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * The source of this environment
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public Labels withSource(String source) {
        this.source = source;
        return this;
    }

}
