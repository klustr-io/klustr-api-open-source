
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffSessionInformation
 * <p>
 * Information on the status and links to execute the session.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "url",
    "vendorData",
    "host",
    "status",
    "sessionToken"
})
@Generated("jsonschema2pojo")
public class VeriffSessionInformation {

    /**
     * String UUID v4 which identifies the verification session
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("String UUID v4 which identifies the verification session")
    private String id;
    /**
     * URL of the verification to which the person is redirected (Combination of the baseUrl and sessionToken)
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("URL of the verification to which the person is redirected (Combination of the baseUrl and sessionToken)")
    private String url;
    /**
     * Customer-specific data string, max 1000 characters long
     * 
     */
    @JsonProperty("vendorData")
    @JsonPropertyDescription("Customer-specific data string, max 1000 characters long")
    private String vendorData;
    /**
     * The base url the sessionToken can be used for
     * 
     */
    @JsonProperty("host")
    @JsonPropertyDescription("The base url the sessionToken can be used for")
    private String host;
    /**
     * Verification session status
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("Verification session status")
    private String status;
    /**
     * Session-specific token of the verification
     * 
     */
    @JsonProperty("sessionToken")
    @JsonPropertyDescription("Session-specific token of the verification")
    private String sessionToken;

    /**
     * String UUID v4 which identifies the verification session
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * String UUID v4 which identifies the verification session
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public VeriffSessionInformation withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * URL of the verification to which the person is redirected (Combination of the baseUrl and sessionToken)
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * URL of the verification to which the person is redirected (Combination of the baseUrl and sessionToken)
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public VeriffSessionInformation withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Customer-specific data string, max 1000 characters long
     * 
     */
    @JsonProperty("vendorData")
    public String getVendorData() {
        return vendorData;
    }

    /**
     * Customer-specific data string, max 1000 characters long
     * 
     */
    @JsonProperty("vendorData")
    public void setVendorData(String vendorData) {
        this.vendorData = vendorData;
    }

    public VeriffSessionInformation withVendorData(String vendorData) {
        this.vendorData = vendorData;
        return this;
    }

    /**
     * The base url the sessionToken can be used for
     * 
     */
    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    /**
     * The base url the sessionToken can be used for
     * 
     */
    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    public VeriffSessionInformation withHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Verification session status
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Verification session status
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public VeriffSessionInformation withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Session-specific token of the verification
     * 
     */
    @JsonProperty("sessionToken")
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Session-specific token of the verification
     * 
     */
    @JsonProperty("sessionToken")
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public VeriffSessionInformation withSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

}
