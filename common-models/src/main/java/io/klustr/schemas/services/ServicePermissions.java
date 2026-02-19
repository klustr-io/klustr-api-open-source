
package io.klustr.schemas.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ServicePermissions
 * <p>
 * The permissions required to be able to access this API catalog and to see it
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "viewers",
    "owners",
    "editors"
})
@Generated("jsonschema2pojo")
public class ServicePermissions {

    /**
     * The viewers of this API and who can see that it exists
     * 
     */
    @JsonProperty("viewers")
    @JsonPropertyDescription("The viewers of this API and who can see that it exists")
    private List<String> viewers = new ArrayList<String>();
    /**
     * The owners of this API and who can modify it or decide to delete it
     * 
     */
    @JsonProperty("owners")
    @JsonPropertyDescription("The owners of this API and who can modify it or decide to delete it")
    private List<String> owners = new ArrayList<String>();
    /**
     * The editors of this API who cant delete it but change things like versions etc.
     * 
     */
    @JsonProperty("editors")
    @JsonPropertyDescription("The editors of this API who cant delete it but change things like versions etc.")
    private List<String> editors = new ArrayList<String>();

    /**
     * The viewers of this API and who can see that it exists
     * 
     */
    @JsonProperty("viewers")
    public List<String> getViewers() {
        return viewers;
    }

    /**
     * The viewers of this API and who can see that it exists
     * 
     */
    @JsonProperty("viewers")
    public void setViewers(List<String> viewers) {
        this.viewers = viewers;
    }

    public ServicePermissions withViewers(List<String> viewers) {
        this.viewers = viewers;
        return this;
    }

    /**
     * The owners of this API and who can modify it or decide to delete it
     * 
     */
    @JsonProperty("owners")
    public List<String> getOwners() {
        return owners;
    }

    /**
     * The owners of this API and who can modify it or decide to delete it
     * 
     */
    @JsonProperty("owners")
    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public ServicePermissions withOwners(List<String> owners) {
        this.owners = owners;
        return this;
    }

    /**
     * The editors of this API who cant delete it but change things like versions etc.
     * 
     */
    @JsonProperty("editors")
    public List<String> getEditors() {
        return editors;
    }

    /**
     * The editors of this API who cant delete it but change things like versions etc.
     * 
     */
    @JsonProperty("editors")
    public void setEditors(List<String> editors) {
        this.editors = editors;
    }

    public ServicePermissions withEditors(List<String> editors) {
        this.editors = editors;
        return this;
    }

}
