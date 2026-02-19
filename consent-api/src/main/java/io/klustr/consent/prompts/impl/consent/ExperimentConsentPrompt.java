package io.klustr.consent.prompts.impl.consent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.Lists;
import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.experiments.Experiment;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;


public class ExperimentConsentPrompt extends ConsentPrompt {

    public static final String BASE_PATH = "/experiments";

    @JsonIgnore
    public List<ConsentAttribute> requested_scopes = Lists.newArrayList();

    @JsonIgnore
    public List<ConsentAttribute> existing_scopes = Lists.newArrayList();

    @JsonPropertyDescription("New scopes the user must provide or consent to.")
    @Schema(description = "New scopes the user must provide or consent to.")
    public List<ConsentAttribute> scopes = Lists.newArrayList();

    @JsonPropertyDescription("Details on the experiment consented.")
    @Schema(description = "Details on the experiment consented.")
    public Experiment experiment;

    @JsonPropertyDescription("The number of times this prompt has been seen by a user.")
    @Schema(description = "The number of times this prompt has been seen by a user.")
    public Integer impressions = 0;

    @JsonPropertyDescription("The number of times this prompt can be seen before being removed.")
    @Schema(description = "The number of times this prompt can be seen before being removed.")
    public Integer remaining_impressions = 0;

    @JsonPropertyDescription("The user already opted into the experiment.")
    @Schema(description = "The user already opted into the experiment.")
    public Boolean opted_in = false;

    @JsonPropertyDescription("If this experiment has expired and can not be joined. Used for display purposes only.")
    @Schema(description = "If this experiment has expired and can not be joined. Used for display purposes only.")
    public Boolean inactive = false;

    public void addExistingScopes(ConsentPromptResponse res) {
        if (this.existing_scopes != null && !this.existing_scopes.isEmpty()) {
            res.existing_scopes.addAll(this.existing_scopes.stream().map(ConsentAttribute::getId).toList());
        }
    }

    @Override
    public String getPath() {
        return BASE_PATH;
    }

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}
