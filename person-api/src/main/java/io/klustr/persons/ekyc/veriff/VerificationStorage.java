package io.klustr.persons.ekyc.veriff;

import io.klustr.persons.ekyc.EkycUserSession;
import io.klustr.schemas.persons.veriff.VeriffNotificationPayload;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class VerificationStorage {

    private final DbAdapter<VeriffMessage> veriff_audit;
    private final DbAdapter<EkycUserSession> eky_user_sessions;

    private final DbAdapter<VeriffNotificationPayload> veriff_notifications;

    public VerificationStorage(DocumentDatabaseFactory pool, DbAdapterBroadcasterFactory factory) {
        this.veriff_audit = new DbAdapter<>("ekyc","veriff_audit", pool, VeriffMessage.class, factory);
        this.eky_user_sessions = new DbAdapter<>("ekyc","ekyc_sessions", pool, EkycUserSession.class, factory);
        this.veriff_notifications = new DbAdapter<>("ekyc","veriff_notifications", pool, VeriffNotificationPayload.class, factory);
    }

    public DbAdapter<VeriffMessage> veriff_audit() {
        return this.veriff_audit;
    }

    public DbAdapter<EkycUserSession> eky_user_sessions() {
        return this.eky_user_sessions;
    }

    public DbAdapter<VeriffNotificationPayload> veriff_notifications() {
        return this.veriff_notifications;
    }
}
