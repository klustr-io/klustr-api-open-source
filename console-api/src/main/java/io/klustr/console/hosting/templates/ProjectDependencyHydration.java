package io.klustr.console.hosting.templates;

import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.ProjectTemplate;

public interface ProjectDependencyHydration {
    void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env);
}
