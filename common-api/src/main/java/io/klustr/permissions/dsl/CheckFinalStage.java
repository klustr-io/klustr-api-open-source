package io.klustr.permissions.dsl;

import io.klustr.permissions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class CheckFinalStage {
    final PermissionProvider provider; final SubjectKey subject; final String op; final String child; final String namespace;
    CheckFinalStage(PermissionProvider p, SubjectKey s, String op, String child, String ns) {
        this.provider = p; this.subject = s; this.op = op; this.child = child; this.namespace = ns;
    }

    public CheckResourceStageEvaluation withId(String id) {
        String relation = child + "_" + op;
        Scope scope = Scope.attribute(namespace, relation);
        boolean v = provider.check(subject, scope, Target.of(namespace, id));
        return new CheckResourceStageEvaluation(v);
    }

    public static class CheckResourceStageEvaluation {
        private boolean value;
        public CheckResourceStageEvaluation(boolean value) {
            this.value = value;
        }
        public boolean result() {
            return this.value;
        }
        public void throwIfUnauthorized() {
            if (!this.value) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
