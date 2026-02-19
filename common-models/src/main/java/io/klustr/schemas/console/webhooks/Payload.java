
package io.klustr.schemas.console.webhooks;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "event_type",
    "data"
})
@Generated("jsonschema2pojo")
public class Payload {

    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("data")
    private Object data;

    @JsonProperty("event_type")
    public String getEventType() {
        return eventType;
    }

    @JsonProperty("event_type")
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Payload withEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }

    public Payload withData(Object data) {
        this.data = data;
        return this;
    }

}
