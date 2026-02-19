
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ComputeInstanceType
 * <p>
 * Defines a reusable 'machine type' configuration — a preset that describes how much CPU, memory, and other system resources a container is allowed to use. Think of this like AWS EC2 instance classes (e.g. 'm1.small', 'c1.medium'). Each type defines performance characteristics and resource boundaries for workloads managed by Portainer or Docker Compose.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "memoryBytes",
    "memorySwapBytes",
    "memoryReservationBytes",
    "nanoCpus",
    "cpuReservation",
    "cpuShares",
    "pidsLimit",
    "blkioWeight",
    "ioBandwidthLimit",
    "networkBandwidthLimit"
})
@Generated("jsonschema2pojo")
public class ComputeInstanceType {

    /**
     * A unique identifier for this machine type (e.g., 'm1.small', 'r1.large'). This name is used as a key when provisioning containers or stacks. Convention: 'm' = general purpose, 'c' = compute optimized, 'r' = memory optimized.
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A unique identifier for this machine type (e.g., 'm1.small', 'r1.large'). This name is used as a key when provisioning containers or stacks. Convention: 'm' = general purpose, 'c' = compute optimized, 'r' = memory optimized.")
    private String name;
    /**
     * A short human-readable summary describing the purpose or target workload for this machine type — for example, 'Balanced general-purpose instance suitable for most web services.'
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("A short human-readable summary describing the purpose or target workload for this machine type \u2014 for example, 'Balanced general-purpose instance suitable for most web services.'")
    private String description;
    /**
     * The **maximum memory** (RAM) this container can use, expressed in bytes. This is a *hard limit* enforced by Docker's cgroup system — once reached, the container can be killed by the kernel to protect the host. Example: 1073741824 (1 GiB).
     * (Required)
     * 
     */
    @JsonProperty("memoryBytes")
    @JsonPropertyDescription("The **maximum memory** (RAM) this container can use, expressed in bytes. This is a *hard limit* enforced by Docker's cgroup system \u2014 once reached, the container can be killed by the kernel to protect the host. Example: 1073741824 (1 GiB).")
    private Long memoryBytes;
    /**
     * The **combined memory + swap limit** in bytes. Must be ≥ memoryBytes, or Docker will throw an error. Typical best practice: set to 2× memoryBytes to allow equal swap space.
     * 
     */
    @JsonProperty("memorySwapBytes")
    @JsonPropertyDescription("The **combined memory + swap limit** in bytes. Must be \u2265 memoryBytes, or Docker will throw an error. Typical best practice: set to 2\u00d7 memoryBytes to allow equal swap space.")
    private Long memorySwapBytes;
    /**
     * The **minimum guaranteed memory** that this container will be given if resources are scarce. Unlike `memoryBytes`, this is a *soft reservation*, meaning the container can use more if available, but the system tries to keep at least this much free. Recommended: ~50% of `memoryBytes`.
     * 
     */
    @JsonProperty("memoryReservationBytes")
    @JsonPropertyDescription("The **minimum guaranteed memory** that this container will be given if resources are scarce. Unlike `memoryBytes`, this is a *soft reservation*, meaning the container can use more if available, but the system tries to keep at least this much free. Recommended: ~50% of `memoryBytes`.")
    private Long memoryReservationBytes;
    /**
     * The total CPU allocation for this container, expressed in nanoseconds of CPU time per second. 1e9 = one full CPU core. Example: 2000000000 = 2 CPUs. This acts as a **hard limit**, meaning the container cannot use more CPU than assigned even if others are idle.
     * (Required)
     * 
     */
    @JsonProperty("nanoCpus")
    @JsonPropertyDescription("The total CPU allocation for this container, expressed in nanoseconds of CPU time per second. 1e9 = one full CPU core. Example: 2000000000 = 2 CPUs. This acts as a **hard limit**, meaning the container cannot use more CPU than assigned even if others are idle.")
    private Long nanoCpus;
    /**
     * The **minimum guaranteed CPU fraction** for scheduling, expressed as a decimal number of cores (e.g., 0.25 = 25% of one core). This is a soft hint for the scheduler to allocate fair shares. Example: a container with 0.25 reservation and 1.0 limit will always get at least 25% CPU but can burst up to 100%.
     * 
     */
    @JsonProperty("cpuReservation")
    @JsonPropertyDescription("The **minimum guaranteed CPU fraction** for scheduling, expressed as a decimal number of cores (e.g., 0.25 = 25% of one core). This is a soft hint for the scheduler to allocate fair shares. Example: a container with 0.25 reservation and 1.0 limit will always get at least 25% CPU but can burst up to 100%.")
    private Double cpuReservation;
    /**
     * Relative weight used when multiple containers compete for CPU time. Higher values give proportionally more CPU cycles. Default is 1024. Example: if one container has 1024 and another has 512, the first gets twice as much CPU under contention.
     * 
     */
    @JsonProperty("cpuShares")
    @JsonPropertyDescription("Relative weight used when multiple containers compete for CPU time. Higher values give proportionally more CPU cycles. Default is 1024. Example: if one container has 1024 and another has 512, the first gets twice as much CPU under contention.")
    private Integer cpuShares;
    /**
     * The maximum number of processes or threads a container can spawn. This prevents runaway forks or thread exhaustion from crashing the host. Example: 512. Best practice: 256–1024 for most applications, depending on concurrency needs.
     * 
     */
    @JsonProperty("pidsLimit")
    @JsonPropertyDescription("The maximum number of processes or threads a container can spawn. This prevents runaway forks or thread exhaustion from crashing the host. Example: 512. Best practice: 256\u20131024 for most applications, depending on concurrency needs.")
    private Long pidsLimit;
    /**
     * Optional: relative weight (10–1000) controlling this container's disk I/O priority compared to others. Higher means more bandwidth when multiple containers share the same disk. Defaults to 500.
     * 
     */
    @JsonProperty("blkioWeight")
    @JsonPropertyDescription("Optional: relative weight (10\u20131000) controlling this container's disk I/O priority compared to others. Higher means more bandwidth when multiple containers share the same disk. Defaults to 500.")
    private Integer blkioWeight;
    /**
     * Optional: maximum allowed I/O bandwidth in bytes per second. Example: 104857600 = 100 MB/s. Use this to prevent one container from monopolizing disk or network throughput.
     * 
     */
    @JsonProperty("ioBandwidthLimit")
    @JsonPropertyDescription("Optional: maximum allowed I/O bandwidth in bytes per second. Example: 104857600 = 100 MB/s. Use this to prevent one container from monopolizing disk or network throughput.")
    private Long ioBandwidthLimit;
    /**
     * Optional: suggested cap on outbound network bandwidth in bytes per second. This can be implemented via Docker network QoS or sidecar rate-limiting. Useful for tiered plans or noisy-neighbor isolation.
     * 
     */
    @JsonProperty("networkBandwidthLimit")
    @JsonPropertyDescription("Optional: suggested cap on outbound network bandwidth in bytes per second. This can be implemented via Docker network QoS or sidecar rate-limiting. Useful for tiered plans or noisy-neighbor isolation.")
    private Long networkBandwidthLimit;

