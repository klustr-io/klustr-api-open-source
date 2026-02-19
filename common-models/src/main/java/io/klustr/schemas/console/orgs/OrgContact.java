
package io.klustr.schemas.console.orgs;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * OrgContact
 * <p>
 * The contact information.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "email",
    "phone",
    "url"
})
@Generated("jsonschema2pojo")
public class OrgContact {

    /**
     * The email for this organization
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email for this organization")
    private String email;
    /**
     * The phone for this organization
     * 
     */
    @JsonProperty("phone")
    @JsonPropertyDescription("The phone for this organization")
    private String phone;
    /**
     * The url for this organization
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url for this organization")
    private String url;

    /**
     * The email for this organization
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email for this organization
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public OrgContact withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The phone for this organization
     * 
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * The phone for this organization
     * 
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrgContact withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * The url for this organization
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url for this organization
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public OrgContact withUrl(String url) {
        this.url = url;
        return this;
    }

}
