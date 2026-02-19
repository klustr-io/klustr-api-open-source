package io.klustr;

import com.google.common.collect.Lists;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.List;
import java.util.Optional;

/**
 * A basic implementation of a security policy for checking for C.R.U.D rights
 * against an object as well as consent scopes given OIDC protocol
 *
 * @param <T> The type of object we are apply the security policy for.
 */
public abstract class AbstractApiSecurityPolicy<T> {

    public boolean isOwner(OAuth2AuthenticatedPrincipal user, T obj) {
        return true;
    }

    /**
     * Checks if a user has read access, if the user does NOT have read access
     * the user will also never have update, or delete permissions.
     *
     * @param user The user to check for read access.
     * @param obj  The object to check.
     * @return If the user has read access true, if not false.
     */
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, T obj) {
        return true;
    }

    /**
     * Optional specific implementation to enable update access.
     *
     * @param user THe user to check for update access.
     * @param obj  The object to check.
     * @return True if the user has the ability to update this object.
     */
    public boolean hasUpdateAccess(OAuth2AuthenticatedPrincipal user, T obj) {
        return this.hasReadAccess(user, obj);
    }

    /**
     * If the user has permissions to create new objects.
     *
     * @param user The user to check if they have permissions to create.
     * @param obj  The object that is being created.
     * @return True or false if the user is allowed to create.
     */
    public boolean hasCreateAccess(OAuth2AuthenticatedPrincipal user, T obj) {
        return this.hasReadAccess(user, obj);
    }

    /**
     * If the user is allowed to delete the specified object.
     *
     * @param user The user to check if they have permissions to create.
     * @param obj  The object that is being deleted.
     * @return True if the user has delete rights.
     */
    public boolean hasDeleteAccess(OAuth2AuthenticatedPrincipal user, T obj) {
        return this.hasReadAccess(user, obj);
    }


    public abstract boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user, String objectId, String ... permissions);

}
