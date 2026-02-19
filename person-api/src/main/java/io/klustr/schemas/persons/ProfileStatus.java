
package io.klustr.schemas.persons;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.persons.supervised.SupervisedStatus;


/**
 * ProfileStatus
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "supervised_status",
    "relationship_status",
    "parental_status"
})
@Generated("jsonschema2pojo")
public class ProfileStatus {

    /**
     * SupervisedStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("supervised_status")
    private SupervisedStatus supervisedStatus;
    /**
     * RelationshipStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("relationship_status")
    @JsonPropertyDescription("The relationship status for the user.")
    private RelationshipStatus relationshipStatus;
    /**
     * ParentalStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("parental_status")
    @JsonPropertyDescription("The relationship status for the user.")
    private ParentalStatus parentalStatus;

    /**
     * SupervisedStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("supervised_status")
    public SupervisedStatus getSupervisedStatus() {
        return supervisedStatus;
    }

    /**
     * SupervisedStatus
     * <p>
     * 
     * 
     */
    @JsonProperty("supervised_status")
    public void setSupervisedStatus(SupervisedStatus supervisedStatus) {
        this.supervisedStatus = supervisedStatus;
    }

    public ProfileStatus withSupervisedStatus(SupervisedStatus supervisedStatus) {
        this.supervisedStatus = supervisedStatus;
        return this;
    }

    /**
     * RelationshipStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("relationship_status")
    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    /**
     * RelationshipStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("relationship_status")
    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public ProfileStatus withRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
        return this;
    }

    /**
     * ParentalStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("parental_status")
    public ParentalStatus getParentalStatus() {
        return parentalStatus;
    }

    /**
     * ParentalStatus
     * <p>
     * The relationship status for the user.
     * 
     */
    @JsonProperty("parental_status")
    public void setParentalStatus(ParentalStatus parentalStatus) {
        this.parentalStatus = parentalStatus;
    }

    public ProfileStatus withParentalStatus(ParentalStatus parentalStatus) {
        this.parentalStatus = parentalStatus;
        return this;
    }

}
