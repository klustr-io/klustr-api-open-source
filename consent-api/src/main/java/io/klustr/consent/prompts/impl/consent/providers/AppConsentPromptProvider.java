package io.klustr.consent.prompts.impl.consent.providers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.AppConsentPrompt;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.console.storage.Storage;
import io.klustr.exceptions.StandardResponseException;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class AppConsentPromptProvider implements PromptProvider {

    private final ConsentStorage consent_storage;

    private final UserConsentRepository user_consent;

    private final Storage storage;

    public AppConsentPromptProvider(
            Storage storage,
            ConsentStorage consent_storage,
            UserConsentRepository user_consent) {
        this.storage = storage;
        this.consent_storage = consent_storage;
        this.user_consent = user_consent;
    }

    @Override
    public Double getRank() {
        return 11.0D;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {

        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        List<ConsentAttribute> availableScopes = this.consent_storage.getConsentScopes(project.get().getOrgId());

        List<ConsentAttribute> scopes = ensureConsentScopeNotNull(request.scopes.stream().map(requestedScope -> {
            return availableScopes.stream().filter(knownScope -> knownScope.getId().equalsIgnoreCase(requestedScope)).findFirst();
        }).toList());

        AppConsentPrompt prompt = new AppConsentPrompt();
        prompt.requested_scopes = scopes;

        // get user prior consent
        Optional<UserApplicationConsent> existing_user_consent = this.user_consent.getUserConsentByClientId(request.subject_id, request.client_id);
        if (existing_user_consent.isPresent()) {
            UserApplicationConsent userConsent = existing_user_consent.get();
            prompt.existing_scopes = ensureConsentScopeNotNull(userConsent.getScopes().stream().filter(Objects::nonNull).map(scope -> {
                return this.consent_storage.consent_scopes().tryGetObject(scope.getId());
            }).toList());
        }

        HashSet<String> requested = Sets.newHashSet(prompt.requested_scopes.stream().map(ConsentAttribute::getId).toList());
        HashSet<String> existing = Sets.newHashSet(prompt.existing_scopes.stream().map(ConsentAttribute::getId).toList());
        if (existing.containsAll(requested)) {
            prompt.skip = true;
        }

        // only new scopes needed
        prompt.scopes = prompt.requested_scopes.stream().filter(x -> {
            return !existing.contains(x.getId());
        }).toList();

        prompt.scopes = prompt.scopes.stream().filter(x -> {
            return !x.getId().equalsIgnoreCase("openid");
        }).toList();


        // skip as we already have everything we need.
        if (prompt.scopes.isEmpty()) {
            return Lists.newArrayList();
        }

        return Lists.newArrayList(prompt);
    }

    private final List<ConsentAttribute> ensureConsentScopeNotNull(List<Optional<ConsentAttribute>> scopes) {
        Optional<Optional<ConsentAttribute>> has_empty_scope = scopes.stream().filter(Optional::isEmpty).findFirst();
        if (has_empty_scope.isPresent()) {
            throw new StandardResponseException(HttpStatus.BAD_REQUEST,
                    "One or more scopes not found. Ensure scope is defined.").withErrorId("scope_not_found");
        }
        return scopes.stream().map(x -> x.get()).toList();
    }
}
