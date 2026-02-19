package io.klustr.notifications;

import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import org.springframework.stereotype.Component;

@Component
public class NotificationStorage {

    private final DbAdapter<SubscriptionChannels> subs;

    public NotificationStorage(DocumentDatabaseFactory pool) {
        this.subs = new DbAdapter<>("notifications", "user_channels", pool, SubscriptionChannels.class)
                .ensureIndex("project_id")
                .ensureIndex("user_id");
    }

    public DbAdapter<SubscriptionChannels> subs() {
        return subs;
    }
}
