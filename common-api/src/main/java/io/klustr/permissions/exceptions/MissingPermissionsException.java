package io.klustr.permissions.exceptions;

import com.google.common.collect.Lists;

import java.util.List;

public class MissingPermissionsException extends RuntimeException {

    private final List<String> permissions;

    public MissingPermissionsException(String ... permissionsRequired) {
        this.permissions = Lists.newArrayList(permissionsRequired);
    }

    public List<String> getMissingPermissions() {
        return this.permissions;
    }
}
