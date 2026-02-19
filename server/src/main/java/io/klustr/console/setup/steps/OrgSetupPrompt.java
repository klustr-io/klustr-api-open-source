package io.klustr.console.setup.steps;

public class OrgSetupPrompt extends SetupPrompt {

    public String name;

    public String email;

    public String domain;

    @Override
    public boolean canSkip() {
        return false;
    }
}
