
package io.klustr.schemas.console.orgs;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * OrgRoles
 * <p>
 * An organizational unit.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "roles"
})
@Generated("jsonschema2pojo")
public class OrgRoles {

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this organization")
    private String id;
    /**
     * The roles defined for this organization
     * 
     */
    @JsonProperty("roles")
    @JsonPropertyDescription("The roles defined for this organization")
    private List<Role> roles = new ArrayList<Role>();

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OrgRoles withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The roles defined for this organization
     * 
     */
    @JsonProperty("roles")
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * The roles defined for this organization
     * 
     */
    @JsonProperty("roles")
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public OrgRoles withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

}
