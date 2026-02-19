package io.klustr.git;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessLevel {
    NO_ACCESS(0),
    MINIMAL_ACCESS(5),
    GUEST(10),
    PLANNER(15),
    REPORTER(20),
    DEVELOPER(30),
    MAINTAINER(40),
    OWNER(50);

    private final int level;

    AccessLevel(int level) {
        this.level = level;
    }

    @JsonValue
    public int getLevel() {
        return level;
    }

    @JsonCreator
    public static AccessLevel from(Object value) {
        if (value instanceof Number) {
            int intValue = ((Number) value).intValue();
            for (AccessLevel a : values()) {
                if (a.level == intValue) {
                    return a;
                }
            }
            throw new IllegalArgumentException("Unknown access level number: " + intValue);
        } else if (value instanceof String) {
            String text = ((String) value).trim();
            // Try numeric first
            try {
                int intValue = Integer.parseInt(text);
                for (AccessLevel a : values()) {
                    if (a.level == intValue) {
                        return a;
                    }
                }
            } catch (NumberFormatException ignored) {}
            // Then try name
            for (AccessLevel a : values()) {
                if (a.name().equalsIgnoreCase(text)) {
                    return a;
                }
            }
            throw new IllegalArgumentException("Unknown access level string: " + text);
        } else {
            throw new IllegalArgumentException("Unsupported type for AccessLevel: " + value);
        }
    }
}
