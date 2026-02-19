package io.klustr.storage.docs.http;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.utils.Json;
import okhttp3.*;

import java.io.IOException;

public class HttpClientCredentialAuthTokenProvider {

    private final String tokenUrl;

    /**
     * The auth token provider to get tokens from.
     * @param tokenUrl https://<hydra-host>/oauth2/token
     */
    public HttpClientCredentialAuthTokenProvider(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public HttpClientCredentialRefreshableToken getRefreshableToken(String client_id, String client_secret) {
        return new HttpClientCredentialRefreshableToken(client_id, client_secret, this);
    }

    public JsonNode auth(String client_id, String client_seceret) {
        OkHttpClient client = new OkHttpClient();

        String credential = Credentials.basic(client_id, client_seceret);

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("scope", "openid")  // optional
                .build();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(body)
                .header("Authorization", credential)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() != null) {
                    throw new RuntimeException("Unexpected code " + response.body().string() + " returned with code " + response.code());
                } else {
                    throw new RuntimeException("Unexpected code " + response.code() + " returned and no body.");
                }
            }

            return Json.toJsonNode(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
