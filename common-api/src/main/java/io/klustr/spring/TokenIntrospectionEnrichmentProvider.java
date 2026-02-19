package io.klustr.spring;

import java.util.Map;

public interface TokenIntrospectionEnrichmentProvider {
    Map<String, String> getEnrichment(String client_id);
}
