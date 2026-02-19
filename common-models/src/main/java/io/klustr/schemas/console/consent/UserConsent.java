
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserConsent
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "subject_id",
    "scopes"
})
@Generated("jsonschema2pojo")
public class UserConsent {

    /**
     * The unique ID of this specific consent.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this specific consent.")
    private String id;
    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The unique ID of this specific consent.")
    private String subjectId;
    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes that were approved by the user.")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();

    /**
     * The unique ID of this specific consent.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this specific consent.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserConsent withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public UserConsent withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserConsent withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

}
