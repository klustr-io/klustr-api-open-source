package io.klustr.consent.admin;

import io.klustr.consent.UserConsentProvider;
import io.klustr.consent.agreements.UserAgreementProvider;
import io.klustr.consent.agreements.models.UserAgreementDetails;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.consent.responses.UserConsent360ResponseBody;
import io.klustr.console.storage.Storage;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.console.consent.UserConsentHierarchy;
import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Administrative API for consent auditing.
 */
@RestController
@Component
@RequestMapping("/consent/household")
@Tag(name = "Consent Household APIs", description = "Provides access to consent and privacy operations.")
public class ConsentHouseholdService {

    private static final Logger log = LoggerFactory.getLogger(ConsentHouseholdService.class);

    private final UserConsentRepository userConsent;
    private final UserConsentProvider provider;
    private final Storage storage;
    private final PersonStorage persons;
    private final UserAgreementProvider agreements;

    public ConsentHouseholdService(
            Storage storage,
            PersonStorage persons,
            UserAgreementProvider agreements,
            UserConsentProvider provider,
            UserConsentRepository userConsent) {
        this.userConsent = userConsent;
        this.provider = provider;
        this.storage = storage;
        this.persons = persons;
        this.agreements = agreements;
    }

    @GetMapping("/{householdId}/members/{personId}")
    @Operation(
operationId = "adminGetUserConsent",
            summary = "Retrieve consent details for a specific user.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows administrators to retrieve all consent information for a specified user within a household. It ensures that only authorized personnel can access sensitive consent data, maintaining privacy and compliance standards.
"""
)
    public UserConsentHierarchy getAllUserConsent(@PathVariable("householdId") String householdId,
                                                  @PathVariable("personId") String personId,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, oauth);
        return this.provider.getHierarchy(member.getPersonId());
    }

    @GetMapping("/{householdId}/members/{personId}/agreements")
    @Operation(
operationId = "getHouseholdUserAgreements",
            summary = "Retrieve agreements for a user in a household.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint returns all user agreements associated with the specified household and person. It is intended for authorized users to access their agreements, ensuring transparency and compliance with privacy regulations. Use this to understand the commitments made within the household context.
"""
)
    public UserAgreementDetails getUserAgreement360(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, user);
        return this.agreements.get360Agreements(personId);
    }

    @DeleteMapping("/{householdId}/members/{personId}/agreements/{agreementId}")
    @Operation(
operationId = "deleteUserAgreement",
            summary = "Delete a user's agreement in the household.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows for the deletion of a specific user agreement within a household. It is intended for use by authorized users to manage consent agreements effectively. Ensure that the correct household and user identifiers are provided to avoid unintended deletions.
"""
)
    public void deleteUserAgreement360(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, user);
        this.agreements.deleteAgreement(personId, agreementId);
    }

    @GetMapping("/{householdId}/members/{personId}/apps/{appId}")
    @Operation(
operationId = "getHouseholdUserAppConsent",
            summary = "Retrieve user consent for a specific application",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
Fetches all consent details associated with the specified application ID for a given household and person. This endpoint is essential for understanding user permissions and privacy settings. It ensures that applications comply with user consent preferences.
"""
)
    public UserConsent360ResponseBody getApplicationUserConsent(@PathVariable("householdId") String householdId,
                                                                @PathVariable("personId") String personId,
                                                                @PathVariable("appId") String appId,
                                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, oauth);
        return this.provider.get360ConsentByAppId(member.getPersonId(), appId);
    }

    @DeleteMapping("/{householdId}/members/{personId}/apps/{appId}")
    @Operation(
operationId = "revokeUserConsentForApplication",
            summary = "Revoke consent for a specific application user.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows users to revoke consent for a specific application associated with their household. It ensures that all permissions granted to the application are removed, enhancing user privacy and control over their data. This operation is crucial for maintaining compliance with consent regulations.
"""
)
    public void revokeApplicationUserConsent(@PathVariable("householdId") String householdId,
                                             @PathVariable("personId") String personId,
                                             @PathVariable("appId") String appId,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, oauth);
        this.provider.revokeAppConsent(member.getPersonId(), appId);
    }

    @DeleteMapping("/{householdId}/members/{personId}/experiments/{experiment_id}")
    @Operation(
operationId = "revokeHouseholdExperimentConsent",
            summary = "Revoke consent for a specific household experiment",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows users to revoke consent for a specific experiment associated with a household. It ensures that the individual's preferences regarding participation in experiments are respected and updated accordingly. This operation is crucial for maintaining privacy and compliance with consent regulations.
"""
)
    public void revokeExperimentConsent(@PathVariable("householdId") String householdId,
                                        @PathVariable("personId") String personId,
                                        @PathVariable("experiment_id") String experimentId,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, oauth);
        this.provider.removeConsentForExperiment(member.getPersonId(), experimentId);
    }


    @DeleteMapping("/{householdId}/members/{personId}/apps/{appId}/scopes/{scope}")
    @Operation(
operationId = "revokeHouseholdUserAppScopeConsent",
            summary = "Revoke consent for a specific application scope",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            description = """
This endpoint allows the removal of consent for a specific scope associated with a user's application. It is intended for household-level consent management, ensuring that users can control their privacy settings effectively. Use this operation to maintain compliance and user trust.
"""
)
    public UserConsent360ResponseBody revokeApplicationFineGrainConsent(@PathVariable("householdId") String householdId,
                                                  @PathVariable("personId") String personId,
                                                  @PathVariable("appId") String appId,
                                                  @PathVariable("scope") String scope,
                                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        HouseholdMember member = authorizeAccessToPerson(householdId, personId, oauth);
        this.provider.revokeAppScope(personId, appId, scope);

        return this.provider.get360ConsentByAppId(member.getPersonId(), appId);
    }

    private HouseholdMember authorizeAccessToPerson(String householdId, String personId, OAuth2AuthenticatedPrincipal oauth) {
        Household household = this.persons.households().getObject(householdId);
        if (household == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Household not found"
            );
        }
        String act = oauth.getName();
        Optional<HouseholdMember> isPrimary =
                household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(act) && x.getPrimary() == true).findFirst();
        if (isPrimary.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Person is not associated to household"
            );
        }

        Optional<HouseholdMember> member = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (member.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not a member of the household"
            );
        }
        return member.get();
    }
}
