package io.klustr.console;

import com.google.common.collect.Lists;
import io.klustr.integrations.kong.interfaces.ServiceRegistry;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.services.ApiServiceRoutesSpecification;
import io.klustr.schemas.services.ApiServiceSpecification;
import io.klustr.schemas.services.ServiceRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.services.*;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.PublicIpResolution;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides registration and configuration of APIs that are available
 * to be consumed by clients. This will enable APIs to be registered in our unified
 * gateway as well as to support billing, usage, and other protections.
 */
@RestController
@Component
@RequestMapping("/console/gateway")
@Tag(name = "Service Catalog API", description = "Management of service catalog which registers services and APIs.")
public class ApiCatalogService {

    private final Storage storage;

    private final ServiceRegistry kong;

    public ApiCatalogService(Storage storage, ServiceRegistry kong) {
        this.storage = storage;
        this.kong = kong;
    }

    private static ApiServiceSpecification toSpec(String projectId, ServiceRegistrationRequest payload) {

        String path = payload.getPath();
        if (path.contains("../")) {
            throw new RuntimeException("Invalid request, user trying to hijack gateway.");
        }
        path = path.replaceAll("/" + projectId, "");
        path = path.replaceAll("//", "/");
        if (path.indexOf("/") != 0) {
            path = path + "/";
        }

        return new ApiServiceSpecification()
                .withId(payload.getId() != null ? payload.getId() : UUID.randomUUID().toString())
                .withIcon(payload.getIcon())
                .withCode(payload.getCode())
                .withCreationDate(DateTime.now())
                .withName(payload.getName())
                .withRoutes(Lists.newArrayList(
                        new ApiServiceRoutesSpecification().withHosts(
                                Lists.newArrayList("gateway.dev.klustr.io")
                        ).withPaths(Lists.newArrayList(
                                ("/" + projectId + path)
                        ))
                ))
                .withExternalId(payload.getId())
                .withOrgId(payload.getOrgId())
                .withProjectId(projectId)
                .withSummary(payload.getSummary())
                .withUrl(payload.getUrl())
                .withPingRoute(payload.getPingApiUrl())
                .withOpenApiUrl(payload.getOpenApiUrl())
                .withDocumentationUrl(payload.getDocumentationUrl())
                .withSupportUrl(payload.getTermsUrl())
                .withSecurity(payload.getSecurity())
                .withStatus(payload.getStatus() != null ? payload.getStatus() : ServiceEntryStatus.ALPHA);
    }

    @Operation(
operationId = "getPublicIpAddresses",
            summary = "Retrieve public IPs for external service security.",
            description = """
This endpoint provides the public IP addresses necessary for securing your external services against unauthorized access. Proper configuration of firewalls using these IPs is crucial for maintaining the integrity and security of your APIs. Utilize this information to strengthen your application's security framework.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.get')")
    @GetMapping("/{projectId}/ips")
    public SecurityConfiguration getPublicIp() {
        Optional<PublicIpResolution.IpResponse> ip = PublicIpResolution.getPublicIp();
        SecurityConfiguration res = new SecurityConfiguration();
        if (ip.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Could not find the public IP address."
            );
        }
        res.allow_ips.add(ip.get().ip);
        return res;
    }

    public static class SecurityConfiguration {
        public List<String> allow_ips = Lists.newArrayList();
    }

    @GetMapping
    @Operation(
operationId = "listApiCatalog",
            summary = "Retrieve a paginated list of available APIs",
            description = """
This endpoint provides a paginated list of APIs that clients can access through the unified gateway. It includes essential information for managing API usage and access, ensuring effective service delivery and compliance with usage policies.
""",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "The API list",
                            content = {
                                    @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ApiServiceSpecification.class)))
                            })
            },
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.list')")
    public List<ApiServiceSpecification> list(
            @RequestParam(value = "limit", defaultValue = "1024") Integer limit,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        // DbQuery fq = Db.query("status").eq("general_availability");
        Pagination pagination = new Pagination().withLimit(limit).withStart(start);
        return this.storage.api_catalog().insecureQuery(pagination).docs;
    }

    @GetMapping("/{serviceId}")
    @Operation(
operationId = "getApiSpecification",
            summary = "Retrieve API specification for a specific service.",
            description = """
This endpoint provides the detailed API specification for the specified service ID. It is designed for users with the necessary permissions to access and understand the API's capabilities and configurations. Ensure you have the appropriate access rights to retrieve this information.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.get')")
    public ApiServiceSpecification get(@PathVariable("serviceId") String serviceId,
                                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return this.storage.api_catalog().getObject(serviceId);
    }

