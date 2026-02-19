
package io.klustr.schemas.console.users;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "scope"
})
@Generated("jsonschema2pojo")
public class Permission {

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public Permission withScope(String scope) {
        this.scope = scope;
        return this;
    }

}
