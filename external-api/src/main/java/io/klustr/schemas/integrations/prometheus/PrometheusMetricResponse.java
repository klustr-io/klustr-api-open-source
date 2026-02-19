
package io.klustr.schemas.integrations.prometheus;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PrometheusMetricResponse
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
public class PrometheusMetricResponse {

    @JsonProperty("status")
    private String status;
    /**
     * PrometheusMetricData
     * <p>
     * The type of metrics you want
     * 
     */
    @JsonProperty("data")
    @JsonPropertyDescription("The type of metrics you want")
    private PrometheusMetricData data;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public PrometheusMetricResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * PrometheusMetricData
     * <p>
     * The type of metrics you want
     * 
     */
    @JsonProperty("data")
    public PrometheusMetricData getData() {
        return data;
    }

    /**
     * PrometheusMetricData
     * <p>
     * The type of metrics you want
     * 
     */
    @JsonProperty("data")
    public void setData(PrometheusMetricData data) {
        this.data = data;
    }

    public PrometheusMetricResponse withData(PrometheusMetricData data) {
        this.data = data;
        return this;
    }

}
