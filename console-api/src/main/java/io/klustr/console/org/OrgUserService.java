package io.klustr.console.org;

import com.google.common.collect.Lists;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.SubjectKey;
import io.klustr.permissions.Target;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.roles.RoleAssignment;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Enables role management that defines roles and their permissions.
 */
@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}")
@ResourceGroup(value = "Directory", description = "Directory Services")
@Tag(name = "Org Role Management", description = "Organization management functions.")
public class OrgUserService {
    private static final Logger log = LoggerFactory.getLogger(OrgUserService.class);

    private final Storage storage;

    private final PermissionProvider permissions;

    public OrgUserService(Storage storage,
                          PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    private static String docID(RoleAssignment assn) {
        if (StringUtils.isBlank(assn.getRoleId())) {
            throw new IllegalArgumentException("No role ID defined");
        }
        if (StringUtils.isBlank(assn.getAssignedTo())) {
            throw new IllegalArgumentException("No role assignment defined");
        }
        if (StringUtils.isBlank(assn.getOrgId())) {
            throw new IllegalArgumentException("No org ID defined");
        }
        if (assn.getAssigneeType() == null) {
            throw new IllegalArgumentException("No assignee type defined");
        }
        return U.md5((assn.getOrgId() + "/" + assn.getAssigneeType() + "/" + assn.getAssignedTo() + "/" + assn.getRoleId()).toLowerCase());
    }

    @GetMapping("/user/roles")
    @Operation(
operationId = "getOrgUserRoles",
            summary = "Retrieve roles for a specified user in the organization",
            description = """
This endpoint returns all roles associated with the specified user within the organization. It is intended for use by authorized personnel to manage user permissions effectively. Ensure that you have the necessary security credentials to access this information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserEntitlements getEntitlements(
            @PathVariable("organizationId") String organizationId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        Set<Relation> acls = this.permissions.listRelations( SubjectKey.user(user), "roles", Target.of("orgs", organizationId));
        if (acls.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User is not a member of this organization."
            );
        }

        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization not found"
            );
        }
        UserEntitlements e = new UserEntitlements();
        e.user_id = user.getName();
        e.org_id = organizationId;

        OrgRoles orgRoles = this.storage.organization_roles().getObject(organizationId);
        if (orgRoles == null) {
            return e;
        }

        // find matches
        acls.forEach(x -> {
            Optional<Role> match = orgRoles.getRoles().stream().filter(r -> {
                return r.getId().equalsIgnoreCase(x.scope().relation());
            }).findAny();
            if (!match.isEmpty()) {
                e.roles.add(match.get());
            }
        });

        return e;
    }

    public static class UserEntitlements {
        public String user_id;
        public String org_id;
        public List<Role> roles = Lists.newArrayList();
    }
}