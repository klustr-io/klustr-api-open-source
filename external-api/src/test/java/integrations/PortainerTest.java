package integrations;

import com.google.common.collect.Maps;
import io.klustr.compute.MockProjectTemplateRepository;
import io.klustr.compute.Timeframe;
import io.klustr.compute.dns.impl.PowerDnsApiComputeDnsProvider;
import io.klustr.compute.impl.PrometheusComputeUsageProvider;
import io.klustr.compute.impl.MockComputeInstanceTypeProvider;
import io.klustr.compute.ingress.impl.DefaultLoadbalancerResolver;
import io.klustr.integrations.portainer.PortainerAuthentication;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.integrations.portainer.PortainerPostgresSharedInitializer;
import io.klustr.integrations.portainer.PortainerPostgresSharedInstanceProvider;
import io.klustr.integrations.prometheus.DataPoint;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.portainer.PortainerComputeLimitsReference;
import io.klustr.schemas.integrations.portainer.PortainerComputeReference;
import io.klustr.schemas.integrations.portainer.PortainerNetworkReference;
import io.klustr.utils.Json;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;


import static org.assertj.core.api.Assertions.*;

public class PortainerTest {

    private static final String url = "https://portainer.dev.klustr.io/api";
    PortainerAuthentication auth = new PortainerAuthentication(url,  "3", "admin", "adminadminadmin");
    PortainerComputeResourceProvider compute = new PortainerComputeResourceProvider(auth, new MockComputeInstanceTypeProvider());
    PrometheusComputeUsageProvider usage = new PrometheusComputeUsageProvider(new PrometheusApi("https://metrics.dev.klustr.io"));
    MockProjectTemplateRepository tempaltes = new MockProjectTemplateRepository();

    PowerDnsApiComputeDnsProvider dns = new PowerDnsApiComputeDnsProvider("http://192.168.0.210:7000", "mykey");
    DefaultLoadbalancerResolver lb = new DefaultLoadbalancerResolver("http://loadbalancer.dev.klustr.io:5555", "admin", "ElUO7Pei");

    @Test
    public void we_can_find_available_port() throws Exception {
        Integer port = this.compute.getAvailablePorts();
        System.out.println(port);
    }

    @Test
    public void we_can_provision_postgre_pool() throws Exception {
        PortainerPostgresSharedInitializer runtime = new PortainerPostgresSharedInitializer(compute, dns, lb, new MockComputeInstanceTypeProvider());
        runtime.init(4);

        PortainerPostgresSharedInstanceProvider postgres
                = new PortainerPostgresSharedInstanceProvider(this.compute);
        postgres.provision("hello_world", "myproject", "password");
    }

    @Test
    public void we_can_generate_a_manifest() throws Exception {
        Optional<ProjectTemplate> template = tempaltes.getTemplate("hello-world");
        String manifest = this.compute.manifest("my_project", "unit_test", "nginxdemos/hello:latest","m1.small", template.get().getManifestFilename(),  Maps.newConcurrentMap());
        System.out.println(manifest);
    }

    @Test
    public void we_can_authenticate() throws Exception {
        String token = auth.getToken();
        assertThat(token).isNotNull();
    }

    @Test
    public void we_can_list_networks() throws Exception {
        List<PortainerNetworkReference> networks = compute.getNetworks();
        assertThat(networks).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(networks));
    }

    @Test
    public void we_can_create_network() throws Exception {
        if (compute.tryGetNetwork("unit_test").isEmpty()) {
            compute.createNetwork("unit_test");
        }
        assertThat(compute.tryGetNetwork("unit_test").isPresent());
    }

    @Test
    public void we_can_provision() throws Exception {
        Optional<ProjectTemplate> template = tempaltes.getTemplate("hello-world");
        Optional<ComputeInstanceType> m1Small = new MockComputeInstanceTypeProvider().getInstanceTypeByName("m1.small");
        this.compute.provision("my_org", "my_project",
                template.get(),
                m1Small.get().getName(), Maps.newConcurrentMap(), Optional.of("registry.dev.klustr.io/rize.fit/lucky-canidae-1d5c99/main:latest"));
    }

    @Test
    public void we_can_get_status() throws Exception {
        List<PortainerComputeReference> nodes = this.compute.listContainersInNetwork("rize.fit");
        System.out.println(Json.toJsonPrettyFormat(nodes));
    }

    @Test
    public void we_can_get_container_limits() {
        this.compute.listContainersInNetwork("rize.fit").forEach(container -> {
            PortainerComputeLimitsReference limits = this.compute.getContainerLimits(container.getId());
            System.out.println(Json.toJsonPrettyFormat(limits));
        });
    }

    @Test
    public void we_can_update_limits() {
        Optional<ComputeInstanceType> m1Small = new MockComputeInstanceTypeProvider().getInstanceTypeByName("m1.small");

        this.compute.listContainersInNetwork("my_org").forEach(container -> {
            PortainerComputeLimitsReference limits = this.compute.getContainerLimits(container.getId());
            System.out.println(Json.toJsonPrettyFormat(limits));
            this.compute.updateContainerLimits(container.getId(), m1Small.get().getName());
        });

        this.compute.listContainersInNetwork("rize.fit").forEach(container -> {
            PortainerComputeLimitsReference limits = this.compute.getContainerLimits(container.getId());
            System.out.println(Json.toJsonPrettyFormat(limits));
            try {
                this.compute.updateContainerLimits(container.getId(), m1Small.get().getName());
            } catch (Exception e) {
                System.out.println("!!! -> " + e.getMessage());
            }
        });
    }

    @Test
    public void we_can_reprovision_to_new_size() {
        Optional<ComputeInstanceType> m1Small = new MockComputeInstanceTypeProvider().getInstanceTypeByName("m1.small");

        this.compute.listContainersInNetwork("rize.fit").forEach(container -> {
            if (container.getLabels() == null || container.getLabels().get("project_id") == null) {
                throw new RuntimeException("Could NOT find project_id metadata on container '" + container.getId() + "'. " + Json.toJson(container));
            }
            String project_id = container.getLabels().get("project_id");
            Optional<ProjectTemplate> template = tempaltes.getTemplate("hello-world");
            this.compute.provision("rize.fit", project_id, template.get(), m1Small.get().getName(), Maps.newConcurrentMap(), Optional.of(container.getImage()));
        });
    }

    @Test
    public void we_can_get_cpu_metrics() {
        List<DataPoint> data = usage.getCpuUsageSecondsTotal("intimate-dormouse-50cfbd", Timeframe.Prior(1).Hours());
        assertThat(data.size()).isGreaterThan(0);
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_get_disk_metrics() {
        List<DataPoint> data = usage.getDiskUsageTotal("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_container_memory_usage_bytes() {
        List<DataPoint> data = usage.getMemoryUsageTotal("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_get_network_recieve_metrics() {
        List<DataPoint> data = usage.getNetworkReceiveBytes("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_get_network_transmit_metrics() {
        List<DataPoint> data = this.usage.getNetworkTransmitBytes("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_get_cpu_throttling_metrics() {
        List<DataPoint> data = usage.getCpuThrottlingPeriodsTotal("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void we_can_get_the_cpu_quota() {
        List<DataPoint> data = usage.getCpuQuotaTotal("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }

    @Test
    public void container_spec_memory_limit_bytes() {
        List<DataPoint> data = usage.getMemoryLimitQuota("gitlab", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(data));
    }


}
