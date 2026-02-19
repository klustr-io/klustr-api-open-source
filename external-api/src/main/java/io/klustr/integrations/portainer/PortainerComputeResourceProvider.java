package io.klustr.integrations.portainer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.klustr.compute.ComputeInstanceTypeProvider;
import io.klustr.compute.ComputeResourceProvider;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.compute.ComputeInstanceType;
import io.klustr.schemas.integrations.portainer.*;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PortainerComputeResourceProvider implements ComputeResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(PortainerComputeResourceProvider.class);
    private static final MediaType mediaType = MediaType.parse("application/json");
    private final OkHttpClient http;


    private String url;
    private PortainerAuthentication auth;
    private String environmentId;

    private final ComputeInstanceTypeProvider metadataProvider;

    public PortainerComputeResourceProvider(PortainerAuthentication auth,
                                            ComputeInstanceTypeProvider metadataProvider) {
        this.url = auth.getUrl();
        this.auth = auth;
        this.environmentId = auth.getEnvironment(); // environmentId; // found under portainer -> environments -> click and get id in url
        this.metadataProvider = metadataProvider;
        this.http = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

    }

    @Override
    public Health health() {
        try {
            String token = this.auth.getToken();
            // good we can auth
            return Health.up().build();
        } catch (Exception ex) {
            return Health.down(ex).withDetail("url", this.url).build();
        }
    }

    public Integer getAvailablePorts() {
        int start = 32768; int stop = 60999;
        Set<Integer> ports_claimed = Sets.newHashSet();
        List<PortainerComputeReference> items = this.listContainers(Maps.newConcurrentMap());
        items.forEach(item -> {
            if (item.getPorts() != null) {
                item.getPorts().forEach(port -> {
                    if (port.getPublicPort() != null) {
                        ports_claimed.add(port.getPublicPort());
                    }
                });
            }
        });

        for (int i=start; i<=stop; i++) {
            if (!ports_claimed.contains(i)) {
                return i;
            }
        }
        throw new RuntimeException("Can not find port");
    }

    public List<PortainerNetworkReference> getNetworks() {
        String token = auth.getToken();

        Request req = new Request.Builder()
                .url(this.url + "/endpoints/" + environmentId + "/docker/networks")
                .header("Authorization", "Bearer " + auth.getToken())
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parseArray(json, PortainerNetworkReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<PortainerNetworkReference> tryGetNetwork(String orgId) {
        return this.getNetworks().stream().filter(x -> {
            return x.getLabels() != null && x.getLabels().containsKey("org_id") &&
                    x.getLabels().get("org_id").equalsIgnoreCase(orgId);
        }).findFirst();
    }

    public void createNetwork(String orgId) {
        String token = auth.getToken();

        PortainerNetworkCreateRequest body = new PortainerNetworkCreateRequest()
                .withName(orgId)
                .withDriver("bridge")
                .withInternal(true)
                .withLabels(new Labels()
                        .withOrgId(orgId)
                        .withSource("klustr.io")
                );

        Request req = new Request.Builder()
                .url(this.url + "/endpoints/" + environmentId + "/docker/networks/create")
                .header("Authorization", "Bearer " + auth.getToken())
                .post(RequestBody.create(Json.toJson(body), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class PortainerStackRequest {
        public String name;
        public String stackFileContent;
        public boolean fromAppTemplate = false;
        public List<NameValuePair> env = Lists.newArrayList();

        public void addEnv(String name, String value) {
            NameValuePair e = new NameValuePair();
            e.name = name;
            e.value = value;
            this.env.add(e);
        }
    }

    public static class NameValuePair {
        public String name;
        public String value;
    }

    public PortainerComputeLimitsReference getContainerLimits(String containerId) {

        Request req = new Request.Builder()
                .url(this.url + "/endpoints/" + environmentId + "/docker/containers/" + containerId + "/json")
                .header("Authorization", "Bearer " + auth.getToken())
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();
            return Json.parse(json, PortainerComputeLimitsReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static class DockerLimits {
        public Integer CpuShares;
        public Long Memory;
        public Integer BlkioWeight;
        public Long CpuPeriod;
        public Long CpuQuota;
        public Long MemoryReservation;
        public Long MemorySwap;
        public Long MemorySwappiness;
        public Long NanoCpus;
        public Boolean OomKillDisable;
        public Long PidsLimit;
    }

    public void updateContainerLimits(String containerId, String machineName) {
        Optional<ComputeInstanceType> machine = this.metadataProvider.getInstanceTypeByName(machineName);
        if (machine.isEmpty()) {
            throw new RuntimeException("Machine type '" + machineName + "' not valid");
        }

        DockerLimits limits = new DockerLimits();
        limits.PidsLimit = machine.get().getPidsLimit();
        limits.NanoCpus = machine.get().getNanoCpus();
        limits.CpuShares = machine.get().getCpuShares();
        limits.Memory = machine.get().getMemoryBytes();
        limits.MemoryReservation = machine.get().getMemoryReservationBytes();
        limits.BlkioWeight = machine.get().getBlkioWeight();
        limits.MemorySwap = machine.get().getMemorySwapBytes();
        // TODO add more limits

        Request req = new Request.Builder()
                .url(this.url + "/endpoints/" + environmentId + "/docker/containers/" + containerId + "/update")
                .header("Authorization", "Bearer " + auth.getToken())
                .post(RequestBody.create(Json.toJson(limits), mediaType))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException("Failed to apply limits: " + res.body().string());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update container " + containerId, ex);
        }
    }

    public List<PortainerComputeReference> listContainersInStack(String stackName) {
        Map<String, Set<String>> filters = Maps.newConcurrentMap();
        filters.put("label", Sets.newHashSet());
        filters.get("label").add("com.docker.compose.project=" + stackName);
        return listContainers(filters);
    }

    public List<PortainerComputeReference> listContainersInNetwork(String orgId) {
        Map<String, Set<String>> filters = Maps.newConcurrentMap();
        filters.put("network", Sets.newHashSet());
        filters.get("network").add(orgId);
        return listContainers(filters);
    }

    public List<PortainerComputeReference> listContainers(Map<String, Set<String>> filters) {
        String url1 = this.url
                + "/endpoints/"
                + environmentId
                + "/docker/containers/json?all=true";

        if (filters != null && !filters.isEmpty()) {
            url1 += "&filters=" + U.encodeURIComponent(Json.toJson(filters));
        }

        Request req = new Request.Builder()
                .url(url1)
                .header("Authorization", "Bearer " + auth.getToken())
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }

            String json = res.body().string();
            return Json.parseArray(json, PortainerComputeReference.class);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PortainerStackReference> listStacks() {
        Request req = new Request.Builder()
                .url(this.url + "/stacks")
                .header("Authorization", "Bearer " + auth.getToken())
                .get()
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new RuntimeException(res.body().string());
            }
            String json = res.body().string();

            return Json.parseArray(json, PortainerStackReference.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<PortainerStackReference> tryGetStack(String orgId, String projectId) {
        return listStacks().stream().filter(x -> {
            String stackName = getStackName(orgId, projectId);
            String refName = x.getName();
            return refName.equalsIgnoreCase(stackName);
        }).findFirst();
    }

    public List<PortainerComputeReference> getComputeNodes(String orgId, String projectId) {
        Optional<PortainerStackReference> stack = this.tryGetStack(orgId, projectId);
        if (stack.isEmpty()) return Lists.newArrayList();

        return this.listContainersInNetwork(orgId).stream().filter(x -> {
            return x.getLabels() != null && x.getLabels().get("project_id") != null && x.getLabels().get("project_id").equalsIgnoreCase(projectId);
        }).toList();
    }

    private static String getStackName(String orgId, String projectId) {
        return  ("x-tenant-" + orgId + "-" + projectId).replace(".","");
    }

    public void provision(String orgId, String projectId,
                          ProjectTemplate template,
                          String machineType,
                          Map<String, String> envVariables,
                          Optional<String> dockerImage) {
        Optional<PortainerNetworkReference> orgNetworkExists = this.tryGetNetwork(orgId);
        if (orgNetworkExists.isEmpty()) {
            this.createNetwork(orgId);
        }

        Optional<ComputeInstanceType> machineInfo = this.metadataProvider.getInstanceTypeByName(machineType);
        if (machineInfo.isEmpty()) {
            throw new RuntimeException("Machine type '" + machineInfo + "' not found");
        }

        envVariables.put("MACHINE_TYPE", machineType);
        envVariables.put("TEMPLATE_ID", template.getId());
        envVariables.put("CPU_LIMIT", String.valueOf(machineInfo.get().getCpuReservation()));
        envVariables.put("MEMORY_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
        envVariables.put("PIDS_LIMIT", String.valueOf(machineInfo.get().getPidsLimit()));
        envVariables.put("CPU_RESERVE", String.valueOf(machineInfo.get().getCpuReservation()));
        envVariables.put("MEMORY_RESERVE", String.valueOf(machineInfo.get().getMemoryReservationBytes()));

        // port bindings if needed and claim them
        if (template.getExposePort() != null && template.getExposePort() == true) {

            AtomicReference<Integer> port = new AtomicReference<>();

            // see if we already provisioned it and already have it
            this.getComputeNodes(orgId, projectId).stream().forEach(node -> {
                if (node.getPorts() == null) return;

                Optional<Port> exists = node.getPorts().stream().filter(p -> {
                    return p.getPublicPort() != null;
                }).findFirst();

                if (exists.isPresent()) {
                    port.set(exists.get().getPublicPort());
                    return;
                }
            });

            if (port.get() == null) {
                port.set(this.getAvailablePorts());
            }

            envVariables.put("EXPOSE_PORT", String.valueOf(port));
        }

        PortainerStackRequest body = new PortainerStackRequest();
        body.name = getStackName(orgId, projectId);
        body.stackFileContent = this.manifest(orgId, projectId, dockerImage.orElse(null), machineType, template.getManifestFilename(), envVariables);
        body.addEnv("COMPOSE_PROJECT_NAME", orgId);

        // machine limits
        body.addEnv("MACHINE_TYPE", machineType);
        body.addEnv("TEMPLATE_ID", template.getId());
        body.addEnv("CPU_LIMIT", String.valueOf(machineInfo.get().getCpuReservation()));
        body.addEnv("MEMORY_LIMIT", String.valueOf(machineInfo.get().getMemoryBytes()));
        body.addEnv("PIDS_LIMIT", String.valueOf(machineInfo.get().getPidsLimit()));
        body.addEnv("CPU_RESERVE", String.valueOf(machineInfo.get().getCpuReservation()));
        body.addEnv("MEMORY_RESERVE", String.valueOf(machineInfo.get().getMemoryReservationBytes()));

        Optional<PortainerStackReference> match = this.tryGetStack(orgId, projectId);
        String json = Json.toJson(body);

        deploy(match.orElse(null), json);

        // now lets enforce harder limits
        this.getComputeNodes(orgId, projectId).forEach(node -> {
            this.updateContainerLimits(node.getId(), machineType);
        });
    }

    private static class DockerExecBody {
        public boolean AttachStdout = true;
        public boolean AttachStderr = true;
        public boolean AttachStdin = false;
        public boolean Tty = false;
        public List<String> Cmd = Lists.newArrayList();
    }

    public void executeCommand(List<String> cmd, PortainerComputeReference compute) {
        try {
            DockerExecBody b = new DockerExecBody();
            b.Cmd = cmd;
            String url = this.url + "/endpoints/" + environmentId + "/docker/containers/" + compute.getId() + "/exec";

            MediaType mediaType = MediaType.parse("application/json");
            Request createExecReq = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + auth.getToken())
                    .post(RequestBody.create(Json.toJson(b), mediaType))
                    .build();

            String execId;
            try (Response res = http.newCall(createExecReq).execute()) {
                if (!res.isSuccessful()) {
                    throw new RuntimeException(res.body().string());
                }

                String body = res.body().string();
                execId = ((ObjectNode)Json.toJsonNode(body)).get("Id").textValue();
            }

            // --------------------------------------------------------
            // 2. START EXEC: push SQL script over stdin
            // --------------------------------------------------------

                // Now start the exec session
            ObjectNode jsonNode = (ObjectNode)Json.toJsonNode("{}");
            jsonNode.put("Detach", false);
            jsonNode.put("Tty", false);

            url = this.url + "/endpoints/" + environmentId + "/docker/exec/" + execId + "/start";
            Request startReq = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + auth.getToken())
                    .post(RequestBody.create(Json.toJson(jsonNode), mediaType))
                    .build();

            try (Response startRes = http.newCall(startReq).execute()) {
                if (!startRes.isSuccessful()) {
                    throw new RuntimeException(startRes.body().string());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deploy(PortainerStackReference stackRef, String json) {
        if (stackRef == null) {
            Request req = new Request.Builder()
                    .url(this.url + "/stacks/create/standalone/string?endpointId=" + environmentId)
                    .header("Authorization", "Bearer " + auth.getToken())
                    .post(RequestBody.create(json, mediaType))
                    .build();

            try (Response res = http.newCall(req).execute()) {
                if (!res.isSuccessful()) {
                    throw new RuntimeException(res.body().string());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Request req = new Request.Builder()
                    .url(this.url + "/stacks/" + stackRef.getId() + "?endpointId=" + environmentId)
                    .header("Authorization", "Bearer " + auth.getToken())
                    .put(RequestBody.create(json, mediaType))
                    .build();

            try (Response res = http.newCall(req).execute()) {
                if (!res.isSuccessful()) {
                    throw new RuntimeException(res.body().string());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    public String manifest(String orgId, String projectId, String image, String machineType, String manifestFile, Map<String, String> envVariables) {

        String source = U.getResourceAsString("templates/" + manifestFile, this);

        envVariables = envVariables != null ? envVariables : new HashMap<>();

        envVariables.put("ORG", orgId);
        envVariables.put("PROJECT", projectId);
        envVariables.put("IMAGE", StringUtils.isBlank(image) ? "****" : image);
        envVariables.put("PERSISTENT_VOLUME_ID", U.md5(projectId));
        envVariables.put("DNS_HOST", "192.168.0.210");
        envVariables.put("SERVER_DOMAIN", projectId + ".dev.klustr.io");
        envVariables.put("MACHINE_TYPE", machineType);
        envVariables.put("ROOT_SERVER_URL", "https://" + projectId + ".dev.klustr.io");

        StringSubstitutor substitutor = new StringSubstitutor(envVariables, "${", "}");
        return substitutor.replace(source);
    }
}
