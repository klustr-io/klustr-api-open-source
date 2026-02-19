package io.klustr.consent.prompts;

import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;

import java.util.List;

public interface PromptProvider {

    List<AbstractPrompt> create(ConsentPromptRequest request);

    Double getRank();
}
