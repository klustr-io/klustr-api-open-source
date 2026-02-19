package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.apache.commons.lang.StringUtils;

public final class OwnerResourceStage {
    final PermissionProvider provider; final SubjectKey subject;
    OwnerResourceStage(PermissionProvider p, SubjectKey s) { this.provider = p; this.subject = s; }

    /** Choose the object namespace to own (e.g. "orgs", "albums") */
    public OwnerFinalStage on(String namespace) {
        return new OwnerFinalStage(provider, subject, namespace);
    }

    /** Convenience: if you already have an ObjectId */
    public void of(Target object) {
        if (StringUtils.isBlank(object.namespace())) {
            throw new RuntimeException("Invalid definition!");
        }
        provider.grant(subject, Scope.admin(object.namespace()), object);
    }
}
