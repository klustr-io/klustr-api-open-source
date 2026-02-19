package io.klustr.consent.agreements;

import io.klustr.consent.agreements.models.UserAgreementDetails;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Provides access to a users general privacy agreements
 * and acceptance and opt-in settings for digital conformance
 * and GDPRC compliance.
 */
@RestController
@Component
@RequestMapping("/consent/admin")
@Tag(name = "Consent Admin APIs", description = "Provides access to consent and privacy operations.")
public class UserAgreementsAdminService {

    private final UserAgreementProvider provider;

    public UserAgreementsAdminService(UserAgreementProvider provider) {
        this.provider = provider;
    }

    @GetMapping("/{userId}/agreements")
    @Operation(
summary = "Retrieve user agreements for admin access",
            operationId = "adminGetUserAgreement360",
            description = """
This endpoint allows administrators to retrieve the user agreements associated with a specific user. It provides essential information regarding the user's privacy agreements and consent settings, ensuring compliance with GDPR and digital conformance requirements.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('user.consent.list')")
    public UserAgreementDetails getUserAgreement360(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        return this.provider.get360Agreements(userId);
    }

    @DeleteMapping("/{userId}/agreements/{agreementId}")
    @Operation(
summary = "Admin delete a user's agreement record",
            operationId = "adminDeleteUserAgreement",
            description = """
This endpoint allows an admin to delete a specific agreement associated with a user. It ensures that the user's privacy agreements are managed effectively, maintaining compliance with GDPRC regulations. Use this operation with caution, as it permanently removes the agreement from the user's profile.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('user.consent.delete')")
    public void deleteAgreement(
            @PathVariable("userId") String userId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        this.provider.deleteAgreement(userId, agreementId);
    }

    @PostMapping("/{userId}/agreements/{agreementId}")
    @Operation(
summary = "Admin adds user agreement to history",
            operationId = "adminAddUserAgreement",
            description = """
This endpoint allows an admin to add a user agreement to the history of agreements made by a specific user. It ensures compliance with privacy regulations and tracks user consent effectively. Use this operation to maintain accurate records of user agreements for GDPR and digital conformance.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('user.consent.create')")
    public void agreement(
            @PathVariable("userId") String userId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        this.provider.recordAgreement(userId, agreementId);
    }
}
