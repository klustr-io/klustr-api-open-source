package io.klustr.integrations.kong.models.plugins;

import io.klustr.integrations.kong.interfaces.GatewayPlugin;
import io.klustr.utils.U;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenIntrospectionPluginConfiguration {
    public String client_id;
    public String client_secret;
    public String introspection_endpoint;
    public boolean allow_anonymous = false;
    public String token_header = "Authorization";
    public Integer ttl = 30;

    public TokenIntrospectionPluginConfiguration(
            @Value("${kong.plugins.token_introspection.client_id:}") String client_id,
            @Value("${kong.plugins.token_introspection.client_secret:}") String client_secret,
            @Value("${kong.plugins.token_introspection.introspection_endpoint:https://hydra-admin.dev.klustr.io/admin/oauth2/introspect}") String introspection_endpoint) {
        this.introspection_endpoint = introspection_endpoint;
        this.client_id =client_id;
        this.client_secret = client_secret;
    }
}
