
package io.klustr.schemas.console.consent.types;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.klustr.schemas.console.consent.ConsentAuditAgent;
import io.klustr.schemas.console.consent.UserConsentScope;
import org.joda.time.DateTime;


/**
 * UserApplicationConsent
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "org_id",
    "project_id",
    "app_id",
    "subject_id",
    "client_id",
    "session_id",
    "timestamp",
    "last_seen_date",
    "first_seen_date",
    "impressions",
    "scopes",
    "agent",
    "ip"
})
@Generated("jsonschema2pojo")
public class UserApplicationConsent {

    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this specific consent.")
    private String id;
    /**
     * The organization that this application is under..
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that this application is under..")
    private String orgId;
    /**
     * The project identifier for this consent.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project identifier for this consent.")
    private String projectId;
    /**
     * The unique application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The unique application identifier for this consent.")
    private String appId;
    /**
     * The subject identifier for this consent.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The subject identifier for this consent.")
    private String subjectId;
    /**
     * The client he user interacted with when giving consent
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The client he user interacted with when giving consent")
    private String clientId;
    /**
     * The session associated with this consent.
     * 
     */
    @JsonProperty("session_id")
    @JsonPropertyDescription("The session associated with this consent.")
    private String sessionId;
    @JsonProperty("timestamp")
    private DateTime timestamp;
    @JsonProperty("last_seen_date")
    private DateTime lastSeenDate;
    @JsonProperty("first_seen_date")
    private DateTime firstSeenDate;
    @JsonProperty("impressions")
    private Integer impressions;
    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes that were approved by the user.")
    private List<UserConsentScope> scopes = new ArrayList<UserConsentScope>();
    /**
     * ConsentAuditAgent
     * <p>
     * The device that was used to initiate this request. See https://github.com/ua-parser/uap-java OR https://github.com/k143408/user-agent-parser-spring-boot-3
     * 
     */
    @JsonProperty("agent")
    @JsonPropertyDescription("The device that was used to initiate this request. See https://github.com/ua-parser/uap-java OR https://github.com/k143408/user-agent-parser-spring-boot-3")
    private ConsentAuditAgent agent;
    /**
     * The raw IP making this request.
     * 
     */
    @JsonProperty("ip")
    @JsonPropertyDescription("The raw IP making this request.")
    private String ip;

    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this specific consent.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public UserApplicationConsent withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The organization that this application is under..
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that this application is under..
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public UserApplicationConsent withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The project identifier for this consent.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project identifier for this consent.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public UserApplicationConsent withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The unique application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The unique application identifier for this consent.
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public UserApplicationConsent withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The subject identifier for this consent.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The subject identifier for this consent.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public UserApplicationConsent withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The client he user interacted with when giving consent
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The client he user interacted with when giving consent
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public UserApplicationConsent withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The session associated with this consent.
     * 
     */
    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    /**
     * The session associated with this consent.
     * 
     */
    @JsonProperty("session_id")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserApplicationConsent withSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UserApplicationConsent withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("last_seen_date")
    public DateTime getLastSeenDate() {
        return lastSeenDate;
    }

    @JsonProperty("last_seen_date")
    public void setLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public UserApplicationConsent withLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
        return this;
    }

    @JsonProperty("first_seen_date")
    public DateTime getFirstSeenDate() {
        return firstSeenDate;
    }

    @JsonProperty("first_seen_date")
    public void setFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
    }

    public UserApplicationConsent withFirstSeenDate(DateTime firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
        return this;
    }

    @JsonProperty("impressions")
    public Integer getImpressions() {
        return impressions;
    }

    @JsonProperty("impressions")
    public void setImpressions(Integer impressions) {
        this.impressions = impressions;
    }

    public UserApplicationConsent withImpressions(Integer impressions) {
        this.impressions = impressions;
        return this;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public List<UserConsentScope> getScopes() {
        return scopes;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
    }

    public UserApplicationConsent withScopes(List<UserConsentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * ConsentAuditAgent
     * <p>
     * The device that was used to initiate this request. See https://github.com/ua-parser/uap-java OR https://github.com/k143408/user-agent-parser-spring-boot-3
     * 
     */
    @JsonProperty("agent")
    public ConsentAuditAgent getAgent() {
        return agent;
    }

    /**
     * ConsentAuditAgent
     * <p>
     * The device that was used to initiate this request. See https://github.com/ua-parser/uap-java OR https://github.com/k143408/user-agent-parser-spring-boot-3
     * 
     */
    @JsonProperty("agent")
    public void setAgent(ConsentAuditAgent agent) {
        this.agent = agent;
    }

    public UserApplicationConsent withAgent(ConsentAuditAgent agent) {
        this.agent = agent;
        return this;
    }

    /**
     * The raw IP making this request.
     * 
     */
    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    /**
     * The raw IP making this request.
     * 
     */
    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public UserApplicationConsent withIp(String ip) {
        this.ip = ip;
        return this;
    }

}
