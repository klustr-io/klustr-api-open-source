
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
import io.klustr.schemas.console.apps.ConsentScope;
import org.joda.time.DateTime;


/**
 * AgreementVersion
 * <p>
 * A packaged version of an agreement indicating that any future version should be approved.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "version_number",
    "version_type",
    "modified_date",
    "effective_date",
    "scopes",
    "content",
    "status"
})
@Generated("jsonschema2pojo")
public class AgreementVersion {

    /**
     * The unique ID of this agreement version.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this agreement version.")
    private String id;
    /**
     * Semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    @JsonPropertyDescription("Semantic version number of the agreement.")
    private String versionNumber;
    /**
     * AgreementVersionType
     * <p>
     * The type of change made.
     * 
     */
    @JsonProperty("version_type")
    @JsonPropertyDescription("The type of change made.")
    private AgreementVersionType versionType;
    /**
     * The date the agreement was modified
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The date the agreement was modified")
    private DateTime modifiedDate;
    /**
     * The date the agreement is effective
     * 
     */
    @JsonProperty("effective_date")
    @JsonPropertyDescription("The date the agreement is effective")
    private DateTime effectiveDate;
    /**
     * The consent associated with this agreement
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The consent associated with this agreement")
    private List<ConsentScope> scopes = new ArrayList<ConsentScope>();
    /**
     * The content for this agreement linked to this version
     * 
     */
    @JsonProperty("content")
    @JsonPropertyDescription("The content for this agreement linked to this version")
    private List<AgreementContent> content = new ArrayList<AgreementContent>();
    /**
     * The current status of this agreement version
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The current status of this agreement version")
    private AgreementVersion.Status status;

    /**
     * The unique ID of this agreement version.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this agreement version.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public AgreementVersion withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    public String getVersionNumber() {
        return versionNumber;
    }

    /**
     * Semantic version number of the agreement.
     * 
     */
    @JsonProperty("version_number")
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public AgreementVersion withVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    /**
     * AgreementVersionType
     * <p>
     * The type of change made.
     * 
     */
    @JsonProperty("version_type")
    public AgreementVersionType getVersionType() {
        return versionType;
    }

    /**
     * AgreementVersionType
     * <p>
     * The type of change made.
     * 
     */
    @JsonProperty("version_type")
    public void setVersionType(AgreementVersionType versionType) {
        this.versionType = versionType;
    }

    public AgreementVersion withVersionType(AgreementVersionType versionType) {
        this.versionType = versionType;
        return this;
    }

    /**
     * The date the agreement was modified
     * 
     */
    @JsonProperty("modified_date")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The date the agreement was modified
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public AgreementVersion withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * The date the agreement is effective
     * 
     */
    @JsonProperty("effective_date")
    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * The date the agreement is effective
     * 
     */
    @JsonProperty("effective_date")
    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public AgreementVersion withEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    /**
     * The consent associated with this agreement
     * 
     */
    @JsonProperty("scopes")
    public List<ConsentScope> getScopes() {
        return scopes;
    }

    /**
     * The consent associated with this agreement
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<ConsentScope> scopes) {
        this.scopes = scopes;
    }

    public AgreementVersion withScopes(List<ConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * The content for this agreement linked to this version
     * 
     */
    @JsonProperty("content")
    public List<AgreementContent> getContent() {
        return content;
    }

    /**
     * The content for this agreement linked to this version
     * 
     */
    @JsonProperty("content")
    public void setContent(List<AgreementContent> content) {
        this.content = content;
    }

    public AgreementVersion withContent(List<AgreementContent> content) {
        this.content = content;
        return this;
    }

    /**
     * The current status of this agreement version
     * 
     */
    @JsonProperty("status")
    public AgreementVersion.Status getStatus() {
        return status;
    }

    /**
     * The current status of this agreement version
     * 
     */
    @JsonProperty("status")
    public void setStatus(AgreementVersion.Status status) {
        this.status = status;
    }

    public AgreementVersion withStatus(AgreementVersion.Status status) {
        this.status = status;
        return this;
    }


    /**
     * The current status of this agreement version
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        DRAFT("draft"),
        PUBLISHED("published"),
        DELETED("deleted");
        private final String value;
        private final static Map<String, AgreementVersion.Status> CONSTANTS = new HashMap<String, AgreementVersion.Status>();

        static {
            for (AgreementVersion.Status c: values()) {
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
        public static AgreementVersion.Status fromValue(String value) {
            AgreementVersion.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
