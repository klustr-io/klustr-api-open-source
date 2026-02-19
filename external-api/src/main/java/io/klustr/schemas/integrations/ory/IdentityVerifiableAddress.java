
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * IdentityVerifiableAddress
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "value",
    "verified",
    "via",
    "status",
    "verified_at"
})
@Generated("jsonschema2pojo")
public class IdentityVerifiableAddress {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonProperty("via")
    private String via;
    @JsonProperty("status")
    private String status;
    @JsonProperty("verified_at")
    private DateTime verifiedAt;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public IdentityVerifiableAddress withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public IdentityVerifiableAddress withValue(String value) {
        this.value = value;
        return this;
    }

    @JsonProperty("verified")
    public Boolean getVerified() {
        return verified;
    }

    @JsonProperty("verified")
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public IdentityVerifiableAddress withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    @JsonProperty("via")
    public String getVia() {
        return via;
    }

    @JsonProperty("via")
    public void setVia(String via) {
        this.via = via;
    }

    public IdentityVerifiableAddress withVia(String via) {
        this.via = via;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public IdentityVerifiableAddress withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("verified_at")
    public DateTime getVerifiedAt() {
        return verifiedAt;
    }

    @JsonProperty("verified_at")
    public void setVerifiedAt(DateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public IdentityVerifiableAddress withVerifiedAt(DateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
        return this;
    }

}
