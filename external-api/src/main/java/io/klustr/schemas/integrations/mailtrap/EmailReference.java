
package io.klustr.schemas.integrations.mailtrap;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * EmailReference
 * <p>
 * The source email.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "email"
})
@Generated("jsonschema2pojo")
public class EmailReference {

    /**
     * The name of the person to send to.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name of the person to send to.")
    private String name;
    /**
     * The email to send to.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("The email to send to.")
    private String email;

    /**
     * The name of the person to send to.
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name of the person to send to.
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public EmailReference withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The email to send to.
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * The email to send to.
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public EmailReference withEmail(String email) {
        this.email = email;
        return this;
    }

}
