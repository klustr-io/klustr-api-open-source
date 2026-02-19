
package io.klustr.schemas.console.agreements;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "role"
})
@Generated("jsonschema2pojo")
public class Approver {

    /**
     * The role that an approvers must have.
     * 
     */
    @JsonProperty("role")
    @JsonPropertyDescription("The role that an approvers must have.")
    private String role;

    /**
     * The role that an approvers must have.
     * 
     */
    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    /**
     * The role that an approvers must have.
     * 
     */
    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    public Approver withRole(String role) {
        this.role = role;
        return this;
    }

}
