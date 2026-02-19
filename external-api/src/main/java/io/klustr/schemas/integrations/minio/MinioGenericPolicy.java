
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioGenericPolicy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Version",
    "Statement"
})
@Generated("jsonschema2pojo")
public class MinioGenericPolicy {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Version")
    private String version;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Statement")
    private List<MinioGenericPolicyStatement> statement = new ArrayList<MinioGenericPolicyStatement>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Version")
    public String getVersion() {
        return version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Version")
    public void setVersion(String version) {
        this.version = version;
    }

    public MinioGenericPolicy withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Statement")
    public List<MinioGenericPolicyStatement> getStatement() {
        return statement;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Statement")
    public void setStatement(List<MinioGenericPolicyStatement> statement) {
        this.statement = statement;
    }

    public MinioGenericPolicy withStatement(List<MinioGenericPolicyStatement> statement) {
        this.statement = statement;
        return this;
    }

}
