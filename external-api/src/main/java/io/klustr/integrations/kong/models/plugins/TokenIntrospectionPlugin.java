package io.klustr.integrations.kong.models.plugins;

import io.klustr.integrations.kong.interfaces.GatewayPlugin;
import io.klustr.utils.U;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenIntrospectionPlugin extends GatewayPlugin {
    public TokenIntrospectionPluginConfiguration config;

    public TokenIntrospectionPlugin(TokenIntrospectionPluginConfiguration configuration) {
        this.id = "16b65162-8d69-4d90-86a5-75c5d23582ff";
        this.name = "token-introspection";
        this.config = configuration;
    }

    @Override
    public String toJson() {
        return U.toJson(this);
    }
}
