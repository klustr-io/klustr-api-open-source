
package io.klustr.schemas.console.apps;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * AppContactInformation
 * <p>
 * The contact information this an application
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "support_email",
    "developer_emails"
})
@Generated("jsonschema2pojo")
public class AppContactInformation {

    /**
     * The email to use when asking for support for this app.
     * 
     */
    @JsonProperty("support_email")
    @JsonPropertyDescription("The email to use when asking for support for this app.")
    private String supportEmail;
    /**
     * Email addresses for us to contact in case of breaking changes or API updates
     * 
     */
    @JsonProperty("developer_emails")
    @JsonPropertyDescription("Email addresses for us to contact in case of breaking changes or API updates")
    private List<String> developerEmails = new ArrayList<String>();

    /**
     * The email to use when asking for support for this app.
     * 
     */
    @JsonProperty("support_email")
    public String getSupportEmail() {
        return supportEmail;
    }

    /**
     * The email to use when asking for support for this app.
     * 
     */
    @JsonProperty("support_email")
    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public AppContactInformation withSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
        return this;
    }

    /**
     * Email addresses for us to contact in case of breaking changes or API updates
     * 
     */
    @JsonProperty("developer_emails")
    public List<String> getDeveloperEmails() {
        return developerEmails;
    }

    /**
     * Email addresses for us to contact in case of breaking changes or API updates
     * 
     */
    @JsonProperty("developer_emails")
    public void setDeveloperEmails(List<String> developerEmails) {
        this.developerEmails = developerEmails;
    }

    public AppContactInformation withDeveloperEmails(List<String> developerEmails) {
        this.developerEmails = developerEmails;
        return this;
    }

}
