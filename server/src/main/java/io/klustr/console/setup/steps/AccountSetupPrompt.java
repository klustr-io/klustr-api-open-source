package io.klustr.console.setup.steps;

import io.klustr.schemas.console.Address;

/**
 * Prompts the user to provide their account setup details.
 */
public class AccountSetupPrompt extends SetupPrompt {

    @Override
    public boolean canSkip() {
        return true;
    }

    public String legal_name;

    public String email;

    public String phone;

    public Address address;

}
