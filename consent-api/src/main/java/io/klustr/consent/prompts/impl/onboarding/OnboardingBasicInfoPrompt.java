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

public class OnboardingBasicInfoPrompt extends OnboardingPrompt {

    public String given_name;

    public String family_name;

    public Integer year;
    public Integer month;
    public Integer day;

    public String gender;

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}

