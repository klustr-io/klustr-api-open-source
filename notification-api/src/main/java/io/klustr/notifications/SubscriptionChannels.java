package io.klustr.notifications;

import com.google.common.collect.Lists;

import java.util.List;

public class SubscriptionChannels {
    public String id;

    public String user_id;
    public String project_id;
    public List<Channel> channels = Lists.newArrayList();
}