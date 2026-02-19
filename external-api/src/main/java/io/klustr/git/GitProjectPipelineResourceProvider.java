package io.klustr.git;

import io.klustr.schemas.integrations.git.GitPipelineJobReference;
import io.klustr.schemas.integrations.git.GitPipelineReference;
import io.klustr.schemas.integrations.git.GitPipelineStageReference;
import io.klustr.schemas.integrations.git.GitProjectReference;

import java.util.List;
import java.util.Optional;

public interface GitProjectPipelineResourceProvider {

    List<GitPipelineReference> getPipelines(GitProjectReference project);

    Optional<GitPipelineReference> getLatestPipeline(GitProjectReference project);

    List<GitPipelineStageReference> getPipelineDetails(GitPipelineReference pipeline);

    String getPipelineRawStepLogFile(GitProjectReference project, GitPipelineJobReference job);
}
