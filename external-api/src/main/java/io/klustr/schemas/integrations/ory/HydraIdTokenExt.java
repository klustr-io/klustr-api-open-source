
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraIdTokenExt
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "email",
    "family_name",
    "given_name",
    "locale",
    "picture",
    "sid"
})
@Generated("jsonschema2pojo")
public class HydraIdTokenExt {

    @JsonProperty("email")
    private String email;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("sid")
    private String sid;

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public HydraIdTokenExt withEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public HydraIdTokenExt withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public HydraIdTokenExt withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public HydraIdTokenExt withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @JsonProperty("picture")
    public String getPicture() {
        return picture;
    }

    @JsonProperty("picture")
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public HydraIdTokenExt withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    @JsonProperty("sid")
    public String getSid() {
        return sid;
    }

    @JsonProperty("sid")
    public void setSid(String sid) {
        this.sid = sid;
    }

    public HydraIdTokenExt withSid(String sid) {
        this.sid = sid;
        return this;
    }

}
