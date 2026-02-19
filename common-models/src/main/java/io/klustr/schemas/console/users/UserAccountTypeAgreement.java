
package io.klustr.schemas.console.users;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserAccountTypeAgreement
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "status",
    "type"
})
@Generated("jsonschema2pojo")
public class UserAccountTypeAgreement {

    /**
     * The agreement that is linked to this.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The agreement that is linked to this.")
    private String id;
    /**
     * The name for this agreement at the time of saving
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this agreement at the time of saving")
    private String name;
    /**
     * The description for this agreement at the time of saving
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description for this agreement at the time of saving")
    private String description;
    /**
     * The status of this agreement at the time of saving.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this agreement at the time of saving.")
    private String status;
    /**
     * The type of this agreement.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of this agreement.")
    private String type;

    /**
     * The agreement that is linked to this.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The agreement that is linked to this.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserAccountTypeAgreement withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The name for this agreement at the time of saving
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this agreement at the time of saving
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UserAccountTypeAgreement withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The description for this agreement at the time of saving
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description for this agreement at the time of saving
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccountTypeAgreement withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The status of this agreement at the time of saving.
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * The status of this agreement at the time of saving.
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public UserAccountTypeAgreement withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * The type of this agreement.
     * 
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * The type of this agreement.
     * 
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public UserAccountTypeAgreement withType(String type) {
        this.type = type;
        return this;
    }

}
