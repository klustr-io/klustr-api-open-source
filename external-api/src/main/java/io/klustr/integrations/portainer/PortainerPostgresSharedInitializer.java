package io.klustr.integrations.portainer;

import com.google.common.collect.Lists;
import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRecord;
import io.klustr.schemas.integrations.compute.ComputeDnsZoneRequest;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.portainer.PortainerStackReference;
import io.klustr.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PortainerPostgresSharedInitializer {

    private final ComputeInstanceTypeProvider metadataProvider;
    private final PortainerComputeResourceProvider provision;
    private final ComputeDnsProvider dns;
    private final ComputeLoadBalancerResolver lb;


    public PortainerPostgresSharedInitializer(PortainerComputeResourceProvider provision,
                                                   ComputeDnsProvider dns,
                                                   ComputeLoadBalancerResolver lb,
                                                   ComputeInstanceTypeProvider metadataProvider) {
        this.provision = provision;
        this.metadataProvider = metadataProvider;
        this.dns = dns;
        this.lb = lb;
    }

    public void init(int count) {
        // not under an org...
        Optional<ComputeInstanceType> machineInfo = this.metadataProvider.getInstanceTypeByName("m1.small");
        if (machineInfo.isEmpty()) {
            throw new RuntimeException("Machine type '" + machineInfo + "' not found");
        }

        String orgId = "klustr.io";
        String projectId = "postgres-pool";
        String imageSize = "m1.small";

        for (int i=0; i<count; i++) {

            Map<String, String> envVariables = new HashMap<>();
            String nodeID = StringUtils.leftPad(String.valueOf(i), 3, "0");
            envVariables.put("NODE_ID", nodeID);

            String instanceName = "postgres-pool-" + nodeID;
            String stackName = "klustr-postgres-pool-" + nodeID;

            PortainerComputeResourceProvider.PortainerStackRequest body = new PortainerComputeResourceProvider.PortainerStackRequest();
            body.name = stackName;

            envVariables.put("MACHINE_TYPE", imageSize);
            envVariables.put("CPU_LIMIT", String.valueOf(machineInfo.get().getNanoCpus()));
            envVariables.put("MEMORY_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
            envVariables.put("PIDS_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
            envVariables.put("CPU_RESERVE", String.valueOf(machineInfo.get().getMemoryBytes()));
            envVariables.put("MEMORY_RESERVE", String.valueOf(machineInfo.get().getMemoryBytes()));

            body.stackFileContent = provision.manifest(orgId,
                    projectId,
                    null,
                    imageSize, "postgres.shared.docker.compose.yaml", envVariables);
            body.addEnv("COMPOSE_PROJECT_NAME", orgId);

            // machine limits
            body.addEnv("MACHINE_TYPE", imageSize);
            body.addEnv("CPU_LIMIT", String.valueOf(machineInfo.get().getNanoCpus()));
            body.addEnv("MEMORY_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
            body.addEnv("PIDS_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
            body.addEnv("CPU_RESERVE", String.valueOf(machineInfo.get().getMemoryBytes()));
            body.addEnv("MEMORY_RESERVE", String.valueOf(machineInfo.get().getMemoryBytes()));

            Optional<PortainerStackReference> match = provision.listStacks().stream().filter(x -> {
                String refName = x.getName();
                return refName.equalsIgnoreCase(stackName);
            }).findFirst();

            String json = Json.toJson(body);

            provision.deploy(match.orElse(null), json);

            provision.getComputeNodes(orgId, projectId).forEach(node -> {
                provision.updateContainerLimits(node.getId(), imageSize);
            });

            ComputeLoadBalancerProvider lb = this.lb.resolve(projectId, "klustr.io");
            Set<String> ips = lb.getPublicIPV4Addresses();

            dns.createRecords("dev.klustr.io.", Lists.newArrayList(
                    new ComputeDnsZoneRequest()
                            .withName(instanceName + ".dev.klustr.io.")
                            .withType("A")
                            .withTtl(60)
                            .withChangetype("REPLACE")
                            .withRecords(Lists.newArrayList(
                                    new ComputeDnsZoneRecord().withContent(ips.stream().findFirst().get()).withDisabled(false)
                            ))
            ));
        }
    }
}
