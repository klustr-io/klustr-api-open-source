package io.klustr.console.org;

import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgAssignment;
import io.klustr.schemas.console.orgs.OrgUnit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Enables role management that defines roles and their permissions.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/users/{userId}")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Unit Management", description = "Organization management functions.")
public class OrgUnitManagementService {
    private static final Logger log = LoggerFactory.getLogger(OrgUnitManagementService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public OrgUnitManagementService(Storage storage,
                                    PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    /**
     * Returns the organizational unit assignments.
     *
     * @param organizationId The organization ID
     * @param userId         The user ID to get organizational units for
     * @param oauth          The authenticated user making this request
     * @return The {@link OrgAssignment} for this service.
     */
    @GetMapping("/ous")
    @Operation(
operationId = "listUserOrgAssignments",
            summary = "List organization assignments for a specific user",
            description = """
Retrieve the organization assignments associated with the specified user. This endpoint provides visibility into the user's roles within the organization, helping to manage permissions effectively. Ideal for administrators and users to understand their organizational context.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.assignment.list')")
    public List<OrgAssignment> getUserAssignments(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        DbQueryAndCondition fq = Db.and(
                Db.query("assigned_to").eq(userId),
                Db.query("assignee_type").eq("user")
        );

        DocumentResult<OrgAssignment> assignments = this.storage.org_assignments().insecureQuery(fq, Pagination.all());
        return assignments.docs;
    }

    @DeleteMapping("/ous")
    @Operation(
operationId = "deleteUserOrgUnitAssignment",
            summary = "Remove organization unit from specified user",
            description = """
This endpoint allows the removal of an organization unit assignment from a user. It is intended for use by authorized personnel to manage user roles effectively. Ensure that the user ID and organization ID are correctly specified to avoid unintended deletions.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.assignment.delete')")
    public void deleteAssignment(@RequestBody String path,
                                 @PathVariable("organizationId") String organizationId,
                                 @PathVariable("userId") String userId,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization Unit Not Found"
            );
        }
        Org organization = org.get();

        Optional<OrgAssignment> orgAssignment = this.storage.org_assignments().tryGetObject(path);
        if (orgAssignment.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Organization assignment does not exist"
            );
        }

        this.storage.org_assignments().deleteObject(path);
    }

    /**
     * Registers a new user to an organizational unit.
     *
     * @param payload        The organizational assignment
     * @param organizationId The organization ID
     * @param userId         The user ID
     * @param oauth          The user making this request
     * @return The {@link OrgAssignment} made.
     */
    @PostMapping("/ous")
    @Operation(
operationId = "addUserOrgAssignment",
            summary = "Assign organization units to a user.",
            description = """
This endpoint updates the organization assignment for a specified user. It allows for the management of user roles within the organization. Ensure that the user has the necessary permissions to perform this action.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('org.user.assignment.create')")
    public OrgAssignment addUserAssignment(
            @RequestBody OrgAssignment payload,
            @PathVariable("organizationId") String organizationId,
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<Org> org = this.storage.organizations().tryGetObject(organizationId);
        if (org.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        Org organization = org.get();

        String path = payload.getOrgPath();
        if (StringUtils.isBlank(path)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Missing path"
            );
        }
        Optional<OrgUnit> orgUnit = OrgUtils.getOrgUnit(path, organization);
        if (orgUnit.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "OU path '" + path + "' could not be found."
            );
        }

        // generate our ID
        String assn_id = OrgUtils.generateId(payload);
        payload.setId(assn_id);

        Optional<OrgAssignment> exists = this.storage.org_assignments().tryGetObject(assn_id);
        if (exists.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User is already member of organizational unit"
            );
        }

        this.storage.org_assignments().insertObject(assn_id, payload);

        return payload;
    }


}