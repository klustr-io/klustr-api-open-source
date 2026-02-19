package io.klustr.console.setup.steps;

public class ProjectSetupPrompt extends SetupPrompt {

    public String name;

    public String id;

    public String description;

    @Override
    public boolean canSkip() {
        return true;
    }
}
