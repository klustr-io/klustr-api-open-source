
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityRecoveryAddress
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "value",
    "via"
})
@Generated("jsonschema2pojo")
public class IdentityRecoveryAddress {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("via")
    private String via;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public IdentityRecoveryAddress withId(String id) {
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

    public IdentityRecoveryAddress withValue(String value) {
        this.value = value;
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

    public IdentityRecoveryAddress withVia(String via) {
        this.via = via;
        return this;
    }

}
