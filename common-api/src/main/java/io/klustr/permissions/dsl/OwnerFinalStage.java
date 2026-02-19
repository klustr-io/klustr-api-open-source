package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class OwnerFinalStage {
    final PermissionProvider provider; final SubjectKey subject; final String namespace;
    OwnerFinalStage(PermissionProvider p, SubjectKey s, String ns) { this.provider = p; this.subject = s; this.namespace = ns; }

    public void withId(String id) {
        provider.grant(subject, Scope.admin(namespace), Target.of(namespace, id));
    }
}
