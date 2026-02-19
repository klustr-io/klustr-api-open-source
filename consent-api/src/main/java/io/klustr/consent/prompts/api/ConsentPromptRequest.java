package io.klustr.consent.prompts.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ConsentPromptRequest {

    @JsonPropertyDescription("Subject identitifer to get consent prompts for.")
    @Schema(description = "Subject identitifer to get consent prompts for.", requiredMode = Schema.RequiredMode.REQUIRED)
    public String subject_id;

    @JsonPropertyDescription("When consent is being delegated we give information on who is performing the delegating and what client.")
    @Schema(description = "When consent is being delegated we give information on who is performing the delegating and what client.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public ConsentDelegateRequest delegate;

    @JsonPropertyDescription("Client to get consent prompts against.")
    @Schema(description = "Client to get consent prompts against.", requiredMode = Schema.RequiredMode.REQUIRED)
    public String client_id;

    @JsonPropertyDescription("Optional experiment ID to ask for consent against.")
    @Schema(description = "Optional experiment ID to ask for consent against.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String experiment_id;

    @JsonPropertyDescription("Optional the type of agreements you want to prompt against.")
    @Schema(description = "Optional the type of agreements you want to prompt against.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public List<String> agreements = Lists.newArrayList();

    @JsonPropertyDescription("Consent scopes being requested by the application or service.")
    @Schema(description = "Consent scopes being requested by the application or service.", requiredMode = Schema.RequiredMode.REQUIRED)
    public List<String> scopes = Lists.newArrayList();

    @JsonPropertyDescription("The locale of the current user for internationalization.")
    @Schema(description = "The locale of the current user for internationalization.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String locale;
}
