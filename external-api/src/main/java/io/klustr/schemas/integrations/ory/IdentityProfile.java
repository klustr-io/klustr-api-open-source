
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.identity.IdentityMetadataPublic;
import io.klustr.schemas.console.identity.IdentityTraits;


/**
 * IdentityProfile
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "traits",
    "metadata_public"
})
@Generated("jsonschema2pojo")
public class IdentityProfile {

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this identity.")
    private String id;
    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    @JsonPropertyDescription("The traits of this individual")
    private IdentityTraits traits;
    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    private IdentityMetadataPublic metadataPublic;

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public IdentityProfile withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    public IdentityTraits getTraits() {
        return traits;
    }

    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    public void setTraits(IdentityTraits traits) {
        this.traits = traits;
    }

    public IdentityProfile withTraits(IdentityTraits traits) {
        this.traits = traits;
        return this;
    }

    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    public IdentityMetadataPublic getMetadataPublic() {
        return metadataPublic;
    }

    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    public void setMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
    }

    public IdentityProfile withMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
        return this;
    }

}
