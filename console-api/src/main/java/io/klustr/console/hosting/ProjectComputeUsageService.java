package io.klustr.console.hosting;

import com.google.common.collect.Maps;
import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.compute.ComputeUsageProvider;
import io.klustr.compute.Timeframe;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.haproxy.HaproxyComputeLoadbalancerProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.integrations.prometheus.DataPoint;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.compute.ComputeIngress;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.portainer.Port;
import io.klustr.schemas.integrations.portainer.PortainerComputeReference;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.DateMathParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.Seconds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Billing & Usage APIs", description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectComputeUsageService {

    private final Storage storage;
    private final PortainerComputeResourceProvider compute;
    private final ComputeUsageProvider usageProvider;
    private final ComputeLoadBalancerResolver loadbalancers;
    private final ComputeInstanceTypeProvider instanceTypes;

    public ProjectComputeUsageService(Storage storage,
                                      PortainerComputeResourceProvider compute,
                                      ComputeLoadBalancerResolver loadbalancers,
                                      ComputeInstanceTypeProvider instanceTypes,
                                      ComputeUsageProvider usageProvider) {
        this.storage = storage;
        this.compute = compute;
        this.usageProvider = usageProvider;
        this.loadbalancers = loadbalancers;
        this.instanceTypes = instanceTypes;
    }


    @GetMapping("/{projectId}/usage/compute")
    @Operation(
            description = """
This endpoint provides the details about any compute resources being consumed by the project. This will return an array of the compute nodes found, their instance type and limits.
""",
            operationId = "getComputeUsage",
            summary = "Retrieve compute usage metrics for a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public ResponseEntity<ComputeUsageResponse> getComputeUsage(@PathVariable("projectId") String projectId,
                                                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Timeframe timeframe = Timeframe.Prior(1).Hours();
        return getUsage(projectId, timeframe, oauth);
    }

    @GetMapping("/{projectId}/usage/compute/history")
    @Operation(
            description = """
Provides graph data and time series data compute and networking and disk usage for any given compute instance.
""",
            operationId = "getComputeUsageHistory",
            summary = "Retrieve compute data for graphs and charts.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public ResponseEntity<ComputeUsageResponse> getComputeUsageHistory(@PathVariable("projectId") String projectId,
                                                @RequestParam(value = "start", defaultValue = "NOW/DAY-1DAY") String start,
                                                @RequestParam(value = "stop", defaultValue = "NOW") String stop,
                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Timeframe timeframe = new Timeframe();
        timeframe.start = start;
        timeframe.stop = stop;
        timeframe.step = chooseInterval(DateMathParser.parse(start), DateMathParser.parse( stop));
        return getUsage(projectId, timeframe, oauth);
    }

    public static Timeframe.StepInterval chooseInterval(DateTime start, DateTime end) {
        long seconds = Seconds.secondsBetween(start, end).getSeconds();

        if (seconds <= 5 * 60) return Timeframe.StepInterval.seconds(1);         // < 5 minutes
        if (seconds <= 15 * 60) return Timeframe.StepInterval.seconds(5);        // < 15 minutes
        if (seconds <= 60 * 60) return Timeframe.StepInterval.seconds(10);       // < 1 hour
        if (seconds <= 3 * 60 * 60) return Timeframe.StepInterval.seconds(30);   // < 3 hours
        if (seconds <= 6 * 60 * 60) return Timeframe.StepInterval.minutes(1);    // < 6 hours
        if (seconds <= 12 * 60 * 60) return Timeframe.StepInterval.minutes(2);   // < 12 hours
        if (seconds <= 24 * 60 * 60) return Timeframe.StepInterval.minutes(5);   // < 1 day
        if (seconds <= 3 * 24 * 60 * 60) return Timeframe.StepInterval.minutes(10); // < 3 days
        if (seconds <= 7 * 24 * 60 * 60) return Timeframe.StepInterval.minutes(30); // < 1 week
        if (seconds <= 30 * 24 * 60 * 60) return Timeframe.StepInterval.hours(1);   // < 1 month
        if (seconds <= 90 * 24 * 60 * 60) return Timeframe.StepInterval.hours(2);   // < 3 months
        if (seconds <= 180 * 24 * 60 * 60) return Timeframe.StepInterval.hours(6);  // < 6 months
        if (seconds <= 365 * 24 * 60 * 60) return Timeframe.StepInterval.hours(12); // < 1 year

        return Timeframe.StepInterval.days(1); // > 1 year
    }

    private ResponseEntity<ComputeUsageResponse> getUsage(String projectId,
                                                          Timeframe timeframe,
                                                          OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);

        List<PortainerComputeReference> nodes = this.compute.getComputeNodes(project.getOrgId(), projectId);
        if (nodes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // TODO assumes only ONE node per project... which would be entirely wrong for a fleet/loadbalanced situation
        PortainerComputeReference node = nodes.get(0);

        ComputeUsageResponse res = new ComputeUsageResponse();
        res.enabled = true;
        res.image_id = node.getImageID();
        res.image = node.getImage();
        res.state =node.getState();
        res.status = node.getStatus();
        res.ports = node.getPorts();
        if (node.getCreated() != null) {
            res.created = new DateTime((long) node.getCreated() * 1000, DateTimeZone.UTC);
        }
        // need to get machine type
        res.machine_type = node.getLabels().get("machine_type");

        res.cpu_quota_total = new ComputeMeasurement(this.usageProvider.getCpuQuotaTotal(projectId, timeframe));

        res.memory_usage_bytes_total = new ComputeMeasurement(this.usageProvider.getMemoryUsageTotal(projectId, timeframe));
        res.memory_limit_quota = new ComputeMeasurement(this.usageProvider.getMemoryLimitQuota(projectId, timeframe));

        res.cpu_usage_seconds_total = new ComputeMeasurement(this.usageProvider.getCpuUsageSecondsTotal(projectId, timeframe));
        res.cpu_throttle_periods_total = new ComputeMeasurement(this.usageProvider.getCpuThrottlingPeriodsTotal(projectId, timeframe));

        res.network_receive_bytes = new ComputeMeasurement(this.usageProvider.getNetworkReceiveBytes(projectId, timeframe));
        res.network_transmit_bytes = new ComputeMeasurement(this.usageProvider.getNetworkTransmitBytes(projectId, timeframe));
        res.disk_usage_bytes = new ComputeMeasurement(this.usageProvider.getDiskUsageTotal(projectId, timeframe));
        res.cpu_throttle_percentage_total = new ComputeMeasurement(this.usageProvider.getCpuThrottlePercent(projectId, timeframe));

        res.instance_type = this.instanceTypes.getInstanceTypeByName(res.machine_type).orElse(null);

        ComputeIngress status = this.loadbalancers.resolve(projectId, project.getOrgId()).getStatus(projectId);

        if (status.getStatus() == ComputeIngress.Status.ACTIVE) {
            res.ingress = new Ingress();
            res.ingress.bytes_out_total = new ComputeMeasurement(this.usageProvider.haproxy_server_bytes_out_total(projectId, timeframe));
            res.ingress.bytes_in_total = new ComputeMeasurement(this.usageProvider.haproxy_server_bytes_in_total(projectId, timeframe));
            res.ingress.current_sessions = new ComputeMeasurement(this.usageProvider.haproxy_server_current_sessions(projectId, timeframe));
            res.ingress.http_requests_total = new ComputeMeasurement(this.usageProvider.haproxy_server_http_requests_total(projectId, timeframe));
            res.ingress.response_time_average_seconds = new ComputeMeasurement(this.usageProvider.haproxy_server_response_time_average_seconds(projectId, timeframe));
            res.ingress.max_response_time_seconds = new ComputeMeasurement(this.usageProvider.haproxy_server_max_response_time_seconds(projectId, timeframe));
            res.ingress.used_connections_current = new ComputeMeasurement(this.usageProvider.haproxy_server_used_connections_current(projectId, timeframe));
        }

        return ResponseEntity.ok(res);
    }



    public static class ComputeMeasurement {
        public List<DataPoint> points;
        public Double first;
        public Double last;
        public Double avg;
        public Double max;
        public Double min;
        public Long count = 0L;

        public ComputeMeasurement(List<DataPoint> points) throws IllegalArgumentException {
            this.points = points != null ? points : List.of();

            if (!this.points.isEmpty()) {
                // Extract numeric values
                List<Double> values = this.points.stream()
                        .map(x -> {
                            Double d = Double.valueOf( x.getValue());
                            if (d.isNaN()) return 0d;
                            if (d.isInfinite()) return 0d;
                            return d;
                        })
                        .toList();

                this.count = (long) values.size();
                if (count > 10000) {
                    throw new IllegalArgumentException("Count for metric exceeds 10,0000. Currenty " + count);
                }

                if (!values.isEmpty()) {
                    this.first = values.get(0);
                    this.last = values.get(values.size() - 1);

                    DoubleSummaryStatistics stats = values.stream()
                            .mapToDouble(Double::doubleValue)
                            .summaryStatistics();

                    this.min = stats.getMin();
                    this.max = stats.getMax();
                    this.avg = stats.getAverage();
                }
            }
        }
    }

    public static class ComputeUsageResponse {
        boolean enabled = false;
        public String image;
        public String image_id;
        public String state;
        public DateTime created;
        public String status;
        public String machine_type;

        public Map<String, String> meta = Maps.newHashMap();

        public List<Port> ports;

        public ComputeMeasurement cpu_throttle_periods_total;
        public ComputeMeasurement cpu_usage_seconds_total;
        public ComputeMeasurement cpu_quota_total;
        public ComputeMeasurement cpu_throttle_percentage_total;

        public ComputeMeasurement memory_usage_bytes_total;
        public ComputeMeasurement memory_limit_quota;

        public ComputeMeasurement disk_usage_bytes;

        public ComputeMeasurement network_receive_bytes;
        public ComputeMeasurement network_transmit_bytes;

        public Ingress ingress;

        public ComputeInstanceType instance_type;
    }

    public static class Ingress {
        public ComputeMeasurement bytes_in_total;
        public ComputeMeasurement bytes_out_total;
        public ComputeMeasurement http_requests_total;
        public ComputeMeasurement used_connections_current;
        public ComputeMeasurement current_sessions;
        public ComputeMeasurement max_response_time_seconds;
        public ComputeMeasurement response_time_average_seconds;
    }
}
