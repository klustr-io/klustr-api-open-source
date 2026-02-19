
package io.klustr.schemas.persons.veriff;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * VeriffPerson
 * <p>
 * A object representing a person in Veriff.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "firstName",
    "lastName",
    "gender",
    "dateOfBirth",
    "citizenship",
    "nationality",
    "placeOfBirth",
    "idNumber",
    "address"
})
@Generated("jsonschema2pojo")
public class VeriffPerson {

    @JsonProperty("firstName")
    private io.klustr.schemas.persons.veriff.VeriffValue firstName;
    @JsonProperty("lastName")
    private io.klustr.schemas.persons.veriff.VeriffValue lastName;
    @JsonProperty("gender")
    private io.klustr.schemas.persons.veriff.VeriffValue gender;
    @JsonProperty("dateOfBirth")
    private io.klustr.schemas.persons.veriff.VeriffValue dateOfBirth;
    @JsonProperty("citizenship")
    private io.klustr.schemas.persons.veriff.VeriffValue citizenship;
    @JsonProperty("nationality")
    private io.klustr.schemas.persons.veriff.VeriffValue nationality;
    @JsonProperty("placeOfBirth")
    private io.klustr.schemas.persons.veriff.VeriffValue placeOfBirth;
    @JsonProperty("idNumber")
    private io.klustr.schemas.persons.veriff.VeriffValue idNumber;
    @JsonProperty("address")
    private io.klustr.schemas.persons.veriff.VeriffValue address;

    @JsonProperty("firstName")
    public io.klustr.schemas.persons.veriff.VeriffValue getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(io.klustr.schemas.persons.veriff.VeriffValue firstName) {
        this.firstName = firstName;
    }

    public VeriffPerson withFirstName(io.klustr.schemas.persons.veriff.VeriffValue firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public io.klustr.schemas.persons.veriff.VeriffValue getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(io.klustr.schemas.persons.veriff.VeriffValue lastName) {
        this.lastName = lastName;
    }

    public VeriffPerson withLastName(io.klustr.schemas.persons.veriff.VeriffValue lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("gender")
    public io.klustr.schemas.persons.veriff.VeriffValue getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(io.klustr.schemas.persons.veriff.VeriffValue gender) {
        this.gender = gender;
    }

    public VeriffPerson withGender(io.klustr.schemas.persons.veriff.VeriffValue gender) {
        this.gender = gender;
        return this;
    }

    @JsonProperty("dateOfBirth")
    public io.klustr.schemas.persons.veriff.VeriffValue getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(io.klustr.schemas.persons.veriff.VeriffValue dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public VeriffPerson withDateOfBirth(io.klustr.schemas.persons.veriff.VeriffValue dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    @JsonProperty("citizenship")
    public io.klustr.schemas.persons.veriff.VeriffValue getCitizenship() {
        return citizenship;
    }

    @JsonProperty("citizenship")
    public void setCitizenship(io.klustr.schemas.persons.veriff.VeriffValue citizenship) {
        this.citizenship = citizenship;
    }

    public VeriffPerson withCitizenship(io.klustr.schemas.persons.veriff.VeriffValue citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    @JsonProperty("nationality")
    public io.klustr.schemas.persons.veriff.VeriffValue getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(io.klustr.schemas.persons.veriff.VeriffValue nationality) {
        this.nationality = nationality;
    }

    public VeriffPerson withNationality(io.klustr.schemas.persons.veriff.VeriffValue nationality) {
        this.nationality = nationality;
        return this;
    }

    @JsonProperty("placeOfBirth")
    public io.klustr.schemas.persons.veriff.VeriffValue getPlaceOfBirth() {
        return placeOfBirth;
    }

    @JsonProperty("placeOfBirth")
    public void setPlaceOfBirth(io.klustr.schemas.persons.veriff.VeriffValue placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public VeriffPerson withPlaceOfBirth(io.klustr.schemas.persons.veriff.VeriffValue placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    @JsonProperty("idNumber")
    public io.klustr.schemas.persons.veriff.VeriffValue getIdNumber() {
        return idNumber;
    }

    @JsonProperty("idNumber")
    public void setIdNumber(io.klustr.schemas.persons.veriff.VeriffValue idNumber) {
        this.idNumber = idNumber;
    }

    public VeriffPerson withIdNumber(io.klustr.schemas.persons.veriff.VeriffValue idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    @JsonProperty("address")
    public io.klustr.schemas.persons.veriff.VeriffValue getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(io.klustr.schemas.persons.veriff.VeriffValue address) {
        this.address = address;
    }

    public VeriffPerson withAddress(io.klustr.schemas.persons.veriff.VeriffValue address) {
        this.address = address;
        return this;
    }

}
