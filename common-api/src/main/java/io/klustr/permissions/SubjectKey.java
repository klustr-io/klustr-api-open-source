package io.klustr.permissions;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * Represents the subject of a permission in Keto.
 *
 * Each subject has a type prefix (user, api_key, app, org, group, client, etc.)
 * followed by its identifier, e.g.:
 *
 * - user:bob
 * - api_key:xyz
 * - org:my_org
 * - group:admins
 * - user:* (ANYONE)
 */
public final class SubjectKey {
    private final String type;
    private final String id;

    public SubjectKey(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public static SubjectKey user(OAuth2AuthenticatedPrincipal id)     { return new SubjectKey("user", id.getName()); }
    public static SubjectKey userId(String id)     { return new SubjectKey("user", id); }
    // public static SubjectKey user(String id)     { return new SubjectKey("user", id); }
    public static SubjectKey client(String id)   { return new SubjectKey("client", id); }
    public static SubjectKey apiKey(String id)   { return new SubjectKey("api_key", id); }
    public static SubjectKey app(String id)      { return new SubjectKey("app", id); }
    public static SubjectKey org(String id)      { return new SubjectKey("org", id); }
    public static SubjectKey group(String id)    { return new SubjectKey("group", id); }

    /** Represents ANY subject (wildcard). */
    public static final SubjectKey ANYONE = new SubjectKey("user", "*");

    public String value() {
        return type + ":" + id;
    }

    public String type() {
        return type;
    }

    public String id() {
        return this.id;
    }

    @Override
    public String toString() {
        return value();
    }
}
