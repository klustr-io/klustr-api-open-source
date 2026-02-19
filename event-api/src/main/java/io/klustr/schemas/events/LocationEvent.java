
package io.klustr.schemas.events;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * LocationEvent
 * <p>
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#commonhits
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "user_id",
    "device_id",
    "timestamp",
    "lat",
    "lng",
    "accuracy",
    "device_time",
    "altitude",
    "speed",
    "provider",
    "client_id",
    "org_id",
    "project_id"
})
@Generated("jsonschema2pojo")
public class LocationEvent {

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique event ID")
    private String id;
    /**
     * The user ID for this location
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The user ID for this location")
    private String userId;
    /**
     * The unique device ID
     * 
     */
    @JsonProperty("device_id")
    @JsonPropertyDescription("The unique device ID")
    private String deviceId;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime timestamp;
    /**
     * The latitude
     * 
     */
    @JsonProperty("lat")
    @JsonPropertyDescription("The latitude")
    private Double lat;
    /**
     * The longitude
     * 
     */
    @JsonProperty("lng")
    @JsonPropertyDescription("The longitude")
    private Double lng;
    /**
     * The accuracy
     * 
     */
    @JsonProperty("accuracy")
    @JsonPropertyDescription("The accuracy")
    private Double accuracy;
    /**
     * The device time of the measurement
     * 
     */
    @JsonProperty("device_time")
    @JsonPropertyDescription("The device time of the measurement")
    private Double deviceTime;
    /**
     * The altitude of the user
     * 
     */
    @JsonProperty("altitude")
    @JsonPropertyDescription("The altitude of the user")
    private Double altitude;
    /**
     * The speed calculated for the user
     * 
     */
    @JsonProperty("speed")
    @JsonPropertyDescription("The speed calculated for the user")
    private Double speed;
    /**
     * The provider of this location event.
     * 
     */
    @JsonProperty("provider")
    @JsonPropertyDescription("The provider of this location event.")
    private String provider;
    /**
     * The client ID who generated this event.
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The client ID who generated this event.")
    private String clientId;
    /**
     * The org ID who generated this event.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The org ID who generated this event.")
    private String orgId;
    /**
     * The project ID who generated this event.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project ID who generated this event.")
    private String projectId;

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public LocationEvent withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The user ID for this location
     * 
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * The user ID for this location
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocationEvent withUserId(String userId) {
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

    public LocationEvent withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocationEvent withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * The latitude
     * 
     */
    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    /**
     * The latitude
     * 
     */
    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public LocationEvent withLat(Double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * The longitude
     * 
     */
    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    /**
     * The longitude
     * 
     */
    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    public LocationEvent withLng(Double lng) {
        this.lng = lng;
        return this;
    }

    /**
     * The accuracy
     * 
     */
    @JsonProperty("accuracy")
    public Double getAccuracy() {
        return accuracy;
    }

    /**
     * The accuracy
     * 
     */
    @JsonProperty("accuracy")
    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public LocationEvent withAccuracy(Double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    /**
     * The device time of the measurement
     * 
     */
    @JsonProperty("device_time")
    public Double getDeviceTime() {
        return deviceTime;
    }

    /**
     * The device time of the measurement
     * 
     */
    @JsonProperty("device_time")
    public void setDeviceTime(Double deviceTime) {
        this.deviceTime = deviceTime;
    }

    public LocationEvent withDeviceTime(Double deviceTime) {
        this.deviceTime = deviceTime;
        return this;
    }

    /**
     * The altitude of the user
     * 
     */
    @JsonProperty("altitude")
    public Double getAltitude() {
        return altitude;
    }

    /**
     * The altitude of the user
     * 
     */
    @JsonProperty("altitude")
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public LocationEvent withAltitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    /**
     * The speed calculated for the user
     * 
     */
    @JsonProperty("speed")
    public Double getSpeed() {
        return speed;
    }

    /**
     * The speed calculated for the user
     * 
     */
    @JsonProperty("speed")
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public LocationEvent withSpeed(Double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * The provider of this location event.
     * 
     */
    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    /**
     * The provider of this location event.
     * 
     */
    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public LocationEvent withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * The client ID who generated this event.
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The client ID who generated this event.
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocationEvent withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The org ID who generated this event.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The org ID who generated this event.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public LocationEvent withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The project ID who generated this event.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project ID who generated this event.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LocationEvent withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

}
