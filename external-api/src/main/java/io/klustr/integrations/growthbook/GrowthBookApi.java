package io.klustr.integrations.growthbook;

import io.klustr.integrations.growthbook.responses.GrowthBookExperimentPaginationResult;
import io.klustr.integrations.growthbook.responses.GrowthBookExperimentResult;
import io.klustr.integrations.growthbook.responses.GrowthBookProjectResult;
import io.klustr.integrations.growthbook.responses.GrowthbookProjectPaginationResult;
import io.klustr.storage.Pagination;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "growthbook.api.url")
public class GrowthBookApi {

    private static final Logger log = LoggerFactory.getLogger(GrowthBookApi.class);
    private static final MediaType mediaType = MediaType.parse("application/json");
    private final String url;
    private final String accessToken;
    private final OkHttpClient http;

    public GrowthBookApi(@Value("${growthbook.api.url:http://localhost:4100/api/v1}") String url,
                         @Value("${growthbook.api.token:secret_readonly_BGkp2UW3Mz8aZ63FhUQK4WRZiNoR4rdYKpZUvKlPRX0}") String accessToken) {
        log.info("growthbook.api.url: " + url);
        this.url = url;
        this.http = new OkHttpClient();
        this.accessToken = accessToken;
    }

    public GrowthBookExperimentResult getExperiment(String id) {
        HttpUrl url1 = HttpUrl.parse(url + "/experiments/" + id).newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url1.toString())
                .header("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful() && res.body() != null) {
                String body = res.body().string();
                return Json.parse(body, GrowthBookExperimentResult.class);
            } else {
                String body = res.body() != null ? res.body().string() : "(no response)";
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public GrowthBookExperimentPaginationResult listExperiments(Pagination pagination) {
        HttpUrl url1 = HttpUrl.parse(url + "/experiments").newBuilder()
                .addQueryParameter("offset", String.valueOf(pagination.start))
                .addQueryParameter("limit", String.valueOf(pagination.limit))
                .build();
        Request request = new Request.Builder()
                .url(url1.toString())
                .header("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful() && res.body() != null) {
                String body = res.body().string();
                return Json.parse(body, GrowthBookExperimentPaginationResult.class);
            } else {
                String body = res.body() != null ? res.body().string() : "(no response)";
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public GrowthbookProjectPaginationResult listProjects(Pagination pagination) {

        HttpUrl url1 = HttpUrl.parse(url + "/projects").newBuilder()
                .addQueryParameter("offset", String.valueOf(pagination.start))
                .addQueryParameter("limit", String.valueOf(pagination.limit))
                .build();

        Request request = new Request.Builder()
                .url(url1.toString())
                .header("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String body = res.body().string();
                return Json.parse(body, GrowthbookProjectPaginationResult.class);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public GrowthBookProjectResult getProject(String id) {

        HttpUrl url1 = HttpUrl.parse(url + "/projects/" + id).newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url1.toString())
                .header("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String body = res.body().string();
                return Json.parse(body, GrowthBookProjectResult.class);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
