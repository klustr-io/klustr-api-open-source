package io.klustr.consent.prompts.impl.onboarding;

/**
 * A prompt required for a user to complete onboarding.
 *
 * Name, Age, and Gender
 * Language, Location
 * Nationality
 *
 * Email verification
 * Phone Verification
 * EKYC verification
 */

public class OnboardingEmailPrompt extends OnboardingPrompt {

    public String email;

    public OnboardingEmailPrompt() {}

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}
