package io.klustr.consent.repository;

import com.google.common.collect.Sets;
import io.klustr.consent.ConsentStorage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import io.klustr.utils.U;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
public class UserConsentRepository {

    private final ConsentStorage storage;

    private final Storage lookup;

    public UserConsentRepository(Storage lookup, ConsentStorage storage) {
        this.storage = storage;
        this.lookup = lookup;
    }

    public DocumentResult<UserApplicationConsent> list(Pagination pagination) {
        return this.storage.user_app_consent().insecureQuery(pagination);
    }

    public List<UserApplicationConsent> getUserAppConsent(String subjectId) {
        DbQuery fq = Db.query("subject_id").eq(subjectId);
        DocumentResult<UserApplicationConsent> result = this.storage.user_app_consent().insecureQuery(fq, Pagination.all());
        // refresh latest metadata for project and app consent
        result.docs.forEach(app -> {
            Optional<Project> project = this.lookup.projects().findFirst(Db.query("app").with("id").eq(app.getAppId()));
            if (project.isEmpty()) return;
            if (project.get().getApp() == null) return;
        });
        return result.docs;
    }

    public List<UserExperimentConsent> getUserExperimentConsent(String subjectId) {
        DbQuery fq = Db.query("subject_id").eq(subjectId);
        DocumentResult<UserExperimentConsent> result = this.storage.user_experiment_consent().insecureQuery(fq, Pagination.all());
        return result.docs;
    }

    /**
     * Returns the consent for the specified user and client ID.
     *
     * @param subjectId The subject to get consent for
     * @param app_id The app ID to get consent for
     * @return The optional user consent if granted.
     */
    public Optional<UserApplicationConsent> getUserConsentByApp(String subjectId, String app_id) {
        DbQueryAndCondition fq = Db.and(
                Db.query("subject_id").eq(subjectId),
                Db.query("app_id").eq(app_id)
        );
        DocumentResult<UserApplicationConsent> l = this.storage.user_app_consent().insecureQuery(fq, Pagination.all());
        Optional<UserApplicationConsent> match = l.docs.stream().findFirst();
        if (match.isEmpty()) return match;

        // refresh latest metadata for project and app consent
        return match;
    }

    public Optional<UserExperimentConsent> getExperimentConsent(String subjectId, String experiment_id) {
        DbQueryAndCondition fq = Db.and(
                Db.query("subject_id").eq(subjectId),
                Db.query("experiment_id").eq(experiment_id)
        );
        DocumentResult<UserExperimentConsent> l = this.storage.user_experiment_consent().insecureQuery(fq, Pagination.all());
        Optional<UserExperimentConsent> match = l.docs.stream().findFirst();
        return match;
    }

    public List<UserExperimentConsent> getExperimentConsentByProject(String subjectId, String project_id) {
        DbQueryAndCondition fq = Db.and(
                Db.query("subject_id").eq(subjectId),
                Db.query("project_id").eq(project_id),
                Db.query("status").eq("active")
        );
        DocumentResult<UserExperimentConsent> l = this.storage.user_experiment_consent().insecureQuery(fq, Pagination.all().withCache(false));
        return l.docs;
    }

    public Optional<UserApplicationConsent> getUserConsentByClientId(String subjectId, String client_id) {
        DbQueryAndCondition fq = Db.and(
                Db.query("subject_id").eq(subjectId),
                Db.query("client_id").eq(client_id)
        );
        DocumentResult<UserApplicationConsent> l = this.storage.user_app_consent().insecureQuery(fq, Pagination.all());
        return l.docs.stream().findFirst();
    }

    public List<UserAgreementConsent> getUserAgreementByOrgId(String subjectId, String orgId) {
        DbQuery fq = Db.query("subject_id").eq(subjectId);
        DocumentResult<UserAgreementConsent> r = this.storage.user_agreement_consent().insecureQuery(fq, Pagination.all());
        return r.docs.stream().filter(x -> {
            Agreement agreement = this.lookup.agreements().getObject(x.getAgreementId());
            if (agreement == null) return false;
            if (agreement.getOrgId() == null) return false;
            if (agreement.getStatus() != null) {
                if (agreement.getStatus() == Agreement.Status.DELETED) {
                    return false;
                }
                if (agreement.getStatus() == Agreement.Status.DEPRECATED) {
                    return false;
                }
                if (agreement.getStatus() == Agreement.Status.DRAFT) {
                    return false;
                }
            }
            return agreement.getOrgId().equalsIgnoreCase(orgId);
        }).toList();
    }

    public void updateAppConsent(UserApplicationConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getAppId() );
        obj.setId(key);
        this.storage.user_app_consent().updateObject(key, obj);
    }

    public void updateExperimentConsent(UserExperimentConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getExperimentId() );
        obj.setId(key);
        this.storage.user_experiment_consent().updateObject(key, obj);
    }

    public void insertApplicationConsent(UserApplicationConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getAppId() );
        obj.setId(key);
        this.storage.user_app_consent().insertObject(key, obj);
    }

    public void insertExperimentConsent(UserExperimentConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getExperimentId() );
        obj.setId(key);
        this.storage.user_experiment_consent().insertObject(key, obj);
    }

    public void deleteApplicationConsent(UserApplicationConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getAppId());
        obj.setId(key);
        this.storage.user_app_consent().deleteObject(key);
    }

    public void deleteExperimentConsent(UserExperimentConsent obj) {
        String key = U.md5(obj.getSubjectId() + ":" + obj.getExperimentId());
        obj.setId(key);
        this.storage.user_experiment_consent().deleteObject(key);
    }

    public Set<String> getExistingScopes(String subjectId, String projectId, String clientId, String orgId) {
        Set<String> scopes = Sets.newHashSet();

        // scopes for the application itself
        Optional<UserApplicationConsent> match = this.getUserConsentByClientId(subjectId, clientId);
        if (match.isEmpty()) return scopes;
        scopes.addAll(match.get().getScopes().stream().map(UserConsentScope::getId).toList());

        // scopes at the organization level as implicit from agreements made by the user to the organization
        // provided the application is trusted by the organization.
        List<UserAgreementConsent> agreements = this.getUserAgreementByOrgId(subjectId, orgId);
        agreements.forEach(agg -> {
            if (agg.getStatus() == UserAgreementConsent.Status.ACTIVE) {
                scopes.addAll(agg.getScopes().stream().map(UserConsentScope::getId).toList());
            }
        });

        // TODO get scopes for agreements

        // scopes for experiments
        List<UserExperimentConsent> user_experiments_joined = this.getExperimentConsentByProject(subjectId, projectId);
        user_experiments_joined.forEach(x -> {
            Experiment exp = this.lookup.experiments().getObject(x.getExperimentId());
            if (exp.getStatus() == Experiment.Status.RUNNING || exp.getStatus() == Experiment.Status.PUBLISHED) {
                if (exp.getStartDate() != null && exp.getStartDate().isBeforeNow()) {
                    if (exp.getStopDate() != null && exp.getStopDate().isAfterNow()) {
                        exp.getScopes().forEach(scope -> {
                            scopes.add(scope.getId());
                        });
                    }
                }
            }
        });

        return Sets.newHashSet(scopes.stream().filter(Objects::nonNull).toList());
    }
}
