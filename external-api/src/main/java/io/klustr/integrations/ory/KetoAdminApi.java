package io.klustr.integrations.ory;

import io.klustr.schemas.integrations.ory.Relationship;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "keto.write.url")
public class KetoAdminApi {

    private static final Logger log = LoggerFactory.getLogger(KetoAdminApi.class);

    private final OkHttpClient http = new OkHttpClient();
    private final String writeUrl;

    private final MediaType jsonMediaType = MediaType.parse("application/json");

    public KetoAdminApi(@Value("${keto.write.url}") String writeUrl) {
        this.writeUrl = writeUrl;
    }

    public void health() {
        // https://keto-admin.dev.klustr.io/health/ready
        // https://keto.dev.klustr.io/health/ready

        Request request = new Request.Builder()
                .url(writeUrl + "/health/ready")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Keto Admin URL not available. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addRelation(Relationship relation) {
        String json = Json.toJson(relation);
        Request request = new Request.Builder()
                .url(writeUrl + "/admin/relation-tuples")
                .put(RequestBody.create(json, jsonMediaType))
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.debug(res.body().string());
            } else {
                log.error(res.body().string());
                throw new RuntimeException("relation failed: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeObject(String namespace, String object) {
        HttpUrl url = HttpUrl.parse(writeUrl + "/admin/relation-tuples").newBuilder()
                .addQueryParameter("object", object)
                .addQueryParameter("namespace", namespace)
                .build();

        Request request = new Request.Builder()
                .url(url.toString())
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.debug(res.body().string());
            } else {
                log.error(res.body().string());
                throw new RuntimeException("relation failed: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeSubject(String subject_id, String namespace) {

        HttpUrl url = HttpUrl.parse(writeUrl + "/admin/relation-tuples").newBuilder()
                .addQueryParameter("subject_id", subject_id)
                .addQueryParameter("namespace", namespace)
                .build();

        Request request = new Request.Builder()
                .url(url.toString())
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.debug(res.body().string());
            } else {
                log.error(res.body().string());
                throw new RuntimeException("relation remove failed: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeRelation(Relationship relation) {
        HttpUrl.Builder builder = HttpUrl.parse(writeUrl + "/admin/relation-tuples").newBuilder();

        if (StringUtils.isNotBlank(relation.getNamespace())) {
            builder.addQueryParameter("namespace", relation.getNamespace());
        }
        if (StringUtils.isNotBlank(relation.getSubjectId())) {
            builder.addQueryParameter("subject_id", relation.getSubjectId());
        }
        if (StringUtils.isNotBlank(relation.getObject())) {
            builder.addQueryParameter("object", relation.getObject());
        }
        if (StringUtils.isNotBlank(relation.getRelation())) {
            builder.addQueryParameter("relation", relation.getRelation());
        }

        HttpUrl url = builder.build();

        Request request = new Request.Builder()
                .url(url.toString())
                .delete()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                log.debug(res.body().string());
            } else {
                log.error(res.body().string());
                throw new RuntimeException("relation remove failed: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
