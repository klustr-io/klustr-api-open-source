
package io.klustr.schemas.console.apps;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.klustr.schemas.console.webhooks.WebhookExternalId;
import org.joda.time.DateTime;


/**
 * WebhookEndpoint
 * <p>
 * An endpoint for a webhook registered under a specific app.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "uid",
    "external_id",
    "description",
    "disabled",
    "secret",
    "metadata",
    "date_created",
    "date_modified",
    "url",
    "filter_types",
    "channels",
    "rate_limit"
})
@Generated("jsonschema2pojo")
public class WebhookEndpoint {

    /**
     * The optional unique identifier for this endpoint.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The optional unique identifier for this endpoint.")
    private java.lang.String id;
    /**
     * Mapping to the external ID of the endpoint.
     * 
     */
    @JsonProperty("uid")
    @JsonPropertyDescription("Mapping to the external ID of the endpoint.")
    private java.lang.String uid;
    /**
     * The ID of this webhook from the external system.
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The ID of this webhook from the external system.")
    private WebhookExternalId externalId;
    /**
     * The friendly description for this endpoint.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The friendly description for this endpoint.")
    private java.lang.String description;
    /**
     * if this endpoint is disabled
     * 
     */
    @JsonProperty("disabled")
    @JsonPropertyDescription("if this endpoint is disabled")
    private Boolean disabled;
    /**
     * The endpoint's verification secret. If null is passed, a secret is automatically generated. Format: base64 encoded random bytes optionally prefixed with whsec_. Recommended size: 24.
     * 
     */
    @JsonProperty("secret")
    @JsonPropertyDescription("The endpoint's verification secret. If null is passed, a secret is automatically generated. Format: base64 encoded random bytes optionally prefixed with whsec_. Recommended size: 24.")
    private java.lang.String secret;
    /**
     * Additional metadata related to the address
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Additional metadata related to the address")
    private Map<String, String> metadata;
    /**
     * The time and date that this endpoint was created
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The time and date that this endpoint was created")
    private DateTime dateCreated;
    /**
     * The time and date that this endpoint was modified
     * 
     */
    @JsonProperty("date_modified")
    @JsonPropertyDescription("The time and date that this endpoint was modified")
    private DateTime dateModified;
    /**
     * The url for this endpoint to send websocket events to
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url for this endpoint to send websocket events to")
    private URI url;
    /**
     * The types of events to filter, leave empty to receive all events.
     * 
     */
    @JsonProperty("filter_types")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("The types of events to filter, leave empty to receive all events.")
    private Set<String> filterTypes = new LinkedHashSet<String>();
    /**
     * List of message channels this endpoint listens to (omit for all)
     * 
     */
    @JsonProperty("channels")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("List of message channels this endpoint listens to (omit for all)")
    private Set<String> channels = new LinkedHashSet<String>();
    /**
     * The rate limits for this project
     * 
     */
    @JsonProperty("rate_limit")
    @JsonPropertyDescription("The rate limits for this project")
    private Integer rateLimit;

    /**
     * The optional unique identifier for this endpoint.
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * The optional unique identifier for this endpoint.
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public WebhookEndpoint withId(java.lang.String id) {
        this.id = id;
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

    public WebhookEndpoint withUid(java.lang.String uid) {
        this.uid = uid;
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

    public WebhookEndpoint withExternalId(WebhookExternalId externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * The friendly description for this endpoint.
     * 
     */
    @JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * The friendly description for this endpoint.
     * 
     */
    @JsonProperty("description")
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public WebhookEndpoint withDescription(java.lang.String description) {
        this.description = description;
        return this;
    }

    /**
     * if this endpoint is disabled
     * 
     */
    @JsonProperty("disabled")
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * if this endpoint is disabled
     * 
     */
    @JsonProperty("disabled")
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public WebhookEndpoint withDisabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    /**
     * The endpoint's verification secret. If null is passed, a secret is automatically generated. Format: base64 encoded random bytes optionally prefixed with whsec_. Recommended size: 24.
     * 
     */
    @JsonProperty("secret")
    public java.lang.String getSecret() {
        return secret;
    }

    /**
     * The endpoint's verification secret. If null is passed, a secret is automatically generated. Format: base64 encoded random bytes optionally prefixed with whsec_. Recommended size: 24.
     * 
     */
    @JsonProperty("secret")
    public void setSecret(java.lang.String secret) {
        this.secret = secret;
    }

    public WebhookEndpoint withSecret(java.lang.String secret) {
        this.secret = secret;
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

    public WebhookEndpoint withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * The time and date that this endpoint was created
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The time and date that this endpoint was created
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public WebhookEndpoint withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The time and date that this endpoint was modified
     * 
     */
    @JsonProperty("date_modified")
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * The time and date that this endpoint was modified
     * 
     */
    @JsonProperty("date_modified")
    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public WebhookEndpoint withDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    /**
     * The url for this endpoint to send websocket events to
     * 
     */
    @JsonProperty("url")
    public URI getUrl() {
        return url;
    }

    /**
     * The url for this endpoint to send websocket events to
     * 
     */
    @JsonProperty("url")
    public void setUrl(URI url) {
        this.url = url;
    }

    public WebhookEndpoint withUrl(URI url) {
        this.url = url;
        return this;
    }

    /**
     * The types of events to filter, leave empty to receive all events.
     * 
     */
    @JsonProperty("filter_types")
    public Set<String> getFilterTypes() {
        return filterTypes;
    }

    /**
     * The types of events to filter, leave empty to receive all events.
     * 
     */
    @JsonProperty("filter_types")
    public void setFilterTypes(Set<String> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public WebhookEndpoint withFilterTypes(Set<String> filterTypes) {
        this.filterTypes = filterTypes;
        return this;
    }

    /**
     * List of message channels this endpoint listens to (omit for all)
     * 
     */
    @JsonProperty("channels")
    public Set<String> getChannels() {
        return channels;
    }

    /**
     * List of message channels this endpoint listens to (omit for all)
     * 
     */
    @JsonProperty("channels")
    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    public WebhookEndpoint withChannels(Set<String> channels) {
        this.channels = channels;
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

    public WebhookEndpoint withRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
        return this;
    }

}
