package io.klustr.integrations.kong.models.plugins;

import org.springframework.stereotype.Component;

@Component
public class PrometheusPluginConfiguration {
    public Boolean bandwidth_metrics = false;
    public Boolean latency_metrics = false;
    public Boolean upstream_health_metrics = false;
    public Boolean per_consumer = true;
    public Boolean status_code_metrics = true;
}
