package io.klustr.integrations.kong.models;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

public class KongConsumer {
    public String username;
    public String custom_id;

    public String id;

    public List<String> tags = Lists.newArrayList();

    public DateTime created_at;
}
