
package io.klustr.schemas.integrations.ory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * HydraIdTokenClaims
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "iss",
    "sub",
    "aud",
    "ext"
})
@Generated("jsonschema2pojo")
public class HydraIdTokenClaims {

    @JsonProperty("iss")
    private String iss;
    /**
     * Will contain the public OR pairwise subject identifer userful for mapping PPID to real ID.
     * 
     */
    @JsonProperty("sub")
    @JsonPropertyDescription("Will contain the public OR pairwise subject identifer userful for mapping PPID to real ID.")
    private String sub;
    @JsonProperty("aud")
    private List<Object> aud = new ArrayList<Object>();
    /**
     * HydraIdTokenExt
     * <p>
     * 
     * 
     */
    @JsonProperty("ext")
    private HydraIdTokenExt ext;

    @JsonProperty("iss")
    public String getIss() {
        return iss;
    }

    @JsonProperty("iss")
    public void setIss(String iss) {
        this.iss = iss;
    }

    public HydraIdTokenClaims withIss(String iss) {
        this.iss = iss;
        return this;
    }

    /**
     * Will contain the public OR pairwise subject identifer userful for mapping PPID to real ID.
     * 
     */
    @JsonProperty("sub")
    public String getSub() {
        return sub;
    }

    /**
     * Will contain the public OR pairwise subject identifer userful for mapping PPID to real ID.
     * 
     */
    @JsonProperty("sub")
    public void setSub(String sub) {
        this.sub = sub;
    }

    public HydraIdTokenClaims withSub(String sub) {
        this.sub = sub;
        return this;
    }

    @JsonProperty("aud")
    public List<Object> getAud() {
        return aud;
    }

    @JsonProperty("aud")
    public void setAud(List<Object> aud) {
        this.aud = aud;
    }

    public HydraIdTokenClaims withAud(List<Object> aud) {
        this.aud = aud;
        return this;
    }

    /**
     * HydraIdTokenExt
     * <p>
     * 
     * 
     */
    @JsonProperty("ext")
    public HydraIdTokenExt getExt() {
        return ext;
    }

    /**
     * HydraIdTokenExt
     * <p>
     * 
     * 
     */
    @JsonProperty("ext")
    public void setExt(HydraIdTokenExt ext) {
        this.ext = ext;
    }

    public HydraIdTokenClaims withExt(HydraIdTokenExt ext) {
        this.ext = ext;
        return this;
    }

}
