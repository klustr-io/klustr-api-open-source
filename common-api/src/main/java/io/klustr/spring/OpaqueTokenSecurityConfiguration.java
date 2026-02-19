package io.klustr.spring;

import com.codahale.metrics.MetricRegistry;
import io.klustr.TokenIntrospectionMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * Enables the configuration of opaqure tokens
 */
@Configuration
public class OpaqueTokenSecurityConfiguration {
    private static final Logger log = LoggerFactory.getLogger(OpaqueTokenSecurityConfiguration.class);

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.expiry:60}")
    Integer expiry;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    String clientSecret;

    @Autowired
    TokenIntrospectionEnrichmentProvider enrichment;

    @Autowired
    MeterRegistry metrics;

    public OpaqueTokenSecurityConfiguration() {}

    @Autowired
    public OpaqueTokenSecurityConfiguration(TokenIntrospectionEnrichmentProvider enrichment) {
        this.enrichment= enrichment;
    }

    @Bean
    public OpaqueTokenIntrospector introspector() {
        if (log.isDebugEnabled()) {
            log.debug("Getting introspection from Cache.");
        }
        return new CacheOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret, expiry, enrichment, new TokenIntrospectionMetrics(metrics));
    }

}