package io.klustr.compute;

import io.klustr.integrations.prometheus.DataPoint;
import org.springframework.boot.actuate.health.Health;

import java.util.List;

public interface ComputeUsageProvider {

    Health health();

    List<DataPoint> getCpuUsageSecondsTotal(String projectId, Timeframe timeframe);

    List<DataPoint> getCpuThrottlePercent(String projectId, Timeframe timeframe);

    List<DataPoint> getDiskUsageTotal(String projectId, Timeframe timeframe);

    List<DataPoint> getMemoryUsageTotal(String projectId, Timeframe timeframe);

    List<DataPoint> getNetworkReceiveBytes(String projectId, Timeframe timeframe);

    List<DataPoint> getNetworkTransmitBytes(String projectId, Timeframe timeframe);

    List<DataPoint> getCpuThrottlingPeriodsTotal(String projectId, Timeframe timeframe);

    List<DataPoint> getCpuQuotaTotal(String projectId, Timeframe timeframe);

    List<DataPoint> getMemoryLimitQuota(String projectId, Timeframe timeframe);

    // haproxy_server_bytes_in_total
    List<DataPoint> haproxy_server_bytes_in_total(String projectId, Timeframe timeframe);

    // haproxy_server_bytes_in_total
    List<DataPoint> haproxy_server_bytes_out_total(String projectId, Timeframe timeframe);

    // haproxy_server_http_requests_total
    List<DataPoint> haproxy_server_http_requests_total(String projectId, Timeframe timeframe);

    // haproxy_server_used_connections_current
    List<DataPoint> haproxy_server_used_connections_current(String projectId, Timeframe timeframe);

    // haproxy_server_current_sessions
    List<DataPoint> haproxy_server_current_sessions(String projectId, Timeframe timeframe);

    // haproxy_backend_max_response_time_seconds
    List<DataPoint> haproxy_server_max_response_time_seconds(String projectId, Timeframe timeframe);

    // haproxy_server_response_time_average_seconds
    List<DataPoint> haproxy_server_response_time_average_seconds(String projectId, Timeframe timeframe);
}
