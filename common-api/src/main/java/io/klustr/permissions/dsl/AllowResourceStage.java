package io.klustr.permissions.dsl;

import io.klustr.permissions.*;

public final class AllowResourceStage {
    final PermissionProvider provider;
    final SubjectKey subject;
    final String op;            // e.g. "read", "admin", "projects#create"
    final String resource;      // e.g. "orgs", "projects"

    AllowResourceStage(PermissionProvider p, SubjectKey s, String op, String resource) {
        this.provider = p; this.subject = s; this.op = op; this.resource = resource;
    }

    /** Direct: admin("orgs").withId("my_org") → relation 'admin' on namespace 'orgs' */
    public void withId(String id) {
        // direct grants use relation = op, namespace = resource
        Scope scope = Scope.attribute(resource, op);
        provider.grant(subject, scope, Target.of(resource, id));
    }

    /** Nested: read("projects").in("orgs").withId("my_org") → relation 'projects#read' on namespace 'orgs' */
    public AllowFinalStage in(String namespace) {
        return new AllowFinalStage(provider, subject, op, resource, namespace);
    }

    public void withGlobalScope() {
        Scope scope = Scope.attribute(resource, op);
        provider.grant(subject, scope, Target.of(resource, "*"));
    }
}
