package io.klustr.integrations.kong.models.plugins;

import io.klustr.integrations.kong.interfaces.GatewayPlugin;
import io.klustr.utils.U;
import org.springframework.stereotype.Component;

@Component
public class PrometheusPlugin extends GatewayPlugin {

    public PrometheusPluginConfiguration config;

    public PrometheusPlugin(PrometheusPluginConfiguration configuration) {
        this.id = "6365aefb-34e2-49a8-93e0-aadcf9de5ff8";
        this.name = "prometheus";
        this.config = configuration;
    }

    @Override
    public String toJson() {
        return U.toJson(this);
    }
}
