
package io.klustr.schemas.console.webhooks;

import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * WebhookProject
 * <p>
 * The project that enables a person to setup various endpoints to collect events
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "uid",
    "metadata",
    "date_created",
    "rate_limit",
    "external_id"
})
@Generated("jsonschema2pojo")
public class WebhookProject {

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this application.")
    private java.lang.String id;
    /**
     * The friendly display name for this application
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The friendly display name for this application")
    private java.lang.String name;
    /**
     * Mapping to the external ID of the endpoint.
     * 
     */
    @JsonProperty("uid")
    @JsonPropertyDescription("Mapping to the external ID of the endpoint.")
    private java.lang.String uid;
    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Additional metadata related to the address")
    private Map<String, String> metadata;
    /**
     * The time and date that this application was created
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The time and date that this application was created")
    private DateTime dateCreated;
    /**
     * The rate limits for this project
     * 
     */
    @JsonProperty("rate_limit")
    @JsonPropertyDescription("The rate limits for this project")
    private Integer rateLimit;
    /**
     * The ID of this webhook from the external system.
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The ID of this webhook from the external system.")
    private WebhookExternalId externalId;

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public WebhookProject withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * The friendly display name for this application
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * The friendly display name for this application
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public WebhookProject withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * Mapping to the external ID of the endpoint.
     * 
     */
    @JsonProperty("uid")
    public java.lang.String getUid() {
        return uid;
    }

    /**
     * Mapping to the external ID of the endpoint.
     * 
     */
    @JsonProperty("uid")
    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }

    public WebhookProject withUid(java.lang.String uid) {
        this.uid = uid;
        return this;
    }

    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public WebhookProject withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * The time and date that this application was created
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The time and date that this application was created
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public WebhookProject withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The rate limits for this project
     * 
     */
    @JsonProperty("rate_limit")
    public Integer getRateLimit() {
        return rateLimit;
    }

    /**
     * The rate limits for this project
     * 
     */
    @JsonProperty("rate_limit")
    public void setRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
    }

    public WebhookProject withRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
        return this;
    }

    /**
     * The ID of this webhook from the external system.
     * 
     */
    @JsonProperty("external_id")
    public WebhookExternalId getExternalId() {
        return externalId;
    }

    /**
     * The ID of this webhook from the external system.
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(WebhookExternalId externalId) {
        this.externalId = externalId;
    }

    public WebhookProject withExternalId(WebhookExternalId externalId) {
        this.externalId = externalId;
        return this;
    }

}
