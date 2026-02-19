
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Runtime configuration options applied when the container was created, including CPU and memory restrictions.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "NanoCpus",
    "CpuQuota",
    "CpuPeriod",
    "CpuShares",
    "CpusetCpus",
    "Memory",
    "MemoryReservation",
    "MemorySwap",
    "PidsLimit",
    "BlkioWeight",
    "IOMaximumIOps",
    "IOMaximumBandwidth",
    "ShmSize",
    "RestartPolicy",
    "Privileged",
    "NetworkMode"
})
@Generated("jsonschema2pojo")
public class HostConfig {

    /**
     * CPU allocation in nanoseconds per second. 1e9 represents one full CPU core. For example, 2e9 means two CPUs allocated. If 0, the container can use all host CPUs.
     * 
     */
    @JsonProperty("NanoCpus")
    @JsonPropertyDescription("CPU allocation in nanoseconds per second. 1e9 represents one full CPU core. For example, 2e9 means two CPUs allocated. If 0, the container can use all host CPUs.")
    private Integer nanoCpus;
    /**
     * CFS (Completely Fair Scheduler) CPU quota in microseconds for the container. Used with CpuPeriod to define CPU time limits. 0 means no enforced limit.
     * 
     */
    @JsonProperty("CpuQuota")
    @JsonPropertyDescription("CFS (Completely Fair Scheduler) CPU quota in microseconds for the container. Used with CpuPeriod to define CPU time limits. 0 means no enforced limit.")
    private Integer cpuQuota;
    /**
     * CFS scheduling period in microseconds for the container’s CPU quota. Typically 100000. Together with CpuQuota, defines CPU cap = CpuQuota / CpuPeriod cores.
     * 
     */
    @JsonProperty("CpuPeriod")
    @JsonPropertyDescription("CFS scheduling period in microseconds for the container\u2019s CPU quota. Typically 100000. Together with CpuQuota, defines CPU cap = CpuQuota / CpuPeriod cores.")
    private Integer cpuPeriod;
    /**
     * Relative CPU weight used by the scheduler when multiple containers share CPU. Default is 1024. Not a hard limit, but a prioritization hint.
     * 
     */
    @JsonProperty("CpuShares")
    @JsonPropertyDescription("Relative CPU weight used by the scheduler when multiple containers share CPU. Default is 1024. Not a hard limit, but a prioritization hint.")
    private Integer cpuShares;
    /**
     * Comma-separated list of specific CPU cores (e.g., '0,1' or '1-3') assigned to the container. Empty means no core pinning.
     * 
     */
    @JsonProperty("CpusetCpus")
    @JsonPropertyDescription("Comma-separated list of specific CPU cores (e.g., '0,1' or '1-3') assigned to the container. Empty means no core pinning.")
    private String cpusetCpus;
    /**
     * Maximum memory (in bytes) available to the container. A value of 0 indicates no memory limit (can use all host RAM).
     * 
     */
    @JsonProperty("Memory")
    @JsonPropertyDescription("Maximum memory (in bytes) available to the container. A value of 0 indicates no memory limit (can use all host RAM).")
    private Integer memory;
    /**
     * Soft memory limit (in bytes). The container is guaranteed this amount if available, but can use more if free memory exists.
     * 
     */
    @JsonProperty("MemoryReservation")
    @JsonPropertyDescription("Soft memory limit (in bytes). The container is guaranteed this amount if available, but can use more if free memory exists.")
    private Integer memoryReservation;
    /**
     * Total memory plus swap limit (in bytes). A value of 0 disables swap limit; -1 means unlimited swap. Often set to twice the Memory value.
     * 
     */
    @JsonProperty("MemorySwap")
    @JsonPropertyDescription("Total memory plus swap limit (in bytes). A value of 0 disables swap limit; -1 means unlimited swap. Often set to twice the Memory value.")
    private Integer memorySwap;
    /**
     * Maximum number of processes (PIDs) allowed inside the container. null or 0 means unlimited.
     * 
     */
    @JsonProperty("PidsLimit")
    @JsonPropertyDescription("Maximum number of processes (PIDs) allowed inside the container. null or 0 means unlimited.")
    private Integer pidsLimit;
    /**
     * Block IO relative weight (between 10 and 1000). Controls disk I/O priority among containers. Default 0 means no explicit weighting.
     * 
     */
    @JsonProperty("BlkioWeight")
    @JsonPropertyDescription("Block IO relative weight (between 10 and 1000). Controls disk I/O priority among containers. Default 0 means no explicit weighting.")
    private Integer blkioWeight;
    /**
     * Maximum I/O operations per second (IOPS) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumIOps")
    @JsonPropertyDescription("Maximum I/O operations per second (IOPS) allowed for the container\u2019s block devices. 0 disables enforcement.")
    private Integer iOMaximumIOps;
    /**
     * Maximum I/O throughput (in bytes per second) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumBandwidth")
    @JsonPropertyDescription("Maximum I/O throughput (in bytes per second) allowed for the container\u2019s block devices. 0 disables enforcement.")
    private Integer iOMaximumBandwidth;
    /**
     * Size (in bytes) of the /dev/shm shared memory segment available to the container. Default is 67,108,864 bytes (64MB).
     * 
     */
    @JsonProperty("ShmSize")
    @JsonPropertyDescription("Size (in bytes) of the /dev/shm shared memory segment available to the container. Default is 67,108,864 bytes (64MB).")
    private Integer shmSize;
    /**
     * Defines the container restart behavior if it exits or fails.
     * 
     */
    @JsonProperty("RestartPolicy")
    @JsonPropertyDescription("Defines the container restart behavior if it exits or fails.")
    private RestartPolicy restartPolicy;
    /**
     * Indicates if the container is running in privileged mode (full access to host devices and kernel features).
     * 
     */
    @JsonProperty("Privileged")
    @JsonPropertyDescription("Indicates if the container is running in privileged mode (full access to host devices and kernel features).")
    private Boolean privileged;
    /**
     * Specifies the container’s networking mode (e.g., 'bridge', 'host', 'none', 'container:<id>', or 'ingress' for swarm).
     * 
     */
    @JsonProperty("NetworkMode")
    @JsonPropertyDescription("Specifies the container\u2019s networking mode (e.g., 'bridge', 'host', 'none', 'container:<id>', or 'ingress' for swarm).")
    private String networkMode;

