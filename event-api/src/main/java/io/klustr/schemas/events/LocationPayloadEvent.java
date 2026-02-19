
package io.klustr.schemas.events;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * LocationPayloadEvent
 * <p>
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#commonhits
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "device_id",
    "received_at",
    "lat",
    "lng",
    "accuracy",
    "time",
    "altitude",
    "speed"
})
@Generated("jsonschema2pojo")
public class LocationPayloadEvent {

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique event ID")
    private String id;
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
    @JsonProperty("received_at")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime receivedAt;
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
    @JsonProperty("time")
    @JsonPropertyDescription("The device time of the measurement")
    private Double time;
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

    public LocationPayloadEvent withId(String id) {
        this.id = id;
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

    public LocationPayloadEvent withDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public LocationPayloadEvent withReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
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

    public LocationPayloadEvent withLat(Double lat) {
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

    public LocationPayloadEvent withLng(Double lng) {
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

    public LocationPayloadEvent withAccuracy(Double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    /**
     * The device time of the measurement
     * 
     */
    @JsonProperty("time")
    public Double getTime() {
        return time;
    }

    /**
     * The device time of the measurement
     * 
     */
    @JsonProperty("time")
    public void setTime(Double time) {
        this.time = time;
    }

    public LocationPayloadEvent withTime(Double time) {
        this.time = time;
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

    public LocationPayloadEvent withAltitude(Double altitude) {
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

    public LocationPayloadEvent withSpeed(Double speed) {
        this.speed = speed;
        return this;
    }

}
