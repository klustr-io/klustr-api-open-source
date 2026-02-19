
package io.klustr.schemas.console;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * EntityReference
 * <p>
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#commonhits
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name"
})
@Generated("jsonschema2pojo")
public class EntityReference {

    /**
     * The id of the entity.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The id of the entity.")
    private String id;
    /**
     * The display name for this entity.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The display name for this entity.")
    private String name;

    /**
     * The id of the entity.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The id of the entity.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public EntityReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The display name for this entity.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The display name for this entity.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public EntityReference withName(String name) {
        this.name = name;
        return this;
    }

}
