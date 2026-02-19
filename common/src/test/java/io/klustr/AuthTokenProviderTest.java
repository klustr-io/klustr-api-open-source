package io.klustr;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.storage.docs.DocumentTable;
import io.klustr.storage.docs.http.HttpClientCredentialAuthTokenProvider;
import io.klustr.storage.docs.http.HttpDocumentDatabase;
import io.klustr.storage.docs.http.HttpClientCredentialRefreshableToken;
import io.klustr.storage.docs.http.TokenExchange;
import io.klustr.utils.Json;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class AuthTokenProviderTest {

    // String API_URL = "http://localhost:7777";
    String API_URL = "https://api.dev.klustr.io";

    @Test
    public void can_we_get_token() {
        TokenExchange ex = new TokenExchange("36966cbd-0b58-48fb-8c72-bf171f2f7e13", "50a1644fbb23385e", "https://hydra.dev.klustr.io/oauth2/token");
        assertThat(ex.getCurrentToken()).isNotNull();
    }

    @Test
    public void we_can_get_a_table() {
        TokenExchange ex = new TokenExchange("36966cbd-0b58-48fb-8c72-bf171f2f7e13", "50a1644fbb23385e", "https://hydra.dev.klustr.io/oauth2/token");
        HttpDocumentDatabase db = new HttpDocumentDatabase(API_URL, ex);
        DocumentTable<JsonNode> my_table = db.table("rize.fit", "my_table", JsonNode.class);
        JsonNode jsonNode = Json.toJsonNode(Json.toJson(new TestObject()));
        my_table.insert(UUID.randomUUID().toString(), jsonNode);
    }

}
