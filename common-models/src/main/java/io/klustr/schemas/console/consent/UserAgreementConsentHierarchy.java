
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserAgreementConsentHierarchy
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "agreement_id",
    "agreement_name",
    "scopes"
})
@Generated("jsonschema2pojo")
public class UserAgreementConsentHierarchy {

    /**
     * The agreement identifier.
     * 
     */
    @JsonProperty("agreement_id")
    @JsonPropertyDescription("The agreement identifier.")
    private String agreementId;
    /**
     * The agreement name.
     * 
     */
    @JsonProperty("agreement_name")
    @JsonPropertyDescription("The agreement name.")
    private String agreementName;
    @JsonProperty("scopes")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();

    /**
     * The agreement identifier.
     * 
     */
    @JsonProperty("agreement_id")
    public String getAgreementId() {
        return agreementId;
    }

    /**
     * The agreement identifier.
     * 
     */
    @JsonProperty("agreement_id")
    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public UserAgreementConsentHierarchy withAgreementId(String agreementId) {
        this.agreementId = agreementId;
        return this;
    }

    /**
     * The agreement name.
     * 
     */
    @JsonProperty("agreement_name")
    public String getAgreementName() {
        return agreementName;
    }

    /**
     * The agreement name.
     * 
     */
    @JsonProperty("agreement_name")
    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public UserAgreementConsentHierarchy withAgreementName(String agreementName) {
        this.agreementName = agreementName;
        return this;
    }

    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserAgreementConsentHierarchy withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

}
