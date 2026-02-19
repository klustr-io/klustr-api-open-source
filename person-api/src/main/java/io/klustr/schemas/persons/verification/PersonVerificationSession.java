
package io.klustr.schemas.persons.verification;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PersonVerificationSession
 * <p>
 * Request to start a verification for a user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sessionToken",
    "url",
    "id",
    "status",
    "creation_date",
    "modified_date",
    "original_request"
})
@Generated("jsonschema2pojo")
public class PersonVerificationSession {

    /**
     * The session token to use.
     * 
     */
    @JsonProperty("sessionToken")
    @JsonPropertyDescription("The session token to use.")
    private String sessionToken;
    /**
     * The URL that can be used to initiate the verification.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The URL that can be used to initiate the verification.")
    private String url;
    /**
     * The unique ID of this session request
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this session request")
    private String id;
    /**
     * The status of this request.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this request.")
    private String status;
    /**
     * The timestamp that this session was created
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The timestamp that this session was created")
    private String creationDate;
    /**
     * The timestamp that this session was last modified
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The timestamp that this session was last modified")
    private String modifiedDate;
    /**
     * PersonVerificationRequest
     * <p>
     * Request to start a verification for a user.
     * 
     */
    @JsonProperty("original_request")
    @JsonPropertyDescription("Request to start a verification for a user.")
    private PersonVerificationRequest originalRequest;

    /**
     * The session token to use.
     * 
     */
    @JsonProperty("sessionToken")
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * The session token to use.
     * 
     */
    @JsonProperty("sessionToken")
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public PersonVerificationSession withSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    /**
     * The URL that can be used to initiate the verification.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The URL that can be used to initiate the verification.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public PersonVerificationSession withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The unique ID of this session request
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this session request
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public PersonVerificationSession withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The status of this request.
     * 
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * The status of this request.
     * 
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public PersonVerificationSession withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * The timestamp that this session was created
     * 
     */
    @JsonProperty("creation_date")
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * The timestamp that this session was created
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public PersonVerificationSession withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The timestamp that this session was last modified
     * 
     */
    @JsonProperty("modified_date")
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The timestamp that this session was last modified
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public PersonVerificationSession withModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * PersonVerificationRequest
     * <p>
     * Request to start a verification for a user.
     * 
     */
    @JsonProperty("original_request")
    public PersonVerificationRequest getOriginalRequest() {
        return originalRequest;
    }

    /**
     * PersonVerificationRequest
     * <p>
     * Request to start a verification for a user.
     * 
     */
    @JsonProperty("original_request")
    public void setOriginalRequest(PersonVerificationRequest originalRequest) {
        this.originalRequest = originalRequest;
    }

    public PersonVerificationSession withOriginalRequest(PersonVerificationRequest originalRequest) {
        this.originalRequest = originalRequest;
        return this;
    }

}
