package io.klustr.persons.ekyc;

import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class EkycStorage {

    private final DbAdapter<EkycUserSession> eykc_sessions;


    public EkycStorage(DocumentDatabaseFactory pool, DbAdapterBroadcasterFactory broadcaster) {
        this.eykc_sessions = new DbAdapter<>("ekyc", "ekyc_sessions", pool, EkycUserSession.class, broadcaster);
    }

    public DbAdapter<EkycUserSession> eykc_sessions() {
        return this.eykc_sessions;
    }

}
