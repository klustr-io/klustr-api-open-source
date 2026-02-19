package io.klustr.consent.prompts.impl.consent.providers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.junk.InMemoryRateUserImpressionTrackingProvider;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.ExperimentConsentPrompt;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.console.storage.Storage;
import io.klustr.exceptions.StandardResponseException;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.schemas.console.experiments.Experiment;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class ExperimentPromptProvider implements PromptProvider {

    private final Storage storage;
    private final ConsentStorage consent_storage;

    private final UserConsentRepository user_consent;

    private final InMemoryRateUserImpressionTrackingProvider impressions = new InMemoryRateUserImpressionTrackingProvider(Duration.ofSeconds(120));

    public ExperimentPromptProvider(Storage storage, ConsentStorage consent_storage, UserConsentRepository user_consent) {
        this.storage = storage;
        this.user_consent = user_consent;
        this.consent_storage = consent_storage;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {
        if (StringUtils.isBlank(request.experiment_id)) {
            return Lists.newArrayList();
        }
        Optional<UserApplicationConsent> existing_user_consent = this.user_consent.getUserConsentByClientId(request.subject_id, request.client_id);
        Experiment experiment = this.storage.experiments().getObject(request.experiment_id);
        if (experiment == null) {
            throw new StandardResponseException(HttpStatus.BAD_REQUEST,
                    "Experiment not found").withErrorId("experiment_not_found");
        }

        ExperimentConsentPrompt prompt = new ExperimentConsentPrompt();
        prompt.experiment = experiment;
        prompt.impressions = this.impressions.incrementAndGet(request.subject_id, "experiments", experiment.getId());
        prompt.requested_scopes = ensureExists(experiment.getScopes().stream().map(x -> {
            return this.consent_storage.consent_scopes().tryGetObject(x.getId());
        }).toList());

        // indicate if experiment is active or not.
        if (experiment.getStopDate() != null) {
            if (experiment.getStopDate().isBefore(DateTime.now())) {
                prompt.inactive = true;
            }
        }

        // check if user already agreed to this experiment
        // and if so the original scopes they requested match
        // the scopes now requested by the experiemnt (experiment can add or remove scopes)
        boolean has_previous_joined = false;
        if (existing_user_consent.isPresent()) {
            Optional<UserExperimentConsent> existingExperiment = this.user_consent.getExperimentConsent(request.subject_id, experiment.getId());
            if (existingExperiment.isPresent() && existingExperiment.get().getStatus() == UserExperimentConsent.Status.ACTIVE) {
                // check if the user consented but experiment changed its scopes
                // and needs additional scopes
                if (existingExperiment.get().getScopes() != null) {
                    List<UserConsentScope> original_agree_consent = existingExperiment.get().getScopes();
                    prompt.existing_scopes = ensureExists(original_agree_consent.stream().map(x -> {
                        return consent_storage.consent_scopes().tryGetObject(x.getId());
                    }).toList());
                }
                // inform that user is already opted in
                prompt.opted_in = true;
                has_previous_joined = true;
            }
        }

        HashSet<String> requested = Sets.newHashSet(prompt.requested_scopes.stream().map(ConsentAttribute::getId).toList());
        HashSet<String> existing = Sets.newHashSet(prompt.existing_scopes.stream().map(ConsentAttribute::getId).toList());
        if (has_previous_joined && existing.containsAll(requested)) {
            prompt.skip = true;
        }

        // only new scopes needed
        prompt.scopes = prompt.requested_scopes.stream().filter(x -> {
            return !existing.contains(x.getId());
        }).toList();

        return Lists.newArrayList(prompt);
    }

    @Override
    public Double getRank() {
        return 100.0;
    }

    private final List<ConsentAttribute> ensureExists(List<Optional<ConsentAttribute>> scopes) {
        return scopes.stream().filter(Optional::isPresent).map(Optional::get).toList();
    }
}
