package io.klustr;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * Contains no checks for security and will just approve everything, useful
 * for generic and testing scenarios but probably not good to use in production.
 *
 * @param <T> The type of object being secured.
 */
public class NoApiSecurityPolicy<T> extends AbstractApiSecurityPolicy<T> {


    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user, String objectId, String... permissions) {
        return true;
    }
}
