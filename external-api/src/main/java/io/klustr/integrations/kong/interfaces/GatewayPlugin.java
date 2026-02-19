package io.klustr.integrations.kong.interfaces;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.schemas.console.EntityReference;
import io.klustr.utils.U;

import java.util.List;
import java.util.Map;

public class GatewayPlugin {
    public String id;
    public String name;
    public String instance_name;
    public Boolean enabled;
    public List<String> protocols = Lists.newArrayList("grpc",
            "grpcs",
            "http",
            "https");

    public EntityReference service;
    public EntityReference consumer;

    public String toJson() {
        return U.toJson(this);
    }
}
