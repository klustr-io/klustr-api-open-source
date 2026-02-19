
package io.klustr.schemas.services;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.services.ServiceEntryStatus;
import io.klustr.schemas.console.services.ServiceSecurityRequirement;


/**
 * ServiceRegistrationRequest
 * <p>
 * Information about a particular API for registration.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "code",
    "org_id",
    "summary",
    "path",
    "icon",
    "url",
    "documentation_url",
    "terms_url",
    "open_api_url",
    "ping_api_url",
    "status",
    "security"
})
@Generated("jsonschema2pojo")
public class ServiceRegistrationRequest {

    /**
     * The ID of the service to update or create, if provided will update.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The ID of the service to update or create, if provided will update.")
    private String id;
    /**
     * The name of the service.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the service.")
    private String name;
    /**
     * The unique code of this service.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The unique code of this service.")
    private String code;
    /**
     * The organization that owns this service.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that owns this service.")
    private String orgId;
    /**
     * The quick summary of this service for basic information on why and when to use it.
     * 
     */
    @JsonProperty("summary")
    @JsonPropertyDescription("The quick summary of this service for basic information on why and when to use it.")
    private String summary;
    /**
     * The path for this service when registered in the API gateway.
     * 
     */
    @JsonProperty("path")
    @JsonPropertyDescription("The path for this service when registered in the API gateway.")
    private String path;
    /**
     * The image or logo to associate with this API which can be base64 or a URL.
     * 
     */
    @JsonProperty("icon")
    @JsonPropertyDescription("The image or logo to associate with this API which can be base64 or a URL.")
    private String icon;
    /**
     * The full URL for this service.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The full URL for this service.")
    private String url;
    /**
     * The URL where additional documentation about this service can be found
     * 
     */
    @JsonProperty("documentation_url")
    @JsonPropertyDescription("The URL where additional documentation about this service can be found")
    private String documentationUrl;
    /**
     * The URL where terms and conditions of this API are used.
     * 
     */
    @JsonProperty("terms_url")
    @JsonPropertyDescription("The URL where terms and conditions of this API are used.")
    private String termsUrl;
    /**
     * The URL where the open api specification is defined.
     * 
     */
    @JsonProperty("open_api_url")
    @JsonPropertyDescription("The URL where the open api specification is defined.")
    private String openApiUrl;
    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("ping_api_url")
    @JsonPropertyDescription("The URL used to check if the service is active. If left blank we assume the default route is ok.")
    private String pingApiUrl;
    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The URL used to check if the service is active. If left blank we assume the default route is ok.")
    private ServiceEntryStatus status;
    /**
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    @JsonPropertyDescription("The security to activate on this service.")
    private ServiceSecurityRequirement security;

    /**
     * The ID of the service to update or create, if provided will update.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The ID of the service to update or create, if provided will update.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ServiceRegistrationRequest withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The name of the service.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the service.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ServiceRegistrationRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The unique code of this service.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The unique code of this service.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public ServiceRegistrationRequest withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * The organization that owns this service.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that owns this service.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ServiceRegistrationRequest withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The quick summary of this service for basic information on why and when to use it.
     * 
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * The quick summary of this service for basic information on why and when to use it.
     * 
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ServiceRegistrationRequest withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * The path for this service when registered in the API gateway.
     * 
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * The path for this service when registered in the API gateway.
     * 
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    public ServiceRegistrationRequest withPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * The image or logo to associate with this API which can be base64 or a URL.
     * 
     */
    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    /**
     * The image or logo to associate with this API which can be base64 or a URL.
     * 
     */
    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ServiceRegistrationRequest withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * The full URL for this service.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The full URL for this service.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public ServiceRegistrationRequest withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The URL where additional documentation about this service can be found
     * 
     */
    @JsonProperty("documentation_url")
    public String getDocumentationUrl() {
        return documentationUrl;
    }

    /**
     * The URL where additional documentation about this service can be found
     * 
     */
    @JsonProperty("documentation_url")
    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public ServiceRegistrationRequest withDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
        return this;
    }

    /**
     * The URL where terms and conditions of this API are used.
     * 
     */
    @JsonProperty("terms_url")
    public String getTermsUrl() {
        return termsUrl;
    }

    /**
     * The URL where terms and conditions of this API are used.
     * 
     */
    @JsonProperty("terms_url")
    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }

    public ServiceRegistrationRequest withTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
        return this;
    }

    /**
     * The URL where the open api specification is defined.
     * 
     */
    @JsonProperty("open_api_url")
    public String getOpenApiUrl() {
        return openApiUrl;
    }

    /**
     * The URL where the open api specification is defined.
     * 
     */
    @JsonProperty("open_api_url")
    public void setOpenApiUrl(String openApiUrl) {
        this.openApiUrl = openApiUrl;
    }

    public ServiceRegistrationRequest withOpenApiUrl(String openApiUrl) {
        this.openApiUrl = openApiUrl;
        return this;
    }

    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("ping_api_url")
    public String getPingApiUrl() {
        return pingApiUrl;
    }

    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("ping_api_url")
    public void setPingApiUrl(String pingApiUrl) {
        this.pingApiUrl = pingApiUrl;
    }

    public ServiceRegistrationRequest withPingApiUrl(String pingApiUrl) {
        this.pingApiUrl = pingApiUrl;
        return this;
    }

    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("status")
    public ServiceEntryStatus getStatus() {
        return status;
    }

    /**
     * The URL used to check if the service is active. If left blank we assume the default route is ok.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ServiceEntryStatus status) {
        this.status = status;
    }

    public ServiceRegistrationRequest withStatus(ServiceEntryStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    public ServiceSecurityRequirement getSecurity() {
        return security;
    }

    /**
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    public void setSecurity(ServiceSecurityRequirement security) {
        this.security = security;
    }

    public ServiceRegistrationRequest withSecurity(ServiceSecurityRequirement security) {
        this.security = security;
        return this;
    }

}
