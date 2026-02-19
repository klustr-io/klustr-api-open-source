package io.klustr.console;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.billing.BillingCustomerApi;
import io.klustr.console.security.AccountSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.*;
import io.klustr.permissions.DefaultUserPermissions;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.schemas.console.accounts.AccountContact;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.spring.OAuthCredentialType;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enables the management of billing, contact, and other related contracting information
 * for an account that consumes or participates. Required in order to create projects.
 * <p>
 * In our world for directory APU account == customer.
 */
@RestController
@Component
@RequestMapping("/console/accounts")
@ResourceGroup(value = "Accounts", description = "Account Services")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final BillingCustomerApi customerApi;

    private final PermissionProvider permissions;

    private final Storage storage;

    private final AccountSecurityPolicy policy;

    private final DefaultUserPermissions perms;

    public AccountService(BillingCustomerApi customerApi,
                          Storage storage,
                          AccountSecurityPolicy policy,
                          PermissionProvider permissions) {
        this.customerApi = customerApi;
        this.policy = policy;
        this.permissions = permissions;
        this.storage = storage;
        this.perms = new DefaultUserPermissions(permissions, storage);
    }

    /**
     * Returns the current account status for the user checking
     * if they have a valid account setup and or force a registration process.
     *
     * @param user The current user to check their account status
     * @return The account status of the user.
     */
    @GetMapping("/status")
    @Operation(
operationId = "getMyAccountStatus",
            summary = "Retrieve the current status of the user's account",
            description = """
Fetches the account status for the authenticated user. This indicates whether there are any setup tasks that need to be completed. It is essential for ensuring users are aware of their account requirements before proceeding with project creation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AccountOnboardingStatus getAccountStatus(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        AccountOnboardingStatus status = new AccountOnboardingStatus();

        String email = PrincipleUtils.tryGetEmailForUser(user);
        status.account = true;

        DefaultUserPermissions.LinkedObjects resolve = this.perms.resolve(user.getName());

        status.project = !resolve.projectIds.isEmpty();
        Set<String> activeProjects = resolve.projectIds.stream().map(x -> this.storage.projects().getObject(x))
                .filter(Objects::nonNull).map(Project::getId).collect(Collectors.toSet());
        status.projects.addAll(activeProjects);

        // add org level perms
        List<Org> orgs = Lists.newArrayList();
        resolve.orgIds.forEach(o -> {
            Org match = this.storage.organizations().getObject(o);
            if (match == null) return;
            orgs.add(match);
        });

        status.organization = !orgs.isEmpty();
        status.organizations = orgs.stream().map(Org::getId).collect(Collectors.toSet());

        // check required
        if (status.organizations.isEmpty()) {
            status.required.add("organization");
        }
        if (status.projects.isEmpty()) {
            status.required.add("project");
        }

        return status;
    }

    @GetMapping("/{accountId}")
    @Operation(
operationId = "getAccountDetails",
            summary = "Retrieve account details for a specific user.",
            description = """
This endpoint fetches the account information linked to the provided account ID. It is crucial for managing billing and contact details. Ensure you have the appropriate authentication to access this sensitive information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('accounts.get')")
    public Account get(@PathVariable("accountId") String accountId,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<Account> match = this.storage.accounts().tryGetObject(accountId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        if (policy.hasReadAccess(oauth, match.get())) {
            return match.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    @Operation(
operationId = "createAccount",
            summary = "Create a new user account for project management.",
            description = """
This endpoint facilitates the creation of a new account for a user. It is crucial for managing billing and contact information. Proper account setup is necessary to initiate project creation and ensure user participation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('accounts.create')")
    public Account post(@RequestBody Account obj,
                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (StringUtils.isBlank(obj.getId())) {
            obj.setId(UUID.randomUUID().toString());
        }

        if (!policy.hasCreateAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        Optional<Account> match = this.storage.accounts().tryGetObject(obj.getId());
        if (match.isEmpty()) {
            this.storage.accounts().insertObject(obj.getId(), obj);
        } else {
            this.storage.accounts().updateObject(obj.getId(), obj);
        }
        this.onCreate(obj, oauth);
        return obj;
    }

    @DeleteMapping("/{accountId}")
    @Operation(
operationId = "deleteAccount",
            summary = "Delete a specified account from the system.",
            description = """
This operation permanently removes the account identified by the provided ID. It is crucial for maintaining the integrity of account management and ensuring that outdated accounts are eliminated. Proper authorization is required to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('accounts.delete')")
    public void delete(@PathVariable("accountId") String id,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Account obj = this.storage.accounts().getObject(id);
        if (obj == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        if (!policy.hasDeleteAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }
        this.storage.accounts().deleteObject(id);
        this.onDelete(obj, oauth);
    }

    @PutMapping("/{accountId}")
    @Operation(
operationId = "updateAccountContactInfo",
            summary = "Update contact and billing information for an account.",
            description = """
This endpoint updates the contact and billing details for a specified account. It is crucial for ensuring accurate account information and is necessary for project creation. Please provide a valid account ID and include all required information in the request body.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('accounts.update')")
    public Account put(@PathVariable("accountId") String accountId,
                       @RequestBody Account obj,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth,
                       @RequestHeader(value = "x-Consumer-Username", required = false) String customer_id) {

        Account existing = this.storage.accounts().getObject(accountId);

        if (existing != null && !policy.hasUpdateAccess(oauth, existing)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (obj != null && !policy.hasUpdateAccess(oauth, obj)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED
            );
        }

        this.storage.accounts().updateObject(accountId, obj);
        this.onUpdate(obj, oauth);
        return obj;
    }

    @PutMapping("/{accountId}/contact")
    @Operation(
operationId = "updateAccountContactInformation",
            summary = "Update contact information for an account.",
            description = """
This endpoint allows authorized users to update the contact information for a specific account. Maintaining accurate billing and communication details is crucial for effective account management. Ensure proper authorization to protect sensitive account data.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Account updateContactInformation(@PathVariable("accountId") String accountId,
                                            @RequestBody AccountContact contact,
                                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        if (StringUtils.isBlank(accountId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID must be provided");
        }
        Account b = this.storage.accounts().getObject(accountId);
        if (!this.policy.hasReadAccess(oauth, b)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized access");
        }
        b.setContact(contact);
        this.storage.accounts().updateObject(accountId, b);
        this.onUpdate(b, oauth);
        return b;
    }

    protected void onCreate(Account obj, OAuth2AuthenticatedPrincipal user) {
        log.info("Triggered new account " + obj.getId());

        DateTime now = DateTime.now();
        obj.setCreationDate(now);
        obj.setModifiedDate(now);

        // save direct to DB to avoid loop callback
        this.storage.accounts().updateObject(obj.getId(), obj);
    }

    protected void onUpdate(Account obj, OAuth2AuthenticatedPrincipal principal) {
        log.info("Triggered update of account " + obj.getId());

        // save direct to DB to avoid loop callback
        this.storage.accounts().updateObject(obj.getId(), obj);
    }

    protected void onDelete(Account obj, OAuth2AuthenticatedPrincipal principal) {
        // we dont delete from lago for the purposes of billing and not losing money!
        log.info("Triggered delete of account " + obj.getId());
    }

    public static class AccountOnboardingStatus {
        public boolean organization = false;
        public boolean account = false;
        public boolean project = false;
        public Set<String> projects = Sets.newHashSet();
        public Set<String> organizations = Sets.newHashSet();

        public Set<String> required = Sets.newHashSet();
    }
}