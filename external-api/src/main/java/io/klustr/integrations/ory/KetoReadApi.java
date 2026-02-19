package io.klustr.integrations.ory;

import io.klustr.schemas.integrations.ory.Relationship;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "keto.read.url")
public class KetoReadApi {

    private static final Logger log = LoggerFactory.getLogger(KetoReadApi.class);

    private final OkHttpClient http = new OkHttpClient();
    private final String readUrl;

    private final MediaType jsonMediaType = MediaType.parse("application/json");

    public KetoReadApi(@Value("${keto.read.url}") String readUrl) {
        this.readUrl = readUrl;
    }

    public void health() {
        // https://keto-admin.dev.klustr.io/health/ready
        // https://keto.dev.klustr.io/health/ready

        Request request = new Request.Builder()
                .url(readUrl + "/health/ready")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Keto Read URL not available. " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public NamespaceResponse getNamespaces() {
        Request request = new Request.Builder()
                .url(readUrl + "/namespaces")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return U.fromJson(res.body().string(), NamespaceResponse.class);
            } else {
                throw new RuntimeException("Failed to get namespaces: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkRelationship(Relationship relationship) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples/check").newBuilder()
                .addQueryParameter("namespace", relationship.getNamespace())
                .addQueryParameter("relation", relationship.getRelation())
                .addQueryParameter("object", relationship.getObject())
                .addQueryParameter("subject_id", relationship.getSubjectId())
                .build();

        Request request = new Request.Builder()
                .url(url.toString())
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return true;
            } else if (res.code() == 401) {
                return false;
            } else {
                throw new RuntimeException("Unknown response of " + res.code() + " from permission check." + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public RelationshipResponse getSubjectRelationships(String subject_id, int page_size) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples").newBuilder()
                .addQueryParameter("subject_id", subject_id)
                .addQueryParameter("page_size", String.valueOf(page_size))
                .build();

        return getRelationshipResponse(url);
    }

    public RelationshipResponse getSubjectRelationships(String namespace, String subject_id, int page_size) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples").newBuilder()
                .addQueryParameter("namespace", namespace)
                .addQueryParameter("subject_id", subject_id)
                .addQueryParameter("page_size", String.valueOf(page_size))
                .build();

        return getRelationshipResponse(url);
    }

    public RelationshipResponse listSubjectRelationships(String namespace, String relation, String subject_id, int page_size) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples").newBuilder()
                .addQueryParameter("namespace", namespace)
                .addQueryParameter("subject_id", subject_id)
                .addQueryParameter("relation", relation)
                .addQueryParameter("page_size", String.valueOf(page_size))
                .build();

        return getRelationshipResponse(url);
    }

    public RelationshipResponse getSubjectRelationshipsToObject(String namespace, String object, String subject_id, int page_size) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples").newBuilder()
                .addQueryParameter("namespace", namespace)
                .addQueryParameter("object", object)
                .addQueryParameter("subject_id", subject_id)
                .addQueryParameter("page_size", String.valueOf(page_size))
                .build();

        return getRelationshipResponse(url);
    }

    public RelationshipResponse getObjectRelationships(String namespace, String object, int page_size) {
        HttpUrl url = HttpUrl.parse(readUrl + "/relation-tuples").newBuilder()
                .addQueryParameter("namespace", namespace)
                .addQueryParameter("object", object)
                .addQueryParameter("page_size", String.valueOf(page_size))
                .build();

        return getRelationshipResponse(url);
    }

    private RelationshipResponse getRelationshipResponse(HttpUrl url) {
        Request request = new Request.Builder()
                .url(url.toString())
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            String json = res.body().string();
            if (res.isSuccessful()) {
                return U.fromJson(json, RelationshipResponse.class);
            } else {
                throw new RuntimeException("Failed to get namespaces: " + json);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class RelationshipResponse {
        public List<Relationship> relation_tuples;
        public String next_page_token;
    }

    public static class NamespaceResponse {
        public List<Namespace> namespaces;
    }

    public static class Namespace {
        public String name;
    }
}
