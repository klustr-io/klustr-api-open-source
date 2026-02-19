
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * RelationshipSubset
 * <p>
 * A way to abstract and indirectly map. For example if you modeled { 'namespace': 'residents', 'object': '*', 'relation': 'viewers' } 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "namespace",
    "object",
    "subject",
    "relation"
})
@Generated("jsonschema2pojo")
public class RelationshipSubset {

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.")
    private String namespace;
    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    @JsonPropertyDescription("Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.")
    private String object;
    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject")
    @JsonPropertyDescription("In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.")
    private String subject;
    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    @JsonPropertyDescription("The relationship to the specified object forming the tie.")
    private String relation;

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public RelationshipSubset withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    public String getObject() {
        return object;
    }

    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    public void setObject(String object) {
        this.object = object;
    }

    public RelationshipSubset withObject(String object) {
        this.object = object;
        return this;
    }

    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public RelationshipSubset withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    public String getRelation() {
        return relation;
    }

    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    public void setRelation(String relation) {
        this.relation = relation;
    }

    public RelationshipSubset withRelation(String relation) {
        this.relation = relation;
        return this;
    }

}
