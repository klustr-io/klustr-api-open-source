package io.klustr.storage.docs.http;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.klustr.storage.docs.DocumentDatabase;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.time.Duration;

@ConditionalOnProperty(name = "klustr.collections.token_url")
public class HttpDocumentDatabaseFactory implements DocumentDatabaseFactory {


    private final HttpDocumentDatabase db;

    public HttpDocumentDatabaseFactory(@Value("${klustr.collections.client_id}") String client_id,
                                       @Value("${klustr.collections.client_secret}") String client_secret,
                                       @Value("${klustr.collections.token_url:https://hydra.dev.klustr.io/oauth2/token}") String tokenUrl,
                                       @Value("${klustr.collections.api_url:https://api.dev.klustr.io}") String apiUrl) {
        this.db = new HttpDocumentDatabase(apiUrl, new TokenExchange(client_id, client_secret, tokenUrl));
    }

    @Override
    public DocumentDatabase resolve() {
        return this.db;
    }
}
