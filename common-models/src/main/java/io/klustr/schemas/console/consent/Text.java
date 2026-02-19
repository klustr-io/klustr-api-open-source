
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Text
 * <p>
 * Supports default english label with i18n translations
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "en",
    "jp"
})
@Generated("jsonschema2pojo")
public class Text {

    /**
     * The english version of this text.
     * 
     */
    @JsonProperty("en")
    @JsonPropertyDescription("The english version of this text.")
    private String en;
    /**
     * The japanese version of this text.
     * 
     */
    @JsonProperty("jp")
    @JsonPropertyDescription("The japanese version of this text.")
    private String jp;

    /**
     * The english version of this text.
     * 
     */
    @JsonProperty("en")
    public String getEn() {
        return en;
    }

    /**
     * The english version of this text.
     * 
     */
    @JsonProperty("en")
    public void setEn(String en) {
        this.en = en;
    }

    public Text withEn(String en) {
        this.en = en;
        return this;
    }

    /**
     * The japanese version of this text.
     * 
     */
    @JsonProperty("jp")
    public String getJp() {
        return jp;
    }

    /**
     * The japanese version of this text.
     * 
     */
    @JsonProperty("jp")
    public void setJp(String jp) {
        this.jp = jp;
    }

    public Text withJp(String jp) {
        this.jp = jp;
        return this;
    }

}
