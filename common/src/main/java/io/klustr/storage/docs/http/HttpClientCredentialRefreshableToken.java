package io.klustr.storage.docs.http;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.utils.Json;
import org.joda.time.DateTime;

public class HttpClientCredentialRefreshableToken {

    private final HttpClientCredentialAuthTokenProvider provider;
    private final String client_id;
    private final String client_secret;

    public HttpClientCredentialRefreshableToken(String client_id, String client_secret, HttpClientCredentialAuthTokenProvider provider) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.provider = provider;
    }

    private static record Token (String access_token, String scope, String token_type, Integer expires_in) {  }

    private static class TokenRef {
        public Token token;
        public DateTime expires;

        public TokenRef(Token token) {
            this.token = token;
            this.expires = DateTime.now().plusSeconds(token.expires_in);
        }

        public boolean isExpired() {
            return this.expires.isAfter(DateTime.now().minusSeconds(60));   // add buffer
        }
    }

    private TokenRef _instance;

    public String getAccessToken() {
        if (this._instance != null) {
            if (!this._instance.isExpired()) {
                return this._instance.token.access_token;
            }
        }

        JsonNode json = this.provider.auth(client_id, client_secret);
        Token parse = Json.parse(json, Token.class);
        this._instance = new TokenRef(parse);
        return this._instance.token.access_token;
    }
}
