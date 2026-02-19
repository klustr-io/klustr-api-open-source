
package io.klustr.schemas.console.experiments;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ExperimentScope
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id"
})
@Generated("jsonschema2pojo")
public class ExperimentScope {

    /**
     * The scope ID
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The scope ID")
    private String id;

    /**
     * The scope ID
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The scope ID
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ExperimentScope withId(String id) {
        this.id = id;
        return this;
    }

}
