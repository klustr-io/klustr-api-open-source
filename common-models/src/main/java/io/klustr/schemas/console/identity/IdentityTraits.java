
package io.klustr.schemas.console.identity;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.Gender;
import org.joda.time.LocalDate;


/**
 * IdentityTraits
 * <p>
 * This maps to the identity traits found in ORY kratos so that we can get basic information about a user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "birthdate",
    "email",
    "username",
    "gender",
    "citizenship",
    "locale"
})
@Generated("jsonschema2pojo")
public class IdentityTraits {

    /**
     * IdentityName
     * <p>
     * The name for this identity
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this identity")
    private IdentityName name;
    /**
     * The date of birth for this identity (YYYY-MM-DD)
     * 
     */
    @JsonProperty("birthdate")
    @JsonPropertyDescription("The date of birth for this identity (YYYY-MM-DD)")
    private LocalDate birthdate;
    /**
     * The email address for this identity
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email address for this identity")
    private String email;
    /**
     * The username that the user can use to login.
     * 
     */
    @JsonProperty("username")
    @JsonPropertyDescription("The username that the user can use to login.")
    private String username;
    /**
     * The gender of the individual
     * 
     */
    @JsonProperty("gender")
    @JsonPropertyDescription("The gender of the individual")
    private Gender gender;
    /**
     * The citizenship of the individual
     * 
     */
    @JsonProperty("citizenship")
    @JsonPropertyDescription("The citizenship of the individual")
    private String citizenship;
    /**
     * The locale of the individual
     * 
     */
    @JsonProperty("locale")
    @JsonPropertyDescription("The locale of the individual")
    private String locale;

    /**
     * IdentityName
     * <p>
     * The name for this identity
     * 
     */
    @JsonProperty("name")
    public IdentityName getName() {
        return name;
    }

    /**
     * IdentityName
     * <p>
     * The name for this identity
     * 
     */
    @JsonProperty("name")
    public void setName(IdentityName name) {
        this.name = name;
    }

    public IdentityTraits withName(IdentityName name) {
        this.name = name;
        return this;
    }

    /**
     * The date of birth for this identity (YYYY-MM-DD)
     * 
     */
    @JsonProperty("birthdate")
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * The date of birth for this identity (YYYY-MM-DD)
     * 
     */
    @JsonProperty("birthdate")
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public IdentityTraits withBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    /**
     * The email address for this identity
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email address for this identity
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public IdentityTraits withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The username that the user can use to login.
     * 
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * The username that the user can use to login.
     * 
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public IdentityTraits withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * The gender of the individual
     * 
     */
    @JsonProperty("gender")
    public Gender getGender() {
        return gender;
    }

    /**
     * The gender of the individual
     * 
     */
    @JsonProperty("gender")
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public IdentityTraits withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * The citizenship of the individual
     * 
     */
    @JsonProperty("citizenship")
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * The citizenship of the individual
     * 
     */
    @JsonProperty("citizenship")
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public IdentityTraits withCitizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    /**
     * The locale of the individual
     * 
     */
    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    /**
     * The locale of the individual
     * 
     */
    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public IdentityTraits withLocale(String locale) {
        this.locale = locale;
        return this;
    }

}
