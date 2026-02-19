
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioGenericPolicyStatement
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Effect",
    "Action",
    "Resource",
    "Condition"
})
@Generated("jsonschema2pojo")
public class MinioGenericPolicyStatement {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Effect")
    private String effect;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Action")
    private List<String> action = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Resource")
    private List<String> resource = new ArrayList<String>();
    /**
     * MinioGenericPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    private MinioGenericPolicyCondition condition;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Effect")
    public String getEffect() {
        return effect;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Effect")
    public void setEffect(String effect) {
        this.effect = effect;
    }

    public MinioGenericPolicyStatement withEffect(String effect) {
        this.effect = effect;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Action")
    public List<String> getAction() {
        return action;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Action")
    public void setAction(List<String> action) {
        this.action = action;
    }

    public MinioGenericPolicyStatement withAction(List<String> action) {
        this.action = action;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Resource")
    public List<String> getResource() {
        return resource;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Resource")
    public void setResource(List<String> resource) {
        this.resource = resource;
    }

    public MinioGenericPolicyStatement withResource(List<String> resource) {
        this.resource = resource;
        return this;
    }

    /**
     * MinioGenericPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    public MinioGenericPolicyCondition getCondition() {
        return condition;
    }

    /**
     * MinioGenericPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    public void setCondition(MinioGenericPolicyCondition condition) {
        this.condition = condition;
    }

    public MinioGenericPolicyStatement withCondition(MinioGenericPolicyCondition condition) {
        this.condition = condition;
        return this;
    }

}
