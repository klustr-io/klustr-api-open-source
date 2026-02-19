package io.klustr.console.hosting.templates;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentVars {

    private Map<String, String> env = Maps.newConcurrentMap();

    public void add(String key, String value) {
        env.put(key, value);
    }

    public Map<String, String> getVars() {
        return env;
    }
}
