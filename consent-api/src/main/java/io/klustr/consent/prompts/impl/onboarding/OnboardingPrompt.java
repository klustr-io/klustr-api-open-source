package io.klustr.consent.prompts.impl.onboarding;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.swagger.v3.oas.annotations.media.Schema;

@com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME,
        include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY,
        visible = true,
        property = "type")
@com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingBasicInfoPrompt.class, name = "onboarding/info"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingUserIdPrompt.class, name = "onboarding/userid"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingPasswordPrompt.class, name = "onboarding/password"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = OnboardingEmailPrompt.class, name = "onboarding/email")
})
public abstract class OnboardingPrompt extends AbstractPrompt {

    public static final String BASE_PATH ="/onboarding";

    @JsonPropertyDescription("If this onboarding has been completed.")
    @Schema(description = "If this onboarding has been completed.")
    public Boolean done = false;

    @Override
    public String getPath() {
        return BASE_PATH;
    }
}
