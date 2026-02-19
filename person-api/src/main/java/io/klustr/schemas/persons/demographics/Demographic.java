
package io.klustr.schemas.persons.demographics;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.klustr.schemas.console.Gender;
import io.klustr.schemas.persons.ParentalStatus;
import io.klustr.schemas.persons.RelationshipStatus;


/**
 * Demographic
 * <p>
 * The demographics for the user.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "age",
    "income",
    "gender",
    "household",
    "relationship_status",
    "parental_status"
})
@Generated("jsonschema2pojo")
public class Demographic {

    /**
     * The age range for this user particular user.
     * 
     */
    @JsonProperty("age")
    @JsonPropertyDescription("The age range for this user particular user.")
    private Demographic.Age age;
    /**
     * The income range for the user.
     * 
     */
    @JsonProperty("income")
    @JsonPropertyDescription("The income range for the user.")
    private Demographic.Income income;
    /**
     * The gender for the specified user.
     * 
     */
    @JsonProperty("gender")
    @JsonPropertyDescription("The gender for the specified user.")
    private Gender gender;
    /**
     * If the person is part of a household of more than 1 person.
     * 
     */
    @JsonProperty("household")
    @JsonPropertyDescription("If the person is part of a household of more than 1 person.")
    private Boolean household;
    /**
     * If the person has declared they are in a relationship.
     * 
     */
    @JsonProperty("relationship_status")
    @JsonPropertyDescription("If the person has declared they are in a relationship.")
    private RelationshipStatus relationshipStatus;
    /**
     * If the person has parental responsibilities or duties.
     * 
     */
    @JsonProperty("parental_status")
    @JsonPropertyDescription("If the person has parental responsibilities or duties.")
    private ParentalStatus parentalStatus;

    /**
     * The age range for this user particular user.
     * 
     */
    @JsonProperty("age")
    public Demographic.Age getAge() {
        return age;
    }

    /**
     * The age range for this user particular user.
     * 
     */
    @JsonProperty("age")
    public void setAge(Demographic.Age age) {
        this.age = age;
    }

    public Demographic withAge(Demographic.Age age) {
        this.age = age;
        return this;
    }

    /**
     * The income range for the user.
     * 
     */
    @JsonProperty("income")
    public Demographic.Income getIncome() {
        return income;
    }

    /**
     * The income range for the user.
     * 
     */
    @JsonProperty("income")
    public void setIncome(Demographic.Income income) {
        this.income = income;
    }

    public Demographic withIncome(Demographic.Income income) {
        this.income = income;
        return this;
    }

    /**
     * The gender for the specified user.
     * 
     */
    @JsonProperty("gender")
    public Gender getGender() {
        return gender;
    }

    /**
     * The gender for the specified user.
     * 
     */
    @JsonProperty("gender")
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Demographic withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * If the person is part of a household of more than 1 person.
     * 
     */
    @JsonProperty("household")
    public Boolean getHousehold() {
        return household;
    }

    /**
     * If the person is part of a household of more than 1 person.
     * 
     */
    @JsonProperty("household")
    public void setHousehold(Boolean household) {
        this.household = household;
    }

    public Demographic withHousehold(Boolean household) {
        this.household = household;
        return this;
    }

    /**
     * If the person has declared they are in a relationship.
     * 
     */
    @JsonProperty("relationship_status")
    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    /**
     * If the person has declared they are in a relationship.
     * 
     */
    @JsonProperty("relationship_status")
    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public Demographic withRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
        return this;
    }

    /**
     * If the person has parental responsibilities or duties.
     * 
     */
    @JsonProperty("parental_status")
    public ParentalStatus getParentalStatus() {
        return parentalStatus;
    }

    /**
     * If the person has parental responsibilities or duties.
     * 
     */
    @JsonProperty("parental_status")
    public void setParentalStatus(ParentalStatus parentalStatus) {
        this.parentalStatus = parentalStatus;
    }

    public Demographic withParentalStatus(ParentalStatus parentalStatus) {
        this.parentalStatus = parentalStatus;
        return this;
    }


    /**
     * The age range for this user particular user.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Age {

        _0_TO_14("_0_to_14"),
        _15_TO_19("_15_to_19"),
        _20_TO_26("_20_to_26"),
        _27_TO_34("_27_to_34"),
        _35_TO_40("_35_to_40"),
        _41_TO_60("_41_to_60"),
        _61_PLUS("_61_plus");
        private final String value;
        private final static Map<String, Demographic.Age> CONSTANTS = new HashMap<String, Demographic.Age>();

        static {
            for (Demographic.Age c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Age(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Demographic.Age fromValue(String value) {
            Demographic.Age constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The income range for the user.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Income {

        NONE("none"),
        LOW("low"),
        MEDIUM("medium"),
        HIGH("high"),
        VERY_HIGH("very-high");
        private final String value;
        private final static Map<String, Demographic.Income> CONSTANTS = new HashMap<String, Demographic.Income>();

        static {
            for (Demographic.Income c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Income(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Demographic.Income fromValue(String value) {
            Demographic.Income constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
