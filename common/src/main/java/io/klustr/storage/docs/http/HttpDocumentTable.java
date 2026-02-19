package io.klustr.storage.docs.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.docs.DocumentTableStats;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbSort;
import io.klustr.utils.Json;
import okhttp3.*;

import java.util.Optional;

public class HttpDocumentTable<T> implements DocumentTable<T> {

    // public final TypeReference<DocumentResult<T>> DOCUMENT_RESULT_TYPE_REFERENCE = new TypeReference<>() {};
    private final String table;
    private final String url;
    private final TokenExchange exchange;
    private final OkHttpClient http;
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private final Class<T> clazz;
    private final String db;
    private final JavaType document_type;

    public HttpDocumentTable(String db, String table, String url, TokenExchange exchange, Class<T> clazz) {
        this.url = url;
        this.db = db;
        this.exchange = exchange;
        this.table = table;
        this.clazz = clazz;
        this.http = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    String token = "Bearer " + exchange.getCurrentToken();

                    Request requestWithToken = original.newBuilder()
                            .header("Authorization", token)
                            .build();

                    return chain.proceed(requestWithToken);
                })
                .build();

        this.document_type = new ObjectMapper()
                .getTypeFactory()
                .constructParametricType(DocumentResult.class, clazz);
    }

    @Override
    public void insert(String id, JsonNode node) {

        if (!node.has("id")) {
            ((ObjectNode)node).put("id", id);
        } else if (!node.get("id").textValue().equalsIgnoreCase(id)) {
            throw new RuntimeException("Document node contains ID that does not match inserted id.");
        }

        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" + this.table)
                .post(RequestBody.create(Json.toJson(node), MEDIA_TYPE))
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not insert document into table " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean update(String id, JsonNode node) {
        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" +  this.table + "/" + id)
                .put(RequestBody.create(Json.toJson(node), MEDIA_TYPE))
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not update document into table " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public void delete(String id) {
        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" + this.table + "/" + id)
                .delete()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not delete document from table " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(DbQuery fq) {
        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" + this.table + "?q=" + fq.toSolrQuery())
                .delete()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not delete document from table " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void ensureIndex(String index) {
        // not applicable
        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" + this.table + "/.index/" + index)
                .put(RequestBody.create("{}", MEDIA_TYPE))
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not add index to " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<T> getDocument(String id) {
        Request request = new Request.Builder()
                .url(this.url + "/" + this.db + "/collections/" + this.table + "/" + id)
                .get()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                if (res.code() != 404) {
                    throw new RuntimeException("Could get document from " + table + ". Response code = " + res.code() + ". " + res.body().string());
                } else {
                    return Optional.empty();
                }
            }
            if (res.body() == null) {
                return Optional.empty();
            }
            String json = res.body().string();
            T obj = Json.parse(json, this.clazz);
            return Optional.of(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DocumentResult<String> getDocumentIds(DbQuery q, Pagination pagination) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(this.url + "/" + this.db + "/collections/" + table)
                .newBuilder()
                .addPathSegments(".metadata/ids")
                .addQueryParameter("fq", q.toSolrQuery());

        if (pagination.limit >= 0) {
            urlBuilder.addQueryParameter("limit", String.valueOf(pagination.limit));
        }
        if (pagination.start >= 0) {
            urlBuilder.addQueryParameter("start", String.valueOf(pagination.start));
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not add index to " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
            if (res.body() == null) {
                DocumentResult<String> yield = new DocumentResult<String>();
                yield.limit = pagination.limit;
                yield.count = 0L;
                yield.skip = pagination.start;
                return yield;
            }
            String json = res.body().string();
            return Json.parse(json, new TypeReference<DocumentResult<String>>() {});
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DocumentResult<T> insecureQuery(DbQuery q, DbSort orderBy, Pagination pagination) {
        String url = this.url + "/" + this.db + "/collections/" + table + "/search";
        url += "?q=" + q.toSolrQuery();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();

        if (pagination != null) {
            if (pagination.limit >= 0) {
                urlBuilder.addQueryParameter("limit", String.valueOf(pagination.limit));
            }
            if (pagination.start >= 0) {
                urlBuilder.addQueryParameter("start", String.valueOf(pagination.start));
            }
        }
        if (orderBy != null) {
            urlBuilder.addQueryParameter("sort", orderBy.toSolrQuery());
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Could not query " + table + ". Response code = " + res.code() + ". " + res.body().string());
            }
            String json = res.body().string();

            return Json.parse(json, document_type);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DocumentResult<T> insecureQuery(DbQuery q, Pagination pagination) {
        return insecureQuery(q, null, pagination);
    }

    @Override
    public DocumentResult<T> insecureQuery(Pagination pagination) {
        return insecureQuery(Db.all(), null, pagination);
    }

    @Override
    public Optional<DocumentTableStats> stats() {
        return Optional.empty();
    }
}
