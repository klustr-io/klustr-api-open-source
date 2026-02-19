
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentAuditAgentOs
 * <p>
 * The OS that the user is using.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "short_name",
    "version",
    "platform",
    "family"
})
@Generated("jsonschema2pojo")
public class ConsentAuditAgentOs {

    /**
     * The name of this OS agent
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of this OS agent")
    private String name;
    /**
     * The short name for this agent
     * 
     */
    @JsonProperty("short_name")
    @JsonPropertyDescription("The short name for this agent")
    private String shortName;
    /**
     * The version of this agent
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("The version of this agent")
    private String version;
    /**
     * The platform of this agent
     * 
     */
    @JsonProperty("platform")
    @JsonPropertyDescription("The platform of this agent")
    private String platform;
    /**
     * The product family of this agent
     * 
     */
    @JsonProperty("family")
    @JsonPropertyDescription("The product family of this agent")
    private String family;

    /**
     * The name of this OS agent
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of this OS agent
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ConsentAuditAgentOs withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The short name for this agent
     * 
     */
    @JsonProperty("short_name")
    public String getShortName() {
        return shortName;
    }

    /**
     * The short name for this agent
     * 
     */
    @JsonProperty("short_name")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ConsentAuditAgentOs withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * The version of this agent
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * The version of this agent
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public ConsentAuditAgentOs withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * The platform of this agent
     * 
     */
    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    /**
     * The platform of this agent
     * 
     */
    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public ConsentAuditAgentOs withPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    /**
     * The product family of this agent
     * 
     */
    @JsonProperty("family")
    public String getFamily() {
        return family;
    }

    /**
     * The product family of this agent
     * 
     */
    @JsonProperty("family")
    public void setFamily(String family) {
        this.family = family;
    }

    public ConsentAuditAgentOs withFamily(String family) {
        this.family = family;
        return this;
    }

}
