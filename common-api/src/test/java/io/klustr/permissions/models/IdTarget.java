package io.klustr.permissions.models;

public final class IdTarget {
    private final String ns;
    private final String obj;
    public IdTarget(String ns, String obj) { this.ns = ns; this.obj = obj; }
    public String namespace() { return ns; }
    public String object() { return obj; }
    public static IdTarget of(String ns, String obj) { return new IdTarget(ns, obj); }
}