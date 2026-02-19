package io.klustr.events;

import io.klustr.schemas.events.ApplicationEvent;
import io.klustr.schemas.events.LocationEvent;
import io.klustr.schemas.events.UserLocationHistory;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import org.springframework.stereotype.Component;

@Component
public class EventStorage {

    private final DbAdapter<LocationEvent> user_locations_events_db;
    private final DbAdapter<UserLocationHistory> user_locations_history;
    private final DbAdapter<ApplicationEvent> events;

    public EventStorage(DocumentDatabaseFactory pool, DbAdapterBroadcasterFactory factory) {
        this.events = new DbAdapter<>("events","events", pool, ApplicationEvent.class, factory);
        this.user_locations_events_db = new DbAdapter<>("events","user_locations_events", pool, LocationEvent.class, factory)
                .ensureIndex("user_id")
                .ensureIndex("client_id")
                .ensureIndex("org_id")
                .ensureIndex("project_id")
                .ensureIndex("timestamp");
        this.user_locations_history = new DbAdapter<>("events","user_locations_history", pool, UserLocationHistory.class, factory);
    }

    public DbAdapter<ApplicationEvent> events() {
        return this.events;
    }

    public DbAdapter<LocationEvent> user_locations_events() {
        return this.user_locations_events_db;
    }

    public DbAdapter<UserLocationHistory> user_locations_history() {
        return this.user_locations_history;
    }
}
