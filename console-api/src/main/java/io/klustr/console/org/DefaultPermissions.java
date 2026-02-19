package io.klustr.console.org;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.orgs.RolePermission;

import java.util.ArrayList;
import java.util.List;

public class DefaultPermissions {
    public static List<Role> apply(OrgRoles org, OrganizationRoleService.Resource r) {
        r.namespace = r.namespace.toLowerCase().replaceAll("[^a-zA-Z0-9]", ".");

        Role admin = new Role()
                .withDescription("Default Allow All Access Role for " + r.namespace)
                .withId(r.namespace + ".admin.role")
                .withNamespace(r.namespace)
                .withName(r.namespace + " - Admin")
                .withStage(Role.Stage.ALPHA)
                .withPermissions(Lists.newArrayList(
                        new RolePermission().withScope(r.namespace + ".create"),
                        new RolePermission().withScope(r.namespace + ".delete"),
                        new RolePermission().withScope(r.namespace + ".update"),
                        new RolePermission().withScope(r.namespace + ".get"),
                        new RolePermission().withScope(r.namespace + ".list"),
                        new RolePermission().withScope(r.namespace + ".find")
                ));

        Role viewer = new Role()
                .withDescription("Default Read Only Access Role for " + r.namespace)
                .withId(r.namespace + ".read-only.role")
                .withName(r.namespace + " - Read Only")
                .withNamespace(r.namespace)
                .withStage(Role.Stage.ALPHA)
                .withPermissions(Lists.newArrayList(
                        new RolePermission().withScope(r.namespace + ".get"),
                        new RolePermission().withScope(r.namespace + ".list"),
                        new RolePermission().withScope(r.namespace + ".find")
                ));


        ArrayList<Role> list = Lists.newArrayList(admin, viewer);
        for (Role role : list) {
            if (!roleExists(org, role)) {
                org.getRoles().add(role);
            }
        }

        return list;
    }

    private static boolean roleExists(OrgRoles orgRoles, Role role) {
        if (orgRoles == null) return false;
        if (orgRoles.getRoles() == null) return false;
        if (orgRoles.getRoles().isEmpty()) return false;
        return orgRoles.getRoles().stream().anyMatch(x -> x.getId().equalsIgnoreCase(role.getId()));
    }
}
