
package io.klustr.schemas.events;

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
import io.klustr.schemas.console.EntityReference;
import org.joda.time.DateTime;


/**
 * EventInviteUserRequest
 * <p>
 * Publish an event that indicates a person would like to invite a user to an organization.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "invite_id",
    "scope",
    "entity",
    "requesting_user_id",
    "requesting_user_email",
    "email",
    "creation_date",
    "expires_date",
    "status",
    "roles"
})
@Generated("jsonschema2pojo")
public class EventInviteUserRequest {

    /**
     * The unique ID for this event.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this event.")
    private String id;
    /**
     * The unique identifier for this specific invite to correlate messages against.
     * 
     */
    @JsonProperty("invite_id")
    @JsonPropertyDescription("The unique identifier for this specific invite to correlate messages against.")
    private String inviteId;
    @JsonProperty("scope")
    private EventInviteUserRequest.Scope scope;
    /**
     * The entity that the invite is sent to.
     * 
     */
    @JsonProperty("entity")
    @JsonPropertyDescription("The entity that the invite is sent to.")
    private EntityReference entity;
    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_id")
    @JsonPropertyDescription("The user who originally requested this invite.")
    private String requestingUserId;
    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_email")
    @JsonPropertyDescription("The user who originally requested this invite.")
    private String requestingUserEmail;
    /**
     * The email address of the user to invite.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email address of the user to invite.")
    private String email;
    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date of birth for this person")
    private DateTime creationDate;
    /**
     * The date this invitation expires.
     * 
     */
    @JsonProperty("expires_date")
    @JsonPropertyDescription("The date this invitation expires.")
    private DateTime expiresDate;
    /**
     * The status of this invite.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this invite.")
    private EventInviteUserRequest.Status status;
    /**
     * The roles that this user will be given when invited.
     * 
     */
    @JsonProperty("roles")
    @JsonPropertyDescription("The roles that this user will be given when invited.")
    private List<String> roles = new ArrayList<String>();

    /**
     * The unique ID for this event.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this event.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public EventInviteUserRequest withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The unique identifier for this specific invite to correlate messages against.
     * 
     */
    @JsonProperty("invite_id")
    public String getInviteId() {
        return inviteId;
    }

    /**
     * The unique identifier for this specific invite to correlate messages against.
     * 
     */
    @JsonProperty("invite_id")
    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public EventInviteUserRequest withInviteId(String inviteId) {
        this.inviteId = inviteId;
        return this;
    }

    @JsonProperty("scope")
    public EventInviteUserRequest.Scope getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(EventInviteUserRequest.Scope scope) {
        this.scope = scope;
    }

    public EventInviteUserRequest withScope(EventInviteUserRequest.Scope scope) {
        this.scope = scope;
        return this;
    }

    /**
     * The entity that the invite is sent to.
     * 
     */
    @JsonProperty("entity")
    public EntityReference getEntity() {
        return entity;
    }

    /**
     * The entity that the invite is sent to.
     * 
     */
    @JsonProperty("entity")
    public void setEntity(EntityReference entity) {
        this.entity = entity;
    }

    public EventInviteUserRequest withEntity(EntityReference entity) {
        this.entity = entity;
        return this;
    }

    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_id")
    public String getRequestingUserId() {
        return requestingUserId;
    }

    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_id")
    public void setRequestingUserId(String requestingUserId) {
        this.requestingUserId = requestingUserId;
    }

    public EventInviteUserRequest withRequestingUserId(String requestingUserId) {
        this.requestingUserId = requestingUserId;
        return this;
    }

    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_email")
    public String getRequestingUserEmail() {
        return requestingUserEmail;
    }

    /**
     * The user who originally requested this invite.
     * 
     */
    @JsonProperty("requesting_user_email")
    public void setRequestingUserEmail(String requestingUserEmail) {
        this.requestingUserEmail = requestingUserEmail;
    }

    public EventInviteUserRequest withRequestingUserEmail(String requestingUserEmail) {
        this.requestingUserEmail = requestingUserEmail;
        return this;
    }

    /**
     * The email address of the user to invite.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email address of the user to invite.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public EventInviteUserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date of birth for this person
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public EventInviteUserRequest withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this invitation expires.
     * 
     */
    @JsonProperty("expires_date")
    public DateTime getExpiresDate() {
        return expiresDate;
    }

    /**
     * The date this invitation expires.
     * 
     */
    @JsonProperty("expires_date")
    public void setExpiresDate(DateTime expiresDate) {
        this.expiresDate = expiresDate;
    }

    public EventInviteUserRequest withExpiresDate(DateTime expiresDate) {
        this.expiresDate = expiresDate;
        return this;
    }

    /**
     * The status of this invite.
     * 
     */
    @JsonProperty("status")
    public EventInviteUserRequest.Status getStatus() {
        return status;
    }

    /**
     * The status of this invite.
     * 
     */
    @JsonProperty("status")
    public void setStatus(EventInviteUserRequest.Status status) {
        this.status = status;
    }

    public EventInviteUserRequest withStatus(EventInviteUserRequest.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The roles that this user will be given when invited.
     * 
     */
    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    /**
     * The roles that this user will be given when invited.
     * 
     */
    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public EventInviteUserRequest withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    @Generated("jsonschema2pojo")
    public enum Scope {

        ORGANIZATION("organization"),
        PROJECT("project");
        private final String value;
        private final static Map<String, EventInviteUserRequest.Scope> CONSTANTS = new HashMap<String, EventInviteUserRequest.Scope>();

        static {
            for (EventInviteUserRequest.Scope c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Scope(String value) {
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
        public static EventInviteUserRequest.Scope fromValue(String value) {
            EventInviteUserRequest.Scope constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The status of this invite.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        PENDING("pending"),
        EXPIRED("expired"),
        CLAIMED("claimed"),
        REVOKED("revoked");
        private final String value;
        private final static Map<String, EventInviteUserRequest.Status> CONSTANTS = new HashMap<String, EventInviteUserRequest.Status>();

        static {
            for (EventInviteUserRequest.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
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
        public static EventInviteUserRequest.Status fromValue(String value) {
            EventInviteUserRequest.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
