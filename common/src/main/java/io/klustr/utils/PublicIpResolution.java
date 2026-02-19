package io.klustr.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Optional;

public class PublicIpResolution {
    private static final Logger log = LoggerFactory.getLogger(PublicIpResolution.class);

    private static Cache<String, Optional<IpResponse>> _cache = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(30))
            .recordStats()
            .build();

    public static Optional<IpResponse> getPublicIp() {
        try {
            String host = InetAddress.getLocalHost().getHostName();
            return _cache.get(host, PublicIpResolution::tryGetPublicIP);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Optional<IpResponse> tryGetPublicIP() {
        OkHttpClient http = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("https://api.ipify.org?format=json")
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                return Optional.of( Json.parse(res.body().string(), IpResponse.class));
            }
        } catch (Exception ex) {
            log.error("Error resolving public IP", ex);
        }
        return Optional.empty();
    }

    public static class IpResponse {
        public String ip;
    }
}