    /**
     * A unique identifier for this machine type (e.g., 'm1.small', 'r1.large'). This name is used as a key when provisioning containers or stacks. Convention: 'm' = general purpose, 'c' = compute optimized, 'r' = memory optimized.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * A unique identifier for this machine type (e.g., 'm1.small', 'r1.large'). This name is used as a key when provisioning containers or stacks. Convention: 'm' = general purpose, 'c' = compute optimized, 'r' = memory optimized.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ComputeInstanceType withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * A short human-readable summary describing the purpose or target workload for this machine type — for example, 'Balanced general-purpose instance suitable for most web services.'
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * A short human-readable summary describing the purpose or target workload for this machine type — for example, 'Balanced general-purpose instance suitable for most web services.'
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ComputeInstanceType withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The **maximum memory** (RAM) this container can use, expressed in bytes. This is a *hard limit* enforced by Docker's cgroup system — once reached, the container can be killed by the kernel to protect the host. Example: 1073741824 (1 GiB).
     * (Required)
     * 
     */
    @JsonProperty("memoryBytes")
    public Long getMemoryBytes() {
        return memoryBytes;
    }

    /**
     * The **maximum memory** (RAM) this container can use, expressed in bytes. This is a *hard limit* enforced by Docker's cgroup system — once reached, the container can be killed by the kernel to protect the host. Example: 1073741824 (1 GiB).
     * (Required)
     * 
     */
    @JsonProperty("memoryBytes")
    public void setMemoryBytes(Long memoryBytes) {
        this.memoryBytes = memoryBytes;
    }

