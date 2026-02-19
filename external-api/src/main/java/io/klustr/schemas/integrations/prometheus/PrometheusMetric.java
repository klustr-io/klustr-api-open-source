
package io.klustr.schemas.integrations.prometheus;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PrometheusMetric
 * <p>
 * The metric being applied.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metric",
    "values"
})
@Generated("jsonschema2pojo")
public class PrometheusMetric {

    /**
     * PrometheusMetricReference
     * <p>
     * 
     * 
     */
    @JsonProperty("metric")
    private PrometheusMetricReference metric;
    /**
     * PrometheusMetricDataFrame
     * <p>
     * 
     * 
     */
    @JsonProperty("values")
    private List<List<Object>> values = new ArrayList<List<Object>>();

    /**
     * PrometheusMetricReference
     * <p>
     * 
     * 
     */
    @JsonProperty("metric")
    public PrometheusMetricReference getMetric() {
        return metric;
    }

    /**
     * PrometheusMetricReference
     * <p>
     * 
     * 
     */
    @JsonProperty("metric")
    public void setMetric(PrometheusMetricReference metric) {
        this.metric = metric;
    }

    public PrometheusMetric withMetric(PrometheusMetricReference metric) {
        this.metric = metric;
        return this;
    }

    /**
     * PrometheusMetricDataFrame
     * <p>
     * 
     * 
     */
    @JsonProperty("values")
    public List<List<Object>> getValues() {
        return values;
    }

    /**
     * PrometheusMetricDataFrame
     * <p>
     * 
     * 
     */
    @JsonProperty("values")
    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public PrometheusMetric withValues(List<List<Object>> values) {
        this.values = values;
        return this;
    }

}
