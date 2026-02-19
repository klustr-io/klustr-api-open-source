
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectSecret
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "label",
    "basic_credentials"
})
@Generated("jsonschema2pojo")
public class ProjectSecret {

    /**
     * The unique ID for this credential.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this credential.")
    private String id;
    /**
     * Friendly display name for these credentials
     * 
     */
    @JsonProperty("label")
    @JsonPropertyDescription("Friendly display name for these credentials")
    private String label;
    /**
     * ProjectSecretBasicCredential
     * <p>
     * Basic credentials issued for this project for a specific product or services
     * 
     */
    @JsonProperty("basic_credentials")
    @JsonPropertyDescription("Basic credentials issued for this project for a specific product or services")
    private ProjectSecretBasicCredential basicCredentials;

    /**
     * The unique ID for this credential.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this credential.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectSecret withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Friendly display name for these credentials
     * 
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * Friendly display name for these credentials
     * 
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    public ProjectSecret withLabel(String label) {
        this.label = label;
        return this;
    }

    /**
     * ProjectSecretBasicCredential
     * <p>
     * Basic credentials issued for this project for a specific product or services
     * 
     */
    @JsonProperty("basic_credentials")
    public ProjectSecretBasicCredential getBasicCredentials() {
        return basicCredentials;
    }

    /**
     * ProjectSecretBasicCredential
     * <p>
     * Basic credentials issued for this project for a specific product or services
     * 
     */
    @JsonProperty("basic_credentials")
    public void setBasicCredentials(ProjectSecretBasicCredential basicCredentials) {
        this.basicCredentials = basicCredentials;
    }

    public ProjectSecret withBasicCredentials(ProjectSecretBasicCredential basicCredentials) {
        this.basicCredentials = basicCredentials;
        return this;
    }

}
