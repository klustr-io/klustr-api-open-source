package io.klustr.permissions.exceptions;

import java.util.List;

/**
 * Permissions Invalid or not available.
 */
public class MissingPermissionsResponse {

    public List<String> missing_permissions;

    public MissingPermissionsResponse(List<String> missing_permissions) {
        this.missing_permissions = missing_permissions;
    }

}
