
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectTemplateRestrictions
 * <p>
 * Any restrictions such as limiting the number of instances allowed to be provisioned. For example, an organization may only have 1 pushgateway or 1 IDP provider etc.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "limit_one_per_org"
})
@Generated("jsonschema2pojo")
public class ProjectTemplateRestrictions {

    /**
     * Indicates that this template can only be issued to one organization.
     * 
     */
    @JsonProperty("limit_one_per_org")
    @JsonPropertyDescription("Indicates that this template can only be issued to one organization.")
    private Boolean limitOnePerOrg;

    /**
     * Indicates that this template can only be issued to one organization.
     * 
     */
    @JsonProperty("limit_one_per_org")
    public Boolean getLimitOnePerOrg() {
        return limitOnePerOrg;
    }

    /**
     * Indicates that this template can only be issued to one organization.
     * 
     */
    @JsonProperty("limit_one_per_org")
    public void setLimitOnePerOrg(Boolean limitOnePerOrg) {
        this.limitOnePerOrg = limitOnePerOrg;
    }

    public ProjectTemplateRestrictions withLimitOnePerOrg(Boolean limitOnePerOrg) {
        this.limitOnePerOrg = limitOnePerOrg;
        return this;
    }

}
