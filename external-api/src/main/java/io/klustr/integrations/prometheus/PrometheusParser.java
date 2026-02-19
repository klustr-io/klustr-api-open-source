package io.klustr.integrations.prometheus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class PrometheusParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<DataPoint> asMetrics(String jsonResponse) throws IOException {
        List<MetricSeries> parsed = asListMetricSeries(jsonResponse);
        return parsed.isEmpty() ? List.of() : parsed.get(0).getPoints();
    }

    public static List<MetricSeries> asListMetricSeries(String jsonResponse) throws IOException {
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode results = root.path("data").path("result");
        List<MetricSeries> seriesList = new ArrayList<>();

        for (JsonNode result : results) {
            // Metric labels
            Map<String, String> metric = new HashMap<>();
            result.path("metric").fields().forEachRemaining(e -> metric.put(e.getKey(), e.getValue().asText()));

            // Values array: [ [timestamp, value], ... ]
            List<DataPoint> points = new ArrayList<>();
            for (JsonNode valueNode : result.path("values")) {
                double ts = valueNode.get(0).asDouble();
                String valStr = valueNode.get(1).asText();
                double val = "NaN".equals(valStr) ? Double.NaN : Double.parseDouble(valStr);
                points.add(new DataPoint(new DateTime((long) ts * 1000), val));
            }

            seriesList.add(new MetricSeries(metric, points));
        }

        return seriesList;
    }
}