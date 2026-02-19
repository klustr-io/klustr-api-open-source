package io.klustr.consent.prompts.impl.consent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.consent.prompts.PromptResponse;

import java.util.ArrayList;
import java.util.List;

public class ConsentPromptResponse<T extends ConsentPrompt> extends PromptResponse<T> {

    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes requested by the application and reviewed by the user.")
    public List<String> scopes = new ArrayList<String>();


    @JsonProperty("skipped")
    @JsonPropertyDescription("If the consent was skipped due to existing business logic.")
    public Boolean skipped;
}
