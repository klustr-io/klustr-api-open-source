package io.klustr.storage.docs.http;

public class TokenExchange {

    private final String client_id;
    private final String client_secret;
    private final String token_url;

    public TokenExchange(String client_id, String client_secret, String token_url) {
        this.client_secret = client_secret;
        this.client_id = client_id;
        this.token_url = token_url;
    }

    public String getCurrentToken() {
        HttpClientCredentialRefreshableToken token = new HttpClientCredentialAuthTokenProvider(this.token_url).getRefreshableToken(this.client_id, this.client_secret);
        return token.getAccessToken();
    }
}
