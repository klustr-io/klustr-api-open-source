
package io.klustr.schemas.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ApiServiceRoutesSpecification
 * <p>
 * Reference to a configured route in a service
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hosts",
    "paths"
})
@Generated("jsonschema2pojo")
public class ApiServiceRoutesSpecification {

    @JsonProperty("hosts")
    private List<String> hosts = new ArrayList<String>();
    @JsonProperty("paths")
    private List<String> paths = new ArrayList<String>();

    @JsonProperty("hosts")
    public List<String> getHosts() {
        return hosts;
    }

    @JsonProperty("hosts")
    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public ApiServiceRoutesSpecification withHosts(List<String> hosts) {
        this.hosts = hosts;
        return this;
    }

    @JsonProperty("paths")
    public List<String> getPaths() {
        return paths;
    }

    @JsonProperty("paths")
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public ApiServiceRoutesSpecification withPaths(List<String> paths) {
        this.paths = paths;
        return this;
    }

}
