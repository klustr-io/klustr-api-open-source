package io.klustr.consent;

import com.google.common.collect.Sets;
import io.klustr.permissions.Target;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.Relation;
import io.klustr.permissions.SubjectKey;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OrgPerms {

    private final PermissionProvider permissions;

    public OrgPerms(PermissionProvider permissions) {
        this.permissions = permissions;
    }


    public boolean hasOrgPermission(OAuth2AuthenticatedPrincipal user, OrgRoles org, String permission) {
        Set<String> perms = getOrganizationPermissions(user, org);
        return perms.contains(permission);
    }

    private List<Role> getOrganizationRoles(OAuth2AuthenticatedPrincipal user, OrgRoles org) {
        // TODO fix or debug me
        Set<Relation> orgRoles = permissions.listRelations(SubjectKey.user(user), "orgs", new Target(org.getId()));
        return orgRoles.stream().map(x -> {
            return org.getRoles().stream().filter(r -> r.getId().equalsIgnoreCase(x.scope().relation())).findFirst().orElse(null);
        }).filter(Objects::nonNull).toList();
    }


    private Set<String> getOrganizationPermissions(OAuth2AuthenticatedPrincipal user, OrgRoles org) {
        List<Role> roles = getOrganizationRoles(user, org);
        Set<String> permissions = Sets.newHashSet();
        roles.forEach(r -> {
            r.getPermissions().forEach(p -> {
                permissions.add(p.getScope());
            });
        });
        return permissions;
    }
}
