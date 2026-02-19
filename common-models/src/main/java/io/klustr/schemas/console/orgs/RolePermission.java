
package io.klustr.schemas.console.orgs;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * RolePermission
 * <p>
 * The permission attributes
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "scope"
})
@Generated("jsonschema2pojo")
public class RolePermission {

    /**
     * The permission such as apikeys.keys.create, apikeys.keys.delete, apikeys.keys.list.
     * 
     */
    @JsonProperty("scope")
    @JsonPropertyDescription("The permission such as apikeys.keys.create, apikeys.keys.delete, apikeys.keys.list.")
    private String scope;

    /**
     * The permission such as apikeys.keys.create, apikeys.keys.delete, apikeys.keys.list.
     * 
     */
    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    /**
     * The permission such as apikeys.keys.create, apikeys.keys.delete, apikeys.keys.list.
     * 
     */
    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public RolePermission withScope(String scope) {
        this.scope = scope;
        return this;
    }

}
