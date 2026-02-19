package io.klustr.compute.impl;

import com.google.common.collect.Lists;
import io.klustr.compute.ComputeUsageProvider;
import io.klustr.compute.Timeframe;
import io.klustr.integrations.prometheus.DataPoint;
import io.klustr.integrations.prometheus.MetricSeries;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.utils.DateMathParser;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrometheusComputeUsageProvider implements ComputeUsageProvider {

    private PrometheusApi api;

    public PrometheusComputeUsageProvider(PrometheusApi api) {
        this.api = api;
    }

    private static String toInterval(Timeframe.StepInterval value) {
        switch (value.step) {
            case days -> {
                return value.value + "d";
            }
            case hnours -> {
                return value.value + "h";
            }
            case minutes -> {
                return value.value + "m";
            }
            case seconds -> {
                return value.value + "s";
            }
        }
        return "1m";
    }

    @Override
    public Health health() {
        return api.health();
    }

    @Override
    public List<DataPoint> getCpuUsageSecondsTotal(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("container_cpu_usage_seconds_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getDiskUsageTotal(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.values("container_blkio_device_usage_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getMemoryUsageTotal(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.values("container_memory_usage_bytes",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getNetworkReceiveBytes(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("container_network_receive_bytes_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getNetworkTransmitBytes(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("container_network_transmit_bytes_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getCpuThrottlingPeriodsTotal(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("container_cpu_cfs_throttled_periods_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    public List<DataPoint> getCpuThrottlePercent(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.percent("container_cpu_cfs_throttled_periods_total",
                "container_cpu_cfs_periods_total", projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));

        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getCpuQuotaTotal(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.values("container_spec_cpu_quota",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> getMemoryLimitQuota(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.values("container_spec_memory_limit_bytes",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_bytes_in_total(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_bytes_in_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_bytes_out_total(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_bytes_out_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_http_requests_total(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_http_requests_total",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_used_connections_current(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_used_connections_current",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_current_sessions(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_current_sessions",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_max_response_time_seconds(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_max_response_time_seconds",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }

    @Override
    public List<DataPoint> haproxy_server_response_time_average_seconds(String projectId, Timeframe timeframe) {
        List<MetricSeries> res = api.rate("haproxy_server_response_time_average_seconds",
                projectId,
                DateMathParser.parse(timeframe.start).toString(),
                DateMathParser.parse(timeframe.stop).toString(),
                toInterval(timeframe.step));
        if (res.isEmpty()) return Lists.newArrayList();
        return res.get(0).getPoints();
    }
}
