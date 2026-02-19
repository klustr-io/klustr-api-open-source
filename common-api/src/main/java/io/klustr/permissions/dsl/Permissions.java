package io.klustr.permissions.dsl;

import io.klustr.permissions.PermissionProvider;

public final class Permissions {
    private final PermissionProvider provider;
    public Permissions(PermissionProvider provider) { this.provider = provider; }

    public AllowSubjectStage allow() { return new AllowSubjectStage(provider); }
    public RevokeSubjectStage revoke() { return new RevokeSubjectStage(provider); }
    public CheckSubjectStage can() { return new CheckSubjectStage(provider); }
    public OwnerSubjectStage owner() { return new OwnerSubjectStage(provider); }
}
