package io.klustr.console.hosting.templates;

import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.ProjectTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectDependencyHydrationRunner {
    private final List<ProjectDependencyHydration> hydrators;

    // Spring automatically injects in @Order-defined order
    public ProjectDependencyHydrationRunner(List<ProjectDependencyHydration> hydrators) {
        this.hydrators = hydrators;
    }

    public EnvironmentVars runHydration(
            Storage storage,
            String orgId,
            String projectId,
            ProjectTemplate template
    ) {
        EnvironmentVars v = new EnvironmentVars();
        hydrators.forEach(h ->
                h.hydrate(storage, orgId, projectId, template, v)
        );
        return v;
    }
}
