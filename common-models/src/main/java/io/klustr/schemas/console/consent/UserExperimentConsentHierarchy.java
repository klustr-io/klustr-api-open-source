
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserExperimentConsentHierarchy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "experiment_id",
    "experiment_name",
    "scopes"
})
@Generated("jsonschema2pojo")
public class UserExperimentConsentHierarchy {

    /**
     * The experiment identifier for this consent.
     * 
     */
    @JsonProperty("experiment_id")
    @JsonPropertyDescription("The experiment identifier for this consent.")
    private String experimentId;
    /**
     * The application name
     * 
     */
    @JsonProperty("experiment_name")
    @JsonPropertyDescription("The application name")
    private String experimentName;
    @JsonProperty("scopes")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();

    /**
     * The experiment identifier for this consent.
     * 
     */
    @JsonProperty("experiment_id")
    public String getExperimentId() {
        return experimentId;
    }

    /**
     * The experiment identifier for this consent.
     * 
     */
    @JsonProperty("experiment_id")
    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public UserExperimentConsentHierarchy withExperimentId(String experimentId) {
        this.experimentId = experimentId;
        return this;
    }

    /**
     * The application name
     * 
     */
    @JsonProperty("experiment_name")
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * The application name
     * 
     */
    @JsonProperty("experiment_name")
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public UserExperimentConsentHierarchy withExperimentName(String experimentName) {
        this.experimentName = experimentName;
        return this;
    }

    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserExperimentConsentHierarchy withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

}