    public ComputeInstanceType withMemoryBytes(Long memoryBytes) {
        this.memoryBytes = memoryBytes;
        return this;
    }

    /**
     * The **combined memory + swap limit** in bytes. Must be ≥ memoryBytes, or Docker will throw an error. Typical best practice: set to 2× memoryBytes to allow equal swap space.
     * 
     */
    @JsonProperty("memorySwapBytes")
    public Long getMemorySwapBytes() {
        return memorySwapBytes;
    }

    /**
     * The **combined memory + swap limit** in bytes. Must be ≥ memoryBytes, or Docker will throw an error. Typical best practice: set to 2× memoryBytes to allow equal swap space.
     * 
     */
    @JsonProperty("memorySwapBytes")
    public void setMemorySwapBytes(Long memorySwapBytes) {
        this.memorySwapBytes = memorySwapBytes;
    }

    public ComputeInstanceType withMemorySwapBytes(Long memorySwapBytes) {
        this.memorySwapBytes = memorySwapBytes;
        return this;
    }

    /**
     * The **minimum guaranteed memory** that this container will be given if resources are scarce. Unlike `memoryBytes`, this is a *soft reservation*, meaning the container can use more if available, but the system tries to keep at least this much free. Recommended: ~50% of `memoryBytes`.
     * 
     */
    @JsonProperty("memoryReservationBytes")
    public Long getMemoryReservationBytes() {
        return memoryReservationBytes;
    }

    /**
     * The **minimum guaranteed memory** that this container will be given if resources are scarce. Unlike `memoryBytes`, this is a *soft reservation*, meaning the container can use more if available, but the system tries to keep at least this much free. Recommended: ~50% of `memoryBytes`.
     * 
     */
    @JsonProperty("memoryReservationBytes")
    public void setMemoryReservationBytes(Long memoryReservationBytes) {
        this.memoryReservationBytes = memoryReservationBytes;
    }

    public ComputeInstanceType withMemoryReservationBytes(Long memoryReservationBytes) {
        this.memoryReservationBytes = memoryReservationBytes;
        return this;
    }

    /**
     * The total CPU allocation for this container, expressed in nanoseconds of CPU time per second. 1e9 = one full CPU core. Example: 2000000000 = 2 CPUs. This acts as a **hard limit**, meaning the container cannot use more CPU than assigned even if others are idle.
     * (Required)
     * 
     */
    @JsonProperty("nanoCpus")
    public Long getNanoCpus() {
        return nanoCpus;
    }

    /**
     * The total CPU allocation for this container, expressed in nanoseconds of CPU time per second. 1e9 = one full CPU core. Example: 2000000000 = 2 CPUs. This acts as a **hard limit**, meaning the container cannot use more CPU than assigned even if others are idle.
     * (Required)
     * 
     */
    @JsonProperty("nanoCpus")
    public void setNanoCpus(Long nanoCpus) {
        this.nanoCpus = nanoCpus;
    }

    public ComputeInstanceType withNanoCpus(Long nanoCpus) {
        this.nanoCpus = nanoCpus;
        return this;
    }

    /**
     * The **minimum guaranteed CPU fraction** for scheduling, expressed as a decimal number of cores (e.g., 0.25 = 25% of one core). This is a soft hint for the scheduler to allocate fair shares. Example: a container with 0.25 reservation and 1.0 limit will always get at least 25% CPU but can burst up to 100%.
     * 
     */
    @JsonProperty("cpuReservation")
    public Double getCpuReservation() {
        return cpuReservation;
    }

    /**
     * The **minimum guaranteed CPU fraction** for scheduling, expressed as a decimal number of cores (e.g., 0.25 = 25% of one core). This is a soft hint for the scheduler to allocate fair shares. Example: a container with 0.25 reservation and 1.0 limit will always get at least 25% CPU but can burst up to 100%.
     * 
     */
    @JsonProperty("cpuReservation")
    public void setCpuReservation(Double cpuReservation) {
        this.cpuReservation = cpuReservation;
    }

    public ComputeInstanceType withCpuReservation(Double cpuReservation) {
        this.cpuReservation = cpuReservation;
        return this;
    }

