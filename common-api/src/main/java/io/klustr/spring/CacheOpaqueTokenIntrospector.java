package io.klustr.spring;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.TokenIntrospectionMetrics;
import io.klustr.utils.Json;
import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.resource.introspection.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * When using token introspection it is important under heavy loads
 * not to hammer the token introspection endpoint. We cache the token
 * for a limited amount of time to reduce the load on the introspection
 * endpoint.
 */
public class CacheOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private static final Logger log = LoggerFactory.getLogger(CacheOpaqueTokenIntrospector.class);

    private final OpaqueTokenIntrospector introspector;

    // cache access tokens for at least 1 minute to avoid saturating
    // the token validation endpoint, this is a balance between asking
    // for information or otherwise invalidating a 'bad actor' and being
    // exposed for up to 5 minute.
    private final Cache<String, OAuth2AuthenticatedPrincipal> accessTokens;

    private final Cache<String, Map<String, String>> extensions;

    private TokenIntrospectionEnrichmentProvider enrichment;

    private final TokenIntrospectionMetrics metrics;

    /**
     * Creates a new token introspector with a default of 5 minutes (300 seconds)
     *
     * @param uri          The URI of the token introspection endpoint
     * @param clientId     The client ID to use when asking for introspection
     * @param clientSecret THe client secret to use when asking for introspection.
     */
    public CacheOpaqueTokenIntrospector(String uri, String clientId, String clientSecret, TokenIntrospectionMetrics metrics) {
        this(uri, clientId, clientSecret, 60, null, metrics);
    }


    /**
     * Creates a new token introspector with a default of 5 minutes (300 seconds)
     *
     * @param uri             The URI of the token introspection endpoint
     * @param clientId        The client ID to use when asking for introspection
     * @param clientSecret    THe client secret to use when asking for introspection.
     * @param expiryInSeconds The number of seconds we should cache the token introspection result.
     */
    public CacheOpaqueTokenIntrospector(String uri, String clientId, String clientSecret, int expiryInSeconds, TokenIntrospectionMetrics metrics) {
        this(uri, clientId, clientSecret, expiryInSeconds, null, metrics);
    }

    /**
     * Creates a new token introspector with a default of 5 minutes (300 seconds)
     *
     * @param uri             The URI of the token introspection endpoint
     * @param clientId        The client ID to use when asking for introspection
     * @param clientSecret    THe client secret to use when asking for introspection.
     * @param expiryInSeconds The number of seconds we should cache the token introspection result.
     */
    public CacheOpaqueTokenIntrospector(String uri, String clientId, String clientSecret, int expiryInSeconds, TokenIntrospectionEnrichmentProvider enrichment, TokenIntrospectionMetrics metrics) {
        this.introspector = new NimbusOpaqueTokenIntrospector(uri, clientId, clientSecret);
        this.accessTokens = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(expiryInSeconds))
                .recordStats()
                .build();
        this.extensions = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(300))
                .recordStats()
                .build();
        this.enrichment = enrichment;
        this.metrics = metrics;
    }

    public @Override OAuth2AuthenticatedPrincipal introspect(String token) {
        try {
            this.metrics.token_introspection_request_count.increment();

            OAuth2AuthenticatedPrincipal principal = accessTokens.getIfPresent(token);
            if (principal == null) {
                principal = introspector.introspect(token);
                if (principal != null) {
                    accessTokens.put(token, principal);
                } else {
                    log.info("Token '{}' could not be introspected..", token);
                    this.metrics.token_introspection_failure_count.increment();
                    return null;
                }
            }

            Object expiry_obj = principal.getAttribute("exp");
            if (expiry_obj != null) {
                Instant expiry = (Instant) expiry_obj;
                if (expiry.isBefore(Instant.now())) {
                    accessTokens.invalidate(token);
                    this.metrics.token_introspection_expired_count.increment();
                    principal = introspector.introspect(token);
                }
            }

            Map<String, Object> extObject = new HashMap<>(principal.getAttributes());

            if (StringUtils.isBlank(principal.getName()) && this.enrichment != null) {
                this.metrics.token_introspection_enrichment_count.increment();
                String clientId = principal.getAttribute("client_id");
                extObject.put("sub", clientId);
                if (clientId != null) {
                    try {
                        if (!extObject.containsKey("ext")) {
                            extObject.put("ext", new HashMap<>());
                        }
                        Map<String, Object> ext = (Map<String, Object>) extObject.get("ext");
                        Map<String, String> cachedVersion = this.extensions.getIfPresent(clientId);
                        if (cachedVersion == null) {
                            cachedVersion = this.enrichment.getEnrichment(clientId);
                            if (cachedVersion != null) {
                                ext.putAll(cachedVersion);
                            }
                        }
                        this.extensions.put(clientId, cachedVersion != null ? cachedVersion : new HashMap<>());
                    } catch (Exception e) {
                        log.warn("Could not fetch Hydra client metadata for {}: {}", clientId, e.getMessage());
                    }
                }
            }

            this.metrics.token_introspection_success_count.increment();
            return new OAuth2IntrospectionAuthenticatedPrincipal(extObject, new PermissionExtractor(principal).getAuthorities());
        } catch (BadOpaqueTokenException ex) {
            this.metrics.token_introspection_failure_count.increment();
            accessTokens.invalidate(token); // Clean up cache

            // Must throw, not return null
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(
                            OAuth2ErrorCodes.INVALID_TOKEN,
                            "Provided token isn't active",
                            null
                    )
            );
        } catch (Exception ex) {
            log.error("Error while doing token introspection", ex);
            this.metrics.token_introspection_failure_count.increment();
            Map<String, String> meta = tryGetRequestMetadata();
            throw new RuntimeException(ex);
        }
    }

    private Map<String, String> tryGetRequestMetadata() {
        Map<String, String> metadata = Maps.newConcurrentMap();
        ServletRequestAttributes req = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (req != null) {
            HttpServletRequest request = req.getRequest();
            metadata.put("url", request.getRequestURI());
            metadata.put("method", request.getMethod());
            metadata.put("remote-host", request.getRemoteHost());
            metadata.put("remote-ip", request.getRemoteAddr());
        }
        return metadata;
    }

    private static class PermissionExtractor {

        private final ImmutableCollection<GrantedAuthority> authorities;

        public PermissionExtractor(OAuth2AuthenticatedPrincipal wrap) {
            // extensions has permissions
            List<GrantedAuthority> auth = Lists.newArrayList();
            Object ext = wrap.getAttribute("ext");
            if (ext != null) {
                ;
                net.minidev.json.JSONObject map = (net.minidev.json.JSONObject) ext;
                if (map.get("permissions") != null) {
                    JSONArray permissions = (JSONArray) map.get("permissions");
                    List<SimpleGrantedAuthority> list = permissions.stream().map(x -> {
                        return new SimpleGrantedAuthority(x.toString());
                    }).toList();
                    auth.addAll(list);
                }
                if (map.get("audiences") != null) {
                    JSONArray audiences = (JSONArray) map.get("audiences");
                    List<SimpleGrantedAuthority> list = audiences.stream().map(x -> {
                        return new SimpleGrantedAuthority(x.toString());
                    }).toList();
                    auth.addAll(list);
                }
            }
            auth.addAll(wrap.getAuthorities());
            this.authorities = ImmutableList.copyOf(auth);
        }

        public Collection<GrantedAuthority> getAuthorities() {
            return this.authorities;
        }
    }
}