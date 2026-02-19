
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectOwner
 * <p>
 * The owner for this project
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id"
})
@Generated("jsonschema2pojo")
public class ProjectOwner {

    /**
     * The unique identifier for the owner
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique identifier for the owner")
    private String id;

    /**
     * The unique identifier for the owner
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique identifier for the owner
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectOwner withId(String id) {
        this.id = id;
        return this;
    }

}