    /**
     * CPU allocation in nanoseconds per second. 1e9 represents one full CPU core. For example, 2e9 means two CPUs allocated. If 0, the container can use all host CPUs.
     * 
     */
    @JsonProperty("NanoCpus")
    public Integer getNanoCpus() {
        return nanoCpus;
    }

    /**
     * CPU allocation in nanoseconds per second. 1e9 represents one full CPU core. For example, 2e9 means two CPUs allocated. If 0, the container can use all host CPUs.
     * 
     */
    @JsonProperty("NanoCpus")
    public void setNanoCpus(Integer nanoCpus) {
        this.nanoCpus = nanoCpus;
    }

    public HostConfig withNanoCpus(Integer nanoCpus) {
        this.nanoCpus = nanoCpus;
        return this;
    }

    /**
     * CFS (Completely Fair Scheduler) CPU quota in microseconds for the container. Used with CpuPeriod to define CPU time limits. 0 means no enforced limit.
     * 
     */
    @JsonProperty("CpuQuota")
    public Integer getCpuQuota() {
        return cpuQuota;
    }

    /**
     * CFS (Completely Fair Scheduler) CPU quota in microseconds for the container. Used with CpuPeriod to define CPU time limits. 0 means no enforced limit.
     * 
     */
    @JsonProperty("CpuQuota")
    public void setCpuQuota(Integer cpuQuota) {
        this.cpuQuota = cpuQuota;
    }

    public HostConfig withCpuQuota(Integer cpuQuota) {
        this.cpuQuota = cpuQuota;
        return this;
    }

    /**
     * CFS scheduling period in microseconds for the container’s CPU quota. Typically 100000. Together with CpuQuota, defines CPU cap = CpuQuota / CpuPeriod cores.
     * 
     */
    @JsonProperty("CpuPeriod")
    public Integer getCpuPeriod() {
        return cpuPeriod;
    }

    /**
     * CFS scheduling period in microseconds for the container’s CPU quota. Typically 100000. Together with CpuQuota, defines CPU cap = CpuQuota / CpuPeriod cores.
     * 
     */
    @JsonProperty("CpuPeriod")
    public void setCpuPeriod(Integer cpuPeriod) {
        this.cpuPeriod = cpuPeriod;
    }

