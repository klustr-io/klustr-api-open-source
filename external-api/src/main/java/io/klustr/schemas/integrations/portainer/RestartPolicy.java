
package io.klustr.schemas.integrations.portainer;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Defines the container restart behavior if it exits or fails.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "MaximumRetryCount"
})
@Generated("jsonschema2pojo")
public class RestartPolicy {

    /**
     * Restart policy name (e.g., 'no', 'on-failure', 'always', 'unless-stopped').
     * 
     */
    @JsonProperty("Name")
    @JsonPropertyDescription("Restart policy name (e.g., 'no', 'on-failure', 'always', 'unless-stopped').")
    private String name;
    /**
     * Maximum retry count for 'on-failure' policy. Ignored for other modes.
     * 
     */
    @JsonProperty("MaximumRetryCount")
    @JsonPropertyDescription("Maximum retry count for 'on-failure' policy. Ignored for other modes.")
    private Integer maximumRetryCount;

    /**
     * Restart policy name (e.g., 'no', 'on-failure', 'always', 'unless-stopped').
     * 
     */
    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    /**
     * Restart policy name (e.g., 'no', 'on-failure', 'always', 'unless-stopped').
     * 
     */
    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public RestartPolicy withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Maximum retry count for 'on-failure' policy. Ignored for other modes.
     * 
     */
    @JsonProperty("MaximumRetryCount")
    public Integer getMaximumRetryCount() {
        return maximumRetryCount;
    }

    /**
     * Maximum retry count for 'on-failure' policy. Ignored for other modes.
     * 
     */
    @JsonProperty("MaximumRetryCount")
    public void setMaximumRetryCount(Integer maximumRetryCount) {
        this.maximumRetryCount = maximumRetryCount;
    }

    public RestartPolicy withMaximumRetryCount(Integer maximumRetryCount) {
        this.maximumRetryCount = maximumRetryCount;
        return this;
    }

}
