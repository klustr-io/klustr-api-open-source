
package io.klustr.schemas.events;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserLocationHistory
 * <p>
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide#commonhits
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "recent",
    "day",
    "week",
    "month"
})
@Generated("jsonschema2pojo")
public class UserLocationHistory {

    /**
     * The unique event ID
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique event ID")
    private String id;
    /**
     * The list of locations seen for this user truncated to the past day.
     * 
     */
    @JsonProperty("recent")
    @JsonPropertyDescription("The list of locations seen for this user truncated to the past day.")
    private List<LocationEvent> recent = new ArrayList<LocationEvent>();
    /**
     * A summarized version for the past day for the user.
     * 
     */
    @JsonProperty("day")
    @JsonPropertyDescription("A summarized version for the past day for the user.")
    private List<LocationEvent> day = new ArrayList<LocationEvent>();
    /**
     * A summarized version of history for the past week for the user.
     * 
     */
    @JsonProperty("week")
    @JsonPropertyDescription("A summarized version of history for the past week for the user.")
    private List<LocationEvent> week = new ArrayList<LocationEvent>();
    /**
     * A summarized version of history for the past month for the user.
     * 
     */
    @JsonProperty("month")
    @JsonPropertyDescription("A summarized version of history for the past month for the user.")
    private List<LocationEvent> month = new ArrayList<LocationEvent>();

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

    public UserLocationHistory withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The list of locations seen for this user truncated to the past day.
     * 
     */
    @JsonProperty("recent")
    public List<LocationEvent> getRecent() {
        return recent;
    }

    /**
     * The list of locations seen for this user truncated to the past day.
     * 
     */
    @JsonProperty("recent")
    public void setRecent(List<LocationEvent> recent) {
        this.recent = recent;
    }

    public UserLocationHistory withRecent(List<LocationEvent> recent) {
        this.recent = recent;
        return this;
    }

    /**
     * A summarized version for the past day for the user.
     * 
     */
    @JsonProperty("day")
    public List<LocationEvent> getDay() {
        return day;
    }

    /**
     * A summarized version for the past day for the user.
     * 
     */
    @JsonProperty("day")
    public void setDay(List<LocationEvent> day) {
        this.day = day;
    }

    public UserLocationHistory withDay(List<LocationEvent> day) {
        this.day = day;
        return this;
    }

    /**
     * A summarized version of history for the past week for the user.
     * 
     */
    @JsonProperty("week")
    public List<LocationEvent> getWeek() {
        return week;
    }

    /**
     * A summarized version of history for the past week for the user.
     * 
     */
    @JsonProperty("week")
    public void setWeek(List<LocationEvent> week) {
        this.week = week;
    }

    public UserLocationHistory withWeek(List<LocationEvent> week) {
        this.week = week;
        return this;
    }

    /**
     * A summarized version of history for the past month for the user.
     * 
     */
    @JsonProperty("month")
    public List<LocationEvent> getMonth() {
        return month;
    }

    /**
     * A summarized version of history for the past month for the user.
     * 
     */
    @JsonProperty("month")
    public void setMonth(List<LocationEvent> month) {
        this.month = month;
    }

    public UserLocationHistory withMonth(List<LocationEvent> month) {
        this.month = month;
        return this;
    }

}
