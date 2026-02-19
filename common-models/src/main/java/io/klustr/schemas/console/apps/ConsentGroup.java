
package io.klustr.schemas.console.apps;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentGroup
 * <p>
 * The scope of consents requested by this application
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "scopes"
})
@Generated("jsonschema2pojo")
public class ConsentGroup {

    /**
     * The unique ID of this consent group (optional) if linked to application.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this consent group (optional) if linked to application.")
    private String id;
    @JsonProperty("scopes")
    private List<ConsentScope> scopes = new ArrayList<ConsentScope>();

    /**
     * The unique ID of this consent group (optional) if linked to application.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this consent group (optional) if linked to application.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentGroup withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("scopes")
    public List<ConsentScope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<ConsentScope> scopes) {
        this.scopes = scopes;
    }

    public ConsentGroup withScopes(List<ConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

}
