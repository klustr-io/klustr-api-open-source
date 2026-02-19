package io.klustr.integrations.kong.models.plugins;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AclPluginConfiguration {
    public List<String> deny = Lists.newArrayList();
    public List<String> allow = Lists.newArrayList();

}
