
package io.klustr.schemas.events;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * ApplicationEvent
 * <p>
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#commonhits
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event_id",
    "user_id",
    "device_id",
    "anonymous_id",
    "ip_address",
    "app_id",
    "received_at",
    "event_type",
    "event_category",
    "event_action",
    "event_label",
    "social_action",
    "social_network",
    "social_target"
})
@Generated("jsonschema2pojo")
public class ApplicationEvent {

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("event_id")
    @JsonPropertyDescription("The unique event ID")
    private String eventId;
    /**
     * The unique user ID for this experiment
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The unique user ID for this experiment")
    private String userId;
    /**
     * The unique device ID
     * 
     */
    @JsonProperty("device_id")
    @JsonPropertyDescription("The unique device ID")
    private String deviceId;
    /**
     * The anonymous ID of the user
     * 
     */
    @JsonProperty("anonymous_id")
    @JsonPropertyDescription("The anonymous ID of the user")
    private String anonymousId;
    /**
     * The IP address of the user
     * 
     */
    @JsonProperty("ip_address")
    @JsonPropertyDescription("The IP address of the user")
    private String ipAddress;
    @JsonProperty("app_id")
    private String appId;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("received_at")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime receivedAt;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("event_category")
    private String eventCategory;
    @JsonProperty("event_action")
    private String eventAction;
    @JsonProperty("event_label")
    private String eventLabel;
    @JsonProperty("social_action")
    private String socialAction;
    @JsonProperty("social_network")
    private String socialNetwork;
    @JsonProperty("social_target")
    private String socialTarget;

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("event_id")
    public String getEventId() {
        return eventId;
    }

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ApplicationEvent withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    /**
     * The unique user ID for this experiment
     * 
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * The unique user ID for this experiment
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ApplicationEvent withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * The unique device ID
     * 
     */
    @JsonProperty("device_id")
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * The unique device ID
     * 
     */
    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ApplicationEvent withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    /**
     * The anonymous ID of the user
     * 
     */
    @JsonProperty("anonymous_id")
    public String getAnonymousId() {
        return anonymousId;
    }

    /**
     * The anonymous ID of the user
     * 
     */
    @JsonProperty("anonymous_id")
    public void setAnonymousId(String anonymousId) {
        this.anonymousId = anonymousId;
    }

    public ApplicationEvent withAnonymousId(String anonymousId) {
        this.anonymousId = anonymousId;
        return this;
    }

    /**
     * The IP address of the user
     * 
     */
    @JsonProperty("ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * The IP address of the user
     * 
     */
    @JsonProperty("ip_address")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ApplicationEvent withIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ApplicationEvent withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("received_at")
    public DateTime getReceivedAt() {
        return receivedAt;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("received_at")
    public void setReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public ApplicationEvent withReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
        return this;
    }

    @JsonProperty("event_type")
    public String getEventType() {
        return eventType;
    }

    @JsonProperty("event_type")
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public ApplicationEvent withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    @JsonProperty("event_category")
    public String getEventCategory() {
        return eventCategory;
    }

    @JsonProperty("event_category")
    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public ApplicationEvent withEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    @JsonProperty("event_action")
    public String getEventAction() {
        return eventAction;
    }

    @JsonProperty("event_action")
    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public ApplicationEvent withEventAction(String eventAction) {
        this.eventAction = eventAction;
        return this;
    }

    @JsonProperty("event_label")
    public String getEventLabel() {
        return eventLabel;
    }

    @JsonProperty("event_label")
    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }

    public ApplicationEvent withEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
        return this;
    }

    @JsonProperty("social_action")
    public String getSocialAction() {
        return socialAction;
    }

    @JsonProperty("social_action")
    public void setSocialAction(String socialAction) {
        this.socialAction = socialAction;
    }

    public ApplicationEvent withSocialAction(String socialAction) {
        this.socialAction = socialAction;
        return this;
    }

    @JsonProperty("social_network")
    public String getSocialNetwork() {
        return socialNetwork;
    }

    @JsonProperty("social_network")
    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public ApplicationEvent withSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
        return this;
    }

    @JsonProperty("social_target")
    public String getSocialTarget() {
        return socialTarget;
    }

    @JsonProperty("social_target")
    public void setSocialTarget(String socialTarget) {
        this.socialTarget = socialTarget;
    }

    public ApplicationEvent withSocialTarget(String socialTarget) {
        this.socialTarget = socialTarget;
        return this;
    }

}
