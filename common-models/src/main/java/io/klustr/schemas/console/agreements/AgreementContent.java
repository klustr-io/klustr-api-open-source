
package io.klustr.schemas.console.agreements;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * AgreementContent
 * <p>
 * The content related to an agreement.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "title",
    "locale",
    "mime_type",
    "date_created",
    "date_modified"
})
@Generated("jsonschema2pojo")
public class AgreementContent {

    /**
     * The url for this content.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url for this content.")
    private String url;
    /**
     * The title of the agreement content.
     * 
     */
    @JsonProperty("title")
    @JsonPropertyDescription("The title of the agreement content.")
    private String title;
    /**
     * The locale and language code for this agreement.
     * 
     */
    @JsonProperty("locale")
    @JsonPropertyDescription("The locale and language code for this agreement.")
    private String locale;
    /**
     * The mime type of this agreement content.
     * 
     */
    @JsonProperty("mime_type")
    @JsonPropertyDescription("The mime type of this agreement content.")
    private String mimeType;
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
     * The url for this content.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url for this content.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public AgreementContent withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The title of the agreement content.
     * 
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * The title of the agreement content.
     * 
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public AgreementContent withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * The locale and language code for this agreement.
     * 
     */
    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    /**
     * The locale and language code for this agreement.
     * 
     */
    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public AgreementContent withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    /**
     * The mime type of this agreement content.
     * 
     */
    @JsonProperty("mime_type")
    public String getMimeType() {
        return mimeType;
    }

    /**
     * The mime type of this agreement content.
     * 
     */
    @JsonProperty("mime_type")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public AgreementContent withMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public AgreementContent withDateCreated(DateTime dateCreated) {
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

    public AgreementContent withDateModified(DateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

}
