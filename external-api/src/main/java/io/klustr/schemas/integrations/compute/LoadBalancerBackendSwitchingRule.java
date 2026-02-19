
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * LoadBalancerBackendSwitchingRule
 * <p>
 * A rule associating a backend to a specific switching rule.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cond",
    "cond_test",
    "name"
})
@Generated("jsonschema2pojo")
public class LoadBalancerBackendSwitchingRule {

    /**
     * if
     * 
     */
    @JsonProperty("cond")
    @JsonPropertyDescription("if")
    private String cond = "if";
    /**
     * The ACL to test
     * 
     */
    @JsonProperty("cond_test")
    @JsonPropertyDescription("The ACL to test")
    private String condTest;
    /**
     * The name of the backend
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the backend")
    private String name;

    /**
     * if
     * 
     */
    @JsonProperty("cond")
    public String getCond() {
        return cond;
    }

    /**
     * if
     * 
     */
    @JsonProperty("cond")
    public void setCond(String cond) {
        this.cond = cond;
    }

    public LoadBalancerBackendSwitchingRule withCond(String cond) {
        this.cond = cond;
        return this;
    }

    /**
     * The ACL to test
     * 
     */
    @JsonProperty("cond_test")
    public String getCondTest() {
        return condTest;
    }

    /**
     * The ACL to test
     * 
     */
    @JsonProperty("cond_test")
    public void setCondTest(String condTest) {
        this.condTest = condTest;
    }

    public LoadBalancerBackendSwitchingRule withCondTest(String condTest) {
        this.condTest = condTest;
        return this;
    }

    /**
     * The name of the backend
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the backend
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public LoadBalancerBackendSwitchingRule withName(String name) {
        this.name = name;
        return this;
    }

}
