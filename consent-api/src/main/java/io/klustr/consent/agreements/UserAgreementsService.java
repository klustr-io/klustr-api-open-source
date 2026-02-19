package io.klustr.consent.agreements;

import io.klustr.consent.agreements.models.UserAgreementDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.spring.OAuthCredentialType;
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
@RequestMapping("/consent/me/agreements")
@Tag(name = "Consent APIs", description = "Provides access to consent and privacy operations.")
public class UserAgreementsService {

    private final UserAgreementProvider provider;

    public UserAgreementsService(UserAgreementProvider provider) {
        this.provider = provider;
    }

    @GetMapping
    @Operation(
summary = "Retrieve user's privacy agreements and settings",
            operationId = "getUserPrivacyAgreements",
            description = """
This endpoint allows users to access their registered privacy agreements and opt-in settings. It ensures compliance with GDPR and digital conformance requirements. The information retrieved is specific to the authenticated user's identity.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAgreementDetails getUserPrivacyAgreements(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        return this.provider.get360Agreements(user.getName());
    }

    @DeleteMapping("/{organizationId}/agreements/{agreementId}")
    @Operation(
summary = "Delete a user's agreement record",
            operationId = "deleteUserAgreement",
            description = """
This endpoint allows the deletion of a specific agreement associated with a user. It ensures that the user's privacy preferences are updated accordingly. Use this operation to manage user consent and compliance with GDPRC regulations effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteAgreement(
            @PathVariable("organizationId") String orgId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        this.provider.deleteAgreement(user.getName(), orgId, agreementId);
    }

    @PostMapping("/{organizationId}/agreements/{agreementId}")
    @Operation(
summary = "Add a user agreement to history",
            operationId = "addUserAgreement",
            description = """
This endpoint allows users to add an agreement to their history of accepted privacy agreements. It ensures compliance with GDPR and other privacy regulations. The operation is intended for individual users managing their consent preferences.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void addAgreement(
            @PathVariable("organizationId") String orgId,
            @PathVariable("agreementId") String agreementId,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        this.provider.recordAgreement(user.getName(), agreementId);
    }
}
