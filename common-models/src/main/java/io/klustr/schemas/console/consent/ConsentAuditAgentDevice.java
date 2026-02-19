
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentAuditAgentDevice
 * <p>
 * The device that the user is using.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "brand",
    "model",
    "code",
    "trusted"
})
@Generated("jsonschema2pojo")
public class ConsentAuditAgentDevice {

    /**
     * The device the user is using
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The device the user is using")
    private String id;
    /**
     * The type of device being used.
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of device being used.")
    private String type;
    /**
     * The brand of the device being used.
     * 
     */
    @JsonProperty("brand")
    @JsonPropertyDescription("The brand of the device being used.")
    private String brand;
    /**
     * The model of the device being used.
     * 
     */
    @JsonProperty("model")
    @JsonPropertyDescription("The model of the device being used.")
    private String model;
    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("code")
    @JsonPropertyDescription("The device code of the device being used.")
    private String code;
    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("trusted")
    @JsonPropertyDescription("The device code of the device being used.")
    private String trusted;

    /**
     * The device the user is using
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The device the user is using
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentAuditAgentDevice withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The type of device being used.
     * 
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * The type of device being used.
     * 
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ConsentAuditAgentDevice withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * The brand of the device being used.
     * 
     */
    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    /**
     * The brand of the device being used.
     * 
     */
    @JsonProperty("brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ConsentAuditAgentDevice withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    /**
     * The model of the device being used.
     * 
     */
    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    /**
     * The model of the device being used.
     * 
     */
    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    public ConsentAuditAgentDevice withModel(String model) {
        this.model = model;
        return this;
    }

    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public ConsentAuditAgentDevice withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("trusted")
    public String getTrusted() {
        return trusted;
    }

    /**
     * The device code of the device being used.
     * 
     */
    @JsonProperty("trusted")
    public void setTrusted(String trusted) {
        this.trusted = trusted;
    }

    public ConsentAuditAgentDevice withTrusted(String trusted) {
        this.trusted = trusted;
        return this;
    }

}
