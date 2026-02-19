package io.klustr.integrations.kong.interfaces;

import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.integrations.kong.KongService;
import io.klustr.schemas.services.ApiServiceSpecification;

import java.util.List;
import java.util.Optional;

public interface GatewayServiceProvider {
    EntityReference create(String serviceId, String url);
    void deleteService(String serviceId);
    void serviceAddRoutes(String serviceId, List<String> hosts, List<String> routePaths);
    List<ServiceRoute> serviceRoutes(String serviceId);

    void deleteServiceRoutes(String serviceId, String routeId);

     Optional<EntityReference> checkServiceExists(String serviceId);

    void requireRoleForService(String serviceId, String role);

    void health();
}
