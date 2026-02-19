
package io.klustr.schemas.integrations;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * ClientCredential
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "access_token_strategy",
    "subject",
    "total_interaction_count",
    "date_first_interaction",
    "date_last_interaction",
    "approved_scopes",
    "experiments"
})
@Generated("jsonschema2pojo")
public class ClientCredential {

    @JsonProperty("access_token_strategy")
    private String accessTokenStrategy;
    /**
     * The subject that gave this consent
     * 
     */
    @JsonProperty("subject")
    @JsonPropertyDescription("The subject that gave this consent")
    private String subject;
    /**
     * The estimated number of logins for the user and consenting to the app.
     * 
     */
    @JsonProperty("total_interaction_count")
    @JsonPropertyDescription("The estimated number of logins for the user and consenting to the app.")
    private Integer totalInteractionCount;
    /**
     * The date the user first interacted with consent for this app.
     * 
     */
    @JsonProperty("date_first_interaction")
    @JsonPropertyDescription("The date the user first interacted with consent for this app.")
    private DateTime dateFirstInteraction;
    /**
     * The date the user last interacted with consent for this app.
     * 
     */
    @JsonProperty("date_last_interaction")
    @JsonPropertyDescription("The date the user last interacted with consent for this app.")
    private DateTime dateLastInteraction;
    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("approved_scopes")
    @JsonPropertyDescription("The scopes that were approved by the user.")
    private List<String> approvedScopes = new ArrayList<String>();
    /**
     * The experiments this user has opted-into.
     * 
     */
    @JsonProperty("experiments")
    @JsonPropertyDescription("The experiments this user has opted-into.")
    private List<UserConsentExperiment> experiments = new ArrayList<UserConsentExperiment>();

    @JsonProperty("access_token_strategy")
    public String getAccessTokenStrategy() {
        return accessTokenStrategy;
    }

    @JsonProperty("access_token_strategy")
    public void setAccessTokenStrategy(String accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
    }

    public ClientCredential withAccessTokenStrategy(String accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
        return this;
    }

    /**
     * The subject that gave this consent
     * 
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * The subject that gave this consent
     * 
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ClientCredential withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * The estimated number of logins for the user and consenting to the app.
     * 
     */
    @JsonProperty("total_interaction_count")
    public Integer getTotalInteractionCount() {
        return totalInteractionCount;
    }

    /**
     * The estimated number of logins for the user and consenting to the app.
     * 
     */
    @JsonProperty("total_interaction_count")
    public void setTotalInteractionCount(Integer totalInteractionCount) {
        this.totalInteractionCount = totalInteractionCount;
    }

    public ClientCredential withTotalInteractionCount(Integer totalInteractionCount) {
        this.totalInteractionCount = totalInteractionCount;
        return this;
    }

    /**
     * The date the user first interacted with consent for this app.
     * 
     */
    @JsonProperty("date_first_interaction")
    public DateTime getDateFirstInteraction() {
        return dateFirstInteraction;
    }

    /**
     * The date the user first interacted with consent for this app.
     * 
     */
    @JsonProperty("date_first_interaction")
    public void setDateFirstInteraction(DateTime dateFirstInteraction) {
        this.dateFirstInteraction = dateFirstInteraction;
    }

    public ClientCredential withDateFirstInteraction(DateTime dateFirstInteraction) {
        this.dateFirstInteraction = dateFirstInteraction;
        return this;
    }

    /**
     * The date the user last interacted with consent for this app.
     * 
     */
    @JsonProperty("date_last_interaction")
    public DateTime getDateLastInteraction() {
        return dateLastInteraction;
    }

    /**
     * The date the user last interacted with consent for this app.
     * 
     */
    @JsonProperty("date_last_interaction")
    public void setDateLastInteraction(DateTime dateLastInteraction) {
        this.dateLastInteraction = dateLastInteraction;
    }

    public ClientCredential withDateLastInteraction(DateTime dateLastInteraction) {
        this.dateLastInteraction = dateLastInteraction;
        return this;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("approved_scopes")
    public List<String> getApprovedScopes() {
        return approvedScopes;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("approved_scopes")
    public void setApprovedScopes(List<String> approvedScopes) {
        this.approvedScopes = approvedScopes;
    }

    public ClientCredential withApprovedScopes(List<String> approvedScopes) {
        this.approvedScopes = approvedScopes;
        return this;
    }

    /**
     * The experiments this user has opted-into.
     * 
     */
    @JsonProperty("experiments")
    public List<UserConsentExperiment> getExperiments() {
        return experiments;
    }

    /**
     * The experiments this user has opted-into.
     * 
     */
    @JsonProperty("experiments")
    public void setExperiments(List<UserConsentExperiment> experiments) {
        this.experiments = experiments;
    }

    public ClientCredential withExperiments(List<UserConsentExperiment> experiments) {
        this.experiments = experiments;
        return this;
    }

}
