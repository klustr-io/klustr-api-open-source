package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class AllowOperationStage {
    final PermissionProvider provider;
    final SubjectKey subject;

    AllowOperationStage(PermissionProvider p, SubjectKey s) {
        this.provider = p; this.subject = s;
    }

    public AllowResourceStage read(String resource)   { return new AllowResourceStage(provider, subject, "read", resource); }
    public AllowResourceStage write(String resource)  { return new AllowResourceStage(provider, subject, "write", resource); }
    public AllowResourceStage delete(String resource) { return new AllowResourceStage(provider, subject, "delete", resource); }
    public AllowResourceStage create(String resource) { return new AllowResourceStage(provider, subject, "create", resource); }
    public AllowResourceStage admin(String resource)  { return new AllowResourceStage(provider, subject, "admin", resource); }
    public AllowResourceStage custom(String resource, String op) {
        return new AllowResourceStage(provider, subject, op, resource);
    }
}
