package io.klustr.integrations.portainer;

import com.svix.openapi.client.StringUtil;
import io.klustr.integrations.gitlab.GitLabGroupResourceProvider;
import io.klustr.schemas.integrations.git.GitGroupReference;
import io.klustr.utils.Json;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PortainerAuthentication {

    private final String username;
    private final String password;
    private final String url;
    private final String environment;


    private static final Logger log = LoggerFactory.getLogger(PortainerAuthentication.class);
    private static final MediaType mediaType = MediaType.parse("application/json");

    private final OkHttpClient http = new OkHttpClient();

    public PortainerAuthentication(@Value("${portainer.api.url:https://portainer.dev.klustr.io/api}") String url,
                                   @Value("${portainer.api.environment:3}") String environment,
                                   @Value("${portainer.api.username:admin}") String username,
                                   @Value("${portainer.api.password:adminadminadmin}") String password) {
        this.username = username;
        this.password = password;
        this.environment = environment;
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
    public String getEnvironment() {
        return this.environment;
    }

    private static class Credentials {
        public String username;
        public String password;
    }

    public static class AuthenticationResponse {
        public String jwt;
    }

    private static String _token;
    private static DateTime _expires;

    public String getToken() {
        if (StringUtils.isNotBlank(_token)) {
            if (_expires.isAfter(DateTime.now().plusMinutes(5))) {
                return _token;
            }
        }

        Credentials creds = new Credentials();
        creds.password = password;
        creds.username = username;

        Request req = new Request.Builder()
                .url(this.url + "/auth")
                .post(RequestBody.create(Json.toJson(creds), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            _token = Json.parse(res.body().string(), AuthenticationResponse.class).jwt;
            _expires = DateTime.now().plusMinutes(60);
            return _token;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
