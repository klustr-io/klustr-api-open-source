
package io.klustr.schemas.integrations.prometheus;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PrometheusMetricReference
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "service",
    "consumer",
    "code"
})
@Generated("jsonschema2pojo")
public class PrometheusMetricReference {

    /**
     * The optional grouping by status service.
     * 
     */
    @JsonProperty("service")
    @JsonPropertyDescription("The optional grouping by status service.")
    private String service;
    /**
     * The optional grouping by consumer.
     * 
     */
    @JsonProperty("consumer")
    @JsonPropertyDescription("The optional grouping by consumer.")
    private String consumer;
    /**
     * The optional grouping by status code.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The optional grouping by status code.")
    private String code;

    /**
     * The optional grouping by status service.
     * 
     */
    @JsonProperty("service")
    public String getService() {
        return service;
    }

    /**
     * The optional grouping by status service.
     * 
     */
    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    public PrometheusMetricReference withService(String service) {
        this.service = service;
        return this;
    }

    /**
     * The optional grouping by consumer.
     * 
     */
    @JsonProperty("consumer")
    public String getConsumer() {
        return consumer;
    }

    /**
     * The optional grouping by consumer.
     * 
     */
    @JsonProperty("consumer")
    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public PrometheusMetricReference withConsumer(String consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * The optional grouping by status code.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The optional grouping by status code.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public PrometheusMetricReference withCode(String code) {
        this.code = code;
        return this;
    }

}