    public HostConfig withCpuPeriod(Integer cpuPeriod) {
        this.cpuPeriod = cpuPeriod;
        return this;
    }

    /**
     * Relative CPU weight used by the scheduler when multiple containers share CPU. Default is 1024. Not a hard limit, but a prioritization hint.
     * 
     */
    @JsonProperty("CpuShares")
    public Integer getCpuShares() {
        return cpuShares;
    }

    /**
     * Relative CPU weight used by the scheduler when multiple containers share CPU. Default is 1024. Not a hard limit, but a prioritization hint.
     * 
     */
    @JsonProperty("CpuShares")
    public void setCpuShares(Integer cpuShares) {
        this.cpuShares = cpuShares;
    }

    public HostConfig withCpuShares(Integer cpuShares) {
        this.cpuShares = cpuShares;
        return this;
    }

    /**
     * Comma-separated list of specific CPU cores (e.g., '0,1' or '1-3') assigned to the container. Empty means no core pinning.
     * 
     */
    @JsonProperty("CpusetCpus")
    public String getCpusetCpus() {
        return cpusetCpus;
    }

    /**
     * Comma-separated list of specific CPU cores (e.g., '0,1' or '1-3') assigned to the container. Empty means no core pinning.
     * 
     */
    @JsonProperty("CpusetCpus")
    public void setCpusetCpus(String cpusetCpus) {
        this.cpusetCpus = cpusetCpus;
    }

    public HostConfig withCpusetCpus(String cpusetCpus) {
        this.cpusetCpus = cpusetCpus;
        return this;
    }

    /**
     * Maximum memory (in bytes) available to the container. A value of 0 indicates no memory limit (can use all host RAM).
     * 
     */
    @JsonProperty("Memory")
    public Integer getMemory() {
        return memory;
    }

    /**
     * Maximum memory (in bytes) available to the container. A value of 0 indicates no memory limit (can use all host RAM).
     * 
     */
    @JsonProperty("Memory")
    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public HostConfig withMemory(Integer memory) {
        this.memory = memory;
        return this;
    }

    /**
     * Soft memory limit (in bytes). The container is guaranteed this amount if available, but can use more if free memory exists.
     * 
     */
    @JsonProperty("MemoryReservation")
    public Integer getMemoryReservation() {
        return memoryReservation;
    }

    /**
     * Soft memory limit (in bytes). The container is guaranteed this amount if available, but can use more if free memory exists.
     * 
     */
    @JsonProperty("MemoryReservation")
    public void setMemoryReservation(Integer memoryReservation) {
        this.memoryReservation = memoryReservation;
    }

    public HostConfig withMemoryReservation(Integer memoryReservation) {
        this.memoryReservation = memoryReservation;
        return this;
    }

    /**
     * Total memory plus swap limit (in bytes). A value of 0 disables swap limit; -1 means unlimited swap. Often set to twice the Memory value.
     * 
     */
    @JsonProperty("MemorySwap")
    public Integer getMemorySwap() {
        return memorySwap;
    }

    /**
     * Total memory plus swap limit (in bytes). A value of 0 disables swap limit; -1 means unlimited swap. Often set to twice the Memory value.
     * 
     */
    @JsonProperty("MemorySwap")
    public void setMemorySwap(Integer memorySwap) {
        this.memorySwap = memorySwap;
    }

    public HostConfig withMemorySwap(Integer memorySwap) {
        this.memorySwap = memorySwap;
        return this;
    }

    /**
     * Maximum number of processes (PIDs) allowed inside the container. null or 0 means unlimited.
     * 
     */
    @JsonProperty("PidsLimit")
    public Integer getPidsLimit() {
        return pidsLimit;
    }

    /**
     * Maximum number of processes (PIDs) allowed inside the container. null or 0 means unlimited.
     * 
     */
    @JsonProperty("PidsLimit")
    public void setPidsLimit(Integer pidsLimit) {
        this.pidsLimit = pidsLimit;
    }

    public HostConfig withPidsLimit(Integer pidsLimit) {
        this.pidsLimit = pidsLimit;
        return this;
    }

    /**
     * Block IO relative weight (between 10 and 1000). Controls disk I/O priority among containers. Default 0 means no explicit weighting.
     * 
     */
    @JsonProperty("BlkioWeight")
    public Integer getBlkioWeight() {
        return blkioWeight;
    }

