
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserOrganizationConsentHierarchy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "org_id",
    "org_name",
    "scopes",
    "apps",
    "agreements"
})
@Generated("jsonschema2pojo")
public class UserOrganizationConsentHierarchy {

    /**
     * The organization ID
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization ID")
    private String orgId;
    /**
     * The organization name
     * 
     */
    @JsonProperty("org_name")
    @JsonPropertyDescription("The organization name")
    private String orgName;
    @JsonProperty("scopes")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();
    /**
     * Application level consent that user has within the organization.
     * 
     */
    @JsonProperty("apps")
    @JsonPropertyDescription("Application level consent that user has within the organization.")
    private List<UserApplicationConsentHierarchy> apps = new ArrayList<UserApplicationConsentHierarchy>();
    /**
     * Consent granted to users through agreements signed.
     * 
     */
    @JsonProperty("agreements")
    @JsonPropertyDescription("Consent granted to users through agreements signed.")
    private List<UserAgreementConsentHierarchy> agreements = new ArrayList<UserAgreementConsentHierarchy>();

    /**
     * The organization ID
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization ID
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserOrganizationConsentHierarchy withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The organization name
     * 
     */
    @JsonProperty("org_name")
    public String getOrgName() {
        return orgName;
    }

    /**
     * The organization name
     * 
     */
    @JsonProperty("org_name")
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public UserOrganizationConsentHierarchy withOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserOrganizationConsentHierarchy withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * Application level consent that user has within the organization.
     * 
     */
    @JsonProperty("apps")
    public List<UserApplicationConsentHierarchy> getApps() {
        return apps;
    }

    /**
     * Application level consent that user has within the organization.
     * 
     */
    @JsonProperty("apps")
    public void setApps(List<UserApplicationConsentHierarchy> apps) {
        this.apps = apps;
    }

    public UserOrganizationConsentHierarchy withApps(List<UserApplicationConsentHierarchy> apps) {
        this.apps = apps;
        return this;
    }

    /**
     * Consent granted to users through agreements signed.
     * 
     */
    @JsonProperty("agreements")
    public List<UserAgreementConsentHierarchy> getAgreements() {
        return agreements;
    }

    /**
     * Consent granted to users through agreements signed.
     * 
     */
    @JsonProperty("agreements")
    public void setAgreements(List<UserAgreementConsentHierarchy> agreements) {
        this.agreements = agreements;
    }

    public UserOrganizationConsentHierarchy withAgreements(List<UserAgreementConsentHierarchy> agreements) {
        this.agreements = agreements;
        return this;
    }

}
