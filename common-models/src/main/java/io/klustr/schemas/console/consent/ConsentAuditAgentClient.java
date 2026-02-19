
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentAuditAgentClient
 * <p>
 * The client that the user is using.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "short_name",
    "version",
    "engine",
    "engine_version",
    "family"
})
@Generated("jsonschema2pojo")
public class ConsentAuditAgentClient {

    /**
     * The client name
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The client name")
    private String name;
    /**
     * The short name
     * 
     */
    @JsonProperty("short_name")
    @JsonPropertyDescription("The short name")
    private String shortName;
    /**
     * The version name
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("The version name")
    private String version;
    /**
     * The engine of this agent
     * 
     */
    @JsonProperty("engine")
    @JsonPropertyDescription("The engine of this agent")
    private String engine;
    /**
     * The engine version
     * 
     */
    @JsonProperty("engine_version")
    @JsonPropertyDescription("The engine version")
    private String engineVersion;
    /**
     * The engine family
     * 
     */
    @JsonProperty("family")
    @JsonPropertyDescription("The engine family")
    private String family;

    /**
     * The client name
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The client name
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ConsentAuditAgentClient withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The short name
     * 
     */
    @JsonProperty("short_name")
    public String getShortName() {
        return shortName;
    }

    /**
     * The short name
     * 
     */
    @JsonProperty("short_name")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ConsentAuditAgentClient withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    /**
     * The version name
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * The version name
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public ConsentAuditAgentClient withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * The engine of this agent
     * 
     */
    @JsonProperty("engine")
    public String getEngine() {
        return engine;
    }

    /**
     * The engine of this agent
     * 
     */
    @JsonProperty("engine")
    public void setEngine(String engine) {
        this.engine = engine;
    }

    public ConsentAuditAgentClient withEngine(String engine) {
        this.engine = engine;
        return this;
    }

    /**
     * The engine version
     * 
     */
    @JsonProperty("engine_version")
    public String getEngineVersion() {
        return engineVersion;
    }

    /**
     * The engine version
     * 
     */
    @JsonProperty("engine_version")
    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public ConsentAuditAgentClient withEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
        return this;
    }

    /**
     * The engine family
     * 
     */
    @JsonProperty("family")
    public String getFamily() {
        return family;
    }

    /**
     * The engine family
     * 
     */
    @JsonProperty("family")
    public void setFamily(String family) {
        this.family = family;
    }

    public ConsentAuditAgentClient withFamily(String family) {
        this.family = family;
        return this;
    }

}
