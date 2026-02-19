package io.klustr.integrations.gitlab;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.git.GitProjectPipelineResourceProvider;
import io.klustr.schemas.integrations.git.*;
import io.klustr.utils.Json;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GitLabProjectPipelineResourceProvider implements GitProjectPipelineResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabProjectPipelineResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();
    private final GitLabConfigurationProvider config;

    public GitLabProjectPipelineResourceProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }

    @Override
    public Optional<GitPipelineReference> getLatestPipeline(GitProjectReference project) {
        String ur = config.getConfiguration().api_url + "/projects/" + project.getId() + "/pipelines/latest";
        Request req = new Request.Builder()
                .url(ur)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() == 404) {
                    return Optional.empty();
                }
                if (res.code() == 403) {
                    // this fails when pipeline hasn't activated yet
                    return Optional.empty();
                }
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Optional.of(Json.parse(json, GitPipelineReference.class));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GitPipelineReference> getPipelines(GitProjectReference project) {
        String ur = config.getConfiguration().api_url + "/projects/" + project.getId() + "/pipelines";
        Request req = new Request.Builder()
                .url(ur)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() == 404) {
                    return Lists.newArrayList();
                }
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, GitPipelineReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GitPipelineStageReference> getPipelineDetails(GitPipelineReference pipeline) {

        // --> /projects/:id/jobs/:job_id
        String ur = config.getConfiguration().api_url + "/projects/" + pipeline.getProjectId() + "/pipelines/" + pipeline.getId() + "/jobs";
        Request req = new Request.Builder()
                .url(ur)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() == 404) {
                    return Lists.newArrayList();
                }
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            List<GitPipelineJobReference> jobs = Json.parseArray(json, GitPipelineJobReference.class);

            LinkedHashMap<String, List<GitPipelineJobReference>> buckets = Maps.newLinkedHashMap();
            jobs.forEach((job) -> {
                buckets.computeIfAbsent(job.getStage(), k -> new ArrayList<>());
                buckets.get(job.getStage()).add(job);
            });

            List<GitPipelineStageReference> stages = new ArrayList<>();
            buckets.keySet().forEach(key -> {
                stages.add(new GitPipelineStageReference()
                        .withStage(key)
                        .withJobs(buckets.get(key))
                );
            });

            return stages;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getPipelineRawStepLogFile(GitProjectReference project, GitPipelineJobReference step) {

        String ur = config.getConfiguration().api_url + "/projects/" + project.getId() + "/jobs/" + step.getId() + "/trace";
        Request req = new Request.Builder()
                .url(ur)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() == 404) {
                    return null;
                }
                throw new RuntimeException(res.body().string());
            }
            return res.body().string();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
