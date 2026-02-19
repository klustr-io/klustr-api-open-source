
package io.klustr.schemas.console.consent;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ConsentScopeMetrics
 * <p>
 * The insights and metrics associated with a consent scope.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "apps",
    "clients",
    "users"
})
@Generated("jsonschema2pojo")
public class ConsentScopeMetrics {

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this consent scope.")
    private String id;
    /**
     * The total applications that have consumed this scope.
     * 
     */
    @JsonProperty("apps")
    @JsonPropertyDescription("The total applications that have consumed this scope.")
    private Integer apps;
    /**
     * The total clients that have consumed this scope.
     * 
     */
    @JsonProperty("clients")
    @JsonPropertyDescription("The total clients that have consumed this scope.")
    private Integer clients;
    /**
     * The total users that have consumed this scope
     * 
     */
    @JsonProperty("users")
    @JsonPropertyDescription("The total users that have consumed this scope")
    private Integer users;

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentScopeMetrics withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The total applications that have consumed this scope.
     * 
     */
    @JsonProperty("apps")
    public Integer getApps() {
        return apps;
    }

    /**
     * The total applications that have consumed this scope.
     * 
     */
    @JsonProperty("apps")
    public void setApps(Integer apps) {
        this.apps = apps;
    }

    public ConsentScopeMetrics withApps(Integer apps) {
        this.apps = apps;
        return this;
    }

    /**
     * The total clients that have consumed this scope.
     * 
     */
    @JsonProperty("clients")
    public Integer getClients() {
        return clients;
    }

    /**
     * The total clients that have consumed this scope.
     * 
     */
    @JsonProperty("clients")
    public void setClients(Integer clients) {
        this.clients = clients;
    }

    public ConsentScopeMetrics withClients(Integer clients) {
        this.clients = clients;
        return this;
    }

    /**
     * The total users that have consumed this scope
     * 
     */
    @JsonProperty("users")
    public Integer getUsers() {
        return users;
    }

    /**
     * The total users that have consumed this scope
     * 
     */
    @JsonProperty("users")
    public void setUsers(Integer users) {
        this.users = users;
    }

    public ConsentScopeMetrics withUsers(Integer users) {
        this.users = users;
        return this;
    }

}
