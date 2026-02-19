
package io.klustr.schemas.integrations.prometheus;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "service"
})
@Generated("jsonschema2pojo")
public class Metric {

    @JsonProperty("service")
    private String service;

    @JsonProperty("service")
    public String getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    public Metric withService(String service) {
        this.service = service;
        return this;
    }

}
