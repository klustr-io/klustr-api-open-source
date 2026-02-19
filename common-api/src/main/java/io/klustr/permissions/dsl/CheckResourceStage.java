package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class CheckResourceStage {

    final PermissionProvider provider; final SubjectKey subject; final String op; final String resource;
    private static final Logger log = LoggerFactory.getLogger(CheckResourceStage.class);

    CheckResourceStage(PermissionProvider p, SubjectKey s, String op, String resource) {
        this.provider = p; this.subject = s; this.op = op; this.resource = resource;
    }

    /** Direct: admin("orgs").withId("my_org") → relation 'admin' on orgs:my_org */
    public CheckResourceStageEvaluation withId(String id) {
        Scope scope = Scope.attribute(resource, op);
        boolean value = provider.check(subject, scope, Target.of(resource, id));
        return new CheckResourceStageEvaluation(value);
    }

    /** Nested: create("projects").in("orgs").withId("my_org") → relation 'projects#create' on orgs:my_org */
    public CheckFinalStage in(String namespace) {
        return new CheckFinalStage(provider, subject, op, resource, namespace);
    }

    public static class CheckResourceStageEvaluation {
        private boolean value;
        public CheckResourceStageEvaluation(boolean value) {
            this.value = value;
        }
        public boolean value() {
            return this.value;
        }
        public void throwIfUnauthorized() {
            if (!this.value) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
