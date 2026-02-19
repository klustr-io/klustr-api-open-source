package io.klustr.consent.prompts.impl.consent;

import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.schemas.console.identity.IdentityTraits;

/**
 * When a person is delegating the consent prompt we just simply
 * let the user know that they need to delegate.
 */
public class DelegatedConsentPrompt extends ConsentPrompt {

    public static final String BASE_PATH = "/delegate";

    public DelegatedConsentPrompt() {

    }

    /**
     * The main contact of the supervisor for this account.
     */
    public IdentityTraits supervisor;

    @Override
    public String getPath() {
        return BASE_PATH;
    }

    @Override
    public Boolean requires_age_of_consent() {
        return false;
    }

    @Override
    public void addExistingScopes(ConsentPromptResponse res) {
        // no scopes
    }
}
