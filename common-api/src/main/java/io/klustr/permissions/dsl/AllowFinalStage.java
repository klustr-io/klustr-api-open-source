package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class AllowFinalStage {
    final PermissionProvider provider;
    final SubjectKey subject;
    final String op;
    final String childResource;   // e.g. "projects"
    final String namespace;       // e.g. "orgs"

    AllowFinalStage(PermissionProvider p, SubjectKey s, String op, String childResource, String ns) {
        this.provider = p; this.subject = s; this.op = op; this.childResource = childResource; this.namespace = ns;
    }

    public void withId(String id) {
        // nested relation: "<child>#<op>" on <namespace>:<id>
        String relation = childResource + "_" + op;                 // e.g. "projects#create"
        Scope scope = Scope.attribute(namespace, relation);            // namespace = "orgs"
        provider.grant(subject, scope, Target.of(namespace, id)); // object orgs:my_org
    }
}
