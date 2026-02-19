package io.klustr.console.openapi;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckAndValidateSecurity {

    public static String markdown(OpenAPI api) {
        Collection<String> errors = new TreeSet<String>(validate(api));

        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    public static String listServiceEndpoints(OpenAPI api, String credentialType) {

        List<Ref> results = new ArrayList<>();

        api.getPaths().keySet().forEach(url -> {
            PathItem path = api.getPaths().get(url);
            if (checkSecurity(path.getDelete(), credentialType)) {
                results.add(formatOutput(url, "DELETE", path.getDelete()));
            }
            if (checkSecurity(path.getPost(), credentialType)) {
                results.add(formatOutput(url, "POST", path.getPost()));
            }
            if (checkSecurity(path.getGet(), credentialType)) {
                results.add(formatOutput(url, "GET", path.getGet()));
            }
            if (checkSecurity(path.getPatch(), credentialType)) {
                results.add(formatOutput(url, "PATCH", path.getPatch()));
            }
            if (checkSecurity(path.getPut(), credentialType)) {
                results.add(formatOutput(url, "PUT", path.getPut()));
            }
        });

        String label = "No Security";
        if (!StringUtils.isBlank(credentialType)) {
            switch (credentialType) {
                case OAuthCredentialType.SERVICE_TO_SERVICE -> label = "Service to Service";
                case OAuthCredentialType.USER_TO_SERVICE -> label = "User to Service";
                case "BASIC" -> label = "Basic Auth";
                case "API_KEY" -> label = "API KEY";
            }
        }

        results.sort(Comparator.comparing(ref -> ref.url));
        StringBuilder sb = new StringBuilder();
        sb.append("""
                <table>
                <thead>
                <tr>
                <th>URL</th>
                <th>method</th>
                <th>Perms</th>
                <th>Consent</th>
                <th>Acls</th>
                </tr>
                <tbody>
                """);

        results.forEach(x -> {
            sb.append(x.text);
        });

        sb.append("</tbody></table>\r\n");

        return "\r\n\r\n### " + label + " Endpoints:\r\n \r\n" + sb.toString();
    }

    private static class Ref {
        public String url;
        public String text;
    }

    private static Ref formatOutput(String url, String verb, Operation path) {
        Set<String> perms = getPermissions(url, path);
        Set<String> scopes = getConsent(url, path);
        List<ApiDocumentation.AclMeta> acls = getAcls(url, path);
        Ref ref = new Ref();
        ref.url = url;
        ref.text = String.format("""
                <tr>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                </tr>
                """, url, verb,
                Joiner.on(",").join(perms) ,
                Joiner.on(",").join(scopes) ,
                Joiner.on(",").join(acls.stream().map(f -> f.id + " has '" + f.relation + "' in '" + f.namespace +"' with ref '" + f.object + "'").toList())
                );
        return ref;
    }

    private static boolean checkSecurity(Operation op, String credentialType) {
        if (op == null) return false;
        if (StringUtils.isBlank(credentialType)) {
            Optional<SecurityRequirement> anySecurity = op.getSecurity().stream().filter(x -> true).findFirst();
            return anySecurity.isEmpty();
        } else {
            Optional<SecurityRequirement> hasUserCredential = op.getSecurity().stream().filter(x -> x.containsKey(credentialType)).findFirst();
            return hasUserCredential.isPresent();
        }
    }


    public static String listUniquePermissions(OpenAPI api) {
        TreeSet<String> permissions = new TreeSet<>();

        api.getPaths().keySet().forEach(key -> {
            PathItem path = api.getPaths().get(key);
            permissions.addAll(getPermissions(key, path.getPost()));
            permissions.addAll(getPermissions(key, path.getDelete()));
            permissions.addAll(getPermissions(key, path.getPut()));
            permissions.addAll(getPermissions(key, path.getGet()));
            permissions.addAll(getPermissions(key, path.getPatch()));
        });

        if (permissions.isEmpty()) {
            return "";
        }
        String txt = Joiner.on("\r\n").join(permissions.stream().map(x -> "* " + x).toList());
        return "\r\n### Permissions:\r\n These api calls require permissions to be added to the calling user/identifier. Use the console or APIs to add users to your project, roles, and manage permissions to the organization to grant them specific permissions to these will enable access.\r\n" + txt;
    }

    public static String listUniqueConsent(OpenAPI api) {
        TreeSet<String> consent = new TreeSet<>();

        api.getPaths().keySet().forEach(key -> {
            PathItem path = api.getPaths().get(key);
            consent.addAll(getConsent(key, path.getPost()));
            consent.addAll(getConsent(key, path.getDelete()));
            consent.addAll(getConsent(key, path.getPut()));
            consent.addAll(getConsent(key, path.getGet()));
            consent.addAll(getConsent(key, path.getPatch()));
        });

        if (consent.isEmpty()) {
            return "";
        }

        String txt = Joiner.on("\r\n").join(consent.stream().map(x -> "* " + x).toList());
        return "\r\n### Consent Scopes:\r\nThese api calls assume that the calling user is the owner of the data and does require the app to be granted consent either directly or indirectly given agreements, consent screens, or organizational level agreements." + txt;
    }

    private static Set<String> getPermissions(String path, Operation op) {
        if (op == null) return Sets.newHashSet();
        if (op.getSecurity() == null) return Sets.newHashSet();
        if (op.getExtensions() == null) return Sets.newHashSet();
        Object permission = op.getExtensions().get("permissions");
        if (permission == null) return Sets.newHashSet();
        if (permission.toString().matches("^SCOPE_.*")) return Sets.newHashSet();

        return Sets.newHashSet(permission.toString());
    }

    private static Set<String> getConsent(String path, Operation op) {
        if (op == null) return Sets.newHashSet();
        if (op.getSecurity() == null) return Sets.newHashSet();
        if (op.getExtensions() == null) return Sets.newHashSet();
        Object permission = op.getExtensions().get("scopes");
        if (permission == null) return Sets.newHashSet();

        return Sets.newHashSet(permission.toString());
    }

    private static List<ApiDocumentation.AclMeta> getAcls(String path, Operation op) {
        if (op == null) return Lists.newArrayList();
        if (op.getSecurity() == null) return Lists.newArrayList();
        if (op.getExtensions() == null) return Lists.newArrayList();
        Object permission = op.getExtensions().get("acls");
        if (permission == null) return Lists.newArrayList();

        return (List<ApiDocumentation.AclMeta>)permission;
    }


    private static boolean isPathWithUserIdentifier(String path) {
        if (path.toLowerCase().contains("{userid}")) {
            return true;
        }
        if (path.toLowerCase().contains("{user}")) {
            return true;
        }
        if (path.toLowerCase().contains("{user_id}")) {
            return true;
        }
        if (path.toLowerCase().contains("{subjectid}")) {
            return true;
        }
        if (path.toLowerCase().contains("{subject_id}")) {
            return true;
        }
        return false;
    }

    private static List<String> getPathResourceIds(String path) {
        Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}");
        List<String> items = Lists.newArrayList();

        Matcher matcher = pattern.matcher(path);
        while (matcher.find()) {
            items.add(matcher.group(1).trim());
        }
        return items;
    }

    private static List<String> validate(String path, Operation op, String verb) {
        if (op == null) return Lists.newArrayList();

        List<String> errors = Lists.newArrayList();
        if (op.getSecurity() == null) {
            errors.add("```" + verb + ":" + path + "``` is missing security!");
        } else if (op.getSecurity().isEmpty()) {
            errors.add("```" + verb + ":" + path + "``` is missing security!");
        }

        Map<String, Object> ext = op.getExtensions();
        Object permission = ext != null ? ext.get("permissions") : null;
        Object roles = ext != null ? ext.get("roles") : null;
        Object acls = ext != null ? ext.get("acls") : null;
        Object scopes = ext != null ? ext.get("scopes") : null;

        // this path has a user identifier, make sure it has security
        // and security is set to NOT a user credential
        Optional<SecurityRequirement> hasUserCredential = op.getSecurity().stream().filter(x -> x.get(OAuthCredentialType.USER_TO_SERVICE) != null).findFirst();
        if (hasUserCredential.isPresent()) {

            if (isPathWithUserIdentifier(path)) {
                if (acls == null) {
                    errors.add("```" + path + " (" + verb + ")``` path contains a subject identifier, and must also then check acls for example ```hasAcl(principal, #organizationId, 'orgs', 'org.user.account.types.update')```");
                }
            }

            // must have scope or permissions
            if (scopes == null && acls == null) {
                errors.add("```" + path + " (" + verb + ")``` requires either scope or acls for access.");
            }
        } else if (path.contains("{") && path.contains("}")) {
            // path is accessing a resource, it should have an ACL to check access rights
            if (acls == null) {
                List<String> resourceIds = getPathResourceIds(path);
                errors.add("```" + path + " (" + verb + ")``` path contains resource item, please ensure checking access ```hasAcl(principal, #" + resourceIds.stream().findFirst().get() + ", '<type>', '<permission>')```");
            }
        } else {
            if (ext == null) {
                errors.add("```" + path + " (" + verb + ")``` has no security constraints!");
            } else if (!ext.keySet().contains("acls")) {
                errors.add("```" + path + " (" + verb + ")``` has no acls");
            }
        }

        return errors;
    }

    private static List<String> validate(OpenAPI api) {
        List<String> errors = Lists.newArrayList();

        api.getPaths().keySet().forEach(key -> {
            PathItem path = api.getPaths().get(key);

            errors.addAll(validate(key, path.getPost(), "POST"));
            errors.addAll(validate(key, path.getDelete(), "DELETE"));
            errors.addAll(validate(key, path.getPut(), "PUT"));
            errors.addAll(validate(key, path.getGet(), "GET"));
            errors.addAll(validate(key, path.getPatch(), "PATCH"));

        });

        return errors;
    }
}
