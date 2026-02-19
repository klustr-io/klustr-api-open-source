package io.klustr;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

@Component
public class TokenIntrospectionMetrics {

    public final Counter token_introspection_request_count;
    public final Counter token_introspection_success_count;
    public final Counter token_introspection_failure_count;
    public final Counter token_introspection_expired_count;
    public final Counter token_introspection_enrichment_count;

    public TokenIntrospectionMetrics(MeterRegistry metrics) {
        Tags tags = Tags.of(Tag.of("component", "security"));
        this.token_introspection_request_count = metrics.counter("token.introspection.request.total.count", tags);
        this.token_introspection_success_count = metrics.counter("token.introspection.request.success.count", tags);
        this.token_introspection_failure_count = metrics.counter("token.introspection.request.failure.count", tags);
        this.token_introspection_expired_count = metrics.counter("token.introspection.token-expired.count", tags);
        this.token_introspection_enrichment_count = metrics.counter("token.introspection.token-enrichment.count", tags);
    }

}
