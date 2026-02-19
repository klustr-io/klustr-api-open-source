
package io.klustr.schemas.integrations.minio;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioGenericPolicyForAnyValue
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "jwt:groups"
})
@Generated("jsonschema2pojo")
public class MinioGenericPolicyForAnyValue {

    @JsonProperty("jwt:groups")
    private String jwtGroups;

    @JsonProperty("jwt:groups")
    public String getJwtGroups() {
        return jwtGroups;
    }

    @JsonProperty("jwt:groups")
    public void setJwtGroups(String jwtGroups) {
        this.jwtGroups = jwtGroups;
    }

    public MinioGenericPolicyForAnyValue withJwtGroups(String jwtGroups) {
        this.jwtGroups = jwtGroups;
        return this;
    }

}
