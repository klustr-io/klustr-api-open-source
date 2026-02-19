package io.klustr.consent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.consent.responses.ConsentScopeDetails;
import io.klustr.consent.responses.ExperimentDetails;
import io.klustr.consent.responses.ExperimentReference;
import io.klustr.consent.responses.UserConsent360ResponseBody;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.consent.*;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.schemas.console.consent.types.UserOrganizationConsent;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.experiments.ExperimentMembership;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserConsentProvider {

    private final UserConsentRepository consent;
    private final ConsentStorage consent_storage;
    private final HydraApi hydra;
    private final Storage storage;

    public UserConsentProvider(Storage storage,
                               ConsentStorage consent_storage,
                               UserConsentRepository consent,
                               HydraApi hydra) {
        this.consent = consent;
        this.hydra = hydra;
        this.storage = storage;
        this.consent_storage = consent_storage;
    }

    public Optional<UserExperimentConsent> getExperimentConsent(String subjectId, String experiment_id) {
        return consent.getExperimentConsent(subjectId, experiment_id);
    }

    public  List<UserExperimentConsent> getExperimentConsentByProject(String subjectId, String project_id) {
        return consent.getExperimentConsentByProject(subjectId, project_id);
    }

    public UserConsentHierarchy getHierarchy(String userId) {

        Map<String, UserOrganizationConsentHierarchy> orgs = Maps.newConcurrentMap();

        DbQuery bySubject = Db.query("subject_id").eq(userId);
        DocumentResult<UserOrganizationConsent> orgConsent = this.consent_storage.user_organization_consent().insecureQuery(bySubject, Pagination.all());
        orgConsent.docs.forEach(doc -> {
            Org ref = this.storage.organizations().getObject(doc.getOrgId());
            UserOrganizationConsentHierarchy org = new UserOrganizationConsentHierarchy()
                    .withOrgId(doc.getOrgId())
                    .withOrgName(ref.getName())
                    .withScopes(doc.getScopes());
            orgs.put(doc.getOrgId(), org);
        });

        DocumentResult<UserAgreementConsent> agreements = this.consent_storage.user_agreement_consent().insecureQuery(bySubject, Pagination.all());
        agreements.docs.forEach(doc -> {

            UserOrganizationConsentHierarchy orgH = orgs.get(doc.getOrgId());
            if (orgH == null) {
                Org ref = this.storage.organizations().getObject(doc.getOrgId());
                orgH = new UserOrganizationConsentHierarchy()
                        .withOrgId(doc.getOrgId())
                        .withOrgName(ref.getName());
                orgs.put(doc.getOrgId(), orgH);
            }

            orgH.getAgreements().add(
                    new UserAgreementConsentHierarchy()
                            .withAgreementId(doc.getAgreementId())
                            .withScopes(doc.getScopes())
            );
        });

        DocumentResult<UserApplicationConsent> apps = this.consent_storage.user_app_consent().insecureQuery(bySubject, Pagination.all());
        apps.docs.forEach(doc -> {

            Optional<Project> project = this.storage.projects().findFirst(Db.query("app").with("id").eq(doc.getAppId()));
            if (project.isEmpty()) {
                // project has been deleted
                return;
            }

            UserOrganizationConsentHierarchy orgH = orgs.get(project.get().getOrgId());
            if (orgH == null) {
                Org ref = this.storage.organizations().getObject(project.get().getOrgId());
                orgH = new UserOrganizationConsentHierarchy()
                        .withOrgId(project.get().getOrgId())
                        .withOrgName(ref.getName());
                orgs.put(orgH.getOrgId(), orgH);
            }

            App app = project.get().getApp();
            UserApplicationConsentHierarchy appConsent = new UserApplicationConsentHierarchy()
                    .withAppId(doc.getAppId())
                    .withAppName(app != null && app.getBrand() != null ? app.getBrand().getName() : null)
                    .withAppLogo(app != null && app.getBrand() != null && app.getBrand().getLogo() != null ? app.getBrand().getLogo().getUrl() : null)
                    .withScopes(doc.getScopes());

            // find experiments
            DocumentResult<UserExperimentConsent> experiments = this.consent_storage.user_experiment_consent().insecureQuery(bySubject, Pagination.all());
            experiments.docs.forEach(exp -> {
                if (!exp.getAppId().equalsIgnoreCase(doc.getAppId())) return;
                Experiment ref = this.storage.experiments().getObject(exp.getExperimentId());

                appConsent.getExperiments().add(new UserExperimentConsentHierarchy()
                        .withExperimentId(exp.getExperimentId())
                        .withExperimentName(ref != null ? ref.getName() : null)
                        .withScopes(exp.getScopes())
                );
            });

            orgH.getApps().add(appConsent);
        });

        boolean isNotEmpty = orgs.values().stream().anyMatch(x -> {
            return !x.getApps().isEmpty();
        });

        return new UserConsentHierarchy()
                .withUserId(userId)
                .withEmpty(!isNotEmpty)
                .withOrgs(Lists.newArrayList(orgs.values()));
    }

    public void revokeAppConsent(String userId, String appId) {
        Optional<UserApplicationConsent> match = this.consent.getUserConsentByApp(userId, appId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        this.consent.deleteApplicationConsent(match.get());

        // revoke consent for all experiments
        hydra.revokeConsent(match.get().getClientId(), userId, true);

        // remove experiments associated to app ID
        // and ensure we publish them.
        DocumentResult<ExperimentMembership> memberships = this.storage
                .experiment_membership()
                .insecureQuery(Db.query("app_id").eq(appId),
                        Pagination.all());
        memberships.docs.forEach(x -> {
            x.setStatus(ExperimentMembership.Status.REVOKED);
            this.storage.experiment_membership().updateObject(x.getId(), x);
        });

        DocumentResult<UserExperimentConsent> user_consent_experiments =
                this.consent_storage.user_experiment_consent().insecureQuery(Db.query("app_id").eq(appId), Pagination.all());
        user_consent_experiments.docs.forEach(x -> {
            x.setStatus(UserExperimentConsent.Status.REVOKED);
            x.getScopes().forEach(s -> s.setStatus(UserConsentScope.Status.REVOKED));
            this.consent_storage.user_experiment_consent().updateObject(x.getId(), x);
        });
    }

    public void revokeAppScope(String userId, String appId, String scope) {
        Optional<UserApplicationConsent> match = this.consent.getUserConsentByApp(userId, appId);

        if (match.isEmpty()) {
            return;
        }

        // remove the scope on the consent granted
        UserApplicationConsent app = match.get();
        app.getScopes().removeIf(x -> x.getId().equalsIgnoreCase(scope));

        this.consent.updateAppConsent(app);

        // revoke any existing tokens
        this.hydra.revokeConsent(match.get().getClientId(), userId, false);
    }

    public UserConsent360ResponseBody get360ConsentByAppId(String userId, String appId) {
        UserConsent360ResponseBody res = new UserConsent360ResponseBody();
        Optional<UserApplicationConsent> match = this.consent.getUserConsentByApp(userId, appId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        res.consent = match.get();

        List<UserConsentScope> scopes = res.consent.getScopes();
        if (scopes != null) {
            res.scopes = scopes.stream().filter(x -> StringUtils.isNotBlank(x.getId())).map(scope -> {
                Optional<ConsentAttribute> detail = consent_storage.consent_scopes().tryGetObject(scope.getId());
                ConsentScopeDetails d = new ConsentScopeDetails();
                d.scope = scope.getId();
                d.details = detail.orElse(null);
                return d;
            }).toList();
        }

        // grab by project
        Optional<Project> project = this.storage.projects().findFirst(Db.query("app").with("id").eq(appId));
        if (project.isEmpty()) {
            throw new RuntimeException("App does not have a project id");
        }

        List<UserExperimentConsent> experiments = this.consent.getExperimentConsentByProject(userId, project.get().getId());
        if (experiments != null) {
            // grab experiments
            res.experiments = experiments.stream().map(x -> {
                Experiment object = this.storage.experiments().getObject(x.getExperimentId());
                ExperimentDetails d = new ExperimentDetails();
                d.details = new ExperimentReference(object);
                d.consent = x;
                return d;
            }).filter(x -> x.consent.getStatus() == UserExperimentConsent.Status.ACTIVE).toList();
        }

        res.metadata = new UserConsent360ResponseBody.AppMetadata();
        res.metadata.brand =project.get().getApp().getBrand();
        res.metadata.contact =project.get().getApp().getContact();
        res.metadata.links =project.get().getApp().getLinks();

        return res;
    }

    public List<UserApplicationConsent> getUserConsent(String userId) {
        return this.consent.getUserAppConsent(userId);
    }

    public UserApplicationConsent getUserConsentByClient(String userId, String clientId) {
        Optional<UserApplicationConsent> match = this.consent.getUserConsentByClientId(userId, clientId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return match.get();
    }

    public void removeConsentForExperiment(String userId, String experimentId) {
        Optional<UserExperimentConsent> match = this.consent.getExperimentConsent(userId, experimentId);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        // update all scopes to rejected
        match.get().setStatus(UserExperimentConsent.Status.REVOKED);
        List<UserConsentScope> updatedScopes = match.get()
                .getScopes().stream().map(x -> x
                        .withStatus(UserConsentScope.Status.REVOKED)
                        .withDateRevoked(DateTime.now())).toList();
        match.get().withScopes(updatedScopes);

        // update membership
        Optional<ExperimentMembership> membership = this.storage.experiment_membership().findFirst(Db.query("experiment_id").eq(experimentId));
        if (membership.isPresent()) {
            membership.get().setStatus(ExperimentMembership.Status.REVOKED);
            this.storage.experiment_membership().updateObject(membership.get().getId(), membership.get());
        }

        // update the users consent
        this.consent.updateExperimentConsent(match.get());

        this.hydra.revokeConsent(match.get().getClientId(), userId, true);
    }
}
