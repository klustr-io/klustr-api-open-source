
package io.klustr.schemas.console.agreements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * Agreement
 * <p>
 * A specified agreement made for a user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "org_id",
    "namespace",
    "object_id",
    "description",
    "versions",
    "status",
    "type",
    "date_created",
    "date_modified",
    "owner"
})
@Generated("jsonschema2pojo")
public class Agreement {

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this agreement.")
    private String id;
    /**
     * The names for this agreement as presented to users.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The names for this agreement as presented to users.")
    private String name;
    /**
     * The organization which owns this agreement.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization which owns this agreement.")
    private String orgId;
    /**
     * AgreementScope
     * <p>
     * The scope that this agreement is applied to.
     * 
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("The scope that this agreement is applied to.")
    private Agreement.AgreementScope namespace;
    /**
     * AgreementScope
     * <p>
     * The mapping of this agreement to a specific object.
     * 
     */
    @JsonProperty("object_id")
    @JsonPropertyDescription("The mapping of this agreement to a specific object.")
    private Agreement.AgreementScope_ objectId;
    /**
     * The description for this agreement, used internally.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description for this agreement, used internally.")
    private String description;
    /**
     * The content for this agreement
     * 
     */
    @JsonProperty("versions")
    @JsonPropertyDescription("The content for this agreement")
    private List<AgreementVersion> versions = new ArrayList<AgreementVersion>();
    /**
     * The status of this agreement.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this agreement.")
    private Agreement.Status status;
    /**
     * The type of the agreement
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of the agreement")
    private Agreement.Type type;
    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    @JsonPropertyDescription("The date the agreement was created.")
    private DateTime dateCreated;
    /**
     * The date the agreement was last modified.
     * 
     */
    @JsonProperty("date_modified")
    @JsonPropertyDescription("The date the agreement was last modified.")
    private DateTime dateModified;
    /**
     * AgreementOwner
     * <p>
     * The owners of this agreement and their contact information.
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("The owners of this agreement and their contact information.")
    private AgreementOwner owner;

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this agreement.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Agreement withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The names for this agreement as presented to users.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The names for this agreement as presented to users.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Agreement withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The organization which owns this agreement.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization which owns this agreement.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Agreement withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * AgreementScope
     * <p>
     * The scope that this agreement is applied to.
     * 
     */
    @JsonProperty("namespace")
    public Agreement.AgreementScope getNamespace() {
        return namespace;
    }

    /**
     * AgreementScope
     * <p>
     * The scope that this agreement is applied to.
     * 
     */
    @JsonProperty("namespace")
    public void setNamespace(Agreement.AgreementScope namespace) {
        this.namespace = namespace;
    }

    public Agreement withNamespace(Agreement.AgreementScope namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * AgreementScope
     * <p>
     * The mapping of this agreement to a specific object.
     * 
     */
    @JsonProperty("object_id")
    public Agreement.AgreementScope_ getObjectId() {
        return objectId;
    }

    /**
     * AgreementScope
     * <p>
     * The mapping of this agreement to a specific object.
     * 
     */
    @JsonProperty("object_id")
    public void setObjectId(Agreement.AgreementScope_ objectId) {
        this.objectId = objectId;
    }

    public Agreement withObjectId(Agreement.AgreementScope_ objectId) {
        this.objectId = objectId;
        return this;
    }

    /**
     * The description for this agreement, used internally.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description for this agreement, used internally.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Agreement withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The content for this agreement
     * 
     */
    @JsonProperty("versions")
    public List<AgreementVersion> getVersions() {
        return versions;
    }

    /**
     * The content for this agreement
     * 
     */
    @JsonProperty("versions")
    public void setVersions(List<AgreementVersion> versions) {
        this.versions = versions;
    }

    public Agreement withVersions(List<AgreementVersion> versions) {
        this.versions = versions;
        return this;
    }

    /**
     * The status of this agreement.
     * 
     */
    @JsonProperty("status")
    public Agreement.Status getStatus() {
        return status;
    }

    /**
     * The status of this agreement.
     * 
     */
    @JsonProperty("status")
    public void setStatus(Agreement.Status status) {
        this.status = status;
    }

    public Agreement withStatus(Agreement.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The type of the agreement
     * 
     */
    @JsonProperty("type")
    public Agreement.Type getType() {
        return type;
    }

    /**
     * The type of the agreement
     * 
     */
    @JsonProperty("type")
    public void setType(Agreement.Type type) {
        this.type = type;
    }

    public Agreement withType(Agreement.Type type) {
        this.type = type;
        return this;
    }

    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    public DateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * The date the agreement was created.
     * 
     */
    @JsonProperty("date_created")
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Agreement withDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * The date the agreement was last modified.
     * 
     */
    @JsonProperty("date_modified")
    public DateTime getDateModified() {
        return dateModified;
    }

    /**
     * The date the agreement was last modified.
     * 
     */
    @JsonProperty("date_modified")
    public void setDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Agreement withDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    /**
     * AgreementOwner
     * <p>
     * The owners of this agreement and their contact information.
     * 
     */
    @JsonProperty("owner")
    public AgreementOwner getOwner() {
        return owner;
    }

    /**
     * AgreementOwner
     * <p>
     * The owners of this agreement and their contact information.
     * 
     */
    @JsonProperty("owner")
    public void setOwner(AgreementOwner owner) {
        this.owner = owner;
    }

    public Agreement withOwner(AgreementOwner owner) {
        this.owner = owner;
        return this;
    }


    /**
     * AgreementScope
     * <p>
     * The scope that this agreement is applied to.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AgreementScope {

        ORG("org"),
        APP("app"),
        EXPERIMENT("experiment"),
        ACCOUNT("account");
        private final String value;
        private final static Map<String, Agreement.AgreementScope> CONSTANTS = new HashMap<String, Agreement.AgreementScope>();

        static {
            for (Agreement.AgreementScope c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AgreementScope(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Agreement.AgreementScope fromValue(String value) {
            Agreement.AgreementScope constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * AgreementScope
     * <p>
     * The mapping of this agreement to a specific object.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum AgreementScope_ {

        ORG("org"),
        APP("app"),
        EXPERIMENT("experiment"),
        ACCOUNT("account");
        private final String value;
        private final static Map<String, Agreement.AgreementScope_> CONSTANTS = new HashMap<String, Agreement.AgreementScope_>();

        static {
            for (Agreement.AgreementScope_ c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AgreementScope_(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Agreement.AgreementScope_ fromValue(String value) {
            Agreement.AgreementScope_ constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The status of this agreement.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        TEST("test"),
        DRAFT("draft"),
        PUBLISHED("published"),
        DELETED("deleted"),
        DEPRECATED("deprecated");
        private final String value;
        private final static Map<String, Agreement.Status> CONSTANTS = new HashMap<String, Agreement.Status>();

        static {
            for (Agreement.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Agreement.Status fromValue(String value) {
            Agreement.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The type of the agreement
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        PRIVACY_POLICY("privacy_policy"),
        TERMS_OF_SERVICE("terms_of_service"),
        COOKIE_POLICY("cookie_policy"),
        THIRD_PARTY("third_party"),
        EULA("eula");
        private final String value;
        private final static Map<String, Agreement.Type> CONSTANTS = new HashMap<String, Agreement.Type>();

        static {
            for (Agreement.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Agreement.Type fromValue(String value) {
            Agreement.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
