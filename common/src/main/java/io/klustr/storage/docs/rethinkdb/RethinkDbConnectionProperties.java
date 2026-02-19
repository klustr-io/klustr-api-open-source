package io.klustr.storage.docs.rethinkdb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rethinkdb")
public class RethinkDbConnectionProperties {

    public RethinkDbConnectionProperties() {}

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() { }
        private RethinkDbConnectionProperties o = new RethinkDbConnectionProperties();
        public Builder withHostname(String hostname) {
            o.setHostname(hostname);
            return this;
        }
        public Builder withPort(int port) {
            o.setPort(port);
            return this;
        }
        public RethinkDbConnectionProperties build() {
            return o;
        }
    }

    /**
     * Defaults here act as fallback if no env var or yml value is set
     */
    private String hostname;
    private int port;

    // Getters and setters
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
