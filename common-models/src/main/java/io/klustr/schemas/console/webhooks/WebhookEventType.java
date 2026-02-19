
package io.klustr.schemas.console.webhooks;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * WebhookEventType
 * <p>
 * The main app that contains zero, one, or more endpoints
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "deprecated",
    "description",
    "schema",
    "date_created",
    "date_modified"
})
@Generated("jsonschema2pojo")
public class WebhookEventType {

    /**
     * The unique name of this event type, in the format of `foo.name` similar or `user.login`.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The unique name of this event type, in the format of `foo.name` similar or `user.login`.")
    private String name;
    /**
     * If this event type has been deprecated
     * 
     */
    @JsonProperty("deprecated")
    @JsonPropertyDescription("If this event type has been deprecated")
    private Boolean deprecated;
    /**
     * The description of this event type available.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of this event type available.")
    private String description;
    /**
     * The JSON schema for this event type if available.
     * 
     */
    @JsonProperty("schema")
    @JsonPropertyDescription("The JSON schema for this event type if available.")
    private Object schema;
    /**
     * The unique timestamp of this entity when it was created.
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The unique timestamp of this entity when it was created.")
    private DateTime dateCreated;
    /**
     * The unique timestamp of this entity when it was modified
     * 
     */
    @JsonProperty("date_modified")
    @JsonPropertyDescription("The unique timestamp of this entity when it was modified")
    private DateTime dateModified;

    /**
     * The unique name of this event type, in the format of `foo.name` similar or `user.login`.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The unique name of this event type, in the format of `foo.name` similar or `user.login`.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public WebhookEventType withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * If this event type has been deprecated
     * 
     */
    @JsonProperty("deprecated")
    public Boolean getDeprecated() {
        return deprecated;
    }

    /**
     * If this event type has been deprecated
     * 
     */
    @JsonProperty("deprecated")
    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public WebhookEventType withDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    /**
     * The description of this event type available.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description of this event type available.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public WebhookEventType withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The JSON schema for this event type if available.
     * 
     */
    @JsonProperty("schema")
    public Object getSchema() {
        return schema;
    }

    /**
     * The JSON schema for this event type if available.
     * 
     */
    @JsonProperty("schema")
    public void setSchema(Object schema) {
        this.schema = schema;
    }

    public WebhookEventType withSchema(Object schema) {
        this.schema = schema;
        return this;
    }

    /**
     * The unique timestamp of this entity when it was created.
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The unique timestamp of this entity when it was created.
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public WebhookEventType withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The unique timestamp of this entity when it was modified
     * 
     */
    @JsonProperty("date_modified")
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * The unique timestamp of this entity when it was modified
     * 
     */
    @JsonProperty("date_modified")
    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public WebhookEventType withDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

}
