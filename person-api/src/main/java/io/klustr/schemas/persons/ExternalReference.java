
package io.klustr.schemas.persons;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * ExternalReference
 * <p>
 * Linkage information to other systems or references related to the person.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "external_id",
    "source",
    "creation_date",
    "expiration_date",
    "update_date"
})
@Generated("jsonschema2pojo")
public class ExternalReference {

    /**
     * The unique ID within this system.
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The unique ID within this system.")
    private String externalId;
    /**
     * The source of this external reference if applicable.
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The source of this external reference if applicable.")
    private String source;
    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date of birth for this person")
    private DateTime creationDate;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime expirationDate;
    /**
     * The date this external reference was last updated.
     * 
     */
    @JsonProperty("update_date")
    @JsonPropertyDescription("The date this external reference was last updated.")
    private DateTime updateDate;

    /**
     * The unique ID within this system.
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * The unique ID within this system.
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ExternalReference withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * The source of this external reference if applicable.
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * The source of this external reference if applicable.
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public ExternalReference withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ExternalReference withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ExternalReference withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * The date this external reference was last updated.
     * 
     */
    @JsonProperty("update_date")
    public DateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * The date this external reference was last updated.
     * 
     */
    @JsonProperty("update_date")
    public void setUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
    }

    public ExternalReference withUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

}
