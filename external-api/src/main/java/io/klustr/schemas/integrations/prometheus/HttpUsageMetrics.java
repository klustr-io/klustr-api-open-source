
package io.klustr.schemas.integrations.prometheus;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HttpUsageMetrics
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "data"
})
@Generated("jsonschema2pojo")
public class HttpUsageMetrics {

    @JsonProperty("status")
    private String status;
    /**
     * HttpUsageMetric
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    private HttpUsageMetric data;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public HttpUsageMetrics withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * HttpUsageMetric
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    public HttpUsageMetric getData() {
        return data;
    }

    /**
     * HttpUsageMetric
     * <p>
     * 
     * 
     */
    @JsonProperty("data")
    public void setData(HttpUsageMetric data) {
        this.data = data;
    }

    public HttpUsageMetrics withData(HttpUsageMetric data) {
        this.data = data;
        return this;
    }

}
