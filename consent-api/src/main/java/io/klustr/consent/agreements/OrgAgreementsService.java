package io.klustr.consent.agreements;

import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.RandomNameGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Provides access to a users general privacy agreements
 * and acceptance and opt-in settings for digital conformance
 * and GDPRC compliance.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/agreements")
@Tag(name = "Agreements API", description = "Provides the functionality to manage, record, and track agreements for orgs, apps, and experiments.")
public class OrgAgreementsService {

    private Storage storage;

    public OrgAgreementsService(Storage storage) {
        this.storage = storage;
    }


    @GetMapping
    @Operation(
summary = "List agreements for the specified organization",
            operationId = "listOrgAgreements",
            description = """
Retrieves all agreements associated with the given organization ID. This endpoint provides visibility into the agreements defined for the organization, ensuring compliance with privacy standards and regulations.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public DocumentResult<Agreement> listAgreements(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        DbQuery dbQuery = Db.query("org_id").eq(organizationId);
        return this.storage.agreements().insecureQuery(dbQuery, Pagination.all());
    }

    @PostMapping
    @Operation(
summary = "Create a new agreement for the organization",
            operationId = "createOrgAgreement",
            description = """
This endpoint allows users to create a new agreement that can be utilized by the specified organization. It is essential for managing compliance with privacy regulations and ensuring proper documentation of agreements. Use this to establish new terms for users within the organization.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Agreement create(
            @PathVariable("organizationId") String organizationId,
            @RequestBody Agreement agreement,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        if (StringUtils.isBlank(organizationId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing organization ID"
            );
        }

        agreement.setOrgId(organizationId);
        agreement.setDateCreated(DateTime.now());
        agreement.setDateModified(DateTime.now());
        agreement.setNamespace(agreement.getNamespace() != null ? agreement.getNamespace() : Agreement.AgreementScope.ORG);
        agreement.setId(RandomNameGenerator.randomHexString(12));

        this.storage.agreements().insertObject(agreement.getId(), agreement);
        return agreement;
    }


    @PutMapping("/{agreementId}")
    @Operation(
summary = "Update an existing organization agreement",
            operationId = "updateOrgAgreement",
            description = """
This endpoint allows you to update an existing agreement for a specified organization. It ensures that the agreement details are modified according to the provided input. This operation is crucial for maintaining accurate records of agreements in compliance with privacy regulations.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Agreement updateAgreement(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("agreementId") String agreementId,
            @RequestBody Agreement agreement,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        agreement.setOrgId(organizationId);
        agreement.setDateCreated(DateTime.now());
        agreement.setDateModified(DateTime.now());
        agreement.setNamespace(agreement.getNamespace() != null ? agreement.getNamespace() : Agreement.AgreementScope.ORG);

        if (this.storage.agreements().getObject(agreementId) == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Already does not exist to update."
            );
        }

        this.storage.agreements().updateObject(agreementId, agreement);
        return agreement;
    }

    @GetMapping("/{agreementId}")
    @Operation(
summary = "Retrieve a specific organization agreement",
            operationId = "getOrgAgreement",
            description = """
Fetches the details of a specific agreement for the given organization. This endpoint is essential for understanding the terms and conditions that apply to the organization. It ensures compliance with privacy regulations and provides users with the necessary information regarding their agreements.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Agreement getAgreement(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        return this.storage.agreements().getObject(agreementId);
    }

    @DeleteMapping("/{agreementId}")
    @Operation(
summary = "Delete an organization's agreement.",
            operationId = "deleteOrgAgreement",
            description = """
This endpoint allows for the removal of an existing agreement for a specified organization. It ensures that the agreement is no longer valid and updates the organization's records accordingly. Use this operation to manage compliance and privacy agreements effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Agreement deleteAgreement(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        if (StringUtils.isBlank(organizationId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing organization ID"
            );
        }
        if (StringUtils.isBlank(agreementId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing agreement reference identifier."
            );
        }
        Agreement existing = this.storage.agreements().getObject(agreementId);
        if (existing == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Agreement does not exist."
            );
        }

        existing.setDateModified(DateTime.now());
        existing.setStatus(Agreement.Status.DELETED);
        this.storage.agreements().updateObject(existing.getId(), existing);
        return existing;
    }
}
