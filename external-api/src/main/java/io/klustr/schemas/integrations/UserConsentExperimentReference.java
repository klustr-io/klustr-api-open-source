
package io.klustr.schemas.integrations;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserConsentExperimentReference
 * <p>
 * The metadata for display
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "scopes"
})
@Generated("jsonschema2pojo")
public class UserConsentExperimentReference {

    /**
     * THe name of this experiment.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("THe name of this experiment.")
    private String name;
    /**
     * The description of this experiment.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of this experiment.")
    private String description;
    /**
     * The scopes that are associated to this experiment.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes that are associated to this experiment.")
    private List<String> scopes = new ArrayList<String>();

    /**
     * THe name of this experiment.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * THe name of this experiment.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UserConsentExperimentReference withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The description of this experiment.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description of this experiment.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public UserConsentExperimentReference withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The scopes that are associated to this experiment.
     * 
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * The scopes that are associated to this experiment.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public UserConsentExperimentReference withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

}
