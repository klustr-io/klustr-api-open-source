package io.klustr.consent;

import com.google.common.collect.Maps;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.exceptions.MissingPermissionsException;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Top level configuration of the scope and the related attributes the consent {@link ConsentAttribute}
 * associated for consent administration and policy and governance.
 */
@RestController
@Component
@RequestMapping("/consent/scopes")
@Tag(name = "Consent Scope APIs", description = "Provides access to consent and privacy operations.")
public class ConsentScopeService {

    private final ConsentStorage consent_storage;

    private final PermissionProvider permissions;

    private final OrgPerms org_policy;

    private final Storage storage;

    public ConsentScopeService(
            ConsentStorage consent_storage,
            Storage storage,
            PermissionProvider permissions) {
        this.consent_storage = consent_storage;
        this.permissions = permissions;
        this.storage = storage;
        this.org_policy = new OrgPerms(permissions);
    }

    /**
     * Will list the current {@link ConsentAttribute} that are configured.
     *
     * @return Returns the currently available and registered consent attributes.
     */
    @Operation(
summary = "List configured consent attributes for the organization",
            description = """
Retrieve all currently available consent attributes configured for the specified organization. This endpoint is crucial for consent administration and governance, ensuring compliance with privacy regulations. It provides essential information to manage user consent effectively.
""",
            operationId = "listConfiguredConsentAttributes",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The available consent scopes to use in applications.", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ConsentAttribute.class)))
                    })
            }
)
    @GetMapping("/{organizationId}")
    public List<ConsentAttribute> list(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {

        DbQuery orgFq = Db.query("org_id").eq(organizationId);
        List<ConsentAttribute> orgSpecific = consent_storage.consent_scopes().insecureQuery(orgFq, Pagination.all()).docs;

        DbQuery globalFq = Db.query("org_id").eq("*");
        List<ConsentAttribute> globalDocs = consent_storage.consent_scopes().insecureQuery(globalFq, Pagination.all()).docs;

        Map<String, ConsentAttribute> map = Maps.newTreeMap();

        // global docs first to allow override
        for (ConsentAttribute scope :
                globalDocs) {
            map.put(scope.getId(), scope);
        }

        // allow overrides of original
        for (ConsentAttribute scope :
                orgSpecific) {
            map.put(scope.getId(), scope);
        }

        return map.values().stream().toList();
    }

    /**
     * Will create a new consent attribute and register it for use within the organization.
     *
     * @param scope     The consent attribute to create.
     * @param principal The authorized principal making the request.
     * @return
     */
    @Operation(
summary = "Create a new consent attribute for the organization",
            operationId = "createConsentAttribute",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows an authorized principal to create and register a new consent attribute within the specified organization. It ensures compliance with privacy regulations and organizational policies, facilitating proper management of consent attributes.
"""
)
    @PostMapping("/{organizationId}")
    public ConsentAttribute post(
            @PathVariable("organizationId") String organizationId,
            @RequestBody ConsentAttribute scope,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {

        if (StringUtils.isBlank(scope.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<ConsentAttribute> match = this.consent_storage.consent_scopes().tryGetObject(scope.getId());
        if (match.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT
            );
        }

        scope.setOrgId(organizationId);

        this.consent_storage.consent_scopes().insertObject(scope.getId(), scope);

        return scope;
    }

    @Operation(
summary = "Update consent scope for the specified organization",
            description = """
This endpoint updates the consent scope linked to a specific organization. It ensures that the consent attributes comply with the organization's governance and policies. Use this operation to effectively manage user consent and maintain regulatory compliance.
""",
            operationId = "updateConsentScopeForOrganization",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PutMapping("/{organizationId}/{scopeId}")
    @PreAuthorize("hasAuthority('org.consent.scopes.update')")
    public ConsentAttribute put(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("scopeId") String scopeId,
            @RequestBody ConsentAttribute scope,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {

        Org org = this.storage.organizations().getObject(organizationId);
        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        boolean hasAccess = this.org_policy.hasOrgPermission(principal, orgRoles, "org.consent.scopes.update");
        if (!hasAccess) {
            throw new MissingPermissionsException("org.consent.scopes.update");
        }

        Optional<ConsentAttribute> match = this.consent_storage.consent_scopes().tryGetObject(scopeId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        scope.setId(scopeId);
        scope.setOrgId(organizationId);
        this.consent_storage.consent_scopes().updateObject(scopeId, scope);
        return scope;
    }
}
