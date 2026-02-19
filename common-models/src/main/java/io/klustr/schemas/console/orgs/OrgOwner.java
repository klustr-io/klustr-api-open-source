
package io.klustr.schemas.console.orgs;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * OrgOwner
 * <p>
 * The singular administrator and owner of this organization.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id"
})
@Generated("jsonschema2pojo")
public class OrgOwner {

    /**
     * The subject identifier for the owner of this organization
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The subject identifier for the owner of this organization")
    private String id;

    /**
     * The subject identifier for the owner of this organization
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The subject identifier for the owner of this organization
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OrgOwner withId(String id) {
        this.id = id;
        return this;
    }

}
