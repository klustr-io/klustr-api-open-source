package io.klustr.integrations.prometheus;

import io.klustr.schemas.integrations.prometheus.HttpUsageMetrics;
import io.klustr.schemas.integrations.prometheus.PrometheusMetricResponse;
import io.klustr.utils.DateMathParser;
import io.klustr.utils.U;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Provides the count of API usage using Prometheus metrics.
 */
@Component
@ConditionalOnProperty(name = "prometheus.url")
public class PrometheusApi {

    private static final Logger log = LoggerFactory.getLogger(PrometheusApi.class);
    private static final MediaType mediaType = MediaType.parse("application/json");
    private final String url;
    private final OkHttpClient http;

    public PrometheusApi(@Value("${prometheus.url}") String url) {
        this.url = url;
        this.http = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public Health health() {

        String url = this.url + "/-/healthy";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (!res.isSuccessful()) {
                String body = res.body().string();
                return Health.down(new RuntimeException(body)).withDetail("url", url).build();
            }
            return Health.up().withDetail("url", url).build();
        } catch (Exception ex) {
            return Health.down(ex).withDetail("url", url).build();
        }
    }

    public PrometheusMetricResponse getKongHttpRequestMetric(String projectId, String start, String interval) {
        // sum(increase(kong_http_requests_total{consumer="head-parakeet-e98a14"}[5m]))[10m:10s]
        String metric = "kong_http_requests_total";
        String command = "sum(increase(" + metric + "{consumer=\"" + projectId + "\"}[5m]))";
        command += ("[" + start + ":" + interval + "]");

        String fetchUrl = url + "/api/v1/query?query=" + command;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                return U.fromJson(json, PrometheusMetricResponse.class);
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public PrometheusMetricResponse getKongHttpRequestsGroupedByAPI(String projectId, String start, String interval) {
        // sum(increase(kong_http_requests_total{consumer="head-parakeet-e98a14"}[5m])) by (service)[10m:10s]
        String metric = "kong_http_requests_total";
        String command = "sum(increase(" + metric + "{consumer=\"" + projectId + "\"}[5m])) by (service)";
        command += ("[" + start + ":" + interval + "]");

        String fetchUrl = url + "/api/v1/query?query=" + command;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();


        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                return U.fromJson(json, PrometheusMetricResponse.class);
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the current usage information for the specified project.
     *
     * @param projectId The project to get usage information for.
     * @return
     */
    public HttpUsageMetrics getCurrentApiGatewayUsage(String projectId, String start, String interval) {
        String queryString = "sum by (service) ( increase(kong_http_requests_total{consumer=\"" + projectId + "\"}[1m]))";
        queryString += ("[" + start + ":" + interval + "]");

        String fetchUrl = url + "/api/v1/query?query=" + queryString;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                return U.fromJson(json, HttpUsageMetrics.class);
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<MetricSeries> query(String query,  String start, String end, String step) {
        String begin = DateMathParser.parse(start).toString();
        String stop = DateMathParser.parse(end).toString();
        String fetchUrl = url + "/api/v1/query_range?query=" + query + "&start=" + begin + "&end=" + stop + "&step=" + step;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                List<MetricSeries> metrics = PrometheusParser.asListMetricSeries(json);
                return metrics;
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Useful for memory or snapshot data, rather than a usage like CPU.
     *
     * @param metric
     * @param projectId
     * @param start
     * @param end
     * @param step
     * @return
     */
    public List<MetricSeries> values(String metric, String projectId, String start, String end, String step) {
        String command = metric + "{namespace=\"" + projectId + "\"}";

        String fetchUrl = url + "/api/v1/query_range?query=" + command + "&start=" + start + "&end=" + end + "&step=" + step;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                List<MetricSeries> metrics = PrometheusParser.asListMetricSeries(json);
                if (!metrics.isEmpty() && metrics.get(0) != null) {
                    if (metrics.get(0).getPoints().size() >= 1000) {
                        log.warn("Exceeds 10k points for command -> " + command + " given query " + fetchUrl);
                    }
                }
                return metrics;
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<MetricSeries> percent(String metric1, String metric2, String projectId, String start, String end, String step) {

        String a = "rate(" + metric1 + "{namespace=\"" + projectId + "\"}[1m])";
        String b = "rate(" + metric2 + "{namespace=\"" + projectId + "\"}[1m])";

        String command = "(" + a + "/" + b + ")";

        String fetchUrl = url + "/api/v1/query_range?query=" + U.encodeURIComponent(command) + "&start=" + start + "&end=" + end + "&step=" + step;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                List<MetricSeries> metrics = PrometheusParser.asListMetricSeries(json);
                if (!metrics.isEmpty() && metrics.get(0) != null) {
                    if (metrics.get(0).getPoints().size() >= 1000) {
                        log.warn("Exceeds 10k points for command -> " + command + " given query " + fetchUrl);
                    }
                }
                return metrics;
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<MetricSeries> rate(String metric, String projectId, String start, String end, String step) {
        String command = "sum(increase(" + metric + "{namespace=\"" + projectId + "\"}[5m]))";

        String fetchUrl = url + "/api/v1/query_range?query=" + command + "&start=" + start + "&end=" + end + "&step=" + step;

        Request request = new Request.Builder()
                .url(fetchUrl)
                .get()
                .build();

        // need to be able to get aggregate by project
        // but then also be able to get by group
        try (Response res = http.newCall(request).execute()) {
            if (res.isSuccessful()) {
                String json = res.body().string();
                List<MetricSeries> metrics = PrometheusParser.asListMetricSeries(json);
                if (!metrics.isEmpty() && metrics.get(0) != null) {
                    if (metrics.get(0).getPoints().size() >= 1000) {
                        log.warn("Exceeds 10k points for command -> " + command + " given query " + fetchUrl);
                    }
                }
                return metrics;
            } else {
                String body = res.body().string();
                throw new RuntimeException("Failed to get metrics for url " + fetchUrl + ": " + body);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static enum Period {
        Minutes,
        Hours,
        Days,
        Weeks
    }
}
