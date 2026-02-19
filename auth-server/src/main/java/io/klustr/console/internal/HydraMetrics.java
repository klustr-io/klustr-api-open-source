package io.klustr.console.internal;

import io.micrometer.core.instrument.*;

/**
 * https://stackoverflow.com/questions/49614147/how-to-modify-prometheus-exposed-metric-names-using-actuator-in-spring-boot-2
 */
public class HydraMetrics {

    private final Counter clients;


    protected HydraMetrics(MeterRegistry registry, String client_id) {
        this(registry, "n/a", client_id);
    }

    protected HydraMetrics(MeterRegistry registry, String project_id, String client_id) {
        Tags tags = Tags.of(Tag.of("client_id", client_id), Tag.of("project_id", project_id));
        clients = registry.counter("hydra.token.enrichment.count", tags);
    }

    public Counter tokenErichment() {
        return this.clients;
    }


}
