
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.identity.IdentityCredentials;
import io.klustr.schemas.console.identity.IdentityMetadataPublic;
import io.klustr.schemas.console.identity.IdentityTraits;
import org.joda.time.DateTime;


/**
 * Identity
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "schema_id",
    "source",
    "schema_url",
    "organization_id",
    "credentials",
    "state",
    "state_changed_at",
    "traits",
    "verifiable_addresses",
    "recovery_addresses",
    "metadata_public",
    "metadata_admin"
})
@Generated("jsonschema2pojo")
public class Identity {

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this identity.")
    private String id;
    /**
     * The schema for this identity.
     * 
     */
    @JsonProperty("schema_id")
    @JsonPropertyDescription("The schema for this identity.")
    private String schemaId;
    /**
     * The source for this identity.
     * 
     */
    @JsonProperty("source")
    @JsonPropertyDescription("The source for this identity.")
    private String source;
    /**
     * The schema URL for this identity.
     * 
     */
    @JsonProperty("schema_url")
    @JsonPropertyDescription("The schema URL for this identity.")
    private String schemaUrl;
    /**
     * The organization that owns this identity, if applicable.
     * 
     */
    @JsonProperty("organization_id")
    @JsonPropertyDescription("The organization that owns this identity, if applicable.")
    private String organizationId;
    /**
     * IdentityCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    @JsonPropertyDescription("The credentials available for this identity")
    private IdentityCredentials credentials;
    /**
     * The state can either be active or inactive.
     * 
     */
    @JsonProperty("state")
    @JsonPropertyDescription("The state can either be active or inactive.")
    private String state;
    /**
     * Last time this identity changed state
     * 
     */
    @JsonProperty("state_changed_at")
    @JsonPropertyDescription("Last time this identity changed state")
    private DateTime stateChangedAt;
    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    @JsonPropertyDescription("The traits of this individual")
    private IdentityTraits traits;
    /**
     * The verified addresses on file for this identity
     * 
     */
    @JsonProperty("verifiable_addresses")
    @JsonPropertyDescription("The verified addresses on file for this identity")
    private List<IdentityVerifiableAddress> verifiableAddresses = new ArrayList<IdentityVerifiableAddress>();
    /**
     * The recovery addresses on file for this identity
     * 
     */
    @JsonProperty("recovery_addresses")
    @JsonPropertyDescription("The recovery addresses on file for this identity")
    private List<IdentityRecoveryAddress> recoveryAddresses = new ArrayList<IdentityRecoveryAddress>();
    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    private IdentityMetadataPublic metadataPublic;
    /**
     * IdentityMetadataAdmin
     * <p>
     * Attributes which can only be modified and read using the /admin/identities APIs. They are never directly exposed to the identity/user.
     * 
     */
    @JsonProperty("metadata_admin")
    @JsonPropertyDescription("Attributes which can only be modified and read using the /admin/identities APIs. They are never directly exposed to the identity/user.")
    private IdentityMetadataAdmin metadataAdmin;

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this identity.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Identity withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The schema for this identity.
     * 
     */
    @JsonProperty("schema_id")
    public String getSchemaId() {
        return schemaId;
    }

    /**
     * The schema for this identity.
     * 
     */
    @JsonProperty("schema_id")
    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public Identity withSchemaId(String schemaId) {
        this.schemaId = schemaId;
        return this;
    }

    /**
     * The source for this identity.
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * The source for this identity.
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public Identity withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * The schema URL for this identity.
     * 
     */
    @JsonProperty("schema_url")
    public String getSchemaUrl() {
        return schemaUrl;
    }

    /**
     * The schema URL for this identity.
     * 
     */
    @JsonProperty("schema_url")
    public void setSchemaUrl(String schemaUrl) {
        this.schemaUrl = schemaUrl;
    }

    public Identity withSchemaUrl(String schemaUrl) {
        this.schemaUrl = schemaUrl;
        return this;
    }

    /**
     * The organization that owns this identity, if applicable.
     * 
     */
    @JsonProperty("organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * The organization that owns this identity, if applicable.
     * 
     */
    @JsonProperty("organization_id")
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Identity withOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    /**
     * IdentityCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    public IdentityCredentials getCredentials() {
        return credentials;
    }

    /**
     * IdentityCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    public void setCredentials(IdentityCredentials credentials) {
        this.credentials = credentials;
    }

    public Identity withCredentials(IdentityCredentials credentials) {
        this.credentials = credentials;
        return this;
    }

    /**
     * The state can either be active or inactive.
     * 
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * The state can either be active or inactive.
     * 
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public Identity withState(String state) {
        this.state = state;
        return this;
    }

    /**
     * Last time this identity changed state
     * 
     */
    @JsonProperty("state_changed_at")
    public DateTime getStateChangedAt() {
        return stateChangedAt;
    }

    /**
     * Last time this identity changed state
     * 
     */
    @JsonProperty("state_changed_at")
    public void setStateChangedAt(DateTime stateChangedAt) {
        this.stateChangedAt = stateChangedAt;
    }

    public Identity withStateChangedAt(DateTime stateChangedAt) {
        this.stateChangedAt = stateChangedAt;
        return this;
    }

    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    public IdentityTraits getTraits() {
        return traits;
    }

    /**
     * IdentityTraits
     * <p>
     * The traits of this individual
     * 
     */
    @JsonProperty("traits")
    public void setTraits(IdentityTraits traits) {
        this.traits = traits;
    }

    public Identity withTraits(IdentityTraits traits) {
        this.traits = traits;
        return this;
    }

    /**
     * The verified addresses on file for this identity
     * 
     */
    @JsonProperty("verifiable_addresses")
    public List<IdentityVerifiableAddress> getVerifiableAddresses() {
        return verifiableAddresses;
    }

    /**
     * The verified addresses on file for this identity
     * 
     */
    @JsonProperty("verifiable_addresses")
    public void setVerifiableAddresses(List<IdentityVerifiableAddress> verifiableAddresses) {
        this.verifiableAddresses = verifiableAddresses;
    }

    public Identity withVerifiableAddresses(List<IdentityVerifiableAddress> verifiableAddresses) {
        this.verifiableAddresses = verifiableAddresses;
        return this;
    }

    /**
     * The recovery addresses on file for this identity
     * 
     */
    @JsonProperty("recovery_addresses")
    public List<IdentityRecoveryAddress> getRecoveryAddresses() {
        return recoveryAddresses;
    }

    /**
     * The recovery addresses on file for this identity
     * 
     */
    @JsonProperty("recovery_addresses")
    public void setRecoveryAddresses(List<IdentityRecoveryAddress> recoveryAddresses) {
        this.recoveryAddresses = recoveryAddresses;
    }

    public Identity withRecoveryAddresses(List<IdentityRecoveryAddress> recoveryAddresses) {
        this.recoveryAddresses = recoveryAddresses;
        return this;
    }

    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    public IdentityMetadataPublic getMetadataPublic() {
        return metadataPublic;
    }

    /**
     * IdentityMetadataPublic
     * <p>
     * 
     * 
     */
    @JsonProperty("metadata_public")
    public void setMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
    }

    public Identity withMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
        return this;
    }

    /**
     * IdentityMetadataAdmin
     * <p>
     * Attributes which can only be modified and read using the /admin/identities APIs. They are never directly exposed to the identity/user.
     * 
     */
    @JsonProperty("metadata_admin")
    public IdentityMetadataAdmin getMetadataAdmin() {
        return metadataAdmin;
    }

    /**
     * IdentityMetadataAdmin
     * <p>
     * Attributes which can only be modified and read using the /admin/identities APIs. They are never directly exposed to the identity/user.
     * 
     */
    @JsonProperty("metadata_admin")
    public void setMetadataAdmin(IdentityMetadataAdmin metadataAdmin) {
        this.metadataAdmin = metadataAdmin;
    }

    public Identity withMetadataAdmin(IdentityMetadataAdmin metadataAdmin) {
        this.metadataAdmin = metadataAdmin;
        return this;
    }

}
