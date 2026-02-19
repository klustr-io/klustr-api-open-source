package io.klustr.console.security;

import io.klustr.AbstractApiSecurityPolicy;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.auth.UserAccessControl;
import io.klustr.console.storage.Storage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

@Component
public class UserAccessControlSecurityPolicy extends AbstractApiSecurityPolicy<UserAccessControl> {

    private final Storage storage;

    private final ProjectSecurityPolicy projectSecurity;

    public UserAccessControlSecurityPolicy(Storage storage, ProjectSecurityPolicy projectSecurity) {
        this.storage = storage;
        this.projectSecurity = projectSecurity;
    }

    @Override
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, UserAccessControl obj) {
        if (user == null) return false;
        String email = PrincipleUtils.tryGetEmailForUser(user);
        if (StringUtils.isEmpty(email)) return false;

        Project project = this.storage.projects().getObject(obj.getProjectId());
        if (project == null) return false;
        return projectSecurity.hasReadAccess(user, project);
    }

    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user, String objectId, String... permissions) {
     return projectSecurity.hasAnyOfThesePermissions(user, objectId, permissions);
    }


}
