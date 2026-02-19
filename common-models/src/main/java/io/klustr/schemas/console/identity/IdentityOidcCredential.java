
package io.klustr.schemas.console.identity;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * IdentityOidcCredential
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "identifiers"
})
@Generated("jsonschema2pojo")
public class IdentityOidcCredential {

    @JsonProperty("type")
    private String type;
    @JsonProperty("identifiers")
    private List<String> identifiers = new ArrayList<String>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public IdentityOidcCredential withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("identifiers")
    public List<String> getIdentifiers() {
        return identifiers;
    }

    @JsonProperty("identifiers")
    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public IdentityOidcCredential withIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
        return this;
    }

}
