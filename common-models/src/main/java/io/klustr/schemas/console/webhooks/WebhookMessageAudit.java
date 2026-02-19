
package io.klustr.schemas.console.webhooks;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * WebhookMessageAudit
 * <p>
 * Attempts made to deliver webhook messages
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "msgId",
    "url",
    "response",
    "responseStatusCode",
    "status",
    "timestamp",
    "trigger_type"
})
@Generated("jsonschema2pojo")
public class WebhookMessageAudit {

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique id of the message.")
    private String id;
    /**
     * The unique message id
     * 
     */
    @JsonProperty("msgId")
    @JsonPropertyDescription("The unique message id")
    private String msgId;
    /**
     * The url for this message
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url for this message")
    private String url;
    /**
     * The response code for the message
     * 
     */
    @JsonProperty("response")
    @JsonPropertyDescription("The response code for the message")
    private String response;
    /**
     * The response status code
     * 
     */
    @JsonProperty("responseStatusCode")
    @JsonPropertyDescription("The response status code")
    private Integer responseStatusCode;
    /**
     * The response status code
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The response status code")
    private WebhookMessageAudit.Status status;
    /**
     * The unique timestamp of this message
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The unique timestamp of this message")
    private DateTime timestamp;
    /**
     * The reason an attempt was made.
     * 
     */
    @JsonProperty("trigger_type")
    @JsonPropertyDescription("The reason an attempt was made.")
    private WebhookMessageAudit.TriggerType triggerType;

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique id of the message.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public WebhookMessageAudit withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The unique message id
     * 
     */
    @JsonProperty("msgId")
    public String getMsgId() {
        return msgId;
    }

    /**
     * The unique message id
     * 
     */
    @JsonProperty("msgId")
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public WebhookMessageAudit withMsgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    /**
     * The url for this message
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url for this message
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public WebhookMessageAudit withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The response code for the message
     * 
     */
    @JsonProperty("response")
    public String getResponse() {
        return response;
    }

    /**
     * The response code for the message
     * 
     */
    @JsonProperty("response")
    public void setResponse(String response) {
        this.response = response;
    }

    public WebhookMessageAudit withResponse(String response) {
        this.response = response;
        return this;
    }

    /**
     * The response status code
     * 
     */
    @JsonProperty("responseStatusCode")
    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    /**
     * The response status code
     * 
     */
    @JsonProperty("responseStatusCode")
    public void setResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public WebhookMessageAudit withResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
        return this;
    }

    /**
     * The response status code
     * 
     */
    @JsonProperty("status")
    public WebhookMessageAudit.Status getStatus() {
        return status;
    }

    /**
     * The response status code
     * 
     */
    @JsonProperty("status")
    public void setStatus(WebhookMessageAudit.Status status) {
        this.status = status;
    }

    public WebhookMessageAudit withStatus(WebhookMessageAudit.Status status) {
        this.status = status;
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

    public WebhookMessageAudit withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * The reason an attempt was made.
     * 
     */
    @JsonProperty("trigger_type")
    public WebhookMessageAudit.TriggerType getTriggerType() {
        return triggerType;
    }

    /**
     * The reason an attempt was made.
     * 
     */
    @JsonProperty("trigger_type")
    public void setTriggerType(WebhookMessageAudit.TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public WebhookMessageAudit withTriggerType(WebhookMessageAudit.TriggerType triggerType) {
        this.triggerType = triggerType;
        return this;
    }


    /**
     * The response status code
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        SUCCESS("success"),
        PENDING("pending"),
        FAIL("fail"),
        SENDING("sending");
        private final String value;
        private final static Map<String, WebhookMessageAudit.Status> CONSTANTS = new HashMap<String, WebhookMessageAudit.Status>();

        static {
            for (WebhookMessageAudit.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static WebhookMessageAudit.Status fromValue(String value) {
            WebhookMessageAudit.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The reason an attempt was made.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum TriggerType {

        SCHEDULED("scheduled"),
        MANUAL("manual");
        private final String value;
        private final static Map<String, WebhookMessageAudit.TriggerType> CONSTANTS = new HashMap<String, WebhookMessageAudit.TriggerType>();

        static {
            for (WebhookMessageAudit.TriggerType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TriggerType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static WebhookMessageAudit.TriggerType fromValue(String value) {
            WebhookMessageAudit.TriggerType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
