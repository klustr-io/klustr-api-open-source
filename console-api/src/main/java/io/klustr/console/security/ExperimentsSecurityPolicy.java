package io.klustr.console.security;

import io.klustr.AbstractApiSecurityPolicy;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExperimentsSecurityPolicy extends AbstractApiSecurityPolicy<Experiment> {

    private final PermissionProvider permissions;

    public ExperimentsSecurityPolicy(PermissionProvider permissions) {
        this.permissions = permissions;
    }


    @Override
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, Experiment obj) {
        if (user == null) return false;
        String email = PrincipleUtils.tryGetEmailForUser(user);
        if (StringUtils.isBlank(email)) return false;

        if (obj == null) return false;
        if (obj.getOwner() == null) return false;

        if (obj.getOwner().equalsIgnoreCase(email)) {
            return true;
        }

        String md5_email = U.md5(email);
        return obj.getOwner().equalsIgnoreCase(md5_email);
    }

    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user, String objectId, String... permissions) {
        // TODO fix this for real permissions
//        List<Acl> acls = this.permissions.listUserAcls(SubjectKey.userId(user.getName()), "experiments", objectId);
//        return hasAnyAclMatch(acls, permissions);
        return true;
    }
}
