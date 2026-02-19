package io.klustr.permissions;

public record Relation(SubjectKey subject, Scope scope, Target object) {}
