
package io.klustr.schemas.integrations.minio;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioOrganizationPolicyCondition
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "StringEquals"
})
@Generated("jsonschema2pojo")
public class MinioOrganizationPolicyCondition {

    /**
     * MinioPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    private MinioPolicyStringEquals stringEquals;

    /**
     * MinioPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    public MinioPolicyStringEquals getStringEquals() {
        return stringEquals;
    }

    /**
     * MinioPolicyStringEquals
     * <p>
     * 
     * 
     */
    @JsonProperty("StringEquals")
    public void setStringEquals(MinioPolicyStringEquals stringEquals) {
        this.stringEquals = stringEquals;
    }

    public MinioOrganizationPolicyCondition withStringEquals(MinioPolicyStringEquals stringEquals) {
        this.stringEquals = stringEquals;
        return this;
    }

}
