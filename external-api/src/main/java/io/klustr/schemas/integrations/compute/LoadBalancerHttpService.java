
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * LoadBalancerHttpService
 * <p>
 * Maps the whole domain concept of a service so we can one-shot the configuration
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "project_id",
    "port"
})
@Generated("jsonschema2pojo")
public class LoadBalancerHttpService {

    /**
     * The project_id we want to expose. This will map to the server hosting.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project_id we want to expose. This will map to the server hosting.")
    private String projectId;
    /**
     * The port the service is running on.
     * 
     */
    @JsonProperty("port")
    @JsonPropertyDescription("The port the service is running on.")
    private Integer port;

    /**
     * The project_id we want to expose. This will map to the server hosting.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project_id we want to expose. This will map to the server hosting.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LoadBalancerHttpService withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The port the service is running on.
     * 
     */
    @JsonProperty("port")
    public Integer getPort() {
        return port;
    }

    /**
     * The port the service is running on.
     * 
     */
    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
    }

    public LoadBalancerHttpService withPort(Integer port) {
        this.port = port;
        return this;
    }

}
