package io.klustr.console.hosting.templates;

import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.O11y;
import io.klustr.schemas.console.projects.O11yEndpoint;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Will enable decorating our project with the o11y as specified by the template. This
 * will enable our crawler to see inside an organization and see if any o11y has been
 * configured and if so enable it for push metrics.
 */
@Component
@Order(3)
public class ProjectDependencyO11yHydration implements ProjectDependencyHydration {

    @Override
    public void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env) {
        Project project = storage.projects().getObject(projectId);
        if (template.getO11y() == null) {
            return;
        }

        if (template.getO11y().getEndpoints() == null) {
            return;
        }
        if (template.getO11y().getEndpoints().isEmpty()) {
            return;
        }
        // convert all to standard names
        List<O11yEndpoint> o11yEndpointsWithRealNames = template.getO11y().getEndpoints().stream().map(x -> {
            return x.withUrl(x.getUrl().replace("{projectId}", projectId)
                    .replace("{orgId}", org_id));
        }).toList();

        O11y existing = project.getO11y();
        if (existing == null || existing.getEndpoints() == null || existing.getEndpoints().isEmpty()) {
            project.setO11y(new O11y()
                    .withEndpoints(o11yEndpointsWithRealNames)
            );
            storage.projects().updateObject(projectId, project);
            return;
        }

        // need to add them if they dont exist
        List<O11yEndpoint> newItems = o11yEndpointsWithRealNames.stream().filter(x -> {
            return existing.getEndpoints().stream().noneMatch(y -> {
                return x.getUrl().equalsIgnoreCase(y.getUrl());
            });
        }).toList();

        if (newItems.isEmpty()) {
            return;
        }

        existing.getEndpoints().addAll(newItems);
        storage.projects().updateObject(projectId, project);
    }
}
