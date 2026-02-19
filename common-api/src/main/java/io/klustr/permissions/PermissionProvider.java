package io.klustr.permissions;

import io.klustr.permissions.dsl.Permissions;
import org.springframework.boot.actuate.health.Health;

import java.util.Set;

public interface PermissionProvider {

    void grant(SubjectKey subject, Scope scope, Target object);

    void revoke(SubjectKey subject, Scope scope, Target object);

    void revokeAll(SubjectKey subjectKey);

    void revokeAll(SubjectKey subjectKey, Target object);

    boolean check(SubjectKey subject, Scope scope, Target object);

    Set<Relation> listRelationsForObject(Target object);

    Set<Relation> listRelations(SubjectKey subject, String namespace, Target target);

    Permissions iam();

    Health health();
}
