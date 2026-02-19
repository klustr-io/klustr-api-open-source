package io.klustr.permissions;

/**
 * Defines standard CRUD + admin scopes for relations in Keto.
 *
 * Example:
 *   Scope.create("projects") → projects#create
 *   Scope.admin("orgs")      → orgs#admin
 */
public final class Scope {
    private final String relation;
    private final String namespace;

    public Scope(String namespace, String relation) {
        if (relation.contains("_")) {
            String[] tokens = relation.split("_");
            if (tokens.length > 2) {
                throw new RuntimeException("Token length is not appropriate we store {noun}_{identifier} in some instances");
            }
            this.namespace = tokens[0];
            this.relation = tokens[1];
        } else {
            this.relation = relation;
            this.namespace = namespace;
        }
    }
    public Scope(String namespace) {
        this.namespace = null;
        this.relation = namespace;
    }

    public static Scope create(String namespace) { return new Scope(namespace, "create"); }
    public static Scope read(String namespace)   { return new Scope(namespace, "read"); }
    public static Scope update(String namespace) { return new Scope(namespace, "update"); }
    public static Scope delete(String namespace) { return new Scope(namespace, "delete"); }
    public static Scope admin(String namespace)  { return new Scope(namespace, "admin"); }

    /** Custom scope, e.g. invite, share, publish */
    public static Scope attribute(String namespace, String op) { return new Scope(namespace, op); }

    public static Scope namespace(String namespace) { return new Scope(namespace); }

    public String relation() { return relation; }

    public String namespace() { return namespace; }

    @Override
    public String toString() {
        return relation;
    }
}
