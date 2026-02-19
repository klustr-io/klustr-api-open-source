package io.klustr.console.org;

import com.google.common.collect.Maps;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.Target;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.console.Status;
import io.klustr.schemas.console.identity.IdentityUserProfile;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgAssignment;
import io.klustr.schemas.console.users.UserAccount;
import io.klustr.schemas.console.users.UserAccountType;
import io.klustr.schemas.console.users.UserReference;
import io.klustr.schemas.persons.Person;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

// TODO deprecate this
/**
 * Enables the defining of user account types and related information
 * for management and definition of account types.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/accounts/users")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Management", description = "Organization management functions.")
public class OrgUserAccountManagement {
    private static final Logger log = LoggerFactory.getLogger(OrgUserAccountManagement.class);

    private final Storage storage;

    private final PersonStorage persons;

    private final PermissionProvider permissions;

    public OrgUserAccountManagement(Storage storage,
                                    PersonStorage persons,
                                    PermissionProvider permissions) {
        this.storage = storage;
        this.persons = persons;
        this.permissions = permissions;
    }

    /**
     * Returns all users and their accounts.
     *
     * @param organizationId The organization ID
     * @param oauth          The authenticated user making this request
     * @return The {@link OrgAssignment} for this service.
     */
    @GetMapping
    @Operation(
operationId = "listUserAccounts",
            summary = "Retrieve user accounts for the organization.",
            description = """
This endpoint returns the account types registered within the specified organization. These accounts can be linked to users, providing visibility into available user account options. It is designed to facilitate user account management and enhance the linking process.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public List<UserReference> getUserAccounts(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("organization_id").eq(organizationId)
        );

        Set<Relation> rel = this.permissions.listRelationsForObject(Target.of("orgs", organizationId));
        // unique user ids
        Map<String, List<Relation>> acls = Maps.newConcurrentMap();
        rel.forEach(m -> {
            List<Relation> relations = acls.get(m.subject().id());
            if (relations == null) {
                relations = new ArrayList<>();
            }
            relations.add(m);
            acls.put(m.subject().id(), relations);
        });

        List<UserReference> list = acls.keySet().stream().map(subjectId -> {

            // lookup user
            UserReference result = new UserReference()
                    .withStatus(Status.active)
                    .withOrgId(organizationId)
                    .withAcls(acls.get(subjectId).stream().map(x -> x.scope().relation()).collect(Collectors.toList()));

            Person person = persons.persons().getObject(subjectId);
            if (person != null) {
                result.withIdentity(
                        new IdentityUserProfile()
                                .withTraits(person.getTraits())
                                .withId(person.getId())
                                .withMetadataPublic(person.getMetadataPublic())
                );
            }
            return result;
        }).toList();

        return list;

        // DocumentResult<UserAccount> assignments = this.storage.user_accounts().insecureQuery(fq, Pagination.all());
        // return assignments.docs;
    }

    @DeleteMapping("/{subjectId}/{code}")
    @Operation(
operationId = "deleteOrgUserAccount",
            summary = "Delete a user account for the organization",
            description = """
This endpoint allows for the deletion of a user account associated with the specified organization. It is intended for use by authorized personnel to manage user accounts effectively. Ensure that the correct organization ID and user details are provided to avoid unintended deletions.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteAccountType(@RequestBody String path,
                                  @PathVariable("organizationId") String organizationId,
                                  @PathVariable("code") String code,
                                  @PathVariable("subjectId") String subject_id,
                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization Unit Not Found"
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("subject_id").eq(subject_id),
                Db.query("code").eq(code)
        );

        Optional<UserAccount> match = this.storage.user_accounts().findFirst(fq);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User and account not found."
            );
        }
        match.get().setStatus(Status.disabled);

        this.storage.user_accounts().updateObject(match.get().getId(), match.get());
    }

    /**
     * Creates a new account type.
     *
     * @param organizationId The organization ID
     * @param subjectId      The subject to assign the account type to.
     * @param code           The account code to assign the account type to.
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @PostMapping("/{subjectId}/{code}")
    @Operation(
operationId = "assignUserToAccount",
            summary = "Assign a user to an account type.",
            description = """
This endpoint creates an association between a user and a specific account type within the organization. It allows for the management of user account types, ensuring proper categorization and access control. This operation is essential for maintaining user account integrity and organization compliance.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAccount addUserAccountType(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("subjectId") String subjectId,
            @PathVariable("code") String code,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO check permissions for access to org.
        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found."
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(organizationId),
                Db.query("code").eq(code)
        );

        // verify account exists
        Optional<UserAccountType> accountType = this.storage.user_account_types().findFirst(fq);
        if (accountType.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account type is not found in organization."
            );
        }

        UserAccount acc = new UserAccount()
                .withId(UUID.randomUUID().toString())
                .withCreationDate(DateTime.now())
                .withEffectiveDate(DateTime.now())
                .withStatus(Status.active)
                .withSubjectId(subjectId)
                .withAccountCode(accountType.get().getCode())
                .withOrgId(accountType.get().getOrgId());


        this.storage.user_accounts().insertObject(acc.getId(), acc);

        return acc;
    }

    public static class UpdateUserAccountRequest {
        public Status status;
    }

    /**
     * Updates the account type associated with a user.
     *
     * @param organizationId The organization ID
     * @param subjectId      The subject to assign the account type to.
     * @param code           The account code to assign the account type to.
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @PutMapping("/{subjectId}/{code}")
    @Operation(
operationId = "updateUserAccountType",
            summary = "Update user account type for organization",
            description = """
This endpoint allows the updating of a user account type within a specified organization. It requires the organization ID, subject ID, and the new account type code. Ensure that the user has the necessary permissions to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserAccount updateAccountType(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("subjectId") String subjectId,
            @PathVariable("code") String code,
            @RequestBody UpdateUserAccountRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO check permissions for access to org.
        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found."
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("org_id").eq(organizationId),
                Db.query("code").eq(code)
        );

        // verify account exists
        Optional<UserAccountType> accountType = this.storage.user_account_types().findFirst(fq);
        if (accountType.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Account type is not found in organization."
            );
        }

        fq = Db.and(
                Db.query("org_id").eq(organizationId),
                Db.query("account_code").eq(code),
                Db.query("subject_id").eq(subjectId)
        );
        Optional<UserAccount> match = this.storage.user_accounts().findFirst(fq);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The user does not have this account type defined."
            );
        }

        match.get().setStatus(request.status);
        this.storage.user_accounts().updateObject(match.get().getId(), match.get());
        return match.get();
    }

}