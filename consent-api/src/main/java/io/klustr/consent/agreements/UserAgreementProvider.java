package io.klustr.consent.agreements;

import com.google.common.collect.Lists;
import io.klustr.consent.ConsentScopeMergeStrategy;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.agreements.models.UserAgreementDetails;
import io.klustr.schemas.console.EntityReference;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQueryAndCondition;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserAgreementProvider {

    private final Storage storage;
    private final ConsentStorage consent;

    public UserAgreementProvider(Storage storage, ConsentStorage consent) {
        this.storage = storage;
        this.consent = consent;
    }

    public List<UserAgreementConsent> getUserAgreements(String userId) {
        DocumentResult<UserAgreementConsent> signed = this.consent.user_agreement_consent().insecureQuery(Db.query("subject_id").eq(userId), Pagination.all());
        return signed.docs;
    }

    public List<UserAgreementConsent> getUserAgreements(String userId, String orgId) {
        DocumentResult<UserAgreementConsent> signed = this.consent.user_agreement_consent().insecureQuery(Db.query("subject_id").eq(userId), Pagination.all());
        return signed.docs.stream().filter(doc -> {
            Agreement agreement = this.storage.agreements().getObject(doc.getAgreementId());
            if (agreement == null) return false;
            if (agreement.getOrgId() == null) return false;
            if (agreement.getStatus() != null) {
                if (agreement.getStatus() == Agreement.Status.DELETED) return false;
                if (agreement.getStatus() == Agreement.Status.DEPRECATED) return false;
            }
            return agreement.getOrgId().equalsIgnoreCase(orgId);
        }).toList();
    }

    public UserAgreementDetails get360Agreements(String userId) {
        DocumentResult<UserAgreementConsent> signed = this.consent.user_agreement_consent().insecureQuery(Db.query("subject_id").eq(userId), Pagination.all());

        UserAgreementDetails d = new UserAgreementDetails();
        d.agreements = signed.docs;
        d.details = new HashMap<>();

        // map agreement lookup information
        d.agreements.forEach(agg -> {
            Agreement agreement = this.storage.agreements().getObject(agg.getAgreementId());
            if (agreement != null) {
                d.details.put(agg.getId(), agreement);
            }
        });

        // map org lookup information
        d.agreements.forEach(agg -> {
            if (StringUtils.isBlank(agg.getOrgId())) return;
            Org org = storage.organizations().getObject(agg.getOrgId());
            if (org != null) {
                d.orgs.put(org.getId(), new EntityReference().withName(org.getName()).withId(org.getId()));
            }
        });
        return d;
    }

    public void deleteAgreement(String userId, String agreementId) {
        DbQueryAndCondition find = Db.and(
                Db.query("subject_id").eq(userId),
                Db.query("agreement_id").eq(agreementId)
        );
        Optional<UserAgreementConsent> match = this.consent.user_agreement_consent().findFirst(find);

        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity is not found."
            );
        }

        this.consent.user_agreement_consent().deleteObject(match.get().getId());
    }

    public void deleteAgreement(String userId, String orgId, String refId) {
        DbQueryAndCondition find = Db.and(
                Db.query("subject_id").eq(userId),
                Db.query("agreement_id").eq(refId)
        );
        Optional<UserAgreementConsent> match = this.consent.user_agreement_consent().findFirst(find);
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User does not have consent on file."
            );
        }
        Agreement agreement = this.storage.agreements().getObject(match.get().getAgreementId());
        if (!agreement.getOrgId().equalsIgnoreCase(orgId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity is not found."
            );
        }

        this.consent.user_agreement_consent().deleteObject(match.get().getId());
    }

    public void recordAgreement(String userId, String agreementId) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Agreement not found."
            );
        }

        DbQueryAndCondition find = Db.and(
                Db.query("subject_id").eq(userId),
                Db.query("agreement_id").eq(agreementId)
        );
        Optional<UserAgreementConsent> match = this.consent.user_agreement_consent().findFirst(find);

        // TODO get the major version/latest version?
        AgreementVersion version = agreement.getVersions().stream().reduce((x, y) -> y).get();

        List<UserConsentScope> newScopes = Lists.newArrayList();
        if (version.getScopes() != null) {
            List<ConsentScope> scopes = version.getScopes();
            newScopes = scopes.stream().map(x -> {
                return new UserConsentScope()
                        .withStatus(UserConsentScope.Status.ACTIVE)
                        .withId(x.getId())
                        .withDateCreated(DateTime.now());
            }).toList();
        }

        if (match.isEmpty()) {
            UserAgreementConsent c = new UserAgreementConsent();
            c.withFirstSeenDate(DateTime.now())
                    .withLastSeenDate(DateTime.now())
                    .withStatus(UserAgreementConsent.Status.ACTIVE)
                    .withAgreementId(agreement.getId())
                    .withOrgId(agreement.getOrgId())
                    .withId(UUID.randomUUID().toString())
                    .withScopes(newScopes)
                    .withSubjectId(userId);
            this.consent.user_agreement_consent().insertObject(c.getId(), c);
        } else {

            // see prior and new and see if anything was removed
            List<UserConsentScope> merged = ConsentScopeMergeStrategy.merge(newScopes, match.get().getScopes());

            match.get().withLastSeenDate(DateTime.now())
                    .withStatus(UserAgreementConsent.Status.ACTIVE)
                    .withScopes(merged);
            this.consent.user_agreement_consent().updateObject(match.get().getId(), match.get());
        }
    }


}
