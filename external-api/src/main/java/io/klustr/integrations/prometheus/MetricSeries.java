package io.klustr.integrations.prometheus;

import java.util.List;
import java.util.Map;

public class MetricSeries {
    private Map<String, String> metric; // e.g. {instance=..., job=...}
    private List<DataPoint> points;

    public MetricSeries(Map<String, String> metric, List<DataPoint> points) {
        this.metric = metric;
        this.points = points;
    }

    public Map<String, String> getMetric() {
        return metric;
    }

    public List<DataPoint> getPoints() {
        return points;
    }
}