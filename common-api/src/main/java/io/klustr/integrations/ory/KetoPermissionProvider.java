package io.klustr.integrations.ory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.klustr.permissions.*;
import io.klustr.permissions.dsl.CheckResourceStage;
import io.klustr.permissions.dsl.Permissions;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KetoPermissionProvider implements PermissionProvider {

    private static final Logger log = LoggerFactory.getLogger(KetoPermissionProvider.class);

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String readUrl;
    private final String adminUrl;

    private final OkHttpClient http;

    public KetoPermissionProvider(@Value("${keto.read.url}")String readUrl, @Value("${keto.write.url}")String adminUrl) {
        this.readUrl = StringUtils.stripEnd(readUrl,"/");
        this.adminUrl = StringUtils.stripEnd(adminUrl,"/");
        this.http = new OkHttpClient();
    }

    private String getKetoRelationKey(Scope scope, Target object) {
        if (scope.namespace() == null) {
            return scope.relation();
        }
        if (StringUtils.isBlank(scope.namespace())) {
            return scope.relation();
        }
        if (scope.namespace().equalsIgnoreCase(object.namespace())) {
            return scope.relation();
        }
        return scope.namespace() + "_" + scope.relation();
    }

    @Override
    public void grant(SubjectKey subject, Scope scope, Target object) {


        RelationTuple tuple = new RelationTuple(
                object.namespace(),   // comes from ObjectId
                object.value(),       // object identifier
                getKetoRelationKey(scope, object),         // e.g. "#create", "#read", "#update", "#delete", "#admin"
                subject.value()       // e.g. "user:bob"
        );

        RequestBody body = RequestBody.create(U.toJson(tuple), JSON);

        Request request = new Request.Builder()
                .url(this.adminUrl + "/relation-tuples")
                .put(body)
                .build();

        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                String err = res.body().string();
                throw new RuntimeException(err);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void revoke(SubjectKey subject, Scope scope, Target object) {
        Request request = new Request.Builder()
                .url(this.adminUrl + "/relation-tuples?subject_id=" + subject.value() + "&namespace=" + object.namespace() + "&object=" + object.value() + "&relation=" + getKetoRelationKey(scope, object))
                .delete()
                .build();

        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                String body = res.body().string();
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void revokeAll(SubjectKey subject, Target object) {
        Request request = new Request.Builder()
                .url(this.adminUrl + "/relation-tuples?subject_id=" + subject.value() + "&namespace=" + object.namespace() + "&object=" + object.value())
                .delete()
                .build();

        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                String body = res.body().string();
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void revokeAll(SubjectKey subject) {
        Request request = new Request.Builder()
                .url(this.adminUrl + "/relation-tuples?subject_id=" + subject.value())
                .delete()
                .build();

        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                String body = res.body().string();
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean check(SubjectKey subject, Scope scope, Target object) {
        HttpUrl url = HttpUrl.parse(this.readUrl + "/relation-tuples/check")
                .newBuilder()
                .addQueryParameter("namespace", scope.namespace())
                .addQueryParameter("object", object.value())
                .addQueryParameter("relation", scope.relation())
                .addQueryParameter("subject_id", subject.value())
                .build();
        Request req = new Request.Builder().url(url).get().build();
        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) {
                log.info("\uD83D\uDD12 Deny access: namespace={}, object={}, relation={}, subject_id={}", scope.namespace(),
                        object.value(), scope.relation(), subject.value());
                return false;
            } else {
                log.info("\uD83D\uDD12 Allow access: namespace={}, object={}, relation={}, subject_id={}", scope.namespace(),
                        object.value(), scope.relation(), subject.value());
            }
            JsonNode node = mapper.readTree(resp.body().string());
            return node.path("allowed").asBoolean(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Relation> listRelations(SubjectKey subject, String relation, Target target) {

        Map<String, String> params = Maps.newHashMap();
        if (!(target.value().equalsIgnoreCase("*") || target.value().isEmpty())) {
            params.put("object", target.value());
        }
        if (target.namespace().isEmpty()) {
            throw new RuntimeException("Target namespace can not be null");
        } else {
            params.put("namespace", target.namespace());
        }
        params.put("subject_id", subject.value());

        HttpUrl.Builder builder = HttpUrl.parse(this.readUrl + "/relation-tuples").newBuilder();
        params.keySet().forEach(key -> {
            builder.addQueryParameter(key, params.get(key));
        });
        HttpUrl url = builder.build();

        Request req = new Request.Builder().url(url).get().build();
        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) {
                throw new RuntimeException("Failed to list relations: " + resp.code());
            }
            String jsonBody = resp.body().string();
            JsonNode arr = mapper.readTree(jsonBody).path("relation_tuples");
            Set<Relation> relations = parseRelations(arr);

            // if compose then strip
            Set<Relation> filtered = relations.stream().filter(x -> {
                return x.scope().namespace().equalsIgnoreCase(relation) ||
                        x.scope().relation().equalsIgnoreCase(relation);
            }).collect(Collectors.toSet());
            return filtered;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Relation> listRelationsForObject(Target object) {
        HttpUrl url = HttpUrl.parse(this.readUrl + "/relation-tuples")
                .newBuilder()
                .addQueryParameter("object", object.value())
                .build();

        Request req = new Request.Builder().url(url).get().build();
        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) {
                throw new RuntimeException("Failed to list relations: " + resp.code());
            }
            JsonNode arr = mapper.readTree(resp.body().string()).path("relation_tuples");
            Set<Relation> relations = parseRelations(arr);
            return relations;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull Set<Relation> parseRelations(JsonNode arr) {
        Set<Relation> relations = new HashSet<>();
        if (arr.isArray()) {
            for (JsonNode node : arr) {
                String namespace = node.path("namespace").asText();
                String objectId   = node.path("object").asText();
                String relation   = node.path("relation").asText();
                String subjectRaw = node.path("subject_id").asText();

                Target obj = new Target(namespace, objectId);
                Scope scope = new Scope(namespace, relation);
                SubjectKey subject = null;
                if (subjectRaw.contains(":")) {
                    String type = subjectRaw.split(":")[0];
                    String id = subjectRaw.split(":")[1];
                    subject = new SubjectKey(type, id);
                } else {
                    // throw new RuntimeException("Invalid subject key must be a pair of <type>:<id>. Such as <user>:bob, or <group>:xxx");
                    // assume user id
                    subject = SubjectKey.userId(subjectRaw);
                }

                relations.add(new Relation(subject, scope, obj));
            }
        }
        return relations;
    }

    @Override
    public Permissions iam() {
        return new Permissions(this);
    }

    @Override
    public Health health() {
        checkUrlHealth(adminUrl + "/version");
        checkUrlHealth(readUrl + "/version");
        return Health.up().build();
    }

    private void checkUrlHealth(String adminHealthUrl) {
        Request request = new Request.Builder()
                .url(adminHealthUrl)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("URL not available. " + adminHealthUrl + " returned " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // DTO only for Keto wire payloads
    private static class RelationTuple {
        public final String namespace;
        public final String object;
        public final String relation;
        public final String subject_id;

        RelationTuple(String namespace, String object, String relation, String subject_id) {
            this.namespace = namespace;
            this.object = object;
            this.relation = relation;
            this.subject_id = subject_id;
        }
    }
}
