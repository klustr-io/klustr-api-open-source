
package io.klustr.schemas.console.projects;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateCredentialsConfiguration
 * <p>
 * Credentials are required for this project to leverage and connect. Can configure 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "basic_credentials"
})
@Generated("jsonschema2pojo")
public class TemplateCredentialsConfiguration {

    /**
     * TemplateCredentialsBasicConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("basic_credentials")
    private TemplateCredentialsBasicConfiguration basicCredentials;

    /**
     * TemplateCredentialsBasicConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("basic_credentials")
    public TemplateCredentialsBasicConfiguration getBasicCredentials() {
        return basicCredentials;
    }

    /**
     * TemplateCredentialsBasicConfiguration
     * <p>
     * 
     * 
     */
    @JsonProperty("basic_credentials")
    public void setBasicCredentials(TemplateCredentialsBasicConfiguration basicCredentials) {
        this.basicCredentials = basicCredentials;
    }

    public TemplateCredentialsConfiguration withBasicCredentials(TemplateCredentialsBasicConfiguration basicCredentials) {
        this.basicCredentials = basicCredentials;
        return this;
    }

}
