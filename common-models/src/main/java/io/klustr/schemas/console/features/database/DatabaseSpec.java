
package io.klustr.schemas.console.features.database;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * DatabaseSpec
 * <p>
 * Generic database engine specification.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "engine",
    "version"
})
@Generated("jsonschema2pojo")
public class DatabaseSpec {

    /**
     * Database engine identifier.
     * (Required)
     * 
     */
    @JsonProperty("engine")
    @JsonPropertyDescription("Database engine identifier.")
    private String engine;
    /**
     * Engine version.
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("Engine version.")
    private String version;

    /**
     * Database engine identifier.
     * (Required)
     * 
     */
    @JsonProperty("engine")
    public String getEngine() {
        return engine;
    }

    /**
     * Database engine identifier.
     * (Required)
     * 
     */
    @JsonProperty("engine")
    public void setEngine(String engine) {
        this.engine = engine;
    }

    public DatabaseSpec withEngine(String engine) {
        this.engine = engine;
        return this;
    }

    /**
     * Engine version.
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * Engine version.
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public DatabaseSpec withVersion(String version) {
        this.version = version;
        return this;
    }

}
