package io.klustr.consent.prompts.impl.consent.providers;

import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.services.AgreementLogic;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrgAgreementPromptProvider implements PromptProvider {

    private final Storage storage;

    private final ConsentStorage consent;

    public OrgAgreementPromptProvider(Storage storage, ConsentStorage consent) {
        this.storage = storage;
        this.consent = consent;
    }

    @Override
    public Double getRank() {
        return 5.0;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {
        List<AbstractPrompt> result = new ArrayList<>();

        // find the client who is going through the OIDC flow
        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);

        // find the project related to this client (group)
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty() || StringUtils.isBlank(project.get().getOrgId())) return result;

        // find the agreements that are organization level
        fq = Db.and(
                Db.query("namespace").eq(Agreement.AgreementScope.ORG.value()),
                Db.query("org_id").eq(project.get().getOrgId())
        );

        DocumentResult<Agreement> page = this.storage.agreements().insecureQuery(fq, Pagination.all());
        if (page.docs.isEmpty()) {
            return result;
        }

        // find all published agreements for this organization
        List<Agreement> published_agreements = page.docs.stream()
                .filter(x -> x.getStatus() != null &&
                        x.getStatus() == Agreement.Status.PUBLISHED).toList();

        // find all the existing agreements signed by the current user
        DocumentResult<UserAgreementConsent> userExistingConsents = this.consent.user_agreement_consent()
                .insecureQuery(Db.query("subject_id").eq(request.subject_id), Pagination.all());


        return AgreementLogic.populateAgreementsToSign(published_agreements, userExistingConsents);
    }

}
