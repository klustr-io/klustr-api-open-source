
package io.klustr.schemas.integrations.compute;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * LoadBalancerAclResource
 * <p>
 * An ACL resource that maps to a rule defining an access control path. Useful for matching connections, headers, etc. In this we are mapping to ACLs in haproxy where `acl is_dns hdr_sub(host) -m beg dns.` == { name: "is_dns", criterion: "hdr_sub(host)", "value": "-m beg dns."  }
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "acl_name",
    "criterion",
    "value"
})
@Generated("jsonschema2pojo")
public class LoadBalancerAclResource {

    /**
     * The acl name use to define it
     * 
     */
    @JsonProperty("acl_name")
    @JsonPropertyDescription("The acl name use to define it")
    private String aclName;
    /**
     * The criteria
     * 
     */
    @JsonProperty("criterion")
    @JsonPropertyDescription("The criteria")
    private String criterion;
    /**
     * The value to match against and logic. For example matching path beg etc.
     * 
     */
    @JsonProperty("value")
    @JsonPropertyDescription("The value to match against and logic. For example matching path beg etc.")
    private String value;

    /**
     * The acl name use to define it
     * 
     */
    @JsonProperty("acl_name")
    public String getAclName() {
        return aclName;
    }

    /**
     * The acl name use to define it
     * 
     */
    @JsonProperty("acl_name")
    public void setAclName(String aclName) {
        this.aclName = aclName;
    }

    public LoadBalancerAclResource withAclName(String aclName) {
        this.aclName = aclName;
        return this;
    }

    /**
     * The criteria
     * 
     */
    @JsonProperty("criterion")
    public String getCriterion() {
        return criterion;
    }

    /**
     * The criteria
     * 
     */
    @JsonProperty("criterion")
    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public LoadBalancerAclResource withCriterion(String criterion) {
        this.criterion = criterion;
        return this;
    }

    /**
     * The value to match against and logic. For example matching path beg etc.
     * 
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * The value to match against and logic. For example matching path beg etc.
     * 
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public LoadBalancerAclResource withValue(String value) {
        this.value = value;
        return this;
    }

}
