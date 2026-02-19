package io.klustr.compute;

import io.klustr.schemas.console.projects.ProjectTemplate;

import java.util.List;
import java.util.Optional;

public interface ProjectTemplateProvider {
    List<ProjectTemplate> getTemplates();
    Optional<ProjectTemplate> getTemplate(String templateId);
}
