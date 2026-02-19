package io.klustr.consent.prompts.impl.consent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.Lists;
import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public class AppConsentPrompt extends ConsentPrompt {

    public static final String BASE_PATH = "/apps";

    @JsonIgnore
    public List<ConsentAttribute> requested_scopes = Lists.newArrayList();

    @JsonIgnore
    public List<ConsentAttribute> existing_scopes = Lists.newArrayList();

    @JsonPropertyDescription("Scopes that the user needs to accept, if empty no scopes are required.")
    @Schema(description = "Scopes that the user needs to accept, if empty no scopes are required.")
    public List<ConsentAttribute> scopes = Lists.newArrayList();

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
        // TODO move this logic, if the scopes are not really sensitive
        return true;
    }
}
