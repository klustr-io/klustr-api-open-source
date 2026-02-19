package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class RevokeOperationStage {
    final PermissionProvider provider; final SubjectKey subject;
    RevokeOperationStage(PermissionProvider p, SubjectKey s) { this.provider = p; this.subject = s; }

    public RevokeResourceStage read(String resource)   { return new RevokeResourceStage(provider, subject, "read", resource); }
    public RevokeResourceStage write(String resource)  { return new RevokeResourceStage(provider, subject, "write", resource); }
    public RevokeResourceStage delete(String resource) { return new RevokeResourceStage(provider, subject, "delete", resource); }
    public RevokeResourceStage create(String resource) { return new RevokeResourceStage(provider, subject, "create", resource); }
    public RevokeResourceStage admin(String resource)  { return new RevokeResourceStage(provider, subject, "admin", resource); }
    public RevokeResourceStage custom(String resource, String op) {
        return new RevokeResourceStage(provider, subject, op, resource);
    }
}
