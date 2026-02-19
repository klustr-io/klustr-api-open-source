package io.klustr.consent;

import com.google.common.collect.Maps;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.consent.ConsentAudit;
import io.klustr.schemas.console.consent.ConsentDelegationRequest;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.schemas.console.consent.types.UserOrganizationConsent;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ConsentStorage {

    private final DbAdapter<ConsentAudit> consent_audit;

    private final DbAdapter<UserApplicationConsent> user_app_consent;

    private final DbAdapter<UserExperimentConsent> user_experiment_consent;

    private final DbAdapter<UserAgreementConsent> user_agreement_consent;

    private final DbAdapter<UserOrganizationConsent> user_organization_consent;

    private final DbAdapter<ConsentAttribute> consent_scopes;

    public ConsentStorage(DocumentDatabaseFactory pool, DbAdapterBroadcasterFactory factory) {
        this.consent_audit = new DbAdapter<>("consent","consent_audit", pool, ConsentAudit.class, factory)
                .ensureIndex("subject_id")
                .ensureIndex("client_id")
                .ensureIndex("org_id");

        this.user_agreement_consent = new DbAdapter<>("consent","user_agreement_consent",
                pool, UserAgreementConsent.class, factory)
                .ensureIndex("subject_id")
                .ensureIndex("agreement_id")
                .ensureIndex("agreement_version");

        this.user_app_consent = new DbAdapter<>("consent","user_app_consent",
                pool, UserApplicationConsent.class, factory).ensureIndex("subject_id");

        this.user_experiment_consent = new DbAdapter<>("consent","user_experiment_consent",
                pool, UserExperimentConsent.class, factory).ensureIndex("subject_id");


        this.consent_scopes = new DbAdapter<>("consent","consent_scopes", pool, ConsentAttribute.class, factory);

        this.user_organization_consent = new DbAdapter<>("consent","user_organization_consent", pool, UserOrganizationConsent.class, factory)
                .ensureIndex("org_id");
    }

    public List<ConsentAttribute> getConsentScopes(String orgId) {
        DbQuery orgFq = Db.query("org_id").eq(orgId);
        List<ConsentAttribute> orgSpecific = consent_scopes().insecureQuery(orgFq, Pagination.all()).docs;

        DbQuery globalFq = Db.query("org_id").eq("*");
        List<ConsentAttribute> globalDocs = consent_scopes().insecureQuery(globalFq, Pagination.all()).docs;

        Map<String, ConsentAttribute> map = Maps.newConcurrentMap();

        // global docs first to allow override
        for (ConsentAttribute scope:
                globalDocs) {
            map.put(scope.getId(), scope);
        }

        // allow overrides of original
        for (ConsentAttribute scope:
                orgSpecific) {
            map.put(scope.getId(), scope);
        }

        return map.values().stream().toList();
    }

    public DbAdapter<ConsentAudit> consent_audit() {
        return this.consent_audit;
    }


    public DbAdapter<UserApplicationConsent> user_app_consent() {
        return this.user_app_consent;
    }

    public DbAdapter<UserExperimentConsent> user_experiment_consent() {
        return this.user_experiment_consent;
    }

    public DbAdapter<ConsentAttribute> consent_scopes() {
        return this.consent_scopes;
    }

    public DbAdapter<UserAgreementConsent> user_agreement_consent() { return this.user_agreement_consent; }

    public DbAdapter<UserOrganizationConsent> user_organization_consent() { return this.user_organization_consent; }
}
