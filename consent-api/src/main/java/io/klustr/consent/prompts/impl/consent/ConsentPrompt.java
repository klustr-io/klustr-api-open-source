package io.klustr.consent.prompts.impl.consent;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public abstract class ConsentPrompt extends AbstractPrompt {

    @JsonPropertyDescription("Scopes that will be applied if the user agrees to the consent.")
    @Schema(description = "Scopes that will be applied if the user agrees to the consent.")
    public Set<String> scopes;

    public abstract void addExistingScopes(ConsentPromptResponse res);
}
