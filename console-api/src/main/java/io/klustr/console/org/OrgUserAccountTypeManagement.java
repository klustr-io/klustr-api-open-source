package io.klustr.console.org;

import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgAssignment;
import io.klustr.schemas.console.users.UserAccountType;
import io.klustr.schemas.console.users.UserAccountTypeAgreement;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Enables the defining of user account types and related information
 * for management and definition of account types.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/accounts/types")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Management", description = "Organization management functions.")
public class OrgUserAccountTypeManagement {
    private static final Logger log = LoggerFactory.getLogger(OrgUserAccountTypeManagement.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public OrgUserAccountTypeManagement(Storage storage,
                                        PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    /**
     * Returns the types of accounts registered in the organization.
     *
     * @param organizationId The organization ID
     * @param oauth          The authenticated user making this request
     * @return The {@link OrgAssignment} for this service.
     */
    @GetMapping
    @Operation(
operationId = "listOrgUserAccountTypes",
            summary = "List account types for the organization",
            description = """
Retrieves the account types registered within the specified organization. These account types are available for linking to user accounts. This endpoint is intended for organizational management and provides essential information for user account type definitions.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<UserAccountType> getAccountTypes(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(organizationId)
        );

        DocumentResult<UserAccountType> assignments = this.storage.user_account_types().insecureQuery(fq, Pagination.all());
        return assignments.docs;
    }

    @DeleteMapping("/{code}")
    @Operation(
operationId = "adminDeleteAccountType",
            summary = "Admin delete account type for organization.",
            description = """
This endpoint allows administrators to delete a specified account type for a given organization. It ensures that account types are managed effectively, maintaining the integrity of user account definitions. Proper authentication is required to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteAccountType(@RequestBody String path,
                                  @PathVariable("organizationId") String organizationId,
                                  @PathVariable("code") String code,
                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization Unit Not Found"
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(organizationId),
                Db.query("code").eq(code)
        );

        Optional<UserAccountType> match = this.storage.user_account_types().findFirst(fq);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Account type not found"
            );
        }

        match.get().setStatus(UserAccountType.Status.INACTIVE);

        this.storage.user_account_types().updateObject(match.get().getId(), match.get());
    }

    /**
     * Creates a new account type.
     *
     * @param payload        The organizational assignment
     * @param organizationId The organization ID
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @PostMapping
    @Operation(
operationId = "createUserAccountType",
            summary = "Creates a new user account type for an organization",
            description = """
This endpoint allows the creation of a user account type within a specified organization. It is intended for management purposes to define various account types and their related information. Ensure that the provided payload meets the required specifications for successful creation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAccountType createUserAccountType(
            @RequestBody UserAccountType payload,
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO check permissions for access to org.

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        Org organization = org.get();

        payload.setOrgId(org.get().getId());

        if (StringUtils.isBlank(payload.getCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization requested is not same as payload."
            );
        }

        payload.setId(UUID.randomUUID().toString());
        DateTime dt = DateTime.now();
        payload.setCreationDate(dt);
        payload.setModifiedDate(dt);

        this.storage.user_account_types().insertObject(payload.getId(), payload);

        return payload;
    }

    /**
     * Creates a new account type.
     *
     * @param payload        The organizational assignment
     * @param organizationId The organization ID
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @PutMapping("/{code}")
    @Operation(
operationId = "updateOrgUserAccountType",
            summary = "Update an organization's user account type",
            description = """
This endpoint allows for the modification of a specific user account type within an organization. It requires the organization ID and the account type code to identify the account type to be updated. Ensure that the provided payload contains the necessary details for the update.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAccountType updateAccountType(
            @RequestBody UserAccountType payload,
            @PathVariable("organizationId") String organizationId,
            @PathVariable("code") String code,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO check permissions for access to org.

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found."
            );
        }
        Org organization = org.get();

        payload.setOrgId(org.get().getId());

        if (StringUtils.isBlank(payload.getCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization requested is not same as payload."
            );
        }

        DateTime dt = DateTime.now();
        payload.setCreationDate(dt);
        payload.setModifiedDate(dt);

        this.storage.user_account_types().updateObject(payload.getId(), payload);

        return payload;
    }

    /**
     * Creates a new account type.
     *
     * @param organizationId The organization ID
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @GetMapping("/{code}")
    @Operation(
operationId = "updateOrgUserAccountType",
            summary = "Update account type for organization users",
            description = """
This endpoint allows the updating of a specific user account type within an organization. It ensures that the modifications are applied to the correct account type identified by the provided code. This operation is essential for maintaining accurate account type definitions and user management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAccountType getAccountType(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("code") String code,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        Org organization = org.get();

        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(organizationId),
                Db.query("code").eq(code)
        );

        Optional<UserAccountType> match = this.storage.user_account_types().findFirst(fq);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User account type not found, please ensure code exists."
            );
        }

        // ensure agreement data matches
        if (match.get().getAgreements() != null) {
            match.get().setAgreements(match.get().getAgreements().stream().map(x -> {
                Agreement agreement = this.storage.agreements().getObject(x.getId());
                return new UserAccountTypeAgreement()
                        .withId(agreement.getId())
                        .withType(agreement.getType().value())
                        .withName(agreement.getName())
                        .withDescription(agreement.getDescription())
                        .withStatus(agreement.getStatus().value());
            }).toList());
        }

        return match.get();
    }

}