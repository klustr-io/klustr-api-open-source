package io.klustr.integrations.kong.models.plugins;

import com.google.common.collect.Lists;
import io.klustr.integrations.kong.interfaces.GatewayPlugin;
import io.klustr.schemas.console.EntityReference;
import io.klustr.utils.U;

import java.util.List;
import java.util.UUID;

public class AclPlugin extends GatewayPlugin {

    public AclPluginConfiguration config;

    @Deprecated
    public AclPlugin() {}

    public AclPlugin(String serviceId, String ... allow) {
        this.id = UUID.randomUUID().toString();
        this.name = "acl";
        this.service = new EntityReference().withId(serviceId).withName(serviceId);
        AclPluginConfiguration config = new AclPluginConfiguration();
        config.allow.addAll(Lists.newArrayList(allow));
        this.config = config;
    }

    @Override
    public String toJson() {
        return U.toJson(this);
    }
}
