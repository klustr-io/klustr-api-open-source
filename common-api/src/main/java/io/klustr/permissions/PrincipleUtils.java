package io.klustr.permissions;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class PrincipleUtils {

    public static String tryGetEmailForUser(OAuth2AuthenticatedPrincipal user) {
        Object ext = user.getAttribute("ext");
        if (ext != null) {
            Map<?, ?> ext_map = (Map<?, ?>) ext;
            if (ext_map.containsKey("email")) {
                return ext_map.get("email").toString().toLowerCase();
            }
        }
        return null;
    }

    public static String tryGetIssuer(OAuth2AuthenticatedPrincipal user) {
        if (user instanceof Jwt jwt) {
            // Direct access to claims
            return jwt.getIssuer().toString();
        }
        Object iss = user.getAttribute("iss");
        return iss != null ? iss.toString() : null;
    }

    public static String tryGetClientId(OAuth2AuthenticatedPrincipal user) {
        Object ext = user.getAttribute("ext");
        if (ext != null) {
            Map<?, ?> ext_map = (Map<?, ?>) ext;
            if (ext_map.containsKey("client_id")) {
                return ext_map.get("client_id").toString().toLowerCase();
            }
        }
        return null;
    }

    public static String tryGetProjectId(OAuth2AuthenticatedPrincipal user) {
        Object ext = user.getAttribute("ext");
        if (ext != null) {
            Map<?, ?> ext_map = (Map<?, ?>) ext;
            if (ext_map.containsKey("project_id")) {
                return ext_map.get("project_id").toString().toLowerCase();
            }
        }
        return null;
    }

    public static String tryGetOrgId(OAuth2AuthenticatedPrincipal user) {
        Object ext = user.getAttribute("ext");
        if (ext != null) {
            Map<?, ?> ext_map = (Map<?, ?>) ext;
            if (ext_map.containsKey("org_id")) {
                return ext_map.get("org_id").toString().toLowerCase();
            }
        }
        return null;
    }
}
