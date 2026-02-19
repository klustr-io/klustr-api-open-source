package io.klustr.integrations.mailtrap;

import io.klustr.schemas.integrations.mailtrap.EmailPayload;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnProperty(name = "mailtrap.apikey")
public class MailTrapApi {

    private final String apiKey;

    public MailTrapApi(@Value("${mailtrap.apikey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public void send(EmailPayload payload) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, U.toJson(payload));
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            IOUtils.closeQuietly(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
