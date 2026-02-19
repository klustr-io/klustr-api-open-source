package io.klustr.console.prometheus;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.gitlab.GitLabProjectResourceProvider;
import io.klustr.permissions.DefaultUserPermissions;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.storage.DbAdapter;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Component
@RequestMapping("/prometheus")
@Tag(name = "Prometheus Proxy",
        description = "Enables grafana to inject authentication bearer token and this proxy will intercept and add the required labels.")
public class PrometheusProxyService {

    private final String userInfoEndpoint = "https://secure.dev.klustr.io/hydra/userinfo";
    private final String prometheusUrl = "https://metrics.dev.klustr.io/";

    private static final Logger log = LoggerFactory.getLogger(GitLabProjectResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();

    private final Cache<String, UserInfoResponse> userInfoCache;

    private final DefaultUserPermissions perms;
    private final Cache<String, DefaultUserPermissions.LinkedObjects> permissionCache;

    public PrometheusProxyService(Storage storage, PermissionProvider permissions) {
        userInfoCache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(300))  // can last awhile due to sign in.
                .recordStats()
                .build();
        perms = new DefaultUserPermissions(permissions, storage);
        permissionCache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(30))
                .recordStats()
                .build();
    }

    private static class UserInfoResponse {
        public String sub;
        public String iss;
        public Set<String> orgs = Sets.newHashSet();
        public Set<String> projects = Sets.newHashSet();
        public Set<String> roles = Sets.newHashSet();
        public Set<String> permissions = Sets.newHashSet();
    }

    private UserInfoResponse getUserInfo(String bearerToken) {

        UserInfoResponse cacheCopy = userInfoCache.getIfPresent(bearerToken);
        if (cacheCopy != null) {
            return cacheCopy;
        }

        Request req = new Request.Builder()
                .url(userInfoEndpoint)
                .header("Authorization", "Bearer " + bearerToken)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            UserInfoResponse obj = U.fromJson(json, UserInfoResponse.class);
            userInfoCache.put(bearerToken, obj);
            return obj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }



    /**
     * Proxy all Prometheus API requests
     * Supports: /api/v1/query, /api/v1/query_range, /api/v1/series, etc.
     */
    @RequestMapping(value = "/api/**", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> proxyPrometheusRequest(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-Service-Token", required = false) String serverAuthHeader,
            HttpServletRequest request,
            @RequestParam Map<String, String> queryParams,
            @RequestBody(required = false) String body) {

        try {

            // server is passing it in as an alert or core system call we can let it through due to trust
            // as this is NOT a user, a core admin/service account.
            if (StringUtils.isBlank(authHeader) && serverAuthHeader.equalsIgnoreCase("Bearer YOUR_MACHINE_TOKEN")) {
                String realPrometheusPath = request.getRequestURI().replace("/prometheus", "");

                return proxy(
                        realPrometheusPath,
                        queryParams,      // original unmodified params
                        body,             // original body
                        request           // original request, for headers/mime-type
                );
            }

            // We actually have a user and we want to extract and check roles/permissions
            String token = extractBearerToken(authHeader);
            if (token == null) {
                if (log.isDebugEnabled()) {
                    log.debug("authorization header not present in query");
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\":\"Invalid Authorization header\"}");
            }

            UserInfoResponse userInfo = getUserInfo(token);

            // is this user a super user? if so we just proxy it through...
            if (isUserSuperAdmin(userInfo)) {
                if (log.isDebugEnabled()) {
                    log.info("User is super-admin → forwarding unmodified request");
                }

                String realPrometheusPath = request.getRequestURI().replace("/prometheus", "");

                return proxy(
                        realPrometheusPath,
                        queryParams,      // original unmodified params
                        body,             // original body
                        request           // original request, for headers/mime-type
                );
            }

            // Lookup the users default permissions to which projects (namespaces/orgs/etc)
            DefaultUserPermissions.LinkedObjects objs = permissionCache.getIfPresent(userInfo.sub);
            if (objs == null) {
                objs = perms.resolve(userInfo.sub);
                permissionCache.put(userInfo.sub, objs);
            }

            if (log.isDebugEnabled()) {
                log.debug("User has access to projects: {}", Joiner.on(",").join(objs.projectIds));
            }

            // Build project label matcher
            String projectMatcher = buildProjectMatcher(objs.projectIds);

            // Modify the query to include project filter
            Map<String, String> modifiedParams = new HashMap<>(queryParams);
            if (modifiedParams.containsKey("query")) {
                String originalQuery = modifiedParams.get("query");
                String filteredQuery = injectProjectFilter(originalQuery, projectMatcher);
                modifiedParams.put("query", filteredQuery);
                if (log.isDebugEnabled()) {
                    log.debug("Original query: {}", originalQuery);
                    log.debug("Filtered query: {}", filteredQuery);
                }
            }

            // Handle POST body for query_range or other endpoints
            String modifiedBody = body;
            if (body != null && body.contains("query")) {
                modifiedBody = injectProjectFilterInBody(body, projectMatcher);
            }

            // fix the path to the right path on prom
            String realPrometheusPath = request.getRequestURI().replace("/prometheus", "");

            if (log.isDebugEnabled()) {
                log.info("URI: {}", realPrometheusPath);
            }

            return proxy(realPrometheusPath, modifiedParams, modifiedBody, request);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + ex.getMessage() + "\"}");
        }
    }

    private ResponseEntity<String> proxy(String path,
                                         Map<String, String> queryParams,
                                         String body,
                                         HttpServletRequest originalRequest) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromHttpUrl(prometheusUrl + path);
            queryParams.forEach(uriBuilder::queryParam);
            String url = uriBuilder.toUriString();

            if (log.isDebugEnabled()) {
                log.info("Proxy resolves URL -> {}", url);
            }

            String method = originalRequest.getMethod();
            String contentType = originalRequest.getContentType();
            String accept = originalRequest.getHeader("Accept");

            Request.Builder builder = new Request.Builder()
                    .url(url);

            if (accept != null) {
                builder.header("Accept", accept);
            }

            // Body only for POST
            okhttp3.RequestBody reqBody = null;
            if ("POST".equalsIgnoreCase(method) && body != null) {
                reqBody = okhttp3.RequestBody.create(body,
                        MediaType.parse(contentType != null ? contentType : "application/x-www-form-urlencoded"));
            }
            if (reqBody != null && contentType != null) {
                builder.header("Content-Type", contentType);
            }

            // add any extra headers like x-foo, go here.
            for (String header : Collections.list(originalRequest.getHeaderNames())) {
                if (header.toLowerCase().startsWith("x-"))
                    builder.header(header, originalRequest.getHeader(header));
            }

            try (Response res = http.newCall(builder.build()).execute()) {
                String text = res.body().string();
                if (!res.isSuccessful()) {
                    throw new RuntimeException(text);
                }
                String downstreamContentType = res.header("Content-Type", "application/json");
                // TODO should this return the proxy mime?
                return ResponseEntity.status(res.code()).contentType(org.springframework.http.MediaType.parseMediaType(downstreamContentType)).body(text);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        } catch (Exception e) {
            log.error("Error forwarding to Prometheus", e);
            throw new RuntimeException("Failed to query Prometheus: " + e.getMessage());
        }
    }

    private String injectProjectFilter(String query, String projectMatcher) {
        if (query == null || query.isEmpty()) return query;

        // Strict metric selector matcher:
        // 1. must start at beginning OR after (space|operator|paren)
        // 2. must start with letter or _:
        // 3. may contain digits/underscores/colons
        // 4. must be immediately followed by {
        String regex = "(?<![a-zA-Z0-9_:])([a-zA-Z_:][a-zA-Z0-9_:]*)\\s*\\{";

        return query.replaceAll(regex, "$1{" + projectMatcher + ",");
    }

    /*
    private String injectProjectFilter(String query, String projectMatcher) {
        if (query == null || query.isEmpty()) {
            return query;
        }

        // Pattern 1: metric{existing_labels} -> metric{project=~"...",existing_labels}
        String modified = query.replaceAll(
                "(\\w[\\w_:]*?)\\s*\\{",
                "$1{" + projectMatcher + ","
        );

        // Pattern 2: metric_name (no labels) -> metric_name{project=~"..."}
        // Only if we didn't already modify it
        if (modified.equals(query)) {
            // Match metric names not followed by {
            modified = query.replaceAll(
                    "(\\w[\\w_:]*?)([^{\\w_:]|$)",
                    "$1{" + projectMatcher + "}$2"
            );
        }

        return modified;
    }*/

    private String injectProjectFilterInBody(String body, String projectMatcher) {
        // Handle form-encoded bodies with query= parameter
        if (body.contains("query=")) {
            String[] params = body.split("&");
            StringBuilder modified = new StringBuilder();

            for (String param : params) {
                String[] kv = param.split("=", 2);
                if (kv.length == 2 && kv[0].equals("query")) {
                    String decodedQuery = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    String filteredQuery = injectProjectFilter(decodedQuery, projectMatcher);
                    String encodedQuery = URLEncoder.encode(filteredQuery, StandardCharsets.UTF_8);
                    modified.append("query=").append(encodedQuery);
                } else {
                    modified.append(param);
                }
                modified.append("&");
            }

            return modified.substring(0, modified.length() - 1); // Remove trailing &
        }

        return body;
    }

    private boolean isUserSuperAdmin(UserInfoResponse info) {
        if (info.roles.contains("orgs:klustr.io:admin")) {
            return true;
        }
        return false;
    }

    private String buildProjectMatcher(Set<String> projects) {
        // Create regex matcher: project=~"proj1|proj2|proj3"
        String projectsRegex = String.join("|", projects);
        return String.format("project=~\"%s\"", projectsRegex);
    }

    private String extractBearerToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
