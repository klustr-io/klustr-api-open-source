package io.klustr.consent.prompts.impl.generic;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Required prompt for when users MUST be forced to setup and complete their account.
 */

public class RedirectPrompt extends AbstractPrompt {
    @JsonPropertyDescription("Where to redirect the user in order to complete their account.")
    @Schema(description = "Where to redirect the user in order to complete their account.")
    public String redirect_to;

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public Boolean requires_age_of_consent() {
        return false;
    }
}
