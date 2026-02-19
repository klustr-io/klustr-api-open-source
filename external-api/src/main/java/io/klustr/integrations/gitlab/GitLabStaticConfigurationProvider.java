package io.klustr.integrations.gitlab;

import org.springframework.stereotype.Component;

@Component
public class GitLabStaticConfigurationProvider implements GitLabConfigurationProvider {

    private final GitLabConfiguration config;

    public GitLabStaticConfigurationProvider(GitLabConfiguration config) {
        this.config = config;
    }

    @Override
    public GitLabConfiguration getConfiguration() {
        return config;
    }
}
