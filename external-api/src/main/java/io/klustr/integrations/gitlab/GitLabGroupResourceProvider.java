package io.klustr.integrations.gitlab;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.git.GitGroupResourceProvider;
import io.klustr.schemas.integrations.git.GitCreateGroupRequest;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.gitlab.GitLabCreateGroupRequest;
import io.klustr.storage.Pagination;
import io.klustr.utils.Json;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GitLabGroupResourceProvider implements GitGroupResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabGroupResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();
    private final GitLabConfigurationProvider config;

    public GitLabGroupResourceProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }

    @Override
    public Optional<GitGroupReference> getGroupByPath(String path) {
        return this.listGroups(Pagination.all()).stream().filter(x -> {
            return x.getPath().equalsIgnoreCase(path);
        }).findFirst();
    }

    @Override
    public List<GitGroupReference> listGroups(Pagination pagination) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            return Json.parseArray(res.body().string(), GitGroupReference.class);
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    @Override
    public GitGroupReference createGroup(GitCreateGroupRequest request) {
        GitLabCreateGroupRequest gitLabRequest = Json.parse(Json.toJson(request), GitLabCreateGroupRequest.class);
        gitLabRequest.setEmailsEnabled(false);
        gitLabRequest.setAutoDevopsEnabled(true);
        gitLabRequest.setMentionsDisabled(true);
        gitLabRequest.setVisibility(GitLabCreateGroupRequest.Visibility.PRIVATE);// private
        gitLabRequest.setProjectCreationLevel(GitLabCreateGroupRequest.ProjectCreationLevel.ADMINISTRATOR);  // only admins which is US klust
        gitLabRequest.setShareWithGroupLock(true);    // cant share
        ObjectNode node = (ObjectNode) Json.toJsonNode(Json.toJson(gitLabRequest));
        RequestBody body = RequestBody.create(Json.toJson(node), mediaType);

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .post(body)
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, GitGroupReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteGroup(GitGroupReference ref) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups/" + ref.getId())
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
}
