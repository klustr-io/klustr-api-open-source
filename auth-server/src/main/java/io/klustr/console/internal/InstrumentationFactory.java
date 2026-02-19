package io.klustr.console.internal;

import com.google.common.collect.Maps;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.Map;

public class InstrumentationFactory {

    private static final Map<String, HydraMetrics> instances = Maps.newConcurrentMap();
    private final MeterRegistry registry;
    // private final Timer enrichment_timer;

    public InstrumentationFactory(MeterRegistry registry) {
        this.registry = registry;
        // this.enrichment_timer = registry.timer("");
    }

    public HydraMetrics client(String client_id) {
        HydraMetrics hydraMetrics = instances.get(client_id);
        if (hydraMetrics == null) {
            synchronized (instances) {
                hydraMetrics = instances.get(client_id);
                if (hydraMetrics == null) {
                    hydraMetrics = new HydraMetrics(registry, client_id);
                    instances.put(client_id, hydraMetrics);
                }
            }
        }
        return hydraMetrics;
    }

    public HydraMetrics client(String project_id, String client_id) {
        String k = project_id + ":" + client_id;
        HydraMetrics hydraMetrics = instances.get(k);
        if (hydraMetrics == null) {
            synchronized (instances) {
                hydraMetrics = instances.get(k);
                if (hydraMetrics == null) {
                    hydraMetrics = new HydraMetrics(registry, project_id, client_id);
                    instances.put(k, hydraMetrics);
                }
            }
        }
        return hydraMetrics;
    }

//    public Timer tokenErichmentTimer() {
//        return this.enrichment_timer;
//    }
}
