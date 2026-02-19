
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name"
})
@Generated("jsonschema2pojo")
public class Owner {

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the user")
    private Integer id;
    /**
     * The display name of the user
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name of the user")
    private String name;

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Owner withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * The display name of the user
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name of the user
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Owner withName(String name) {
        this.name = name;
        return this;
    }

}
