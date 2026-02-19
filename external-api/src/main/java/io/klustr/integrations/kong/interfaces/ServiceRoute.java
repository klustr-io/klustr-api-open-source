package io.klustr.integrations.kong.interfaces;

import io.klustr.schemas.console.EntityReference;

import java.util.List;

public class ServiceRoute {
    public String id;
    public String name;
    public List<String> hosts;
    public List<String> paths;
    public List<String> protocols;
    public EntityReference service;
}
