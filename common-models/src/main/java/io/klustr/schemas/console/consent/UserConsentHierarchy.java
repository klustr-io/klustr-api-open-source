
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserConsentHierarchy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user_id",
    "empty",
    "orgs"
})
@Generated("jsonschema2pojo")
public class UserConsentHierarchy {

    /**
     * The unique application identifier.
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The unique application identifier.")
    private String userId;
    /**
     * No consent on file.
     * 
     */
    @JsonProperty("empty")
    @JsonPropertyDescription("No consent on file.")
    private Boolean empty;
    /**
     * The organizations the user has made consent agreements with.
     * 
     */
    @JsonProperty("orgs")
    @JsonPropertyDescription("The organizations the user has made consent agreements with.")
    private List<UserOrganizationConsentHierarchy> orgs = new ArrayList<UserOrganizationConsentHierarchy>();

    /**
     * The unique application identifier.
     * 
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * The unique application identifier.
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserConsentHierarchy withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * No consent on file.
     * 
     */
    @JsonProperty("empty")
    public Boolean getEmpty() {
        return empty;
    }

    /**
     * No consent on file.
     * 
     */
    @JsonProperty("empty")
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public UserConsentHierarchy withEmpty(Boolean empty) {
        this.empty = empty;
        return this;
    }

    /**
     * The organizations the user has made consent agreements with.
     * 
     */
    @JsonProperty("orgs")
    public List<UserOrganizationConsentHierarchy> getOrgs() {
        return orgs;
    }

    /**
     * The organizations the user has made consent agreements with.
     * 
     */
    @JsonProperty("orgs")
    public void setOrgs(List<UserOrganizationConsentHierarchy> orgs) {
        this.orgs = orgs;
    }

    public UserConsentHierarchy withOrgs(List<UserOrganizationConsentHierarchy> orgs) {
        this.orgs = orgs;
        return this;
    }

}
