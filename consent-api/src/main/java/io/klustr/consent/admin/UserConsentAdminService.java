package io.klustr.consent.admin;

import io.klustr.schemas.console.consent.UserConsentHierarchy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.consent.UserConsentProvider;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.consent.responses.UserConsent360ResponseBody;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Administrative API for consent auditing.
 */
@RestController
@Component
@RequestMapping("/consent/admin")
@Tag(name = "Consent Admin APIs", description = "Provides access to consent and privacy operations.")
public class UserConsentAdminService {

    private static final Logger log = LoggerFactory.getLogger(UserConsentAdminService.class);

    private final UserConsentRepository userConsent;
    private final UserConsentProvider provider;
    private final Storage storage;

    public UserConsentAdminService(
            Storage storage,
            UserConsentProvider provider,
            UserConsentRepository userConsent) {
        this.userConsent = userConsent;
        this.provider = provider;
        this.storage = storage;
    }

    @GetMapping("/{userId}")
    @Operation(
operationId = "adminGetAllUserConsent",
            summary = "Retrieve all consents for a specified user.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
This endpoint allows administrators to access and list all consent records associated with a specific user. It is intended for auditing purposes and ensures that consent management is transparent and compliant with privacy regulations.
"""
)
    @PreAuthorize("hasAuthority('user.consent.get')")
    public UserConsentHierarchy getAllUserConsent(@PathVariable("userId") String userId,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        return this.provider.getHierarchy(userId);
    }

    @GetMapping("/{userId}/apps/{appId}")
    @Operation(
operationId = "adminGetApplicationUserConsent",
            summary = "Retrieve user consent for a specific application.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
Fetches all consent records associated with the given user ID and application ID. This endpoint is intended for administrative use, allowing authorized personnel to audit user consents for compliance and privacy management.
"""
)
    @PreAuthorize("hasAuthority('user.consent.get')")
    public UserConsent360ResponseBody getApplicationUserConsent(@PathVariable("userId") String userId,
                                                                @PathVariable("appId") String appId,
                                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return this.provider.get360ConsentByAppId(userId, appId);
    }

    @DeleteMapping("/{userId}/apps/{appId}")
    @Operation(
operationId = "adminRevokeApplicationUserConsent",
            summary = "Admin removes user consent for a specific application.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
This endpoint allows administrators to revoke consent granted by a user for a specific application. It ensures that all associated permissions are removed, enhancing user privacy and control over their data. Use this operation to manage user consents effectively.
"""
)
    @PreAuthorize("hasAuthority('user.consent.delete')")
    public void revokeApplicationUserConsent(@PathVariable("userId") String userId,
                                             @PathVariable("appId") String appId) {
        this.provider.revokeAppConsent(userId, appId);
    }

    @DeleteMapping("/{userId}/experiments/{experiment_id}")
    @Operation(
operationId = "adminRevokeExperimentConsent",
            summary = "Admin remove consent for a specific experiment.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
This endpoint allows administrators to revoke consent for a user in relation to a specific experiment. It ensures that the user's preferences are respected and that their data is managed appropriately. Use this operation to maintain compliance with consent requirements.
"""
)
    @PreAuthorize("hasAuthority('user.consent.delete')")
    public void revokeExperimentConsent(@PathVariable("userId") String userId,
                                        @PathVariable("experiment_id") String experimentId) {
        this.provider.removeConsentForExperiment(userId, experimentId);
    }


    @DeleteMapping("/{userId}/apps/{appId}/scopes/{scope}")
    @Operation(
operationId = "adminRevokeApplicationFineGrainConsent",
            summary = "Remove specific consent for a client application.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
Revokes a specific consent scope associated with a client application for a user. This operation is intended for administrative users to manage consent settings effectively. It ensures that user privacy preferences are respected and maintained.
"""
)
    @PreAuthorize("hasAuthority('user.consent.delete')")
    public void revokeApplicationFineGrainConsent(@PathVariable("userId") String userId,
                                                  @PathVariable("appId") String appId,
                                                  @PathVariable("scope") String scope) {
        this.provider.revokeAppScope(userId, appId, scope);
    }
}
