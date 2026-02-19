
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * GitUserFederatedIdentity
 * <p>
 * The linked identities to a specific user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "provider",
    "extern_uid"
})
@Generated("jsonschema2pojo")
public class GitUserFederatedIdentity {

    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    @JsonPropertyDescription("The provider that created this user, used to link your system to gitlab user.")
    private String provider;
    /**
     * The unique ID of the user from the external system.
     * 
     */
    @JsonProperty("extern_uid")
    @JsonPropertyDescription("The unique ID of the user from the external system.")
    private String externUid;

    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    /**
     * The provider that created this user, used to link your system to gitlab user.
     * 
     */
    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public GitUserFederatedIdentity withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * The unique ID of the user from the external system.
     * 
     */
    @JsonProperty("extern_uid")
    public String getExternUid() {
        return externUid;
    }

    /**
     * The unique ID of the user from the external system.
     * 
     */
    @JsonProperty("extern_uid")
    public void setExternUid(String externUid) {
        this.externUid = externUid;
    }

    public GitUserFederatedIdentity withExternUid(String externUid) {
        this.externUid = externUid;
        return this;
    }

}
