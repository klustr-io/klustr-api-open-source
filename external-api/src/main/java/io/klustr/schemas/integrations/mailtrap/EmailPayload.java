
package io.klustr.schemas.integrations.mailtrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * EmailPayload
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to",
    "subject",
    "text",
    "html",
    "timestamp",
    "template_uuid",
    "template_variables"
})
@Generated("jsonschema2pojo")
public class EmailPayload {

    /**
     * EmailReference
     * <p>
     * The source email.
     * 
     */
    @JsonProperty("from")
    @JsonPropertyDescription("The source email.")
    private EmailReference from;
    /**
     * The source email.
     * 
     */
    @JsonProperty("to")
    @JsonPropertyDescription("The source email.")
    private List<EmailReference> to = new ArrayList<EmailReference>();
    /**
     * The subject of this email
     * 
     */
    @JsonProperty("subject")
    @JsonPropertyDescription("The subject of this email")
    private java.lang.String subject;
    /**
     * The text version of this email
     * 
     */
    @JsonProperty("text")
    @JsonPropertyDescription("The text version of this email")
    private java.lang.String text;
    /**
     * The html version of this email
     * 
     */
    @JsonProperty("html")
    @JsonPropertyDescription("The html version of this email")
    private java.lang.String html;
    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The feature that was generated, populated on server side.")
    private DateTime timestamp;
    @JsonProperty("template_uuid")
    private java.lang.String templateUuid;
    /**
     * The variables used to populate the template
     * 
     */
    @JsonProperty("template_variables")
    @JsonPropertyDescription("The variables used to populate the template")
    private Map<String, String> templateVariables;

    /**
     * EmailReference
     * <p>
     * The source email.
     * 
     */
    @JsonProperty("from")
    public EmailReference getFrom() {
        return from;
    }

    /**
     * EmailReference
     * <p>
     * The source email.
     * 
     */
    @JsonProperty("from")
    public void setFrom(EmailReference from) {
        this.from = from;
    }

    public EmailPayload withFrom(EmailReference from) {
        this.from = from;
        return this;
    }

    /**
     * The source email.
     * 
     */
    @JsonProperty("to")
    public List<EmailReference> getTo() {
        return to;
    }

    /**
     * The source email.
     * 
     */
    @JsonProperty("to")
    public void setTo(List<EmailReference> to) {
        this.to = to;
    }

    public EmailPayload withTo(List<EmailReference> to) {
        this.to = to;
        return this;
    }

    /**
     * The subject of this email
     * 
     */
    @JsonProperty("subject")
    public java.lang.String getSubject() {
        return subject;
    }

    /**
     * The subject of this email
     * 
     */
    @JsonProperty("subject")
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }

    public EmailPayload withSubject(java.lang.String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * The text version of this email
     * 
     */
    @JsonProperty("text")
    public java.lang.String getText() {
        return text;
    }

    /**
     * The text version of this email
     * 
     */
    @JsonProperty("text")
    public void setText(java.lang.String text) {
        this.text = text;
    }

    public EmailPayload withText(java.lang.String text) {
        this.text = text;
        return this;
    }

    /**
     * The html version of this email
     * 
     */
    @JsonProperty("html")
    public java.lang.String getHtml() {
        return html;
    }

    /**
     * The html version of this email
     * 
     */
    @JsonProperty("html")
    public void setHtml(java.lang.String html) {
        this.html = html;
    }

    public EmailPayload withHtml(java.lang.String html) {
        this.html = html;
        return this;
    }

    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The feature that was generated, populated on server side.
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public EmailPayload withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("template_uuid")
    public java.lang.String getTemplateUuid() {
        return templateUuid;
    }

    @JsonProperty("template_uuid")
    public void setTemplateUuid(java.lang.String templateUuid) {
        this.templateUuid = templateUuid;
    }

    public EmailPayload withTemplateUuid(java.lang.String templateUuid) {
        this.templateUuid = templateUuid;
        return this;
    }

    /**
     * The variables used to populate the template
     * 
     */
    @JsonProperty("template_variables")
    public Map<String, String> getTemplateVariables() {
        return templateVariables;
    }

    /**
     * The variables used to populate the template
     * 
     */
    @JsonProperty("template_variables")
    public void setTemplateVariables(Map<String, String> templateVariables) {
        this.templateVariables = templateVariables;
    }

    public EmailPayload withTemplateVariables(Map<String, String> templateVariables) {
        this.templateVariables = templateVariables;
        return this;
    }

}
