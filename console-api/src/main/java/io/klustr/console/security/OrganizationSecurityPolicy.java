package io.klustr.console.security;

import io.klustr.console.storage.Storage;
import io.klustr.permissions.*;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrganizationSecurityPolicy {
    private static final Logger log = LoggerFactory.getLogger(OrganizationSecurityPolicy.class);

    private final Storage storage;
    private final PermissionProvider permissions;

    public OrganizationSecurityPolicy(Storage storage, PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
    }

    public Set<String> listOrgsForUser(OAuth2AuthenticatedPrincipal user) {

        // any specifically added
        Set<String> ids = this.permissions.listRelations(SubjectKey.user(user),
                "members", Target.inAny("orgs")).stream().map(x ->
        {
            return x.object().value();
        }).collect(Collectors.toSet());


        // see any projects that are owned
        String email = PrincipleUtils.tryGetEmailForUser(user);
        DbQuery fq = Db.query("owner").with("id").eq(email);
        DocumentResult<Org> orgs = this.storage.organizations().insecureQuery(fq, Pagination.all());
        if (orgs.docs.isEmpty()) {
            return ids;
        }

        ids.addAll(orgs.docs.stream().map(x -> x.getId()).collect(Collectors.toSet()));
        return ids;
    }

    public boolean hasOrgRole(OAuth2AuthenticatedPrincipal user, String organizationId, String role) {
        Set<Relation> perms = getOrganizationRoles(user, organizationId);
        return perms.stream().anyMatch(x -> x.subject().id().equalsIgnoreCase(role));
    }

    private Set<Relation> getOrganizationRoles(OAuth2AuthenticatedPrincipal user, String organizationId) {
        Set<Relation> userRoles = this.permissions.listRelations(SubjectKey.user(user), "roles", Target.of("orgs", organizationId));
        return userRoles;
    }

    public Org throwIfPermissionMissingRole(OAuth2AuthenticatedPrincipal user, String organizationId, String role) throws ResponseStatusException {
        String email = PrincipleUtils.tryGetEmailForUser(user);
        Org org = this.storage.organizations().getObject(organizationId);
        if (org == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Organization is not available."
            );
        }
        // TODO fix this for permissions
        return org;
    }
}
