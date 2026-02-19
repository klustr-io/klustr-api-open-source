package io.klustr.notifications.firebase;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import java.util.Map;

public class Notification {
    public String projectId;
    public String largeIcon;
    public String channelId;
    public String type;
    public String defaultButtonText;
    public String route;
    public String dismissButtonText;
    public String tag;
    public String color = "#ffffff";
    public String clickAction;
    public DateTime send;
    public String body;
    public String image;
    public String title;
    public String token;
    public String userId;
    public Map<String, String> params = Maps.newHashMap();
}
