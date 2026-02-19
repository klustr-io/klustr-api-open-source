package io.klustr.console.openapi;

import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApiDocumentation {

    public static OperationCustomizer sanityCheckAccess() {

        return (operation, handlerMethod) -> {
            Optional<Operation> op = Optional.ofNullable(handlerMethod.getMethodAnnotation(Operation.class));
            return operation;
        };
    }

    public static OperationCustomizer serviceAccountExpected() {
        return (operation, handlerMethod) -> {
            Optional<Operation> op = Optional.ofNullable(handlerMethod.getMethodAnnotation(Operation.class));
            StringBuilder sb = new StringBuilder();
            if (operation.getDescription() != null) {
                sb.append(operation.getDescription());
            }

            if (op.isPresent()) {
                SecurityRequirement[] security = op.get().security();
                if (security != null && security.length > 0) {
                    Optional<SecurityRequirement> accessToken = Arrays.stream(security).filter(x -> x.name().equalsIgnoreCase(OAuthCredentialType.SERVICE_TO_SERVICE)).findFirst();
                    if (accessToken.isPresent()) {
                        sb.append("\r\n");
                        sb.append("""

                                <div class="callout callout-info" style="margin: 1em 0em 1em 0em">
                                <p>
                                💻 <strong>Service Credentials</strong> is required to access this service.
                                </p>
                                </div>

                                """);
                    }
                }
            }

            operation.setDescription(sb.toString());
            return operation;
        };
    }

    public static OperationCustomizer oauthExpected() {
        return (operation, handlerMethod) -> {
            Optional<Operation> op = Optional.ofNullable(handlerMethod.getMethodAnnotation(Operation.class));
            StringBuilder sb = new StringBuilder();
            if (operation.getDescription() != null) {
                sb.append(operation.getDescription());
            }

            if (op.isPresent()) {
                SecurityRequirement[] security = op.get().security();
                if (security != null && security.length > 0) {
                    Optional<SecurityRequirement> accessToken = Arrays.stream(security).filter(x -> x.name().equalsIgnoreCase(OAuthCredentialType.USER_TO_SERVICE)).findFirst();
                    if (accessToken.isPresent()) {
                        sb.append("\r\n");
                        sb.append("""

                                <div class="callout callout-info" style="margin: 1em 0em 1em 0em">
                                <p>
                                👤 <strong>User Credential</strong>: You must
                                login and authenticate a user and use their access token
                                to invoke this service. This will operate the action under the
                                specified user.
                                </p>
                                </div>

                                """);
                    }
                }
            }

            if (operation.getSecurity() == null) {
                operation.setSecurity(Lists.newArrayList());
            }

            operation.setDescription(sb.toString());
            return operation;
        };
    }


    public static OperationCustomizer permissionCheck() {
        return (operation, handlerMethod) -> {
            Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
            StringBuilder sb = new StringBuilder();
            if (operation.getDescription() != null) {
                sb.append(operation.getDescription());
            }

            if (preAuthorizeAnnotation.isPresent()) {
                ExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext context = new StandardEvaluationContext();
                // Register mock security context
                context.setVariable("hasAuthority", true);
                context.setVariable("hasRole", true);

                Map<String, List<String>> perms = parsePreAuthorize(preAuthorizeAnnotation.get().value());
                List<AclMeta> aclMetas = extractAcls(preAuthorizeAnnotation.get().value());

                List<String> authorities = perms.get("authorities");
                List<String> roles = perms.get("roles");

                boolean isScope = false;

                Set<String> consent_scopes = authorities.stream().filter(x -> {
                    return x.startsWith("SCOPE_");
                }).map(x -> x.replace("SCOPE_", ""))
                        .collect(Collectors.toSet());

                // remove any consent scopes so we end up with only perms
                authorities.removeIf(x -> x.startsWith("SCOPE_"));

                if (!aclMetas.isEmpty()) {
                    sb.append("""
                            <div class="callout callout-warning" style="margin: 1em 0em 1em 0em">
                            <p><strong>🔒 Object Permission:</strong> Your calling token must have the following permissions...</p>
                            """);
                    aclMetas.forEach(acl -> {
                        sb.append("""
                                <ul>
                                <li>Permission: %s</li>
                                <li>Subject: %s</li>
                                <li>Namespace: %s</li>
                                <li>Object: %s</li>
                                <ul>
                            """.formatted(acl.relation, acl.id, acl.namespace, acl.object));
                    });
                    sb.append("""
                         </div>""");
                }

                if (!consent_scopes.isEmpty()) {
                    consent_scopes.forEach(scope -> {
                        sb.append("""
                                
                            <div class="callout callout-warning" style="margin: 1em 0em 1em 0em">
                            <p>
                            🔒 <strong>Consent: <span style="font-family: monospace">%s</span></strong> is required in order for the call to succeed. Ensure that you have the consent
                            approval for the user or this call will fail.
                            </p>
                            </div>
                            """.formatted(scope));
                    });
                } else {
                    authorities.forEach(perm -> {
                        sb.append("""
                                
                            <div class="callout callout-warning" style="margin: 1em 0em 1em 0em">
                            <p>
                            🔒 <strong>Permission: <span style="font-family: monospace">%s</span></strong> is required in order for the call to succeed. You must configure permissions
                            for the person or service calling this endpoint and add this permission to their identity.
                            </p>
                            </div>
                                
                            """.formatted(perm));
                    });
                }
                if (!roles.isEmpty()) {
                    roles.forEach(perm -> {
                        sb.append("""
                                
                            <div class="callout callout-warning" style="margin: 1em 0em 1em 0em">
                            <p>
                            🔒 <strong>Role: <span style="font-family: monospace">%s</span></strong> is required in order for the call to succeed. You must configure permissions
                            for the person or service calling this endpoint and add this role to their identity.
                            </p>
                            </div>
                                
                            """.formatted(perm));
                    });
                }


                // add the extension for metadata extraction
                Map<String, Object> extensions = operation.getExtensions();
                if (extensions == null) {
                    operation.setExtensions(Maps.newHashMap());
                }

                if (!consent_scopes.isEmpty()) {
                    operation.getExtensions().put("x-scopes", consent_scopes);
                }
                if (!authorities.isEmpty()) {
                    operation.getExtensions().put("x-permissions", authorities);
                }
                if (!roles.isEmpty()) {
                    operation.getExtensions().put("x-roles", roles);
                }
                if (!aclMetas.isEmpty()) {
                    operation.getExtensions().put("x-acls", aclMetas);
                }
            }

            operation.setDescription(sb.toString());
            return operation;
        };
    }

    public static Map<String, List<String>> parsePreAuthorize(String preAuthValue) {
        Map<String, List<String>> parsedData = new HashMap<>();

        // Patterns to match hasAuthority('...') and hasRole('...')
        Pattern authorityPattern = Pattern.compile("hasAuthority\\('([^']+)'\\)");
        Pattern rolePattern = Pattern.compile("hasRole\\('([^']+)'\\)");

        List<String> authorities = extractMatches(preAuthValue, authorityPattern);
        List<String> roles = extractMatches(preAuthValue, rolePattern);

        parsedData.put("authorities", authorities);
        parsedData.put("roles", roles);

        return parsedData;
    }

    private static List<String> extractMatches(String input, Pattern pattern) {
        List<String> matches = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group(1)); // Extract content inside the single quotes
        }
        return matches;
    }

    private static List<AclMeta> extractAcls(String expression) {

        List<AclMeta> results = new ArrayList<>();

        // Regex pattern to extract parameters from hasAcl(principal, objectId, namespace, relation)
        Pattern pattern = Pattern.compile("hasAcl\\(([^,]+),\\s*([^,]+),\\s*'([^']+)',\\s*'([^']+)'\\)");

        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            AclMeta aclMeta = new AclMeta();
            aclMeta.id =  matcher.group(1).trim();
            aclMeta.object =  matcher.group(2).trim();
            aclMeta.namespace =  matcher.group(3).trim();
            aclMeta.relation =  matcher.group(4).trim();

            if (aclMeta.object.startsWith("#")) {
                aclMeta.object = "{" + aclMeta.object.replace("#","") + "}";
            }
            if (aclMeta.id.startsWith("principle.name")) {
                aclMeta.id = "{user.id}";
            }
            results.add(aclMeta);
        }

        return results;
    }

    public static class AclMeta {
        public String id;
        public String object;
        public String namespace;
        public String relation;
    }
}

