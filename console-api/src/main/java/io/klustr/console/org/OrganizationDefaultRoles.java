package io.klustr.console.org;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.orgs.Role;
import io.klustr.schemas.console.orgs.RolePermission;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class OrganizationDefaultRoles {

    private Storage storage;

    public OrganizationDefaultRoles(Storage storage) {
        this.storage = storage;
    }

    public void generateAndSave(OrgRoles orgRoles) {

        OrganizationRoleService.GenerateRequest req = new OrganizationRoleService.GenerateRequest();

        // permissions and roles required for managing the org and roles
        req.resources.add(new OrganizationRoleService.Resource("user.consent"));
        req.resources.add(new OrganizationRoleService.Resource("org.directory"));
        req.resources.add(new OrganizationRoleService.Resource("org.members"));
        req.resources.add(new OrganizationRoleService.Resource("org.groups"));
        req.resources.add(new OrganizationRoleService.Resource("org.roles"));
        req.resources.add(new OrganizationRoleService.Resource("org.user.roles"));
        req.resources.add(new OrganizationRoleService.Resource("org.user.permissions"));
        req.resources.add(new OrganizationRoleService.Resource("org.user.assignment"));

        req.resources.add(new OrganizationRoleService.Resource("org.consent.scopes"));

        req.resources.add(new OrganizationRoleService.Resource("projects"));
        req.resources.add(new OrganizationRoleService.Resource("projects.storage.buckets"));

        req.resources.add(new OrganizationRoleService.Resource("projects.experiments"));
        req.resources.add(new OrganizationRoleService.Resource("projects.credentials"));
        req.resources.add(new OrganizationRoleService.Resource("projects.billing"));
        req.resources.add(new OrganizationRoleService.Resource("projects.verification"));
        req.resources.add(new OrganizationRoleService.Resource("api-catalogs"));
        req.resources.add(new OrganizationRoleService.Resource("persons"));
        req.resources.add(new OrganizationRoleService.Resource("identity"));
        req.resources.add(new OrganizationRoleService.Resource("notifications"));
        req.resources.add(new OrganizationRoleService.Resource("emails"));
        req.resources.add(new OrganizationRoleService.Resource("accounts"));
        req.resources.add(new OrganizationRoleService.Resource("ekyc.session"));
        req.resources.add(new OrganizationRoleService.Resource("org.user.account.types"));
        req.resources.add(new OrganizationRoleService.Resource("org.user.account"));


        // add the roles with default permissions
        for (OrganizationRoleService.Resource r : req.resources) {
            DefaultPermissions.apply(orgRoles, r);
        }

        Optional<OrgRoles> record = this.storage.organization_roles().tryGetObject(orgRoles.getId());
        if (record.isEmpty()) {
            this.storage.organization_roles().insertObject(orgRoles.getId(), orgRoles);
            record = this.storage.organization_roles().tryGetObject(orgRoles.getId());
        }

        record.ifPresent(this::ensureSuperUserRoleExists);
    }

    private void ensureSuperUserRoleExists(OrgRoles record) {

        // adminRole user with special role of "*"
        Role adminRole = new Role().withId("admin");
        adminRole.withNamespace("admin")
                .withStage(Role.Stage.GA)
                .withName("Admin")
                .withDescription("Root access granted to the owners of the organization.");
        List<OrgService.Permission> allPermissions = getPermissions(record, x -> !x.getId().equalsIgnoreCase(adminRole.getId()));
        adminRole.withPermissions(allPermissions.stream().map(x -> new RolePermission().withScope(x.scope)).toList());

        // update admin
        Optional<Role> hasAdminRole = getExistingRole(record, adminRole.getId());
        if (hasAdminRole.isEmpty() || hasAdminRole.get().getPermissions().isEmpty()) {
            record.getRoles().add(adminRole);
            // refresh admin permissions to include them all
            this.storage.organization_roles().updateObject(record.getId(), record);
        }
    }

    private void ensureExists(String orgId, Role role) {
        Optional<OrgRoles> match = this.storage.organization_roles().tryGetObject(orgId);
        if (match.isEmpty()) {
            OrgRoles r = new OrgRoles()
                    .withId(orgId)
                    .withRoles(Lists.newArrayList(role));
            this.storage.organization_roles().insertObject(orgId, r);
        } else {
            Optional<Role> roleExists = match.get().getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(role.getId())).findFirst();
            if (roleExists.isEmpty()) {
                match.get().getRoles().add(role);
                this.storage.organization_roles().updateObject(orgId, match.get());
            }
        }
    }





    private Optional<Role> getExistingRole(OrgRoles orgRoles, String roleId) {
        if (orgRoles == null) return Optional.empty();
        if (orgRoles.getRoles() == null) return Optional.empty();
        if (orgRoles.getRoles().isEmpty()) return Optional.empty();

        return orgRoles.getRoles().stream().filter(x -> x.getId().equalsIgnoreCase(roleId)).findAny();
    }

    private List<OrgService.Permission> getPermissions(OrgRoles orgRoles, Predicate<Role> excludeFilter) {
        Map<String, OrgService.Permission> permissions = Maps.newConcurrentMap();
        orgRoles.getRoles().stream().filter(excludeFilter).forEach(x -> {
            x.getPermissions().forEach(p -> {
                permissions.put(p.getScope(), new OrgService.Permission(p.getScope()));
            });
        });
        return permissions.values().stream().toList();
    }
}
