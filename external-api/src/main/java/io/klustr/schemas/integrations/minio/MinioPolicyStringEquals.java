
package io.klustr.schemas.integrations.minio;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MinioPolicyStringEquals
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "jwt:sub"
})
@Generated("jsonschema2pojo")
public class MinioPolicyStringEquals {

    @JsonProperty("jwt:sub")
    private List<String> jwtSub = new ArrayList<String>();

    @JsonProperty("jwt:sub")
    public List<String> getJwtSub() {
        return jwtSub;
    }

    @JsonProperty("jwt:sub")
    public void setJwtSub(List<String> jwtSub) {
        this.jwtSub = jwtSub;
    }

    public MinioPolicyStringEquals withJwtSub(List<String> jwtSub) {
        this.jwtSub = jwtSub;
        return this;
    }

}
