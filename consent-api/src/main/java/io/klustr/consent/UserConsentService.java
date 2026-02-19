package io.klustr.consent;

import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.consent.UserConsentHierarchy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Returns the consent for the specified user and enables
 * control over consent and privacy by enabling users to reject
 * any prior consent and invalidating any and all access and
 * refresh tokens and access tokens.
 */
@RestController
@Component
@RequestMapping("/consent/me")
@Tag(name = "Consent APIs", description = "Provides access to consent and privacy operations.")
public class UserConsentService {

    private static final Logger log = LoggerFactory.getLogger(UserConsentService.class);

    private final UserConsentProvider provider;

    public UserConsentService(UserConsentProvider provider) {
        this.provider = provider;
    }

    @GetMapping
    @Operation(
operationId = "getMyConsents",
            summary = "Retrieve active consents for the current user.",
            description = """
This endpoint provides a comprehensive view of all active consents associated with the user. It empowers users to understand their consent status and manage their privacy preferences effectively. This visibility is essential for compliance with privacy regulations and for making informed decisions about data sharing.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The consent scopes available", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserApplicationConsent.class)))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_consent.read')")
    public UserConsentHierarchy getConsent(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        return this.provider.getHierarchy(email);
    }

    @GetMapping("/apps/{appId}")
    @Operation(
operationId = "getMyConsentForApplication",
            summary = "Retrieve my consent for a specific application",
            description = """
Fetches the current consent status for the specified application. This includes details such as the last update time and last access time. Users can manage their consent and privacy preferences effectively through this operation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_consent.read')")
    public UserApplicationConsent getConsentForApplication(
            @PathVariable("appId") String app_id,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        List<UserApplicationConsent> consent = this.provider.getUserConsent(email);
        Optional<UserApplicationConsent> match = consent.stream().filter(x -> x.getAppId().equalsIgnoreCase(app_id)).findFirst();
        return match.get();
    }

    @DeleteMapping("/apps/{appId}")
    @Operation(
operationId = "revokeMyConsentForApp",
            summary = "Revoke my consent for a specific application",
            description = """
This endpoint allows users to revoke their consent for a specific application. It invalidates all associated OAuth 2.0 Access Tokens, requiring re-authentication. This action enhances user privacy and control over consent preferences.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_consent.update')")
    public void removeConsentForApp(
            @PathVariable("appId") String app_id,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        this.provider.revokeAppConsent(email, app_id);
    }

    @DeleteMapping("/experiments/{experimentId}")
    @Operation(
operationId = "revokeMyConsentForExperiment",
            summary = "Revoke my consent for a specific experiment.",
            parameters = {
                    @Parameter(name = "client_id", description = "The client ID to perform the consent operation against.", required = true),
                    @Parameter(name = "experiment_id", description = "The experiment ID to perform the consent operation against.", required = true)
            },
            description = """
This endpoint allows users to revoke their consent for a specific experiment. It invalidates any prior consent along with all associated access and refresh tokens. This operation is essential for maintaining user privacy and control over personal data.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_consent.update')")
    public void removeConsentForExperiment(
            @PathVariable("experimentId") String experimentId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        this.provider.removeConsentForExperiment(email, experimentId);
    }

    @DeleteMapping("/apps/{appId}/scopes/{scope}")
    @Operation(
operationId = "revokeMyConsentForApp",
            summary = "Revoke user consent for a specific application scope",
            description = """
This endpoint allows users to revoke consent for specific scopes linked to an application. Users can selectively maintain certain consents while removing others, thereby enhancing their control over privacy and consent management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('SCOPE_consent.update')")
    public void removeConsentForApp(@PathVariable("appId") String app_id,
                                    @PathVariable("scope") String scope,
                                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        this.provider.revokeAppScope(email, app_id, scope);
    }


}
