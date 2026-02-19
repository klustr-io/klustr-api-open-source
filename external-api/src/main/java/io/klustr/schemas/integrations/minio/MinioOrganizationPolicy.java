
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioOrganizationPolicy
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
public class MinioOrganizationPolicy {

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
    private List<MinioOrganizationPolicyStatement> statement = new ArrayList<MinioOrganizationPolicyStatement>();

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

    public MinioOrganizationPolicy withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Statement")
    public List<MinioOrganizationPolicyStatement> getStatement() {
        return statement;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Statement")
    public void setStatement(List<MinioOrganizationPolicyStatement> statement) {
        this.statement = statement;
    }

    public MinioOrganizationPolicy withStatement(List<MinioOrganizationPolicyStatement> statement) {
        this.statement = statement;
        return this;
    }

}
