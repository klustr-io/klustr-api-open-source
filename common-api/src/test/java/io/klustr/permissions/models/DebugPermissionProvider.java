package io.klustr.permissions.models;

import io.klustr.permissions.*;
import io.klustr.permissions.dsl.Permissions;
import org.springframework.boot.actuate.health.Health;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A drop-in PermissionProvider that does NOT talk to Keto.
 * It formats the tuple EXACTLY as Keto expects:
 *     <namespace>:<object>#<relation>@<subject>
 *
 * - grant(): records an ALLOW of that tuple
 * - revoke(): removes that ALLOW
 * - check(): returns true iff an identical ALLOW exists (pure string match)
 *
 * This is for debugging your fluent interface → tuple-key mapping only.
 * It does NOT simulate OPL cascade (parent->read, etc).
 */
public class DebugPermissionProvider implements PermissionProvider {

    // We store pure tuple atoms as Keto expects, e.g. "projects:1234#read@user:bob"
    private final Set<String> grants = ConcurrentHashMap.newKeySet();

    @Override
    public void grant(SubjectKey subject, Scope scope, Target object) {
        String tuple = toTuple(scope, object, subject);
        grants.add(tuple);
        System.out.println("ALLOW " + tuple);
    }

    @Override
    public void revoke(SubjectKey subject, Scope scope, Target object) {
        String tuple = toTuple(scope, object, subject);
        boolean removed = grants.remove(tuple);
        System.out.println((removed ? "REVOKE " : "NOOP   ") + tuple);
    }

    @Override
    public boolean check(SubjectKey subject, Scope scope, Target object) {
        String tuple = toTuple(scope, object, subject);
        boolean allowed = grants.contains(tuple);
        System.out.println("CHECK  " + tuple + " -> " + (allowed ? "YES" : "NO"));
        return allowed;
    }

    // Optional diagnostics — we keep it minimal for now.
    // If you want, you can parse 'grants' and return Relation instances later.
    @Override
    public Set<Relation> listRelationsForObject(Target object) {
        // Keep the contract, return empty for now (optional helper per your comment)
        return Collections.emptySet();
    }

    @Override
    public Set<Relation> listRelations(SubjectKey subject, String namespace, Target target) {
        return Collections.emptySet();
    }

    @Override
    public void revokeAll(SubjectKey subjectKey, Target object) {

    }

    @Override
    public void revokeAll(SubjectKey subjectKey) {

    }

    // ------------------------
    // Tuple formatting helpers
    // ------------------------

    private String toTuple(Scope scope, Target object, SubjectKey subject) {
        String namespace = requireNonEmpty(extract(scope,
                        "namespace", "getNamespace", "resource", "getResource", "type", "getType", "name", "getName"),
                "Scope.namespace");
        String relation = requireNonEmpty(extract(scope,
                        "relation", "getRelation", "operation", "getOperation", "op", "getOp", "permission", "getPermission"),
                "Scope.relation/operation");

        String objectId = requireNonEmpty(extract(object,
                        "id", "getId", "value", "getValue", "object", "getObject", "key", "getKey", "name", "getName"),
                "ObjectId.id");

        String subjectAtom = toSubjectAtom(subject); // e.g. "user:bob"

        // Keto-form tuple: <namespace>:<object>#<relation>@<subject>
        return namespace + ":" + objectId + "#" + relation + "@" + subjectAtom;
    }

    private String toSubjectAtom(SubjectKey subject) {
        return subject.value();
    }

    // ------------------------
    // Small utility layer
    // ------------------------

    private static String requireNonEmpty(String val, String label) {
        if (val == null || val.isBlank()) {
            throw new IllegalArgumentException("Missing required " + label + " for tuple formatting");
        }
        return val;
    }

    /**
     * Lightweight, defensive reflection to read a string-ish property without
     * coupling to your concrete classes. Tries common getters in order.
     */
    private static String extract(Object instance, String... methodCandidates) {
        for (String name : methodCandidates) {
            String v = invokeStringGetter(instance, name);
            if (v != null && !v.isBlank()) return v;
        }
        return null;
    }

    private static String invokeStringGetter(Object target, String methodName) {
        try {
            Method m = target.getClass().getMethod(methodName);
            m.setAccessible(true);
            Object val = m.invoke(target);
            return (val == null) ? null : String.valueOf(val);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    @Override
    public Permissions iam() {
        return new Permissions(this);
    }

    @Override
    public Health health() {
        return Health.up().build();
    }

    // ------------------------
    // Test-only helpers
    // ------------------------

    /** Expose the raw tuple strings for assertions in unit tests. */
    public Set<String> snapshotGrantedTuples() {
        return Collections.unmodifiableSet(grants);
    }
}
