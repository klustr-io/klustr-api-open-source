
package io.klustr.schemas.integrations.prometheus;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HttpMetric
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metric",
    "values"
})
@Generated("jsonschema2pojo")
public class HttpMetric {

    @JsonProperty("metric")
    private Metric metric;
    @JsonProperty("values")
    private List<List<Object>> values = new ArrayList<List<Object>>();

    @JsonProperty("metric")
    public Metric getMetric() {
        return metric;
    }

    @JsonProperty("metric")
    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public HttpMetric withMetric(Metric metric) {
        this.metric = metric;
        return this;
    }

    @JsonProperty("values")
    public List<List<Object>> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<List<Object>> values) {
        this.values = values;
    }

    public HttpMetric withValues(List<List<Object>> values) {
        this.values = values;
        return this;
    }

}
