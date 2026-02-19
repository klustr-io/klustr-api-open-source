
package io.klustr.schemas.console.storage;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * StorageObject
 * <p>
 * Information on the object found in storage.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "object_name",
    "version_id",
    "is_latest",
    "last_modified",
    "etag",
    "size"
})
@Generated("jsonschema2pojo")
public class StorageObject {

    /**
     * The object name found in storage.
     * 
     */
    @JsonProperty("object_name")
    @JsonPropertyDescription("The object name found in storage.")
    private String objectName;
    /**
     * The version ID of this storage object.
     * 
     */
    @JsonProperty("version_id")
    @JsonPropertyDescription("The version ID of this storage object.")
    private String versionId;
    /**
     * If this object is the latest version of this object.
     * 
     */
    @JsonProperty("is_latest")
    @JsonPropertyDescription("If this object is the latest version of this object.")
    private Boolean isLatest;
    /**
     * The date the object was last modified
     * 
     */
    @JsonProperty("last_modified")
    @JsonPropertyDescription("The date the object was last modified")
    private DateTime lastModified;
    /**
     * Etag information for this storage object.
     * 
     */
    @JsonProperty("etag")
    @JsonPropertyDescription("Etag information for this storage object.")
    private String etag;
    /**
     * The size of this object.
     * 
     */
    @JsonProperty("size")
    @JsonPropertyDescription("The size of this object.")
    private Long size;

    /**
     * The object name found in storage.
     * 
     */
    @JsonProperty("object_name")
    public String getObjectName() {
        return objectName;
    }

    /**
     * The object name found in storage.
     * 
     */
    @JsonProperty("object_name")
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public StorageObject withObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    /**
     * The version ID of this storage object.
     * 
     */
    @JsonProperty("version_id")
    public String getVersionId() {
        return versionId;
    }

    /**
     * The version ID of this storage object.
     * 
     */
    @JsonProperty("version_id")
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public StorageObject withVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    /**
     * If this object is the latest version of this object.
     * 
     */
    @JsonProperty("is_latest")
    public Boolean getIsLatest() {
        return isLatest;
    }

    /**
     * If this object is the latest version of this object.
     * 
     */
    @JsonProperty("is_latest")
    public void setIsLatest(Boolean isLatest) {
        this.isLatest = isLatest;
    }

    public StorageObject withIsLatest(Boolean isLatest) {
        this.isLatest = isLatest;
        return this;
    }

    /**
     * The date the object was last modified
     * 
     */
    @JsonProperty("last_modified")
    public DateTime getLastModified() {
        return lastModified;
    }

    /**
     * The date the object was last modified
     * 
     */
    @JsonProperty("last_modified")
    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    public StorageObject withLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    /**
     * Etag information for this storage object.
     * 
     */
    @JsonProperty("etag")
    public String getEtag() {
        return etag;
    }

    /**
     * Etag information for this storage object.
     * 
     */
    @JsonProperty("etag")
    public void setEtag(String etag) {
        this.etag = etag;
    }

    public StorageObject withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * The size of this object.
     * 
     */
    @JsonProperty("size")
    public Long getSize() {
        return size;
    }

    /**
     * The size of this object.
     * 
     */
    @JsonProperty("size")
    public void setSize(Long size) {
        this.size = size;
    }

    public StorageObject withSize(Long size) {
        this.size = size;
        return this;
    }

}
