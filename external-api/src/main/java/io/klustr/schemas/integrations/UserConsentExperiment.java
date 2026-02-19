
package io.klustr.schemas.integrations;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * UserConsentExperiment
 * <p>
 * Record of a users consent to an experiment
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "first_seen_date",
    "last_seen_date",
    "metadata"
})
@Generated("jsonschema2pojo")
public class UserConsentExperiment {

    /**
     * THe ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("THe ID of the experiment that was consented to.")
    private String id;
    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    @JsonPropertyDescription("The first consent date this experiment was last consented to.")
    private DateTime firstSeenDate;
    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    @JsonPropertyDescription("The last consent date this experiment was last consented to.")
    private DateTime lastSeenDate;
    /**
     * UserConsentExperimentReference
     * <p>
     * The metadata for display
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("The metadata for display")
    private UserConsentExperimentReference metadata;

    /**
     * THe ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * THe ID of the experiment that was consented to.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserConsentExperiment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    public DateTime getFirstSeenDate() {
        return firstSeenDate;
    }

    /**
     * The first consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("first_seen_date")
    public void setFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
    }

    public UserConsentExperiment withFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
        return this;
    }

    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    public DateTime getLastSeenDate() {
        return lastSeenDate;
    }

    /**
     * The last consent date this experiment was last consented to.
     * 
     */
    @JsonProperty("last_seen_date")
    public void setLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public UserConsentExperiment withLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
        return this;
    }

    /**
     * UserConsentExperimentReference
     * <p>
     * The metadata for display
     * 
     */
    @JsonProperty("metadata")
    public UserConsentExperimentReference getMetadata() {
        return metadata;
    }

    /**
     * UserConsentExperimentReference
     * <p>
     * The metadata for display
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(UserConsentExperimentReference metadata) {
        this.metadata = metadata;
    }

    public UserConsentExperiment withMetadata(UserConsentExperimentReference metadata) {
        this.metadata = metadata;
        return this;
    }

}
