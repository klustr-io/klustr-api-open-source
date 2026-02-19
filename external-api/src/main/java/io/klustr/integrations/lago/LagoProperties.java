package io.klustr.integrations.lago;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lago")
public class LagoProperties {

    public LagoProperties() {}

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() { }
        private LagoProperties o = new LagoProperties();
        public Builder withUrl(String url) {
            o.setUrl(url);
            return this;
        }
        public Builder withApiKey(String key) {
            o.setApiKey(key);
            return this;
        }
        public LagoProperties build() {
            return o;
        }
    }

    /**
     * Defaults here act as fallback if no env var or yml value is set
     */
    private String url = "https://lago-api.dev.klustr.io/api/v1";
    private String apiKey = "default-api-key";

    // Getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}