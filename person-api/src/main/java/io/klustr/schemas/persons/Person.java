
package io.klustr.schemas.persons;

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
 * Person
 * <p>
 * A sample person
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "namespace",
    "deleted",
    "verification",
    "name",
    "citizenship",
    "contact",
    "ids",
    "documents",
    "credentials",
    "creation_date",
    "status",
    "traits",
    "metadata_public"
})
@Generated("jsonschema2pojo")
public class Person {

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of the person.")
    private String id;
    /**
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("The namespace for this person which owns the record or operates it.")
    private String namespace;
    /**
     * If the person is deleted.
     * 
     */
    @JsonProperty("deleted")
    @JsonPropertyDescription("If the person is deleted.")
    private Boolean deleted;
    /**
     * PersonVerification
     * <p>
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("verification")
    @JsonPropertyDescription("The namespace for this person which owns the record or operates it.")
    private PersonVerification verification;
    /**
     * The names for this person.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The names for this person.")
    private List<Name> name = new ArrayList<Name>();
    /**
     * The citizenship for this person.
     * 
     */
    @JsonProperty("citizenship")
    @JsonPropertyDescription("The citizenship for this person.")
    private List<Citizenship> citizenship = new ArrayList<Citizenship>();
    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("The contact information for a person")
    private ContactInformation contact;
    /**
     * Identities that can be used to map this person to other systems and management systems. For example if this person is from a CRM or could be linked to an identity management solution.
     * 
     */
    @JsonProperty("ids")
    @JsonPropertyDescription("Identities that can be used to map this person to other systems and management systems. For example if this person is from a CRM or could be linked to an identity management solution.")
    private List<ExternalReference> ids = new ArrayList<ExternalReference>();
    /**
     * The ID documents related for this person.
     * 
     */
    @JsonProperty("documents")
    @JsonPropertyDescription("The ID documents related for this person.")
    private List<PersonIdentityDocument> documents = new ArrayList<PersonIdentityDocument>();
    /**
     * PersonCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    @JsonPropertyDescription("The credentials available for this identity")
    private IdentityCredentials credentials;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * ProfileStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("status")
    private ProfileStatus status;
    /**
     * Traits and meta data related to the user that are public available.
     * 
     */
    @JsonProperty("traits")
    @JsonPropertyDescription("Traits and meta data related to the user that are public available.")
    private IdentityTraits traits;
    /**
     * Public related information
     * 
     */
    @JsonProperty("metadata_public")
    @JsonPropertyDescription("Public related information")
    private IdentityMetadataPublic metadataPublic;

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of the person.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Person withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    /**
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Person withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * If the person is deleted.
     * 
     */
    @JsonProperty("deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * If the person is deleted.
     * 
     */
    @JsonProperty("deleted")
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Person withDeleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    /**
     * PersonVerification
     * <p>
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("verification")
    public PersonVerification getVerification() {
        return verification;
    }

    /**
     * PersonVerification
     * <p>
     * The namespace for this person which owns the record or operates it.
     * 
     */
    @JsonProperty("verification")
    public void setVerification(PersonVerification verification) {
        this.verification = verification;
    }

    public Person withVerification(PersonVerification verification) {
        this.verification = verification;
        return this;
    }

    /**
     * The names for this person.
     * 
     */
    @JsonProperty("name")
    public List<Name> getName() {
        return name;
    }

    /**
     * The names for this person.
     * 
     */
    @JsonProperty("name")
    public void setName(List<Name> name) {
        this.name = name;
    }

    public Person withName(List<Name> name) {
        this.name = name;
        return this;
    }

    /**
     * The citizenship for this person.
     * 
     */
    @JsonProperty("citizenship")
    public List<Citizenship> getCitizenship() {
        return citizenship;
    }

    /**
     * The citizenship for this person.
     * 
     */
    @JsonProperty("citizenship")
    public void setCitizenship(List<Citizenship> citizenship) {
        this.citizenship = citizenship;
    }

    public Person withCitizenship(List<Citizenship> citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    public ContactInformation getContact() {
        return contact;
    }

    /**
     * ContactInformation
     * <p>
     * The contact information for a person
     * 
     */
    @JsonProperty("contact")
    public void setContact(ContactInformation contact) {
        this.contact = contact;
    }

    public Person withContact(ContactInformation contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Identities that can be used to map this person to other systems and management systems. For example if this person is from a CRM or could be linked to an identity management solution.
     * 
     */
    @JsonProperty("ids")
    public List<ExternalReference> getIds() {
        return ids;
    }

    /**
     * Identities that can be used to map this person to other systems and management systems. For example if this person is from a CRM or could be linked to an identity management solution.
     * 
     */
    @JsonProperty("ids")
    public void setIds(List<ExternalReference> ids) {
        this.ids = ids;
    }

    public Person withIds(List<ExternalReference> ids) {
        this.ids = ids;
        return this;
    }

    /**
     * The ID documents related for this person.
     * 
     */
    @JsonProperty("documents")
    public List<PersonIdentityDocument> getDocuments() {
        return documents;
    }

    /**
     * The ID documents related for this person.
     * 
     */
    @JsonProperty("documents")
    public void setDocuments(List<PersonIdentityDocument> documents) {
        this.documents = documents;
    }

    public Person withDocuments(List<PersonIdentityDocument> documents) {
        this.documents = documents;
        return this;
    }

    /**
     * PersonCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    public IdentityCredentials getCredentials() {
        return credentials;
    }

    /**
     * PersonCredentials
     * <p>
     * The credentials available for this identity
     * 
     */
    @JsonProperty("credentials")
    public void setCredentials(IdentityCredentials credentials) {
        this.credentials = credentials;
    }

    public Person withCredentials(IdentityCredentials credentials) {
        this.credentials = credentials;
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

    public Person withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * ProfileStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("status")
    public ProfileStatus getStatus() {
        return status;
    }

    /**
     * ProfileStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("status")
    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public Person withStatus(ProfileStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Traits and meta data related to the user that are public available.
     * 
     */
    @JsonProperty("traits")
    public IdentityTraits getTraits() {
        return traits;
    }

    /**
     * Traits and meta data related to the user that are public available.
     * 
     */
    @JsonProperty("traits")
    public void setTraits(IdentityTraits traits) {
        this.traits = traits;
    }

    public Person withTraits(IdentityTraits traits) {
        this.traits = traits;
        return this;
    }

    /**
     * Public related information
     * 
     */
    @JsonProperty("metadata_public")
    public IdentityMetadataPublic getMetadataPublic() {
        return metadataPublic;
    }

    /**
     * Public related information
     * 
     */
    @JsonProperty("metadata_public")
    public void setMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
    }

    public Person withMetadataPublic(IdentityMetadataPublic metadataPublic) {
        this.metadataPublic = metadataPublic;
        return this;
    }

}
