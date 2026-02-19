package io.klustr.integrations.gitlab;

import io.klustr.git.AccessLevel;
import io.klustr.git.GitGroupMembershipResourceProvider;
import io.klustr.git.GitGroupResourceProvider;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.schemas.integrations.git.GitUserReference;
import io.klustr.utils.Json;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GitLabGroupMembershipProvider implements GitGroupMembershipResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabGroupMembershipProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();

    private final GitLabConfigurationProvider config;


    public GitLabGroupMembershipProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }

    @Override
    public void addGroupMember(GitGroupReference groupRef, GitUserReference userRef, AccessLevel level) {

        FormBody.Builder form = new FormBody.Builder();
        form.add("user_id", String.valueOf(userRef.getId()));
        form.add("access_level", String.valueOf( level.getLevel()));
        form.add("invite_source", "klustr.io");

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups/" + groupRef.getId() + "/invitations")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .post(form.build())
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }

    public Optional<GroupMember> getGroupMemberByEmail(GitGroupReference group, GitUserReference userRef) {
        Optional<GroupMember> match = this.listGroupMembers(group).stream().filter(x -> {
            return x.getId() == userRef.getId();
        }).findFirst();
        return match;
    }

    @Override
    public List<GroupMember> listGroupMembers(GitGroupReference group) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups/" + group.getId() + "/members")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, GroupMember.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class GroupMember extends GitGroupReference {
        public AccessLevel access_level;
        public String expires_at;
    }

    @Override
    public void removeGroupMember(GitGroupReference groupRef, GitUserReference userRef) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups/" + groupRef.getId() + "/members/" + userRef.getId())
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
    public void setUserAccessLevel(GitGroupReference groupRef, GitUserReference userRef, AccessLevel level) {
        FormBody.Builder form = new FormBody.Builder();
        form.add("user_id", String.valueOf( userRef.getId()));
        form.add("access_level", String.valueOf( level.getLevel()));

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/groups/" + groupRef.getId() + "/members/" + userRef.getId())
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .put(form.build())
                .build();


        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
