
package io.klustr.schemas.console.agreements;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AgreementReference
 * <p>
 * Reference to a specific agreement without exposing public metadata.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "version"
})
@Generated("jsonschema2pojo")
public class AgreementReference {

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this agreement.")
    private String id;
    /**
     * The name of this agreement.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of this agreement.")
    private String name;
    /**
     * AgreementVersion
     * <p>
     * A packaged version of an agreement indicating that any future version should be approved.
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("A packaged version of an agreement indicating that any future version should be approved.")
    private AgreementVersion version;

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public AgreementReference withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The name of this agreement.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of this agreement.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public AgreementReference withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * AgreementVersion
     * <p>
     * A packaged version of an agreement indicating that any future version should be approved.
     * 
     */
    @JsonProperty("version")
    public AgreementVersion getVersion() {
        return version;
    }

    /**
     * AgreementVersion
     * <p>
     * A packaged version of an agreement indicating that any future version should be approved.
     * 
     */
    @JsonProperty("version")
    public void setVersion(AgreementVersion version) {
        this.version = version;
    }

    public AgreementReference withVersion(AgreementVersion version) {
        this.version = version;
        return this;
    }

}
