package io.klustr.integrations.kong.interfaces;

import java.util.List;

public interface GatewayPluginProvider {
    List<GatewayPlugin> getPlugins();
    void upsertPlugin(GatewayPlugin plugin);

    void health();
}
