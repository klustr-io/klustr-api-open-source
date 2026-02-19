package io.klustr.console.org;

import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("/directory/admin/orgs")
@Tag(name = "Org Management", description = "Organization management functions.")
public class OrgAdminService {
    private static final Logger log = LoggerFactory.getLogger(OrgService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public OrgAdminService(Storage storage,
                      PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    @GetMapping
    @Operation(
operationId = "adminListOrganizations",
            summary = "Retrieve a list of organizations for admin users.",
            description = """
This endpoint allows admin users to list all organizations within the system. It supports pagination through the 'limit' and 'start' parameters, enabling efficient data retrieval. Use this to manage organizational data effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.directory.list')")
    public DocumentResult<Org> list(
            @Parameter(name = "limit", description = "The maximum number of rows to return")
            @RequestParam(value = "limit", defaultValue = "1024") int limit,
            @Parameter(name = "start", description = "The starting index for pagination.")
            @RequestParam(value = "start", defaultValue = "0") int start,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principle) {
        return this.storage.organizations().insecureQuery(new Pagination().withLimit(limit).withStart(start));
    }
}
