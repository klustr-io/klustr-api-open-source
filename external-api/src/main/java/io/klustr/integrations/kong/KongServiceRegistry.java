package io.klustr.integrations.kong;

import io.klustr.integrations.kong.interfaces.GatewayPluginProvider;
import io.klustr.integrations.kong.interfaces.GatewayServiceProvider;
import io.klustr.integrations.kong.interfaces.ServiceRegistry;
import io.klustr.integrations.kong.models.plugins.PrometheusPlugin;
import io.klustr.integrations.kong.models.plugins.TokenIntrospectionPlugin;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.services.ApiServiceRoutesSpecification;
import io.klustr.schemas.services.ApiServiceSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KongServiceRegistry implements ServiceRegistry {

    private final GatewayServiceProvider services;
    private final GatewayPluginProvider plugins;

    public KongServiceRegistry(GatewayServiceProvider services,
                               GatewayPluginProvider plugins,
                               TokenIntrospectionPlugin tokenPlugin,
                               PrometheusPlugin promethusPlugin) {
        this.services = services;
        this.plugins = plugins;
        // token and promethus are global scoped
        this.plugins.upsertPlugin(tokenPlugin);
        this.plugins.upsertPlugin(promethusPlugin);
    }

    public void health() {
        services.health();
        plugins.health();
    }

    public EntityReference create(ApiServiceSpecification obj) {
        EntityReference ref = this.services.create(obj.getId(), obj.getUrl());

        for (ApiServiceRoutesSpecification route : obj.getRoutes()) {
            this.services.serviceAddRoutes(ref.getId(), route.getHosts(), route.getPaths());
        }

        // ensure acls exist on the service by default
        this.services.requireRoleForService(ref.getId(), obj.getId());

        return ref;
    }

    @Override
    public void delete(ApiServiceSpecification obj) {
        if (this.services.checkServiceExists(obj.getExternalId()).isPresent()) {
            this.services.deleteService(obj.getExternalId());
        }
    }

    public EntityReference update(ApiServiceSpecification obj) {
        if (StringUtils.isNotBlank(obj.getExternalId())) {
            Optional<EntityReference> ref = this.services.checkServiceExists(obj.getExternalId());
            if (ref.isEmpty()) {
                return this.create(obj);
            } else {
                this.delete(obj);   // delete and re-add
                return this.create(obj);
            }
        } else {
            this.delete(obj);
            return this.create(obj);
        }
    }
}