    /**
     * Relative weight used when multiple containers compete for CPU time. Higher values give proportionally more CPU cycles. Default is 1024. Example: if one container has 1024 and another has 512, the first gets twice as much CPU under contention.
     * 
     */
    @JsonProperty("cpuShares")
    public Integer getCpuShares() {
        return cpuShares;
    }

    /**
     * Relative weight used when multiple containers compete for CPU time. Higher values give proportionally more CPU cycles. Default is 1024. Example: if one container has 1024 and another has 512, the first gets twice as much CPU under contention.
     * 
     */
    @JsonProperty("cpuShares")
    public void setCpuShares(Integer cpuShares) {
        this.cpuShares = cpuShares;
    }

    public ComputeInstanceType withCpuShares(Integer cpuShares) {
        this.cpuShares = cpuShares;
        return this;
    }

    /**
     * The maximum number of processes or threads a container can spawn. This prevents runaway forks or thread exhaustion from crashing the host. Example: 512. Best practice: 256–1024 for most applications, depending on concurrency needs.
     * 
     */
    @JsonProperty("pidsLimit")
    public Long getPidsLimit() {
        return pidsLimit;
    }

    /**
     * The maximum number of processes or threads a container can spawn. This prevents runaway forks or thread exhaustion from crashing the host. Example: 512. Best practice: 256–1024 for most applications, depending on concurrency needs.
     * 
     */
    @JsonProperty("pidsLimit")
    public void setPidsLimit(Long pidsLimit) {
        this.pidsLimit = pidsLimit;
    }

    public ComputeInstanceType withPidsLimit(Long pidsLimit) {
        this.pidsLimit = pidsLimit;
        return this;
    }

    /**
     * Optional: relative weight (10–1000) controlling this container's disk I/O priority compared to others. Higher means more bandwidth when multiple containers share the same disk. Defaults to 500.
     * 
     */
    @JsonProperty("blkioWeight")
    public Integer getBlkioWeight() {
        return blkioWeight;
    }

    /**
     * Optional: relative weight (10–1000) controlling this container's disk I/O priority compared to others. Higher means more bandwidth when multiple containers share the same disk. Defaults to 500.
     * 
     */
    @JsonProperty("blkioWeight")
    public void setBlkioWeight(Integer blkioWeight) {
        this.blkioWeight = blkioWeight;
    }

    public ComputeInstanceType withBlkioWeight(Integer blkioWeight) {
        this.blkioWeight = blkioWeight;
        return this;
    }

    /**
     * Optional: maximum allowed I/O bandwidth in bytes per second. Example: 104857600 = 100 MB/s. Use this to prevent one container from monopolizing disk or network throughput.
     * 
     */
    @JsonProperty("ioBandwidthLimit")
    public Long getIoBandwidthLimit() {
        return ioBandwidthLimit;
    }

    /**
     * Optional: maximum allowed I/O bandwidth in bytes per second. Example: 104857600 = 100 MB/s. Use this to prevent one container from monopolizing disk or network throughput.
     * 
     */
    @JsonProperty("ioBandwidthLimit")
    public void setIoBandwidthLimit(Long ioBandwidthLimit) {
        this.ioBandwidthLimit = ioBandwidthLimit;
    }

    public ComputeInstanceType withIoBandwidthLimit(Long ioBandwidthLimit) {
        this.ioBandwidthLimit = ioBandwidthLimit;
        return this;
    }

    /**
     * Optional: suggested cap on outbound network bandwidth in bytes per second. This can be implemented via Docker network QoS or sidecar rate-limiting. Useful for tiered plans or noisy-neighbor isolation.
     * 
     */
    @JsonProperty("networkBandwidthLimit")
    public Long getNetworkBandwidthLimit() {
        return networkBandwidthLimit;
    }

    /**
     * Optional: suggested cap on outbound network bandwidth in bytes per second. This can be implemented via Docker network QoS or sidecar rate-limiting. Useful for tiered plans or noisy-neighbor isolation.
     * 
     */
    @JsonProperty("networkBandwidthLimit")
    public void setNetworkBandwidthLimit(Long networkBandwidthLimit) {
        this.networkBandwidthLimit = networkBandwidthLimit;
    }

    public ComputeInstanceType withNetworkBandwidthLimit(Long networkBandwidthLimit) {
        this.networkBandwidthLimit = networkBandwidthLimit;
        return this;
    }

}
