package io.klustr.integrations.gitlab;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.git.GitUserResourceProvider;
import io.klustr.schemas.integrations.git.GitCreateUserRequest;
import io.klustr.schemas.integrations.git.GitUserReference;
import io.klustr.storage.Pagination;
import io.klustr.utils.ImageUtils;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Component
public class GitLabUserResourceProvider implements GitUserResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(GitLabUserResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();

    private final GitLabConfigurationProvider config;

    public GitLabUserResourceProvider(GitLabConfigurationProvider config) {
        this.config = config;
    }

    @Override
    public void linkOpenIdProvider(String email, String extern_uid) {
        Optional<GitUserReference> match = this.getByEmail(email);
        if (match.isEmpty()) {
            throw new RuntimeException("Could not find user by specified email");
        }
        Integer id = match.get().getId();

        FormBody.Builder form = new FormBody.Builder();
        form.add("extern_uid", extern_uid);
        form.add("provider", config.getConfiguration().openid_connect);

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/users/" + id)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .put(form.build())
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
    public Optional<GitUserReference> getByOpenIdProvider(String extern_uid) {
        // TODO there is a better way to search this...
        return this.listUsers(Pagination.all()).stream().filter(x -> {
            return x.getIdentities() != null
                    && x.getIdentities().stream().anyMatch(id -> id.getExternUid().equalsIgnoreCase(extern_uid));
        }).findFirst();
    }

    @Override
    public Optional<GitUserReference> getByEmail(String email) {
        return this.listUsers(Pagination.all()).stream().filter(x -> {
            return x.getEmail().equalsIgnoreCase(email);
        }).findFirst();
    }

    @Override
    public GitUserReference getByUserId(String userId) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/users/" + userId)
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            GitUserReference ref = Json.parse(json, GitUserReference.class);
            return ref;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deactivateUser(String id) {
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/users/" + id)
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

    public static URLStreamHandler getURLStreamHandler(String protocolIdentifier) {
        try {
            URL url = new URL(protocolIdentifier);
            Field handlerField = URL.class.getDeclaredField("handler");
            handlerField.setAccessible(true);
            return (URLStreamHandler)handlerField.get(url);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GitUserReference createUser(GitCreateUserRequest request) {

        ObjectNode node = (ObjectNode) Json.toJsonNode(Json.toJson(request));
        node = node.put("force_random_password", true);
        node = node.put("can_create_group", false); // we disable these operations on gitlab
        node = node.put("can_create_project", false);  // we disable operations on gitlab
        node = node.put("is_admin", false);  // we ensure no admin
        node = node.put("bot", false);  // not an bot
        node = node.put("locked", false);  // not an bot
        node = node.put("projects_limit", 0);   // can not create projects

        // we need to send this over as a filecontent type
        String userAvatarUrl = request.getAvatar();
        if (StringUtils.isNotBlank(userAvatarUrl)) {
            node.remove("avatar");
        }

        RequestBody body = RequestBody.create(Json.toJson(node), mediaType);

        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/users")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .post(body)
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();

            GitUserReference userRef = Json.parse(json, GitUserReference.class);
            // add the avatar
            if (userAvatarUrl != null && userAvatarUrl.startsWith("http")) {
                updateAvatar(userRef.getId().toString(), userAvatarUrl);
            }

            return userRef;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateAvatar(String userId, byte[] imageBytes) {
        GitUserReference user = this.getByUserId(userId);
        if (user == null) {
            throw new RuntimeException("Could not find user by id.");
        }

        try {
            String mimeType = ImageUtils.getMimeType(imageBytes);
            if (StringUtils.isBlank(mimeType)) {
                throw new RuntimeException("Can not determine extension from mime type from bytes");
            }
            String ext = switch (mimeType) {
                case "image/jpeg" -> "jpg";
                case "image/png" -> "png";
                case "image/gif" -> "gif";
                case "image/webp" -> "webp";
                default -> "unknown";
            };
            if (ext.equalsIgnoreCase("unknown")) {
                throw new RuntimeException("Can not determine extension from mimeType: " + mimeType);
            }

            // always converts to png for broad support
            ImageUtils.Png png = ImageUtils.standardize(imageBytes, 256);

            RequestBody avatarPart = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("avatar", "@/folder/avatar.png",
                            RequestBody.create(png.image(), MediaType.parse(ImageUtils.Png.mimeType)))
                    .build();

            Request request = new Request.Builder()
                    .url(config.getConfiguration().api_url + "/users/" + user.getId())
                    .header("Authorization", "Bearer " + config.getConfiguration().token)
                    .put(avatarPart)
                    .build();

            try (Response res = http.newCall(request).execute()) {
                if (!res.isSuccessful()) {
                    throw new RuntimeException(res.body().string());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateAvatar(String userId, String url) {
        byte[] imageBytes;
        try (InputStream in = new URL(url).openStream()) {
            imageBytes = in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.updateAvatar(userId, imageBytes);
    }

    @Override
    public List<GitUserReference> listUsers(Pagination pagination) {
        // TODO https://docs.gitlab.com/api/rest/#keyset-based-pagination for pagination support
        Request req = new Request.Builder()
                .url(config.getConfiguration().api_url + "/users")
                .header("Authorization", "Bearer " + config.getConfiguration().token)
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, GitUserReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
