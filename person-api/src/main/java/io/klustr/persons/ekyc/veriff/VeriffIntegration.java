package io.klustr.persons.ekyc.veriff;

import io.klustr.schemas.persons.veriff.VeriffSessionRequest;
import io.klustr.schemas.persons.veriff.VeriffSessionResponse;
import io.klustr.utils.U;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VeriffIntegration {
    private static final Logger log = LoggerFactory.getLogger(VeriffIntegration.class);
    private static final MediaType jsonMediaType = MediaType.parse("application/json");

    private final String baseUrl;

    private final String authKey;

    private final OkHttpClient http;


    public VeriffIntegration(@Value("${veriff.baseUrl}") String baseUrl, @Value("${veriff.authKey}") String authKey) {
        http = new OkHttpClient();
        this.baseUrl = baseUrl;
        this.authKey = authKey;
    }

    public VeriffSessionResponse session(VeriffSessionRequest req) {

        // create
        Request request = new Request.Builder()
                .url(this.baseUrl + "/v1/sessions/")
                .header("X-AUTH-CLIENT", this.authKey)
                .post(RequestBody.create(U.toJson(req), jsonMediaType))
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String body = res.body().string();
                return U.fromJson(body, VeriffSessionResponse.class);
            } else {
                String body = res.body().string();
                log.error(body);
                throw new RuntimeException(body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
