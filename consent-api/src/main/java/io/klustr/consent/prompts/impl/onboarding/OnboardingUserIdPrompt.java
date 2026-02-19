package io.klustr.consent.prompts.impl.onboarding;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.Sets;
import io.klustr.consent.prompts.impl.AbstractPrompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

public class OnboardingUserIdPrompt extends OnboardingPrompt {

    public String user_id;

    public OnboardingUserIdPrompt() {}

    @Override
    public Boolean requires_age_of_consent() {
        return true;
    }
}
