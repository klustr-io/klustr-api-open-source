
package io.klustr.schemas.persons.supervised;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * SupervisedStatus
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "avatar",
    "third_party_consent"
})
@Generated("jsonschema2pojo")
public class SupervisedStatus {

    /**
     * AvatarPermissions
     * <p>
     * The supervised account can change their profile picture
     * 
     */
    @JsonProperty("avatar")
    @JsonPropertyDescription("The supervised account can change their profile picture")
    private AvatarPermission avatar;
    /**
     * ThirdPartyPermission
     * <p>
     * The supervised account can approve consent for basic operations or not.
     * 
     */
    @JsonProperty("third_party_consent")
    @JsonPropertyDescription("The supervised account can approve consent for basic operations or not.")
    private ThirdPartyPermission thirdPartyConsent;

    /**
     * AvatarPermissions
     * <p>
     * The supervised account can change their profile picture
     * 
     */
    @JsonProperty("avatar")
    public AvatarPermission getAvatar() {
        return avatar;
    }

    /**
     * AvatarPermissions
     * <p>
     * The supervised account can change their profile picture
     * 
     */
    @JsonProperty("avatar")
    public void setAvatar(AvatarPermission avatar) {
        this.avatar = avatar;
    }

    public SupervisedStatus withAvatar(AvatarPermission avatar) {
        this.avatar = avatar;
        return this;
    }

    /**
     * ThirdPartyPermission
     * <p>
     * The supervised account can approve consent for basic operations or not.
     * 
     */
    @JsonProperty("third_party_consent")
    public ThirdPartyPermission getThirdPartyConsent() {
        return thirdPartyConsent;
    }

    /**
     * ThirdPartyPermission
     * <p>
     * The supervised account can approve consent for basic operations or not.
     * 
     */
    @JsonProperty("third_party_consent")
    public void setThirdPartyConsent(ThirdPartyPermission thirdPartyConsent) {
        this.thirdPartyConsent = thirdPartyConsent;
    }

    public SupervisedStatus withThirdPartyConsent(ThirdPartyPermission thirdPartyConsent) {
        this.thirdPartyConsent = thirdPartyConsent;
        return this;
    }

}
