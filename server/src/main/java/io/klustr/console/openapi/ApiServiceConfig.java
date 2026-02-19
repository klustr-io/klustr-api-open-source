package io.klustr.console.openapi;

import com.google.common.collect.Lists;

import java.util.List;

public class ApiServiceConfig {
    public List<ApiService> services = Lists.newArrayList();
    public List<Servers> servers = Lists.newArrayList();

    public static class ApiService {
        public String group;
        public List<String> match = Lists.newArrayList();
        public List<String> exclude = Lists.newArrayList();
        public String title;
        public String description;
    }

    public static class Servers {
        public String url;
        public String name;
    }
}