
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioGenericPolicyStringEquals
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "jwt:sub",
    "jwt:groups",
    "jwt:email"
})
@Generated("jsonschema2pojo")
public class MinioGenericPolicyStringEquals {

    @JsonProperty("jwt:sub")
    private List<String> jwtSub = new ArrayList<String>();
    @JsonProperty("jwt:groups")
    private List<String> jwtGroups = new ArrayList<String>();
    @JsonProperty("jwt:email")
    private List<String> jwtEmail = new ArrayList<String>();

    @JsonProperty("jwt:sub")
    public List<String> getJwtSub() {
        return jwtSub;
    }

    @JsonProperty("jwt:sub")
    public void setJwtSub(List<String> jwtSub) {
        this.jwtSub = jwtSub;
    }

    public MinioGenericPolicyStringEquals withJwtSub(List<String> jwtSub) {
        this.jwtSub = jwtSub;
        return this;
    }

    @JsonProperty("jwt:groups")
    public List<String> getJwtGroups() {
        return jwtGroups;
    }

    @JsonProperty("jwt:groups")
    public void setJwtGroups(List<String> jwtGroups) {
        this.jwtGroups = jwtGroups;
    }

    public MinioGenericPolicyStringEquals withJwtGroups(List<String> jwtGroups) {
        this.jwtGroups = jwtGroups;
        return this;
    }

    @JsonProperty("jwt:email")
    public List<String> getJwtEmail() {
        return jwtEmail;
    }

    @JsonProperty("jwt:email")
    public void setJwtEmail(List<String> jwtEmail) {
        this.jwtEmail = jwtEmail;
    }

    public MinioGenericPolicyStringEquals withJwtEmail(List<String> jwtEmail) {
        this.jwtEmail = jwtEmail;
        return this;
    }

}
