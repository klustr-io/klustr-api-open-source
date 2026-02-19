package io.klustr.console.security;

import io.klustr.AbstractApiSecurityPolicy;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Applies a standard security policy for accessing and creating accounts.
 */
@Component
public class AccountSecurityPolicy extends AbstractApiSecurityPolicy<Account> {

    private final PermissionProvider permissions;

    public AccountSecurityPolicy(PermissionProvider permissions) {
        this.permissions = permissions;
    }


    @Override
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, Account obj) {
        if (user == null) return false;
        String email = PrincipleUtils.tryGetEmailForUser(user);
        if (!StringUtils.isEmpty(email)) {
            String key = U.md5(email);
            if (obj.getId().equalsIgnoreCase(key)) {
                return true;
            }
            if (obj.getId().equalsIgnoreCase(email)) {
                return true;
            }
        }
        if (obj.getId().equalsIgnoreCase(user.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user, String objectId, String... permissions) {
        // TODO fix this
        return true;
    }
}
