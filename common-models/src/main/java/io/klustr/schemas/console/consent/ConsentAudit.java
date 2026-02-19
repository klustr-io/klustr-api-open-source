
package io.klustr.schemas.console.consent;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * ConsentAudit
 * <p>
 * A record of a specific event where a user gave or issued consent.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "subject_id",
    "org_id",
    "project_id",
    "app_id",
    "client_id",
    "session_id",
    "timestamp",
    "skipped",
    "scopes",
    "experiments",
    "agent",
    "ip"
})
@Generated("jsonschema2pojo")
public class ConsentAudit {

    /**
     * The unique ID of this audit record.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this audit record.")
    private String id;
    /**
     * The identity or subject of this consent.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("The identity or subject of this consent.")
    private String subjectId;
    /**
     * The org id that owns this consent audit
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The org id that owns this consent audit")
    private String orgId;
    /**
     * The project id that owns this client.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project id that owns this client.")
    private String projectId;
    /**
     * The application id that made this request
     * 
     */
    @JsonProperty("app_id")
    @JsonPropertyDescription("The application id that made this request")
    private String appId;
    /**
     * The client id that made this request
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The client id that made this request")
    private String clientId;
    /**
     * The session id that if the user was answering multiple requests.
     * 
     */
    @JsonProperty("session_id")
    @JsonPropertyDescription("The session id that if the user was answering multiple requests.")
    private String sessionId;
    /**
     * The timestamp of this audit record.
     * 
     */
    @JsonProperty("timestamp")
    @JsonPropertyDescription("The timestamp of this audit record.")
    private DateTime timestamp;
    /**
     * If the consent was skipped due to existing business logic.
     * 
     */
    @JsonProperty("skipped")
    @JsonPropertyDescription("If the consent was skipped due to existing business logic.")
    private Boolean skipped;
    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The scopes that were approved by the user.")
    private List<String> scopes = new ArrayList<String>();
    /**
     * The scopes requested by the application and reviewed by the user.
     * 
     */
    @JsonProperty("experiments")
    @JsonPropertyDescription("The scopes requested by the application and reviewed by the user.")
    private List<String> experiments = new ArrayList<String>();
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
     * The unique ID of this audit record.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this audit record.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentAudit withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The identity or subject of this consent.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * The identity or subject of this consent.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public ConsentAudit withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The org id that owns this consent audit
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The org id that owns this consent audit
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ConsentAudit withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The project id that owns this client.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project id that owns this client.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ConsentAudit withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The application id that made this request
     * 
     */
    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * The application id that made this request
     * 
     */
    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ConsentAudit withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * The client id that made this request
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The client id that made this request
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ConsentAudit withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The session id that if the user was answering multiple requests.
     * 
     */
    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    /**
     * The session id that if the user was answering multiple requests.
     * 
     */
    @JsonProperty("session_id")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ConsentAudit withSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * The timestamp of this audit record.
     * 
     */
    @JsonProperty("timestamp")
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * The timestamp of this audit record.
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ConsentAudit withTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * If the consent was skipped due to existing business logic.
     * 
     */
    @JsonProperty("skipped")
    public Boolean getSkipped() {
        return skipped;
    }

    /**
     * If the consent was skipped due to existing business logic.
     * 
     */
    @JsonProperty("skipped")
    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    public ConsentAudit withSkipped(Boolean skipped) {
        this.skipped = skipped;
        return this;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * The scopes that were approved by the user.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public ConsentAudit withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * The scopes requested by the application and reviewed by the user.
     * 
     */
    @JsonProperty("experiments")
    public List<String> getExperiments() {
        return experiments;
    }

    /**
     * The scopes requested by the application and reviewed by the user.
     * 
     */
    @JsonProperty("experiments")
    public void setExperiments(List<String> experiments) {
        this.experiments = experiments;
    }

    public ConsentAudit withExperiments(List<String> experiments) {
        this.experiments = experiments;
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

    public ConsentAudit withAgent(ConsentAuditAgent agent) {
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

    public ConsentAudit withIp(String ip) {
        this.ip = ip;
        return this;
    }

}
