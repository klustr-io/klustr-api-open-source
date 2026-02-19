package io.klustr.integrations.gitlab;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import io.klustr.git.GitGroupResourceProvider;
import io.klustr.git.GitProjectDockerImageReferenceProvider;
import io.klustr.schemas.integrations.git.GitCreateGroupRequest;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.git.GitProjectDockerImageReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.gitlab.GitLabCreateGroupRequest;
import io.klustr.schemas.integrations.gitlab.GitLabProjectRepositoryReference;
import io.klustr.storage.Pagination;
import io.klustr.utils.Json;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GitLabProjectDockerImageReferenceProvider implements GitProjectDockerImageReferenceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabProjectDockerImageReferenceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();
    private final GitLabConfigurationProvider config;

    public GitLabProjectDockerImageReferenceProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }


    @Override
    public GitProjectDockerImageReference getLastestImage(GitProjectReference ref) {
        // https://gitlab.dev.klustr.io/api/v4/projects/25
        //  .container_registry_enabled
        //  .container_registry_image_prefix


        // -->
        // https://gitlab.dev.klustr.io/api/v4/projects/25/registry/repositories
        //   [].id
        List<GitLabProjectRepositoryReference> repos = listRepos(ref);
        if (repos.isEmpty()) {
            return null;
        }

        // TODO can i just pluck first? I guess?
        // https://gitlab.dev.klustr.io/api/v4/projects/{project}/registry/repositories/{id}/tags
        //   [].name,[].path,[].location
        List<GitProjectDockerImageReference> images = listDockerImages(ref, repos.get(0));

        if (images.isEmpty()) {
            return  null;
        }

        Optional<GitProjectDockerImageReference> latest = images.stream().filter(x -> {
            return x.getName().equalsIgnoreCase("latest");
        }).findFirst();
        if (latest.isEmpty()) return null;

        // https://gitlab.dev.klustr.io/api/v4/projects/18/registry/repositories/13/tags/latest
        // https://gitlab.dev.klustr.io/api/v4/projects/18/registry/repositories/13/tags/{name}     like .. 3a1b629f1075b09ce3981420df038f9eced920b5
        //  .location, .path, .name, .total_size, .created_at, .revision
        return getImageDetails(ref, repos.get(0), latest.get());
    }

    private GitProjectDockerImageReference getImageDetails(GitProjectReference projectRef, GitLabProjectRepositoryReference repoRef, GitProjectDockerImageReference imageRef) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + projectRef.getId() + "/registry/repositories/" + repoRef.getId() + "/tags/" + imageRef.getName())
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, GitProjectDockerImageReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<GitProjectDockerImageReference> listDockerImages(GitProjectReference projectRef, GitLabProjectRepositoryReference repoRef) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + projectRef.getId() + "/registry/repositories/" + repoRef.getId() + "/tags")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, GitProjectDockerImageReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<GitLabProjectRepositoryReference> listRepos(GitProjectReference ref) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + ref.getId() + "/registry/repositories")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, GitLabProjectRepositoryReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GitProjectDockerImageReference> getImages(GitProjectReference ref) {
        List<GitLabProjectRepositoryReference> repos = listRepos(ref);
        if (repos.isEmpty()) {
            return Lists.newArrayList();
        }

        return listDockerImages(ref, repos.get(0));
    }

    public void deleteRepository(GitProjectReference ref) {

        this.listRepos(ref).forEach(repo -> {
            Request req = new Request.Builder()
                    .url(config.getConfiguration().api_url + "/projects/" + ref.getId() + "/registry/repositories/" + repo.getId())
                    .header("Authorization", "Bearer " + config.getConfiguration().token)
                    .delete()
                    .build();

            try (Response res = http.newCall(req).execute()) {
                if (!res.isSuccessful()) {
                    throw new RuntimeException(res.body().string());
                }
                String json = res.body().string();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
