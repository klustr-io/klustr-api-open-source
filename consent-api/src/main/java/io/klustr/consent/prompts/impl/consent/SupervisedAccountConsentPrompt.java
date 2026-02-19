package io.klustr.consent.prompts.impl.consent;

import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.integrations.ory.Identity;

public class SupervisedAccountConsentPrompt extends ConsentPrompt {

    public static final String BASE_PATH = "/supervised";

    public SupervisedAccountConsentPrompt() {

    }

    /**
     * If the child is requesting to the parent access or not to skip.
     */
    public boolean request;

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
