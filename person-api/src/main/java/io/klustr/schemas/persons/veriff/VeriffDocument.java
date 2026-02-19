
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffDocument
 * <p>
 * The document that was verified
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "number",
    "country",
    "validFrom",
    "validUntil"
})
@Generated("jsonschema2pojo")
public class VeriffDocument {

    @JsonProperty("type")
    private io.klustr.schemas.persons.veriff.VeriffValue type;
    @JsonProperty("number")
    private io.klustr.schemas.persons.veriff.VeriffValue number;
    @JsonProperty("country")
    private io.klustr.schemas.persons.veriff.VeriffValue country;
    @JsonProperty("validFrom")
    private io.klustr.schemas.persons.veriff.VeriffValue validFrom;
    @JsonProperty("validUntil")
    private io.klustr.schemas.persons.veriff.VeriffValue validUntil;

    @JsonProperty("type")
    public io.klustr.schemas.persons.veriff.VeriffValue getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(io.klustr.schemas.persons.veriff.VeriffValue type) {
        this.type = type;
    }

    public VeriffDocument withType(io.klustr.schemas.persons.veriff.VeriffValue type) {
        this.type = type;
        return this;
    }

    @JsonProperty("number")
    public io.klustr.schemas.persons.veriff.VeriffValue getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(io.klustr.schemas.persons.veriff.VeriffValue number) {
        this.number = number;
    }

    public VeriffDocument withNumber(io.klustr.schemas.persons.veriff.VeriffValue number) {
        this.number = number;
        return this;
    }

    @JsonProperty("country")
    public io.klustr.schemas.persons.veriff.VeriffValue getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(io.klustr.schemas.persons.veriff.VeriffValue country) {
        this.country = country;
    }

    public VeriffDocument withCountry(io.klustr.schemas.persons.veriff.VeriffValue country) {
        this.country = country;
        return this;
    }

    @JsonProperty("validFrom")
    public io.klustr.schemas.persons.veriff.VeriffValue getValidFrom() {
        return validFrom;
    }

    @JsonProperty("validFrom")
    public void setValidFrom(io.klustr.schemas.persons.veriff.VeriffValue validFrom) {
        this.validFrom = validFrom;
    }

    public VeriffDocument withValidFrom(io.klustr.schemas.persons.veriff.VeriffValue validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    @JsonProperty("validUntil")
    public io.klustr.schemas.persons.veriff.VeriffValue getValidUntil() {
        return validUntil;
    }

    @JsonProperty("validUntil")
    public void setValidUntil(io.klustr.schemas.persons.veriff.VeriffValue validUntil) {
        this.validUntil = validUntil;
    }

    public VeriffDocument withValidUntil(io.klustr.schemas.persons.veriff.VeriffValue validUntil) {
        this.validUntil = validUntil;
        return this;
    }

}
