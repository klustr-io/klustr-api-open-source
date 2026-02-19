
package io.klustr.schemas.console.apps;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLinks;
import org.joda.time.DateTime;


/**
 * App
 * <p>
 * A app that is defined for a project
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "brand",
    "creation_date",
    "contact",
    "verification",
    "trust",
    "links",
    "domains",
    "webhooks",
    "request_uris",
    "consent",
    "agreements",
    "access_mode"
})
@Generated("jsonschema2pojo")
public class App {

    /**
     * The unique ID for this application.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this application.")
    private String id;
    /**
     * AppBrand
     * <p>
     * The brand information for this application.
     * 
     */
    @JsonProperty("brand")
    @JsonPropertyDescription("The brand information for this application.")
    private AppBrand brand;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * AppContactInformation
     * <p>
     * The contact information this an application
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("The contact information this an application")
    private AppContactInformation contact;
    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("verification")
    @JsonPropertyDescription("Contains approval status and overall status indicators for publication.")
    private AppVerification verification;
    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("trust")
    @JsonPropertyDescription("Contains approval status and overall status indicators for publication.")
    private AppVerification trust;
    /**
     * AppLinks
     * <p>
     * Links referenced by the author for linking to consent.
     * 
     */
    @JsonProperty("links")
    @JsonPropertyDescription("Links referenced by the author for linking to consent.")
    private AppLinks links;
    /**
     * When a domain is used on the consent screen or in an OAuth client’s configuration, it must be pre-registered here.
     * 
     */
    @JsonProperty("domains")
    @JsonPropertyDescription("When a domain is used on the consent screen or in an OAuth client\u2019s configuration, it must be pre-registered here.")
    private List<String> domains = new ArrayList<String>();
    /**
     * AppWebhooks
     * <p>
     * Webhooks configured for this application to apply to events generated in the system.
     * 
     */
    @JsonProperty("webhooks")
    @JsonPropertyDescription("Webhooks configured for this application to apply to events generated in the system.")
    private AppWebhooks webhooks;
    @JsonProperty("request_uris")
    private List<String> requestUris = new ArrayList<String>();
    /**
     * ConsentGroup
     * <p>
     * The scope of consents requested by this application
     * 
     */
    @JsonProperty("consent")
    @JsonPropertyDescription("The scope of consents requested by this application")
    private ConsentGroup consent;
    /**
     * The agreements required to sign before using this app.
     * 
     */
    @JsonProperty("agreements")
    @JsonPropertyDescription("The agreements required to sign before using this app.")
    private List<String> agreements = new ArrayList<String>();
    /**
     * Configures if any authenticated user can access or if it is restricted access based on user.
     * 
     */
    @JsonProperty("access_mode")
    @JsonPropertyDescription("Configures if any authenticated user can access or if it is restricted access based on user.")
    private AccessMode accessMode;

    /**
     * The unique ID for this application.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this application.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public App withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * AppBrand
     * <p>
     * The brand information for this application.
     * 
     */
    @JsonProperty("brand")
    public AppBrand getBrand() {
        return brand;
    }

    /**
     * AppBrand
     * <p>
     * The brand information for this application.
     * 
     */
    @JsonProperty("brand")
    public void setBrand(AppBrand brand) {
        this.brand = brand;
    }

    public App withBrand(AppBrand brand) {
        this.brand = brand;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public App withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * AppContactInformation
     * <p>
     * The contact information this an application
     * 
     */
    @JsonProperty("contact")
    public AppContactInformation getContact() {
        return contact;
    }

    /**
     * AppContactInformation
     * <p>
     * The contact information this an application
     * 
     */
    @JsonProperty("contact")
    public void setContact(AppContactInformation contact) {
        this.contact = contact;
    }

    public App withContact(AppContactInformation contact) {
        this.contact = contact;
        return this;
    }

    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("verification")
    public AppVerification getVerification() {
        return verification;
    }

    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("verification")
    public void setVerification(AppVerification verification) {
        this.verification = verification;
    }

    public App withVerification(AppVerification verification) {
        this.verification = verification;
        return this;
    }

    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("trust")
    public AppVerification getTrust() {
        return trust;
    }

    /**
     * AppVerification
     * <p>
     * Contains approval status and overall status indicators for publication.
     * 
     */
    @JsonProperty("trust")
    public void setTrust(AppVerification trust) {
        this.trust = trust;
    }

    public App withTrust(AppVerification trust) {
        this.trust = trust;
        return this;
    }

    /**
     * AppLinks
     * <p>
     * Links referenced by the author for linking to consent.
     * 
     */
    @JsonProperty("links")
    public AppLinks getLinks() {
        return links;
    }

    /**
     * AppLinks
     * <p>
     * Links referenced by the author for linking to consent.
     * 
     */
    @JsonProperty("links")
    public void setLinks(AppLinks links) {
        this.links = links;
    }

    public App withLinks(AppLinks links) {
        this.links = links;
        return this;
    }

    /**
     * When a domain is used on the consent screen or in an OAuth client’s configuration, it must be pre-registered here.
     * 
     */
    @JsonProperty("domains")
    public List<String> getDomains() {
        return domains;
    }

    /**
     * When a domain is used on the consent screen or in an OAuth client’s configuration, it must be pre-registered here.
     * 
     */
    @JsonProperty("domains")
    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public App withDomains(List<String> domains) {
        this.domains = domains;
        return this;
    }

    /**
     * AppWebhooks
     * <p>
     * Webhooks configured for this application to apply to events generated in the system.
     * 
     */
    @JsonProperty("webhooks")
    public AppWebhooks getWebhooks() {
        return webhooks;
    }

    /**
     * AppWebhooks
     * <p>
     * Webhooks configured for this application to apply to events generated in the system.
     * 
     */
    @JsonProperty("webhooks")
    public void setWebhooks(AppWebhooks webhooks) {
        this.webhooks = webhooks;
    }

    public App withWebhooks(AppWebhooks webhooks) {
        this.webhooks = webhooks;
        return this;
    }

    @JsonProperty("request_uris")
    public List<String> getRequestUris() {
        return requestUris;
    }

    @JsonProperty("request_uris")
    public void setRequestUris(List<String> requestUris) {
        this.requestUris = requestUris;
    }

    public App withRequestUris(List<String> requestUris) {
        this.requestUris = requestUris;
        return this;
    }

    /**
     * ConsentGroup
     * <p>
     * The scope of consents requested by this application
     * 
     */
    @JsonProperty("consent")
    public ConsentGroup getConsent() {
        return consent;
    }

    /**
     * ConsentGroup
     * <p>
     * The scope of consents requested by this application
     * 
     */
    @JsonProperty("consent")
    public void setConsent(ConsentGroup consent) {
        this.consent = consent;
    }

    public App withConsent(ConsentGroup consent) {
        this.consent = consent;
        return this;
    }

    /**
     * The agreements required to sign before using this app.
     * 
     */
    @JsonProperty("agreements")
    public List<String> getAgreements() {
        return agreements;
    }

    /**
     * The agreements required to sign before using this app.
     * 
     */
    @JsonProperty("agreements")
    public void setAgreements(List<String> agreements) {
        this.agreements = agreements;
    }

    public App withAgreements(List<String> agreements) {
        this.agreements = agreements;
        return this;
    }

    /**
     * Configures if any authenticated user can access or if it is restricted access based on user.
     * 
     */
    @JsonProperty("access_mode")
    public AccessMode getAccessMode() {
        return accessMode;
    }

    /**
     * Configures if any authenticated user can access or if it is restricted access based on user.
     * 
     */
    @JsonProperty("access_mode")
    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public App withAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
        return this;
    }

}
