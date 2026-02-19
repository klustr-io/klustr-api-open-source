
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioOrganizationPolicyStatement
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
public class MinioOrganizationPolicyStatement {

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
     * MinioOrganizationPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    private MinioOrganizationPolicyCondition condition;

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

    public MinioOrganizationPolicyStatement withEffect(String effect) {
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

    public MinioOrganizationPolicyStatement withAction(List<String> action) {
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

    public MinioOrganizationPolicyStatement withResource(List<String> resource) {
        this.resource = resource;
        return this;
    }

    /**
     * MinioOrganizationPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    public MinioOrganizationPolicyCondition getCondition() {
        return condition;
    }

    /**
     * MinioOrganizationPolicyCondition
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("Condition")
    public void setCondition(MinioOrganizationPolicyCondition condition) {
        this.condition = condition;
    }

    public MinioOrganizationPolicyStatement withCondition(MinioOrganizationPolicyCondition condition) {
        this.condition = condition;
        return this;
    }

}
