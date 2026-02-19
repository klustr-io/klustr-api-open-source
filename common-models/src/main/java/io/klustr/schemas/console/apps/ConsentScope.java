
package io.klustr.schemas.console.apps;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.agreements.DataPurpose;


/**
 * ConsentScope
 * <p>
 * The consent information requested by this app or available for this app to request.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "sensitivity",
    "purpose"
})
@Generated("jsonschema2pojo")
public class ConsentScope {

    /**
     * The unique ID of this consent attribute.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this consent attribute.")
    private String id;
    /**
     * The sensitivity of this consent scope. This is read only and will be assigned by organization privacy.
     * 
     */
    @JsonProperty("sensitivity")
    @JsonPropertyDescription("The sensitivity of this consent scope. This is read only and will be assigned by organization privacy.")
    private String sensitivity;
    /**
     * The purposes associated with this consent scope.
     * 
     */
    @JsonProperty("purpose")
    @JsonPropertyDescription("The purposes associated with this consent scope.")
    private List<DataPurpose> purpose = new ArrayList<DataPurpose>();

    /**
     * The unique ID of this consent attribute.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this consent attribute.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentScope withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The sensitivity of this consent scope. This is read only and will be assigned by organization privacy.
     * 
     */
    @JsonProperty("sensitivity")
    public String getSensitivity() {
        return sensitivity;
    }

    /**
     * The sensitivity of this consent scope. This is read only and will be assigned by organization privacy.
     * 
     */
    @JsonProperty("sensitivity")
    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public ConsentScope withSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
        return this;
    }

    /**
     * The purposes associated with this consent scope.
     * 
     */
    @JsonProperty("purpose")
    public List<DataPurpose> getPurpose() {
        return purpose;
    }

    /**
     * The purposes associated with this consent scope.
     * 
     */
    @JsonProperty("purpose")
    public void setPurpose(List<DataPurpose> purpose) {
        this.purpose = purpose;
    }

    public ConsentScope withPurpose(List<DataPurpose> purpose) {
        this.purpose = purpose;
        return this;
    }

}
