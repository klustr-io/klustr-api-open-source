package io.klustr.permissions;

/**
 * Represents a namespaced object in Keto.
 * Typically combines a hierarchy like:
 *   org/my_org, projects/proj1, albums/album42/photo123
 */
public final class Target {
    private final String value;
    private String namespace = null;

    public Target(String value) {
        this.value = value;
    }

    public Target(String namespace, String value) {
        this.value = value;
        this.namespace = namespace;
    }

    /** Create a new object id from a raw string. */
    public static Target of(String value) {
        return new Target(value);
    }

    public static Target inAny(String namespace) {
        return new Target(namespace, "*");
    }

    public static Target of(String namespace, String value) {
        return new Target(namespace, value);
    }

    /** Append a child identifier to the current path. */
    public Target with(String child) {
        return new Target(this.value + "/" + child);
    }

    public String value() {
        return value;
    }

    public String namespace() {
        return namespace;
    }

    @Override
    public String toString() {
        return value;
    }
}
