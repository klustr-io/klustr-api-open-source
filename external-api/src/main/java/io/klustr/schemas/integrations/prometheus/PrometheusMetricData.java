
package io.klustr.schemas.integrations.prometheus;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PrometheusMetricData
 * <p>
 * The type of metrics you want
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resultType",
    "result"
})
@Generated("jsonschema2pojo")
public class PrometheusMetricData {

    @JsonProperty("resultType")
    private String resultType;
    @JsonProperty("result")
    private List<PrometheusMetric> result = new ArrayList<PrometheusMetric>();

    @JsonProperty("resultType")
    public String getResultType() {
        return resultType;
    }

    @JsonProperty("resultType")
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public PrometheusMetricData withResultType(String resultType) {
        this.resultType = resultType;
        return this;
    }

    @JsonProperty("result")
    public List<PrometheusMetric> getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(List<PrometheusMetric> result) {
        this.result = result;
    }

    public PrometheusMetricData withResult(List<PrometheusMetric> result) {
        this.result = result;
        return this;
    }

}
