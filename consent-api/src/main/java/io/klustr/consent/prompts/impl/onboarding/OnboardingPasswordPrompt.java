package io.klustr.consent.prompts.impl.onboarding;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.klustr.consent.prompts.impl.AbstractPrompt;

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

public class OnboardingPasswordPrompt extends OnboardingPrompt {

    public String password;

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}