    /**
     * Block IO relative weight (between 10 and 1000). Controls disk I/O priority among containers. Default 0 means no explicit weighting.
     * 
     */
    @JsonProperty("BlkioWeight")
    public void setBlkioWeight(Integer blkioWeight) {
        this.blkioWeight = blkioWeight;
    }

    public HostConfig withBlkioWeight(Integer blkioWeight) {
        this.blkioWeight = blkioWeight;
        return this;
    }

    /**
     * Maximum I/O operations per second (IOPS) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumIOps")
    public Integer getIOMaximumIOps() {
        return iOMaximumIOps;
    }

    /**
     * Maximum I/O operations per second (IOPS) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumIOps")
    public void setIOMaximumIOps(Integer iOMaximumIOps) {
        this.iOMaximumIOps = iOMaximumIOps;
    }

    public HostConfig withIOMaximumIOps(Integer iOMaximumIOps) {
        this.iOMaximumIOps = iOMaximumIOps;
        return this;
    }

    /**
     * Maximum I/O throughput (in bytes per second) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumBandwidth")
    public Integer getIOMaximumBandwidth() {
        return iOMaximumBandwidth;
    }

    /**
     * Maximum I/O throughput (in bytes per second) allowed for the container’s block devices. 0 disables enforcement.
     * 
     */
    @JsonProperty("IOMaximumBandwidth")
    public void setIOMaximumBandwidth(Integer iOMaximumBandwidth) {
        this.iOMaximumBandwidth = iOMaximumBandwidth;
    }

    public HostConfig withIOMaximumBandwidth(Integer iOMaximumBandwidth) {
        this.iOMaximumBandwidth = iOMaximumBandwidth;
        return this;
    }

    /**
     * Size (in bytes) of the /dev/shm shared memory segment available to the container. Default is 67,108,864 bytes (64MB).
     * 
     */
    @JsonProperty("ShmSize")
    public Integer getShmSize() {
        return shmSize;
    }

    /**
     * Size (in bytes) of the /dev/shm shared memory segment available to the container. Default is 67,108,864 bytes (64MB).
     * 
     */
    @JsonProperty("ShmSize")
    public void setShmSize(Integer shmSize) {
        this.shmSize = shmSize;
    }

    public HostConfig withShmSize(Integer shmSize) {
        this.shmSize = shmSize;
        return this;
    }

    /**
     * Defines the container restart behavior if it exits or fails.
     * 
     */
    @JsonProperty("RestartPolicy")
    public RestartPolicy getRestartPolicy() {
        return restartPolicy;
    }

    /**
     * Defines the container restart behavior if it exits or fails.
     * 
     */
    @JsonProperty("RestartPolicy")
    public void setRestartPolicy(RestartPolicy restartPolicy) {
        this.restartPolicy = restartPolicy;
    }

    public HostConfig withRestartPolicy(RestartPolicy restartPolicy) {
        this.restartPolicy = restartPolicy;
        return this;
    }

    /**
     * Indicates if the container is running in privileged mode (full access to host devices and kernel features).
     * 
     */
    @JsonProperty("Privileged")
    public Boolean getPrivileged() {
        return privileged;
    }

    /**
     * Indicates if the container is running in privileged mode (full access to host devices and kernel features).
     * 
     */
    @JsonProperty("Privileged")
    public void setPrivileged(Boolean privileged) {
        this.privileged = privileged;
    }

    public HostConfig withPrivileged(Boolean privileged) {
        this.privileged = privileged;
        return this;
    }

    /**
     * Specifies the container’s networking mode (e.g., 'bridge', 'host', 'none', 'container:<id>', or 'ingress' for swarm).
     * 
     */
    @JsonProperty("NetworkMode")
    public String getNetworkMode() {
        return networkMode;
    }

    /**
     * Specifies the container’s networking mode (e.g., 'bridge', 'host', 'none', 'container:<id>', or 'ingress' for swarm).
     * 
     */
    @JsonProperty("NetworkMode")
    public void setNetworkMode(String networkMode) {
        this.networkMode = networkMode;
    }

    public HostConfig withNetworkMode(String networkMode) {
        this.networkMode = networkMode;
        return this;
    }

}