    protected ApiServiceSpecification create(ApiServiceSpecification req, OAuth2AuthenticatedPrincipal oauth) {
        this.storage.api_catalog().insertObject(req.getId(), req);
        this.onCreate(req, oauth);
        return req;
    }

    @PostMapping("/{projectId}")
    @Operation(
operationId = "createApiSpecification",
            summary = "Create a new API specification for a project",
            description = """
This endpoint allows users to create a new API specification within a specified project. It is crucial for registering APIs in the unified gateway, which supports billing, usage tracking, and other protective measures. Ensure that proper authentication is provided to access this functionality.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.create')")
    public ApiServiceSpecification create(
            @PathVariable("projectId") String projectId,
            @RequestBody ServiceRegistrationRequest payload,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO: user have rights to this project?

        if (StringUtils.isBlank(payload.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing name"
            );
        }
        if (StringUtils.isBlank(payload.getUrl())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing URL"
            );
        }
        if (StringUtils.isBlank(payload.getPath())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing path"
            );
        }
        if (StringUtils.isBlank(payload.getId())) {
            String id = projectId + "-" + payload.getCode();
            payload.setId(id);
        }

        boolean codeOk = isCodeOk(payload.getId());
        if (!codeOk) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad code is either invalid or is already claimed."
            );
        }

        ApiServiceSpecification spec = toSpec(projectId, payload);

        ApiServiceSpecification exists = this.storage.api_catalog().getObject(payload.getId());
        if (exists == null) {
            this.storage.api_catalog().insertObject(spec.getId(), spec);
            this.onCreate(spec, oauth);
        } else {
            this.storage.api_catalog().updateObject(spec.getId(), spec);
            this.onUpdate(spec, oauth);
        }

        return spec;
    }

    @PutMapping("/{projectId}/{serviceId}")
    @Operation(
operationId = "updateApiSpecification",
            summary = "Update details of a registered API specification.",
            description = """
This endpoint allows authorized users to modify the specifications of a registered API within the project. Ensure that the request body contains valid data to facilitate a successful update. This operation is crucial for maintaining accurate API information in the service catalog.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.update')")
    public ApiServiceSpecification update(
            @PathVariable("projectId") String projectId,
            @PathVariable("serviceId") String serviceId,
                                          @RequestBody ServiceRegistrationRequest payload,
                                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        payload.setId(serviceId);   // ensure same
        ApiServiceSpecification spec = toSpec(projectId, payload);
        spec = spec.withProjectId(projectId);
        this.storage.api_catalog().updateObject(serviceId, spec);
        this.onUpdate(spec, oauth);
        return spec;
    }

    @DeleteMapping("/{serviceId}")
    @Operation(
operationId = "deleteApiCatalog",
            summary = "Remove an API from the service catalog",
            description = """
This operation deletes an API from the catalog, ensuring it is no longer accessible for consumption. It plays a vital role in maintaining the relevance and integrity of available APIs. Use this endpoint to effectively manage your API lifecycle and ensure your offerings are up-to-date.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('api.catalogs.delete')")
    public ApiServiceSpecification delete(@PathVariable("serviceId") String serviceId,
                                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        ApiServiceSpecification object = this.storage.api_catalog().getObject(serviceId);
        this.onDelete(object, oauth);
        object.setStatus(ServiceEntryStatus.DELETED);
        this.storage.api_catalog().updateObject(serviceId, object);
        return object;
    }

    /**
     * Codes must be unique in all organizations and layouts.
     */
    private boolean isCodeOk(String code) {
        if (!code.matches("^(?!-)[a-z0-9]+(-[a-z0-9]+)*$")) {
            return false;
        }

        // code must be formatted correctly
        String id = code.toLowerCase();
        Optional<ApiServiceSpecification> spec = this.storage.api_catalog().tryGetObject(id);
        return spec.isEmpty();
    }

    protected void onCreate(ApiServiceSpecification obj, OAuth2AuthenticatedPrincipal principal) {
        EntityReference ref = this.kong.create(obj);
        obj.setExternalId(ref.getId());
        this.storage.api_catalog().updateObject(obj.getId(), obj);
    }

    protected void onDelete(ApiServiceSpecification obj, OAuth2AuthenticatedPrincipal principal) {
        this.kong.delete(obj);
    }

    protected void onUpdate(ApiServiceSpecification obj, OAuth2AuthenticatedPrincipal principal) {
        this.kong.update(obj);
    }
}
