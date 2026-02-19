package io.klustr.console.hosting.templates;

import com.google.common.collect.Lists;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLogo;
import io.klustr.schemas.console.apps.*;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.console.projects.TemplateAppConfiguration;
import org.joda.time.DateTime;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Order(1)
public class ProjectDependencyAppHydration implements ProjectDependencyHydration {

    @Override
    public void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env) {
        // check if app exists, if not add one. be mindful that
        // redirect URLS will likely need injection from template
        if (template.getRequiredFeatures() == null) return;
        TemplateAppConfiguration app = template.getRequiredFeatures().getApp();
        if (app == null) return;

        // project already has app defined
        Project project = storage.projects().getObject(projectId);
        if (project.getApp() != null && project.getApp().getId() != null) return;

        project.withApp(new App()
                .withBrand(new AppBrand()
                        .withLogo(new AppLogo()
                                .withUrl(URI.create(app.getLogoUrl()))
                                .withFilename("demo.png")   // TODO not needed
                        )
                        .withName(app.getName())
                )
                .withConsent(new ConsentGroup()
                        .withScopes(app.getScopes() != null ? app.getScopes().stream().map(x -> {
                            return new ConsentScope().withId(x);
                        }).toList() : Lists.newArrayList())
                )
                .withId(projectId)
                .withCreationDate(DateTime.now())
                .withDomains(app.getDomains().stream().map(x -> {
                    return x.replace("{projectId}", projectId);
                }).toList())
                .withContact(new AppContactInformation()
                        .withSupportEmail("support@org.com")
                )
                .withAccessMode(new AccessMode()
                        .withMode(AccessMode.Mode.PUBLIC)
                )
        );
        storage.projects().updateObject(projectId, project);
    }
}