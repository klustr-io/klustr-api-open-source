package io.klustr.consent.prompts.impl.consent;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.agreements.AgreementReference;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;


public class AgreementConsentPrompt extends ConsentPrompt {
    public static final String BASE_PATH = "/agreements";

    @JsonPropertyDescription("Details on the agreement")
    @Schema(description = "Details on the agreement.")
    public AgreementReference agreement;

    @JsonPropertyDescription("The user has opted into this agreement.")
    @Schema(description = "The user has opted into this agreement.")
    public Boolean opted_in = false;

    @JsonPropertyDescription("If the user needs to resign the agreement has it has been changed.")
    @Schema(description = "If the user needs to resign the agreement has it has been changed.")
    public Boolean resign = false;

    @Override
    public String getPath() {
        return BASE_PATH;
    }

    @Override
    public void addExistingScopes(ConsentPromptResponse res) {

    }

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}
