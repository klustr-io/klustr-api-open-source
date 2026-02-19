package io.klustr.integrations.gitlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitLabConfiguration {
    public String api_url;
    public String token;
    public String openid_connect = "openid_connect";

    public static final String default_url = "https://gitlab.dev.klustr.io/api/v4";

    public GitLabConfiguration(@Value("${gitlab.url:" + default_url + "}") String api_url,
                               /**
                                * Must create manually https://gitlab.dev.klustr.io/-/user_settings/personal_access_tokens
                                * TODO in the future abstract this so that this is setup during first boot of klustr.io
                                * or during "add gitlab" to the console
                                */
                               @Value("${gitlab.token:}") String token) {
        this.api_url = api_url;
        this.token = token;
    }
}
