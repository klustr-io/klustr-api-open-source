package io.klustr.consent.prompts.impl.consent.providers;

import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.services.AgreementLogic;
import io.klustr.console.storage.Storage;
import io.klustr.exceptions.StandardResponseException;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.users.UserAccountType;
import io.klustr.schemas.console.users.UserAccountTypeAgreement;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Will deliver agreements based on the type of the user account provided.
 */
@Component
public class AgreementPromptProvider implements PromptProvider {
    private final Storage storage;
    private final ConsentStorage consent;

    public AgreementPromptProvider(Storage storage, ConsentStorage consent) {
        this.storage = storage;
        this.consent = consent;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {
        List<AbstractPrompt> result = new ArrayList<>();

        if (request.agreements == null || request.agreements.isEmpty()) {
            return result;
        }
        // grab all the agreements linked to this account/org

        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty() || StringUtils.isBlank(project.get().getOrgId())) return result;

        Org org = this.storage.organizations().getObject(project.get().getOrgId());

        List<Optional<Agreement>> matches = request.agreements.stream().map(a -> {
            return this.storage.agreements().findFirst(Db.and(
                    Db.query("org_id").eq(org.getId()),
                    Db.query("id").eq(a)
                    ));
        }).toList();

        Optional<Optional<Agreement>> anyMissing = matches.stream().filter(x -> x.isEmpty()).findFirst();
        if (anyMissing.isPresent()) {
            throw new StandardResponseException(HttpStatus.BAD_REQUEST,
                    "Agreement not found within organization or not found at all").withErrorId("agreement_not_found");
        }

        // find all active and published agreements
        List<Agreement> published_agreements = matches.stream().map(i -> {
            return i.get();
        }).filter(x -> {
            return x.getStatus() == Agreement.Status.PUBLISHED;
        }).toList();

        // find all the existing agreements signed by the current user
        DocumentResult<UserAgreementConsent> userExistingConsents = this.consent.user_agreement_consent()
                .insecureQuery(Db.query("subject_id").eq(request.subject_id), Pagination.all());

        return AgreementLogic.populateAgreementsToSign(published_agreements, userExistingConsents);
    }

    @Override
    public Double getRank() {
        return 2.0D;
    }
}
