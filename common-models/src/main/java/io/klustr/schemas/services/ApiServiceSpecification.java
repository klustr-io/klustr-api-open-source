
package io.klustr.schemas.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.services.ServiceEntryStatus;
import io.klustr.schemas.console.services.ServiceSecurityRequirement;
import org.joda.time.DateTime;


/**
 * ApiServiceSpecification
 * <p>
 * Reference to a service.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "code",
    "summary",
    "org_id",
    "project_id",
    "icon",
    "creation_date",
    "external_id",
    "url",
    "documentation_url",
    "terms_url",
    "health_check_url",
    "open_api_url",
    "mock_api_url",
    "ping_route",
    "price_url",
    "support_url",
    "webhook_url",
    "status",
    "consent_scopes",
    "routes",
    "tags",
    "permissions",
    "security"
})
@Generated("jsonschema2pojo")
public class ApiServiceSpecification {

    /**
     * The unique ID for this service
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this service")
    private String id;
    /**
     * The name of the service.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the service.")
    private String name;
    /**
     * The code of this service for registration and tagging.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The code of this service for registration and tagging.")
    private String code;
    /**
     * The simple summary of this service.
     * 
     */
    @JsonProperty("summary")
    @JsonPropertyDescription("The simple summary of this service.")
    private String summary;
    /**
     * The organization that owns this specific API.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that owns this specific API.")
    private String orgId;
    /**
     * The project that manages this API specification.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project that manages this API specification.")
    private String projectId;
    /**
     * A reference icon for this service
     * 
     */
    @JsonProperty("icon")
    @JsonPropertyDescription("A reference icon for this service")
    private String icon;
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("A link to an external definition of this service")
    private String externalId;
    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("A link to an external definition of this service")
    private String url;
    /**
     * The url containing additional documentation about this service.
     * 
     */
    @JsonProperty("documentation_url")
    @JsonPropertyDescription("The url containing additional documentation about this service.")
    private String documentationUrl;
    /**
     * The url containing additional terms that users must agree for this service.
     * 
     */
    @JsonProperty("terms_url")
    @JsonPropertyDescription("The url containing additional terms that users must agree for this service.")
    private String termsUrl;
    /**
     * The health check API endpoint for this to determine if alive or up.
     * 
     */
    @JsonProperty("health_check_url")
    @JsonPropertyDescription("The health check API endpoint for this to determine if alive or up.")
    private String healthCheckUrl;
    /**
     * The open API json url
     * 
     */
    @JsonProperty("open_api_url")
    @JsonPropertyDescription("The open API json url")
    private String openApiUrl;
    /**
     * If the service has a mock API endpoint that can help users integrate or test.
     * 
     */
    @JsonProperty("mock_api_url")
    @JsonPropertyDescription("If the service has a mock API endpoint that can help users integrate or test.")
    private String mockApiUrl;
    /**
     * The route to use to check and ping the service.
     * 
     */
    @JsonProperty("ping_route")
    @JsonPropertyDescription("The route to use to check and ping the service.")
    private String pingRoute;
    /**
     * The url containing additional documentation about the pricing of this service.
     * 
     */
    @JsonProperty("price_url")
    @JsonPropertyDescription("The url containing additional documentation about the pricing of this service.")
    private String priceUrl;
    /**
     * The url containing additional documentation about how to get support for this service.
     * 
     */
    @JsonProperty("support_url")
    @JsonPropertyDescription("The url containing additional documentation about how to get support for this service.")
    private String supportUrl;
    /**
     * The url you will be called when a person subscribes or unsubscribes from this service.
     * 
     */
    @JsonProperty("webhook_url")
    @JsonPropertyDescription("The url you will be called when a person subscribes or unsubscribes from this service.")
    private String webhookUrl;
    /**
     * ServiceEntryStatus
     * <p>
     * The status of the service
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of the service")
    private ServiceEntryStatus status;
    /**
     * The scopes that this service needs permissions for in order to be used.
     * 
     */
    @JsonProperty("consent_scopes")
    @JsonPropertyDescription("The scopes that this service needs permissions for in order to be used.")
    private List<String> consentScopes = new ArrayList<String>();
    @JsonProperty("routes")
    private List<ApiServiceRoutesSpecification> routes = new ArrayList<ApiServiceRoutesSpecification>();
    @JsonProperty("tags")
    private List<String> tags = new ArrayList<String>();
    /**
     * ServicePermissions
     * <p>
     * The permissions required to be able to access this API catalog and to see it
     * 
     */
    @JsonProperty("permissions")
    @JsonPropertyDescription("The permissions required to be able to access this API catalog and to see it")
    private ServicePermissions permissions;
    /**
     * ServiceEntryStatus
     * <p>
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    @JsonPropertyDescription("The security to activate on this service.")
    private ServiceSecurityRequirement security;

    /**
     * The unique ID for this service
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this service
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ApiServiceSpecification withId(String id) {
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

    public ApiServiceSpecification withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The code of this service for registration and tagging.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The code of this service for registration and tagging.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public ApiServiceSpecification withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * The simple summary of this service.
     * 
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * The simple summary of this service.
     * 
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ApiServiceSpecification withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * The organization that owns this specific API.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that owns this specific API.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ApiServiceSpecification withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The project that manages this API specification.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project that manages this API specification.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ApiServiceSpecification withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * A reference icon for this service
     * 
     */
    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    /**
     * A reference icon for this service
     * 
     */
    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ApiServiceSpecification withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ApiServiceSpecification withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public ApiServiceSpecification withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * A link to an external definition of this service
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public ApiServiceSpecification withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The url containing additional documentation about this service.
     * 
     */
    @JsonProperty("documentation_url")
    public String getDocumentationUrl() {
        return documentationUrl;
    }

    /**
     * The url containing additional documentation about this service.
     * 
     */
    @JsonProperty("documentation_url")
    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public ApiServiceSpecification withDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
        return this;
    }

    /**
     * The url containing additional terms that users must agree for this service.
     * 
     */
    @JsonProperty("terms_url")
    public String getTermsUrl() {
        return termsUrl;
    }

    /**
     * The url containing additional terms that users must agree for this service.
     * 
     */
    @JsonProperty("terms_url")
    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }

    public ApiServiceSpecification withTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
        return this;
    }

    /**
     * The health check API endpoint for this to determine if alive or up.
     * 
     */
    @JsonProperty("health_check_url")
    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    /**
     * The health check API endpoint for this to determine if alive or up.
     * 
     */
    @JsonProperty("health_check_url")
    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public ApiServiceSpecification withHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
        return this;
    }

    /**
     * The open API json url
     * 
     */
    @JsonProperty("open_api_url")
    public String getOpenApiUrl() {
        return openApiUrl;
    }

    /**
     * The open API json url
     * 
     */
    @JsonProperty("open_api_url")
    public void setOpenApiUrl(String openApiUrl) {
        this.openApiUrl = openApiUrl;
    }

    public ApiServiceSpecification withOpenApiUrl(String openApiUrl) {
        this.openApiUrl = openApiUrl;
        return this;
    }

    /**
     * If the service has a mock API endpoint that can help users integrate or test.
     * 
     */
    @JsonProperty("mock_api_url")
    public String getMockApiUrl() {
        return mockApiUrl;
    }

    /**
     * If the service has a mock API endpoint that can help users integrate or test.
     * 
     */
    @JsonProperty("mock_api_url")
    public void setMockApiUrl(String mockApiUrl) {
        this.mockApiUrl = mockApiUrl;
    }

    public ApiServiceSpecification withMockApiUrl(String mockApiUrl) {
        this.mockApiUrl = mockApiUrl;
        return this;
    }

    /**
     * The route to use to check and ping the service.
     * 
     */
    @JsonProperty("ping_route")
    public String getPingRoute() {
        return pingRoute;
    }

    /**
     * The route to use to check and ping the service.
     * 
     */
    @JsonProperty("ping_route")
    public void setPingRoute(String pingRoute) {
        this.pingRoute = pingRoute;
    }

    public ApiServiceSpecification withPingRoute(String pingRoute) {
        this.pingRoute = pingRoute;
        return this;
    }

    /**
     * The url containing additional documentation about the pricing of this service.
     * 
     */
    @JsonProperty("price_url")
    public String getPriceUrl() {
        return priceUrl;
    }

    /**
     * The url containing additional documentation about the pricing of this service.
     * 
     */
    @JsonProperty("price_url")
    public void setPriceUrl(String priceUrl) {
        this.priceUrl = priceUrl;
    }

    public ApiServiceSpecification withPriceUrl(String priceUrl) {
        this.priceUrl = priceUrl;
        return this;
    }

    /**
     * The url containing additional documentation about how to get support for this service.
     * 
     */
    @JsonProperty("support_url")
    public String getSupportUrl() {
        return supportUrl;
    }

    /**
     * The url containing additional documentation about how to get support for this service.
     * 
     */
    @JsonProperty("support_url")
    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    public ApiServiceSpecification withSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
        return this;
    }

    /**
     * The url you will be called when a person subscribes or unsubscribes from this service.
     * 
     */
    @JsonProperty("webhook_url")
    public String getWebhookUrl() {
        return webhookUrl;
    }

    /**
     * The url you will be called when a person subscribes or unsubscribes from this service.
     * 
     */
    @JsonProperty("webhook_url")
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public ApiServiceSpecification withWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        return this;
    }

    /**
     * ServiceEntryStatus
     * <p>
     * The status of the service
     * 
     */
    @JsonProperty("status")
    public ServiceEntryStatus getStatus() {
        return status;
    }

    /**
     * ServiceEntryStatus
     * <p>
     * The status of the service
     * 
     */
    @JsonProperty("status")
    public void setStatus(ServiceEntryStatus status) {
        this.status = status;
    }

    public ApiServiceSpecification withStatus(ServiceEntryStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The scopes that this service needs permissions for in order to be used.
     * 
     */
    @JsonProperty("consent_scopes")
    public List<String> getConsentScopes() {
        return consentScopes;
    }

    /**
     * The scopes that this service needs permissions for in order to be used.
     * 
     */
    @JsonProperty("consent_scopes")
    public void setConsentScopes(List<String> consentScopes) {
        this.consentScopes = consentScopes;
    }

    public ApiServiceSpecification withConsentScopes(List<String> consentScopes) {
        this.consentScopes = consentScopes;
        return this;
    }

    @JsonProperty("routes")
    public List<ApiServiceRoutesSpecification> getRoutes() {
        return routes;
    }

    @JsonProperty("routes")
    public void setRoutes(List<ApiServiceRoutesSpecification> routes) {
        this.routes = routes;
    }

    public ApiServiceSpecification withRoutes(List<ApiServiceRoutesSpecification> routes) {
        this.routes = routes;
        return this;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ApiServiceSpecification withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * ServicePermissions
     * <p>
     * The permissions required to be able to access this API catalog and to see it
     * 
     */
    @JsonProperty("permissions")
    public ServicePermissions getPermissions() {
        return permissions;
    }

    /**
     * ServicePermissions
     * <p>
     * The permissions required to be able to access this API catalog and to see it
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(ServicePermissions permissions) {
        this.permissions = permissions;
    }

    public ApiServiceSpecification withPermissions(ServicePermissions permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * ServiceEntryStatus
     * <p>
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    public ServiceSecurityRequirement getSecurity() {
        return security;
    }

    /**
     * ServiceEntryStatus
     * <p>
     * The security to activate on this service.
     * 
     */
    @JsonProperty("security")
    public void setSecurity(ServiceSecurityRequirement security) {
        this.security = security;
    }

    public ApiServiceSpecification withSecurity(ServiceSecurityRequirement security) {
        this.security = security;
        return this;
    }

}
