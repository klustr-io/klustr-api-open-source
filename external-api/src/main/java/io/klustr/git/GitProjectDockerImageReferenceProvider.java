package io.klustr.git;

import io.klustr.schemas.integrations.git.GitCreateProjectRequest;
import io.klustr.schemas.integrations.git.GitProjectDockerImageReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.git.GitUpdateProjectRequest;

import java.util.List;
import java.util.Optional;

public interface GitProjectDockerImageReferenceProvider {

    GitProjectDockerImageReference getLastestImage(GitProjectReference ref);

    List<GitProjectDockerImageReference> getImages(GitProjectReference ref);

    void deleteRepository(GitProjectReference ref);
}
