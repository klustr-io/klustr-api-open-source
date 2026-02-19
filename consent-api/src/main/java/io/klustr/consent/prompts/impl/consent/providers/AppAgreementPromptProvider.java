package io.klustr.consent.prompts.impl.consent.providers;

import com.google.common.collect.Sets;
import io.klustr.SemanticVersion;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.AgreementConsentPrompt;
import io.klustr.console.agreements.AgreementVersionLogic;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementReference;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AppAgreementPromptProvider implements PromptProvider {

    private final Storage storage;

    private final ConsentStorage consent;

    public AppAgreementPromptProvider(Storage storage, ConsentStorage consent) {
        this.storage = storage;
        this.consent = consent;
    }

    @Override
    public Double getRank() {
        return 9.0D;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {
        List<AbstractPrompt> result = new ArrayList<>();

        // ensure we have a locale
        request.locale = StringUtils.isNotBlank(request.locale) ? request.locale : "en-us";

        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty() || StringUtils.isBlank(project.get().getOrgId())) return result;
        if (project.get().getApp() == null) return result;
        if (StringUtils.isBlank(project.get().getApp().getId())) return result;

        // for the given app find any agreements linked to it.
        List<String> agreements = project.get().getApp().getAgreements();
        if (agreements == null || agreements.isEmpty()) return result;

        List<Agreement> matches = agreements.stream().map(id -> {
            return this.storage.agreements().getObject(id);
        }).toList();

        List<Agreement> published_agreements = matches.stream().filter(x -> x.getStatus() == Agreement.Status.PUBLISHED).toList();

        // subject id and agreement id
        DocumentResult<UserAgreementConsent> agreementsUserHasMade = this.consent.user_agreement_consent()
                .insecureQuery(Db.query("subject_id").eq(request.subject_id), Pagination.all());

        published_agreements.forEach(agreement -> {

            // did the user sign the last major edition?
            Optional<AgreementVersion> minimumVersion = AgreementVersionLogic.getMinimumVersionRequired(agreement);
            // no minimum version exists
            if (minimumVersion.isEmpty()) {
                return;
            }

            SemanticVersion minimumSemanticVersion = new SemanticVersion(minimumVersion.get().getVersionNumber());

            Optional<UserAgreementConsent> has_required_agreement = agreementsUserHasMade.docs.stream().filter(userRecord -> {
                return userRecord.getAgreementId().equalsIgnoreCase(agreement.getId()) &&
                        userRecord.getVersionNumber() != null &&
                        new SemanticVersion(userRecord.getVersionNumber()).isGreaterThanOrEqualTo(minimumSemanticVersion);
            }).findFirst();

            if (has_required_agreement.isPresent()) return;

            AgreementConsentPrompt promo = new AgreementConsentPrompt();
            promo.agreement = U.fromJson(U.toJson(agreement), AgreementReference.class);

            // TODO get the major version/latest version?
            AgreementVersion version = agreement.getVersions().stream().reduce((x, y) -> y).get();
            promo.agreement.withVersion(version);

            if (version.getScopes() != null) {
                promo.scopes = promo.scopes != null ? promo.scopes : Sets.newHashSet();
                promo.scopes.addAll(version.getScopes().stream().map(ConsentScope::getId).toList());
            }
            promo.opted_in = false;
            promo.skip = false;
            result.add(promo);
        });

        return result;
    }
}
