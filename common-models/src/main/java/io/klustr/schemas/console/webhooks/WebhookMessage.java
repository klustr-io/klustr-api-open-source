
package io.klustr.schemas.console.webhooks;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.joda.time.DateTime;


/**
 * WebhookMessage
 * <p>
 * A particular message broadcast to various apps and services.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "event_type",
    "timestamp",
    "channels",
    "event_id",
    "payload",
    "payload_retention_period"
})
@Generated("jsonschema2pojo")
public class WebhookMessage {

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique id of the message.")
    private java.lang.String id;
    /**
     * The event type that is used to filter and also verify json schema against.
     * 
     */
    @JsonProperty("event_type")
    @JsonPropertyDescription("The event type that is used to filter and also verify json schema against.")
    private java.lang.String eventType;
    /**
     * The unique timestamp of this message
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The unique timestamp of this message")
    private DateTime timestamp;
    /**
     * List of free-form identifiers that endpoints can filter by
     * 
     */
    @JsonProperty("channels")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("List of free-form identifiers that endpoints can filter by")
    private Set<String> channels = new LinkedHashSet<String>();
    /**
     * The unique identifier for this message
     * 
     */
    @JsonProperty("event_id")
    @JsonPropertyDescription("The unique identifier for this message")
    private java.lang.String eventId;
    @JsonProperty("payload")
    private Payload payload;
    /**
     * The number of days that this payload should be retained.
     * 
     */
    @JsonProperty("payload_retention_period")
    @JsonPropertyDescription("The number of days that this payload should be retained.")
    private Integer payloadRetentionPeriod;

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public WebhookMessage withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * The event type that is used to filter and also verify json schema against.
     * 
     */
    @JsonProperty("event_type")
    public java.lang.String getEventType() {
        return eventType;
    }

    /**
     * The event type that is used to filter and also verify json schema against.
     * 
     */
    @JsonProperty("event_type")
    public void setEventType(java.lang.String eventType) {
        this.eventType = eventType;
    }

    public WebhookMessage withEventType(java.lang.String eventType) {
        this.eventType = eventType;
        return this;
    }

    /**
     * The unique timestamp of this message
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The unique timestamp of this message
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public WebhookMessage withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * List of free-form identifiers that endpoints can filter by
     * 
     */
    @JsonProperty("channels")
    public Set<String> getChannels() {
        return channels;
    }

    /**
     * List of free-form identifiers that endpoints can filter by
     * 
     */
    @JsonProperty("channels")
    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    public WebhookMessage withChannels(Set<String> channels) {
        this.channels = channels;
        return this;
    }

    /**
     * The unique identifier for this message
     * 
     */
    @JsonProperty("event_id")
    public java.lang.String getEventId() {
        return eventId;
    }

    /**
     * The unique identifier for this message
     * 
     */
    @JsonProperty("event_id")
    public void setEventId(java.lang.String eventId) {
        this.eventId = eventId;
    }

    public WebhookMessage withEventId(java.lang.String eventId) {
        this.eventId = eventId;
        return this;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public WebhookMessage withPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    /**
     * The number of days that this payload should be retained.
     * 
     */
    @JsonProperty("payload_retention_period")
    public Integer getPayloadRetentionPeriod() {
        return payloadRetentionPeriod;
    }

    /**
     * The number of days that this payload should be retained.
     * 
     */
    @JsonProperty("payload_retention_period")
    public void setPayloadRetentionPeriod(Integer payloadRetentionPeriod) {
        this.payloadRetentionPeriod = payloadRetentionPeriod;
    }

    public WebhookMessage withPayloadRetentionPeriod(Integer payloadRetentionPeriod) {
        this.payloadRetentionPeriod = payloadRetentionPeriod;
        return this;
    }

}
