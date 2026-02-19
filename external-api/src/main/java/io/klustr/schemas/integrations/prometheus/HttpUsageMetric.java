
package io.klustr.schemas.integrations.prometheus;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HttpUsageMetric
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resultType",
    "result"
})
@Generated("jsonschema2pojo")
public class HttpUsageMetric {

    @JsonProperty("resultType")
    private String resultType;
    @JsonProperty("result")
    private List<HttpMetric> result = new ArrayList<HttpMetric>();

    @JsonProperty("resultType")
    public String getResultType() {
        return resultType;
    }

    @JsonProperty("resultType")
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public HttpUsageMetric withResultType(String resultType) {
        this.resultType = resultType;
        return this;
    }

    @JsonProperty("result")
    public List<HttpMetric> getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(List<HttpMetric> result) {
        this.result = result;
    }

    public HttpUsageMetric withResult(List<HttpMetric> result) {
        this.result = result;
        return this;
    }

}
