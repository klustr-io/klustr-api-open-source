
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityMetadataPublic
 * <p>
 * Store metadata about the identity which the identity itself can see when calling for example the session endpoint. Do not store sensitive information (e.g. credit score) about the identity in this field.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "picture"
})
@Generated("jsonschema2pojo")
public class IdentityMetadataPublic {

    /**
     * The photo of the user, usually of their face or avatar.
     * 
     */
    @JsonProperty("picture")
    @JsonPropertyDescription("The photo of the user, usually of their face or avatar.")
    private String picture;

    /**
     * The photo of the user, usually of their face or avatar.
     * 
     */
    @JsonProperty("picture")
    public String getPicture() {
        return picture;
    }

    /**
     * The photo of the user, usually of their face or avatar.
     * 
     */
    @JsonProperty("picture")
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public IdentityMetadataPublic withPicture(String picture) {
        this.picture = picture;
        return this;
    }

}
