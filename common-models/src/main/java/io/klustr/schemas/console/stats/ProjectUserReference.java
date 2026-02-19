
package io.klustr.schemas.console.stats;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.identity.IdentityMetadataPublic;
import io.klustr.schemas.console.identity.IdentityTraits;
import org.joda.time.DateTime;


/**
 * ProjectUserReference
 * <p>
 * A reference to a user of a project
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "total_visits",
    "first_access",
    "last_access",
    "traits",
    "metadata_public"
})
@Generated("jsonschema2pojo")
public class ProjectUserReference {

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the user")
    private String id;
    /**
     * The total number of times a user visited.
     * 
     */
    @JsonProperty("total_visits")
    @JsonPropertyDescription("The total number of times a user visited.")
    private Double totalVisits;
    /**
     * The date the user first accessed.
     * 
     */
    @JsonProperty("first_access")
    @JsonPropertyDescription("The date the user first accessed.")
    private DateTime firstAccess;
    /**
     * The date the user last accessed.
     * 
     */
    @JsonProperty("last_access")
    @JsonPropertyDescription("The date the user last accessed.")
    private DateTime lastAccess;
    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("traits")
    @JsonPropertyDescription("Traits and meta data related to the user")
    private IdentityTraits traits;
    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("metadata_public")
    @JsonPropertyDescription("Traits and meta data related to the user")
    private IdentityMetadataPublic metadataPublic;

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of the user
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectUserReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The total number of times a user visited.
     * 
     */
    @JsonProperty("total_visits")
    public Double getTotalVisits() {
        return totalVisits;
    }

    /**
     * The total number of times a user visited.
     * 
     */
    @JsonProperty("total_visits")
    public void setTotalVisits(Double totalVisits) {
        this.totalVisits = totalVisits;
    }

    public ProjectUserReference withTotalVisits(Double totalVisits) {
        this.totalVisits = totalVisits;
        return this;
    }

    /**
     * The date the user first accessed.
     * 
     */
    @JsonProperty("first_access")
    public DateTime getFirstAccess() {
        return firstAccess;
    }

    /**
     * The date the user first accessed.
     * 
     */
    @JsonProperty("first_access")
    public void setFirstAccess(DateTime firstAccess) {
        this.firstAccess = firstAccess;
    }

    public ProjectUserReference withFirstAccess(DateTime firstAccess) {
        this.firstAccess = firstAccess;
        return this;
    }

    /**
     * The date the user last accessed.
     * 
     */
    @JsonProperty("last_access")
    public DateTime getLastAccess() {
        return lastAccess;
    }

    /**
     * The date the user last accessed.
     * 
     */
    @JsonProperty("last_access")
    public void setLastAccess(DateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public ProjectUserReference withLastAccess(DateTime lastAccess) {
        this.lastAccess = lastAccess;
        return this;
    }

    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("traits")
    public IdentityTraits getTraits() {
        return traits;
    }

    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("traits")
    public void setTraits(IdentityTraits traits) {
        this.traits = traits;
    }

    public ProjectUserReference withTraits(IdentityTraits traits) {
        this.traits = traits;
        return this;
    }

    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("metadata_public")
    public IdentityMetadataPublic getMetadataPublic() {
        return metadataPublic;
    }

    /**
     * Traits and meta data related to the user
     * 
     */
    @JsonProperty("metadata_public")
    public void setMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
    }

    public ProjectUserReference withMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
        return this;
    }

}
