package io.klustr.storage.docs.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Sets;
import io.klustr.storage.docs.DocumentDatabase;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.utils.Json;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Set;

public class HttpDocumentDatabase implements DocumentDatabase {

    public static final TypeReference<Set<String>> SET_TYPE_REFERENCE = new TypeReference<>() {
    };
    private final TokenExchange exchange;
    private final String url;
    private final OkHttpClient http;

    public HttpDocumentDatabase(String url, TokenExchange exchange) {
        this.exchange = exchange;
        this.url = url;
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
    }

    @Override
    public <T> DocumentTable<T> table(String database, String table, Class<T> clazz) {
        // this doesn't need to do much other than call / poke our endpoint
        // and potentially store metadata on client / clazz and
        // check ownership

        Request request = new Request.Builder()
                .url(this.url + "/" + database + "/collections/" + table)
                .get()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return new HttpDocumentTable<>(database, table, this.url, exchange, clazz);
            } else {
                throw new RuntimeException("Could not establish table " + table + ". Response code = " + res.code());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Set<String> getTables(String database) {
        Request request = new Request.Builder()
                .url(this.url + "/" + database + "/collections")
                .get()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                if (res.body() == null) {
                    return Sets.newHashSet();
                }
                return Json.parse(res.body().string(), SET_TYPE_REFERENCE);
            } else {
                throw new RuntimeException("Could not list tables " + res.code());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteTable(String database, String table) {
        Request request = new Request.Builder()
                .url(this.url + "/" + database + "/collections/" + table)
                .delete()
                .build();
        try (Response res = this.http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                // likely not enough permissions
                // or it is owned by someone else etc
                throw new RuntimeException("Could not delete table " + table + ". Response code = " + res.code());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteDatabase(String database) {
        // do nothing for now
    }
}
