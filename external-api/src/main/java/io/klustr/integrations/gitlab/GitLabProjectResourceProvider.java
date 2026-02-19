package io.klustr.integrations.gitlab;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.schemas.integrations.git.GitCreateProjectRequest;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.git.GitUpdateProjectRequest;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GitLabProjectResourceProvider implements GitProjectResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabProjectResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();
    private final GitLabConfigurationProvider config;

    public GitLabProjectResourceProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }

    @Override
    public Health health() {
        try {
            List<GitProjectReference> projects = this.listProjects();
            return Health.up().withDetail("url", config.getConfiguration().api_url).build();
        } catch (Exception ex) {
            return Health.down(ex).withDetail("url", config.getConfiguration().api_url).build();
        }
    }

    @Override
    public GitProjectReference createProject(GitCreateProjectRequest request) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .post(RequestBody.create(U.toJson(request), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, GitProjectReference.class);
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateProject(String id, GitUpdateProjectRequest updateRequest) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + id)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .put(RequestBody.create(U.toJson(updateRequest), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteProject(GitProjectReference ref) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + ref.getId())
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .delete()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GitProjectReference> listProjects() {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Json.parseArray(res.body().string(), GitProjectReference.class);
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<GitProjectReference> listProjectsInGroup(String groupPath) {
        return this.listProjects().stream().filter(x -> {
            return x.getNamespace().getPath().equalsIgnoreCase(groupPath);
        }).toList();
    }

    @Override
    public Optional<GitProjectReference> tryGetProject(String projectPath) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects?search=" + projectPath + "&simple=true")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() == 404) {
                    return Optional.empty();
                }
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            List<GitProjectReference> list = Json.parseArray(json, GitProjectReference.class);
            Optional<GitProjectReference> ref = list.stream().filter(x -> x.getPath().equalsIgnoreCase(projectPath)).findFirst();
            if (ref.isEmpty()) return ref;
            return Optional.of(getProjectById(ref.get().getId().toString()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public GitProjectReference getProjectById(String id) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + id + "?statistics=true")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            GitProjectReference data = Json.parse(json, GitProjectReference.class);

            // set languages (work around as stats should really include this)
            data.getStatistics().setLanguages(getProjectLanguages(data.getId()));

            return data;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Map<String, Double> getProjectLanguages(Integer projectId) {

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/projects/" + projectId + "/languages")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, new TypeReference<Map<String, Double>>() {});
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
