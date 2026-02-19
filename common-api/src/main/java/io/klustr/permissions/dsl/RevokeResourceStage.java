package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class RevokeResourceStage {
    final PermissionProvider provider; final SubjectKey subject; final String op; final String resource;
    RevokeResourceStage(PermissionProvider p, SubjectKey s, String op, String resource) {
        this.provider = p; this.subject = s; this.op = op; this.resource = resource;
    }

    public void withId(String id) {
        Scope scope = Scope.attribute(resource, op);
        provider.revoke(subject, scope, Target.of(resource, id));
    }

    public RevokeFinalStage in(String namespace) {
        return new RevokeFinalStage(provider, subject, op, resource, namespace);
    }
}
