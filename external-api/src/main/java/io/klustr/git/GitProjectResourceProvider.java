package io.klustr.git;

import io.klustr.schemas.integrations.git.GitCreateProjectRequest;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.git.GitUpdateProjectRequest;
import org.springframework.boot.actuate.health.Health;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GitProjectResourceProvider {

    Health health();

    GitProjectReference createProject(GitCreateProjectRequest request);

    void deleteProject(GitProjectReference ref);

    GitProjectReference getProjectById(String id);

    void updateProject(String id, GitUpdateProjectRequest updateRequest);

    List<GitProjectReference> listProjects();

    List<GitProjectReference> listProjectsInGroup(String groupPath);

    Optional<GitProjectReference> tryGetProject(String projectPath);
}
