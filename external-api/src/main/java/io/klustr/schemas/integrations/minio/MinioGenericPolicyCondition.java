
package io.klustr.schemas.integrations.minio;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioGenericPolicyCondition
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ForAnyValue:StringEquals",
    "StringEquals"
})
@Generated("jsonschema2pojo")
public class MinioGenericPolicyCondition {

    /**
     * MinioGenericPolicyForAnyValue
     * <p>
     * 
     * 
     */
    @JsonProperty("ForAnyValue:StringEquals")
    private MinioGenericPolicyForAnyValue forAnyValueStringEquals;
    /**
     * MinioGenericPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    private MinioGenericPolicyStringEquals stringEquals;

    /**
     * MinioGenericPolicyForAnyValue
     * <p>
     * 
     * 
     */
    @JsonProperty("ForAnyValue:StringEquals")
    public MinioGenericPolicyForAnyValue getForAnyValueStringEquals() {
        return forAnyValueStringEquals;
    }

    /**
     * MinioGenericPolicyForAnyValue
     * <p>
     * 
     * 
     */
    @JsonProperty("ForAnyValue:StringEquals")
    public void setForAnyValueStringEquals(MinioGenericPolicyForAnyValue forAnyValueStringEquals) {
        this.forAnyValueStringEquals = forAnyValueStringEquals;
    }

    public MinioGenericPolicyCondition withForAnyValueStringEquals(MinioGenericPolicyForAnyValue forAnyValueStringEquals) {
        this.forAnyValueStringEquals = forAnyValueStringEquals;
        return this;
    }

    /**
     * MinioGenericPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    public MinioGenericPolicyStringEquals getStringEquals() {
        return stringEquals;
    }

    /**
     * MinioGenericPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    public void setStringEquals(MinioGenericPolicyStringEquals stringEquals) {
        this.stringEquals = stringEquals;
    }

    public MinioGenericPolicyCondition withStringEquals(MinioGenericPolicyStringEquals stringEquals) {
        this.stringEquals = stringEquals;
        return this;
    }

}
