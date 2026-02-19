
package io.klustr.schemas.console.webhooks;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * WebhookExternalId
 * <p>
 * References an entity in an external system
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id"
})
@Generated("jsonschema2pojo")
public class WebhookExternalId {

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this application.")
    private String id;

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this application.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public WebhookExternalId withId(String id) {
        this.id = id;
        return this;
    }

}
