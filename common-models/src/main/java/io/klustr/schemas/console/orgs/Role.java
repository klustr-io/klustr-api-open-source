
package io.klustr.schemas.console.orgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Role
 * <p>
 * An role within an organization.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "namespace",
    "description",
    "stage",
    "permissions"
})
@Generated("jsonschema2pojo")
public class Role {

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this organization")
    private String id;
    /**
     * Name of the role.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Name of the role.")
    private String name;
    /**
     * The namespace for this role which could be a product or service. For example AI Platform, API Gateway, etc.
     * 
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("The namespace for this role which could be a product or service. For example AI Platform, API Gateway, etc.")
    private String namespace;
    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("A short description of the role.")
    private String description;
    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("stage")
    @JsonPropertyDescription("A short description of the role.")
    private Role.Stage stage;
    /**
     * The permissions assigned to this particular role.
     * 
     */
    @JsonProperty("permissions")
    @JsonPropertyDescription("The permissions assigned to this particular role.")
    private List<RolePermission> permissions = new ArrayList<RolePermission>();

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this organization
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Role withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Name of the role.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name of the role.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Role withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The namespace for this role which could be a product or service. For example AI Platform, API Gateway, etc.
     * 
     */
    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    /**
     * The namespace for this role which could be a product or service. For example AI Platform, API Gateway, etc.
     * 
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Role withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Role withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("stage")
    public Role.Stage getStage() {
        return stage;
    }

    /**
     * A short description of the role.
     * 
     */
    @JsonProperty("stage")
    public void setStage(Role.Stage stage) {
        this.stage = stage;
    }

    public Role withStage(Role.Stage stage) {
        this.stage = stage;
        return this;
    }

    /**
     * The permissions assigned to this particular role.
     * 
     */
    @JsonProperty("permissions")
    public List<RolePermission> getPermissions() {
        return permissions;
    }

    /**
     * The permissions assigned to this particular role.
     * 
     */
    @JsonProperty("permissions")
    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public Role withPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
        return this;
    }


    /**
     * A short description of the role.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Stage {

        ALPHA("alpha"),
        BETA("beta"),
        GA("ga"),
        DISABLED("disabled"),
        DEPRECATED("deprecated");
        private final String value;
        private final static Map<String, Role.Stage> CONSTANTS = new HashMap<String, Role.Stage>();

        static {
            for (Role.Stage c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Stage(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Role.Stage fromValue(String value) {
            Role.Stage constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
