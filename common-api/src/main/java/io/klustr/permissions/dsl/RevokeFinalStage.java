package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class RevokeFinalStage {
    final PermissionProvider provider; final SubjectKey subject; final String op; final String child; final String namespace;
    RevokeFinalStage(PermissionProvider p, SubjectKey s, String op, String child, String ns) {
        this.provider = p; this.subject = s; this.op = op; this.child = child; this.namespace = ns;
    }

    public void withId(String id) {
        String relation = child + "_" + op;
        Scope scope = Scope.attribute(namespace, relation);
        provider.revoke(subject, scope, Target.of(namespace, id));
    }
}
