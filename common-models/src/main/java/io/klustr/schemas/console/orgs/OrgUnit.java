
package io.klustr.schemas.console.orgs;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.joda.time.DateTime;


/**
 * OrgUnit
 * <p>
 * An organizational unit or bundle.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "children",
    "name",
    "creation_date",
    "modified_date"
})
@Generated("jsonschema2pojo")
public class OrgUnit {

    /**
     * The unique ID of this entity
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this entity")
    private String id;
    /**
     * The child departments for this org
     * 
     */
    @JsonProperty("children")
    @JsonPropertyDescription("The child departments for this org")
    private List<OrgUnit> children = new ArrayList<OrgUnit>();
    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("A friendly description for this billing account.")
    private String name;
    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this billing info was created.")
    private DateTime creationDate;
    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    @JsonPropertyDescription("The date this billing info was modified.")
    private DateTime modifiedDate;

    /**
     * The unique ID of this entity
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this entity
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public OrgUnit withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The child departments for this org
     * 
     */
    @JsonProperty("children")
    public List<OrgUnit> getChildren() {
        return children;
    }

    /**
     * The child departments for this org
     * 
     */
    @JsonProperty("children")
    public void setChildren(List<OrgUnit> children) {
        this.children = children;
    }

    public OrgUnit withChildren(List<OrgUnit> children) {
        this.children = children;
        return this;
    }

    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * A friendly description for this billing account.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public OrgUnit withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this billing info was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OrgUnit withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * The date this billing info was modified.
     * 
     */
    @JsonProperty("modified_date")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public OrgUnit withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

}
