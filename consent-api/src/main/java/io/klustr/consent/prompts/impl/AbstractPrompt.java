package io.klustr.consent.prompts.impl;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.consent.prompts.impl.consent.*;
import io.klustr.consent.prompts.impl.generic.RedirectPrompt;
import io.klustr.consent.prompts.impl.onboarding.*;
import io.swagger.v3.oas.annotations.media.Schema;

@com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME,
        include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY,
        defaultImpl = AppConsentPrompt.class,
        visible = true,
        property = "type")
@com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = AppConsentPrompt.class, name = "app"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = ExperimentConsentPrompt.class, name = "experiment"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = AgreementConsentPrompt.class, name = "agreement"),
        // supervised account prompt
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = SupervisedAccountConsentPrompt.class, name = "supervised"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = DelegatedConsentPrompt.class, name = "delegated"),

        // redirect link
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = RedirectPrompt.class, name = "redirect"),
        // onboarding
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingPrompt.class, name = "onboarding"),
        // email
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingEmailPrompt.class, name = "email")
})
public abstract class AbstractPrompt {

    @JsonPropertyDescription("If this prompt can be skipped and automatically applied.")
    @Schema(description = "If this prompt can be skipped and automatically applied.")
    public Boolean skip = false;

    /**
     * Returns the path related to where to submit the answer to this prompt.
     * @return The path to return for the prompt.
     */
    @JsonPropertyDescription("The url base path to use to send relative to the service")
    @Schema(description = "The url base path to use to send relative to the service.")
    public abstract String getPath();

    @JsonPropertyDescription("Indicates if this prompt requires legal consent and age of consent.")
    @Schema(description = "If the consent requires age.")
    public abstract Boolean requires_age_of_consent();
}
