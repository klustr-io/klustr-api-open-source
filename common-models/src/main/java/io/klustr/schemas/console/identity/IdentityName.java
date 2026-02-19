
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityName
 * <p>
 * The name for this identity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "given_name",
    "family_name",
    "middle_name"
})
@Generated("jsonschema2pojo")
public class IdentityName {

    /**
     * The given name of this identity
     * 
     */
    @JsonProperty("given_name")
    @JsonPropertyDescription("The given name of this identity")
    private String givenName;
    /**
     * The family name of this identity
     * 
     */
    @JsonProperty("family_name")
    @JsonPropertyDescription("The family name of this identity")
    private String familyName;
    /**
     * The middle name of this identity
     * 
     */
    @JsonProperty("middle_name")
    @JsonPropertyDescription("The middle name of this identity")
    private String middleName;

    /**
     * The given name of this identity
     * 
     */
    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    /**
     * The given name of this identity
     * 
     */
    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public IdentityName withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    /**
     * The family name of this identity
     * 
     */
    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * The family name of this identity
     * 
     */
    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public IdentityName withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    /**
     * The middle name of this identity
     * 
     */
    @JsonProperty("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * The middle name of this identity
     * 
     */
    @JsonProperty("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public IdentityName withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

}
