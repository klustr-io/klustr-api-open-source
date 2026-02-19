package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class CheckOperationStage {
    final PermissionProvider provider; final SubjectKey subject;
    CheckOperationStage(PermissionProvider p, SubjectKey s) { this.provider = p; this.subject = s; }

    public CheckResourceStage read(String resource)   { return new CheckResourceStage(provider, subject, "read", resource); }
    public CheckResourceStage write(String resource)  { return new CheckResourceStage(provider, subject, "write", resource); }
    public CheckResourceStage delete(String resource) { return new CheckResourceStage(provider, subject, "delete", resource); }
    public CheckResourceStage create(String resource) { return new CheckResourceStage(provider, subject, "create", resource); }
    public CheckResourceStage admin(String resource)  { return new CheckResourceStage(provider, subject, "admin", resource); }
    public CheckResourceStage custom(String resource, String op) {
        return new CheckResourceStage(provider, subject, op, resource);
    }
}
